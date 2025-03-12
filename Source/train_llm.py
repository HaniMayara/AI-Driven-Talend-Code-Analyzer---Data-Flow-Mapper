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
        model_name: str = "codellama/CodeLlama-7b-hf",  # Default to CodeLlama
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
            'per_device_train_batch_size': 4,
            'per_device_eval_batch_size': 4,
            'warmup_steps': 500,
            'weight_decay': 0.01,
            'logging_dir': './logs',
        }
        
        if not use_openai:
            self.load_model()

    def load_model(self) -> None:
        """Load the selected model and tokenizer."""
        try:
            logging.info(f"Loading model: {self.model_name}")
            self.tokenizer = AutoTokenizer.from_pretrained(self.model_name)
            self.model = AutoModelForCausalLM.from_pretrained(
                self.model_name,
                torch_dtype=torch.float16 if torch.cuda.is_available() else torch.float32
            )
            logging.info("Model loaded successfully")
        except Exception as e:
            logging.error(f"Error loading model: {str(e)}")
            raise

    def prepare_dataset(
        self,
        annotations_dir: str,
        test_size: float = 0.2
    ) -> Tuple[Dataset, Dataset]:
        """Prepare training and validation datasets from annotations.

        Args:
            annotations_dir: Directory containing JSON annotation files
            test_size: Proportion of data to use for validation

        Returns:
            Tuple of (training_dataset, validation_dataset)
        """
        try:
            data = []
            for file in Path(annotations_dir).glob('*.json'):
                with open(file, 'r', encoding='utf-8') as f:
                    annotation = json.load(f)
                    
                # Create prompt from annotation metadata
                prompt = f"""Extract metadata from the following Talend job:
                Job Name: {annotation.get('job_name', '')}
                Version: {annotation.get('job_version', '')}
                Author: {annotation.get('author', '')}
                """
                
                # Format the response as JSON string
                response = json.dumps({
                    'input_components': annotation.get('input_components', []),
                    'transformation_steps': annotation.get('transformation_steps', []),
                    'output_components': annotation.get('output_components', [])
                }, indent=2)
                
                data.append({
                    'prompt': prompt,
                    'response': response
                })

            # Split into train and validation sets
            train_data, val_data = train_test_split(data, test_size=test_size)
            
            # Convert to HuggingFace datasets
            train_dataset = Dataset.from_dict({
                'prompt': [d['prompt'] for d in train_data],
                'response': [d['response'] for d in train_data]
            })
            val_dataset = Dataset.from_dict({
                'prompt': [d['prompt'] for d in val_data],
                'response': [d['response'] for d in val_data]
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