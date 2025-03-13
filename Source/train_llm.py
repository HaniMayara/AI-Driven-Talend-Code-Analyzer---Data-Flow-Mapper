import os
import json
import logging
from typing import Dict, List, Optional, Tuple
from pathlib import Path
import torch
from transformers import (
    AutoModelForCausalLM,
    AutoTokenizer,
    Trainer,
    TrainingArguments,
    DataCollatorForLanguageModeling
)
from datasets import Dataset
from sklearn.model_selection import train_test_split

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler('training.log'),
        logging.StreamHandler()
    ]
)

class TalendMetadataExtractor:
    def __init__(
        self,
        model_name: str = "gpt2",  # Using a smaller model for testing
        use_openai: bool = False,
        training_args: Optional[Dict] = None
    ):
        """Initialize the metadata extractor with model selection and training parameters.

        Args:
            model_name: Name of the model to use (HuggingFace model ID)
            use_openai: Whether to use OpenAI's API instead of local model
            training_args: Custom training arguments
        """
        self.use_openai = use_openai
        self.model_name = model_name
        self.training_args = training_args or {
            'output_dir': 'results',
            'num_train_epochs': 3,
            'per_device_train_batch_size': 2,  # Reduced batch size
            'per_device_eval_batch_size': 2,   # Reduced batch size
            'warmup_steps': 100,               # Reduced warmup steps
            'weight_decay': 0.01,
            'logging_dir': './logs',
            'fp16': torch.cuda.is_available(),  # Enable mixed precision if GPU available
            'gradient_accumulation_steps': 4    # Add gradient accumulation
        }
        
        if not use_openai:
            try:
                self.load_model()
            except Exception as e:
                logging.error(f"Failed to load model: {str(e)}")
                raise

    def load_model(self) -> None:
        """Load the selected model and tokenizer."""
        try:
            logging.info(f"Loading model: {self.model_name}")
            self.tokenizer = AutoTokenizer.from_pretrained(self.model_name)
            # Set up padding token
            if self.tokenizer.pad_token is None:
                self.tokenizer.pad_token = self.tokenizer.eos_token
                self.tokenizer.pad_token_id = self.tokenizer.eos_token_id
            
            self.model = AutoModelForCausalLM.from_pretrained(
                self.model_name,
                torch_dtype=torch.float16 if torch.cuda.is_available() else torch.float32
            )
            # Ensure the model knows about the padding token
            self.model.config.pad_token_id = self.tokenizer.pad_token_id
            logging.info("Model loaded successfully")
        except Exception as e:
            logging.error(f"Error loading model: {str(e)}")
            raise

    def prepare_dataset(
        self,
        annotations_dir: str,
        test_size: float = 0.2
    ) -> Tuple[Dataset, Dataset]:
        try:
            data = []
            for file in Path(annotations_dir).glob('*.json'):
                with open(file, 'r', encoding='utf-8') as f:
                    annotation = json.load(f)
                    
                # Create prompt from annotation metadata
                prompt = f"""Extract metadata from the following Talend job:\nJob Name: {annotation.get('job_name', '')}\nVersion: {annotation.get('job_version', '')}\nAuthor: {annotation.get('author', '')}"""
                
                # Format the response as JSON string
                response = json.dumps({
                    'input_components': annotation.get('input_components', []),
                    'transformation_steps': annotation.get('transformation_steps', []),
                    'output_components': annotation.get('output_components', [])
                })
                
                # Tokenize the input
                inputs = self.tokenizer(prompt, truncation=True, padding='max_length', max_length=512)
                labels = self.tokenizer(response, truncation=True, padding='max_length', max_length=512)
                
                data.append({
                    'input_ids': inputs['input_ids'],
                    'attention_mask': inputs['attention_mask'],
                    'labels': labels['input_ids']
                })

            # Split into train and validation sets
            train_data, val_data = train_test_split(data, test_size=test_size)
            
            # Convert to HuggingFace datasets
            train_dataset = Dataset.from_dict({
                'input_ids': [d['input_ids'] for d in train_data],
                'attention_mask': [d['attention_mask'] for d in train_data],
                'labels': [d['labels'] for d in train_data]
            })
            val_dataset = Dataset.from_dict({
                'input_ids': [d['input_ids'] for d in val_data],
                'attention_mask': [d['attention_mask'] for d in val_data],
                'labels': [d['labels'] for d in val_data]
            })
            
            logging.info(f"Prepared {len(train_data)} training and {len(val_data)} validation examples")
            return train_dataset, val_dataset
            
        except Exception as e:
            logging.error(f"Error preparing dataset: {str(e)}")
            raise

    def train_model(
        self,
        train_dataset: Dataset,
        val_dataset: Dataset
    ) -> None:
        """Fine-tune the model on the prepared datasets.

        Args:
            train_dataset: Training dataset
            val_dataset: Validation dataset
        """
        if self.use_openai:
            logging.warning("Training not supported for OpenAI API")
            return

        try:
            # Set up training arguments
            training_args = TrainingArguments(**self.training_args)

            # Initialize trainer
            trainer = Trainer(
                model=self.model,
                args=training_args,
                train_dataset=train_dataset,
                eval_dataset=val_dataset,
                data_collator=DataCollatorForLanguageModeling(self.tokenizer, mlm=False)
            )

            # Start training
            logging.info("Starting model training")
            trainer.train()
            
            # Save the fine-tuned model
            trainer.save_model()
            logging.info(f"Model saved to {self.training_args['output_dir']}")

        except Exception as e:
            logging.error(f"Error during training: {str(e)}")
            raise

    def evaluate_model(self, val_dataset: Dataset) -> Dict:
        """Evaluate the model on validation dataset.

        Args:
            val_dataset: Validation dataset

        Returns:
            Dictionary containing evaluation metrics
        """
        try:
            if self.use_openai:
                logging.warning("Evaluation not supported for OpenAI API")
                return {}

            trainer = Trainer(
                model=self.model,
                args=TrainingArguments(**self.training_args),
                eval_dataset=val_dataset,
            )

            metrics = trainer.evaluate()
            logging.info(f"Evaluation metrics: {metrics}")
            return metrics

        except Exception as e:
            logging.error(f"Error during evaluation: {str(e)}")
            raise

def main():
    # Initialize extractor
    extractor = TalendMetadataExtractor()

    # Prepare datasets
    train_dataset, val_dataset = extractor.prepare_dataset(
        "training_data/annotations"
    )

    # Train model
    extractor.train_model(train_dataset, val_dataset)

    # Evaluate model
    metrics = extractor.evaluate_model(val_dataset)

if __name__ == "__main__":
    main()