#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Train LLM Model for Java Code to Structured Output Generation

This script trains an LLM model to generate structured outputs (annotations, flowcharts, mappings)
from Java code. It loads Java files and their corresponding metadata, preprocesses the code,
trains the model, and evaluates its performance.
"""

import os
import json
import glob
import re
import logging
import numpy as np
import pandas as pd
from typing import Dict, List, Tuple, Any, Optional
from pathlib import Path
from sklearn.model_selection import train_test_split
from transformers import (
    AutoTokenizer, 
    AutoModelForSeq2SeqLM,
    Seq2SeqTrainingArguments,
    Seq2SeqTrainer,
    DataCollatorForSeq2Seq
)
import torch
from datasets import Dataset

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler("training.log"),
        logging.StreamHandler()
    ]
)
logger = logging.getLogger(__name__)

class JavaToStructuredOutputTrainer:
    """Class for training an LLM model to generate structured outputs from Java code."""
    
    def __init__(self, base_dir: str, model_name: str = "t5-base", output_dir: str = "./model_output"):
        """
        Initialize the trainer.
        
        Args:
            base_dir: Base directory containing training data
            model_name: Name of the pre-trained model to use
            output_dir: Directory to save the trained model
        """
        self.base_dir = Path(base_dir)
        self.model_name = model_name
        self.output_dir = Path(output_dir)
        self.output_dir.mkdir(exist_ok=True, parents=True)
        
        # Paths to data directories
        self.java_dir = self.base_dir / "java_jobs"
        self.annotations_dir = self.base_dir / "annotations"
        self.flowcharts_dir = self.base_dir / "flowcharts"
        self.mappings_dir = self.base_dir / "mappings"
        
        # Initialize tokenizer and model
        self.tokenizer = AutoTokenizer.from_pretrained(model_name)
        self.model = AutoModelForSeq2SeqLM.from_pretrained(model_name)
        
        # Training datasets
        self.train_dataset = None
        self.eval_dataset = None
        
        logger.info(f"Initialized trainer with model: {model_name}")
    
    def load_data(self) -> List[Dict[str, Any]]:
        """Load Java files and their corresponding metadata."""
        logger.info("Loading data...")
        
        data_pairs = []
        
        # Get all Java files
        java_files = list(self.java_dir.glob("*.java"))
        logger.info(f"Found {len(java_files)} Java files")
        
        for java_file in java_files:
            base_name = java_file.stem
            
            # Find corresponding metadata files
            annotation_file = self.annotations_dir / f"{base_name}.json"
            flowchart_file = self.flowcharts_dir / f"flowchart_{base_name}.json"
            mapping_file = self.mappings_dir / f"mapping_{base_name}.json"
            
            if not annotation_file.exists():
                logger.warning(f"No annotation file found for {base_name}")
                continue
                
            # Load Java code
            with open(java_file, 'r', encoding='utf-8') as f:
                java_code = f.read()
            
            # Load annotation
            with open(annotation_file, 'r', encoding='utf-8') as f:
                annotation = json.load(f)
            
            # Load flowchart if exists
            flowchart = None
            if flowchart_file.exists():
                with open(flowchart_file, 'r', encoding='utf-8') as f:
                    flowchart = json.load(f)
            
            # Load mapping if exists
            mapping = None
            if mapping_file.exists():
                with open(mapping_file, 'r', encoding='utf-8') as f:
                    mapping = json.load(f)
            
            # Add to data pairs
            data_pairs.append({
                'java_code': java_code,
                'annotation': annotation,
                'flowchart': flowchart,
                'mapping': mapping,
                'base_name': base_name
            })
        
        logger.info(f"Loaded {len(data_pairs)} complete data pairs")
        return data_pairs
    
    def preprocess_java_code(self, code: str) -> str:
        """Preprocess Java code by cleaning and segmenting key logic blocks."""
        # Remove comments
        code = re.sub(r'//.*?\n|/\*.*?\*/', '', code, flags=re.DOTALL)
        
        # Remove empty lines
        code = re.sub(r'\n\s*\n', '\n', code)
        
        # Extract key components (classes, methods, fields)
        # This is a simplified approach - a more sophisticated parser would be better
        classes = re.findall(r'class\s+\w+\s*(?:extends|implements)?\s*[\w,\s]*\{', code)
        methods = re.findall(r'(?:public|private|protected)\s+(?:static)?\s*[\w<>\[\],\s]+\s+\w+\s*\([^)]*\)\s*(?:throws\s+[\w,\s]+)?\s*\{', code)
        
        # Identify data structures
        structs = re.findall(r'(?:public|private)\s+(?:static)?\s*class\s+\w+\s*(?:implements|extends)?\s*[\w,\s]*\{', code)
        
        # Extract field declarations
        fields = re.findall(r'(?:public|private|protected)\s+(?:static|final)?\s*[\w<>\[\],\s]+\s+\w+\s*=?[^;]*;', code)
        
        # Create a simplified representation
        preprocessed = "\n\n".join([
            "CLASSES:\n" + "\n".join(classes),
            "METHODS:\n" + "\n".join(methods[:10]),  # Limit to first 10 methods for brevity
            "STRUCTS:\n" + "\n".join(structs),
            "FIELDS:\n" + "\n".join(fields[:20])  # Limit to first 20 fields for brevity
        ])
        
        return preprocessed
    
    def prepare_datasets(self, data_pairs: List[Dict[str, Any]], test_size: float = 0.2):
        """Prepare datasets for training and evaluation."""
        logger.info("Preparing datasets...")
        
        # Prepare data for annotation generation task
        annotation_data = []
        for pair in data_pairs:
            # Preprocess Java code
            preprocessed_code = self.preprocess_java_code(pair['java_code'])
            
            # Prepare annotation as target
            annotation_json = json.dumps(pair['annotation'])
            
            annotation_data.append({
                'input_text': f"Generate annotation for Java code: {preprocessed_code}",
                'target_text': f"Annotation: {annotation_json}"
            })
        
        # Split into train and evaluation sets
        train_data, eval_data = train_test_split(annotation_data, test_size=test_size, random_state=42)
        
        # Convert to HuggingFace datasets
        self.train_dataset = Dataset.from_pandas(pd.DataFrame(train_data))
        self.eval_dataset = Dataset.from_pandas(pd.DataFrame(eval_data))
        
        logger.info(f"Prepared {len(self.train_dataset)} training samples and {len(self.eval_dataset)} evaluation samples")
    
    def tokenize_function(self, examples):
        """Tokenize the input and target texts."""
        model_inputs = self.tokenizer(examples['input_text'], max_length=1024, truncation=True, padding="max_length")
        
        # Setup the tokenizer for targets
        with self.tokenizer.as_target_tokenizer():
            labels = self.tokenizer(examples['target_text'], max_length=1024, truncation=True, padding="max_length")
            
        model_inputs["labels"] = labels["input_ids"]
        return model_inputs
    
    def train(self, batch_size: int = 4, num_epochs: int = 3, learning_rate: float = 5e-5):
        """Train the model."""
        logger.info("Starting training...")
        
        # Tokenize datasets
        tokenized_train_dataset = self.train_dataset.map(self.tokenize_function, batched=True)
        tokenized_eval_dataset = self.eval_dataset.map(self.tokenize_function, batched=True)
        
        # Data collator
        data_collator = DataCollatorForSeq2Seq(
            tokenizer=self.tokenizer,
            model=self.model,
            padding=True
        )
        
        # Training arguments
        training_args = Seq2SeqTrainingArguments(
            output_dir=str(self.output_dir),
            evaluation_strategy="epoch",
            learning_rate=learning_rate,
            per_device_train_batch_size=batch_size,
            per_device_eval_batch_size=batch_size,
            weight_decay=0.01,
            save_total_limit=3,
            num_train_epochs=num_epochs,
            predict_with_generate=True,
            logging_dir=str(self.output_dir / "logs"),
            logging_steps=100,
            save_strategy="epoch"
        )
        
        # Initialize trainer
        trainer = Seq2SeqTrainer(
            model=self.model,
            args=training_args,
            train_dataset=tokenized_train_dataset,
            eval_dataset=tokenized_eval_dataset,
            tokenizer=self.tokenizer,
            data_collator=data_collator
        )
        
        # Train the model
        trainer.train()
        
        # Save the model
        self.model.save_pretrained(self.output_dir / "final_model")
        self.tokenizer.save_pretrained(self.output_dir / "final_model")
        
        logger.info("Training completed successfully")
        
        # Evaluate the model
        eval_results = trainer.evaluate()
        logger.info(f"Evaluation results: {eval_results}")
        
        return eval_results
    
    def generate_annotation(self, java_code: str) -> Dict[str, Any]:
        """Generate annotation for a given Java code."""
        preprocessed_code = self.preprocess_java_code(java_code)
        input_text = f"Generate annotation for Java code: {preprocessed_code}"
        
        inputs = self.tokenizer(input_text, return_tensors="pt", max_length=1024, truncation=True)
        inputs = inputs.to(self.model.device)
        
        outputs = self.model.generate(
            inputs["input_ids"],
            max_length=1024,
            num_beams=4,
            early_stopping=True
        )
        
        decoded_output = self.tokenizer.decode(outputs[0], skip_special_tokens=True)
        
        # Extract the JSON part
        annotation_json = decoded_output.replace("Annotation: ", "")
        
        # Post-process the output to ensure valid JSON
        return self.post_process_output(annotation_json)
    
    def post_process_output(self, text: str) -> Dict[str, Any]:
        """Post-process model output to ensure valid JSON."""
        # Try direct parsing first
        try:
            return json.loads(text)
        except json.JSONDecodeError:
            logger.info("Direct JSON parsing failed, attempting post-processing")
            
        # If the output contains Java code snippets, try to extract structured information
        result = {
            "job_name": "",
            "description": "Automatically extracted from Java code structure",
            "input_components": [],
            "output_components": [],
            "processing_logic": {}
        }
        
        # Extract class names as potential job names
        class_matches = re.findall(r'class\s+(\w+)', text)
        if class_matches:
            result["job_name"] = class_matches[0]
        
        # Look for implements/extends relationships
        implements_matches = re.findall(r'class\s+\w+\s+implements\s+([\w,\s]+)', text)
        extends_matches = re.findall(r'class\s+\w+\s+extends\s+([\w,\s]+)', text)
        
        if implements_matches:
            result["description"] += f". Implements: {implements_matches[0]}"
        if extends_matches:
            result["description"] += f". Extends: {extends_matches[0]}"
            
        # Extract potential component information
        if "STRUCTS:" in text:
            # Try to extract structs section
            structs_section = text.split("STRUCTS:")[1].split("FIELDS:")[0] if "FIELDS:" in text else text.split("STRUCTS:")[1]
            
            # Process structs section
            struct_matches = re.findall(r'(?:public|private)\s+(?:static)?\s*class\s+(\w+)', structs_section)
            
            # Add structs as components
            for i, struct in enumerate(struct_matches):
                if i == 0 and struct == result["job_name"]:  # Skip if it's the main job class
                    continue
                    
                component_type = "input"
                if any(output_keyword in struct.lower() for output_keyword in ["output", "result", "target", "destination"]):
                    component_type = "output"
                elif any(input_keyword in struct.lower() for input_keyword in ["input", "source", "data"]):
                    component_type = "input"
                else:
                    # If can't determine from name, alternate between input and output
                    component_type = "output" if i % 2 == 0 else "input"
                
                component = {
                    "name": struct,
                    "type": component_type,
                    "data_structure": {
                        "type": "class",
                        "fields": []
                    }
                }
                
                if component_type == "input":
                    result["input_components"].append(component)
                else:
                    result["output_components"].append(component)
        
        # Extract fields from the entire text
        field_matches = re.findall(r'(?:public|private|protected)\s+(?:static|final)?\s*([\w<>\[\],\s]+)\s+(\w+)\s*=?[^;]*;', text)
        
        # Create a default component if none were found
        if not result["input_components"] and not result["output_components"] and field_matches:
            input_component = {
                "name": "DefaultInputComponent",
                "type": "input",
                "data_structure": {
                    "type": "class",
                    "fields": []
                }
            }
            result["input_components"].append(input_component)
            
            output_component = {
                "name": "DefaultOutputComponent",
                "type": "output",
                "data_structure": {
                    "type": "class",
                    "fields": []
                }
            }
            result["output_components"].append(output_component)
        
        # Add fields to components
        for field_type, field_name in field_matches:
            field = {
                "name": field_name,
                "type": field_type.strip(),
                "description": f"Field extracted from Java code"
            }
            
            # Determine if this is likely an input or output field
            is_output = any(output_keyword in field_name.lower() for output_keyword in ["output", "result", "target", "destination"])
            
            if is_output and result["output_components"]:
                result["output_components"][0]["data_structure"]["fields"].append(field)
            elif result["input_components"]:
                result["input_components"][0]["data_structure"]["fields"].append(field)
        
        # Extract method names for processing logic
        method_matches = re.findall(r'(?:public|private|protected)\s+(?:static)?\s*[\w<>\[\],\s]+\s+(\w+)\s*\([^)]*\)', text)
        
        # Add processing logic
        result["processing_logic"] = {
            "description": "Processing logic extracted from Java code",
            "main_flow": "Data processing flow: " + " -> ".join(method_matches[:5]) if method_matches else "Data processing flow extracted from code",
            "methods": method_matches[:10] if method_matches else []
        }
        
        # Add error handling if found in the text
        if "try" in text and "catch" in text:
            result["processing_logic"]["error_handling"] = "Uses try-catch blocks for error handling"
        
        logger.info(f"Created structured output through post-processing")
        return result
    
    def evaluate_generation(self, test_data: List[Dict[str, Any]]) -> Dict[str, float]:
        """Evaluate the model's generation capabilities."""
        logger.info("Evaluating generation capabilities...")
        
        results = {
            'annotation_accuracy': 0.0,
            'field_match_rate': 0.0,
            'structure_match_rate': 0.0
        }
        
        total_samples = len(test_data)
        correct_annotations = 0
        total_fields = 0
        matched_fields = 0
        
        for sample in test_data:
            # Generate annotation
            generated_annotation = self.generate_annotation(sample['java_code'])
            
            # Compare with ground truth
            ground_truth = sample['annotation']
            
            # Check if job name matches
            if generated_annotation.get('job_name') == ground_truth.get('job_name'):
                correct_annotations += 1
            
            # Check field matches in input components
            if 'input_components' in ground_truth and 'input_components' in generated_annotation:
                for i, comp in enumerate(ground_truth['input_components']):
                    if i < len(generated_annotation['input_components']):
                        gen_comp = generated_annotation['input_components'][i]
                        
                        # Count fields
                        if 'data_structure' in comp and 'fields' in comp['data_structure']:
                            gt_fields = comp['data_structure']['fields']
                            total_fields += len(gt_fields)
                            
                            if 'data_structure' in gen_comp and 'fields' in gen_comp['data_structure']:
                                gen_fields = gen_comp['data_structure']['fields']
                                
                                # Count matched fields
                                for gt_field in gt_fields:
                                    for gen_field in gen_fields:
                                        if gt_field.get('name') == gen_field.get('name'):
                                            matched_fields += 1
                                            break
        
        # Calculate metrics
        if total_samples > 0:
            results['annotation_accuracy'] = correct_annotations / total_samples
        
        if total_fields > 0:
            results['field_match_rate'] = matched_fields / total_fields
        
        logger.info(f"Evaluation results: {results}")
        return results

def main():
    """Main function to run the training process."""
    import argparse
    
    # Parse command line arguments
    parser = argparse.ArgumentParser(description='Train an LLM model to generate structured outputs from Java code')
    parser.add_argument('--data_dir', type=str, default='./training_data', help='Directory containing training data')
    parser.add_argument('--model_name', type=str, default='t5-base', help='Pre-trained model to use')
    parser.add_argument('--output_dir', type=str, default='./model_output', help='Directory to save the trained model')
    parser.add_argument('--batch_size', type=int, default=4, help='Batch size for training')
    parser.add_argument('--epochs', type=int, default=3, help='Number of training epochs')
    parser.add_argument('--learning_rate', type=float, default=5e-5, help='Learning rate')
    parser.add_argument('--test_size', type=float, default=0.2, help='Proportion of data to use for evaluation')
    
    args = parser.parse_args()
    
    # Initialize trainer
    trainer = JavaToStructuredOutputTrainer(
        base_dir=args.data_dir,
        model_name=args.model_name,
        output_dir=args.output_dir
    )
    
    # Load data
    data_pairs = trainer.load_data()
    
    # Prepare datasets
    trainer.prepare_datasets(data_pairs, test_size=args.test_size)
    
    # Train model
    trainer.train(
        batch_size=args.batch_size,
        num_epochs=args.epochs,
        learning_rate=args.learning_rate
    )
    
    # Evaluate on test data
    # The eval_dataset doesn't contain base_name directly, so we need to extract it from the input_text
    eval_base_names = []
    for item in trainer.eval_dataset:
        # Extract base_name from input_text if possible
        try:
            # This is a simple extraction approach - might need refinement based on actual format
            input_text = item['input_text']
            # Assuming base_name might be in the input text somewhere
            for pair in data_pairs:
                if pair['base_name'] in input_text:
                    eval_base_names.append(pair['base_name'])
                    break
        except:
            logger.warning(f"Could not extract base_name from evaluation item")
    
    test_data = [pair for pair in data_pairs if pair['base_name'] in eval_base_names]
    evaluation_results = trainer.evaluate_generation(test_data)
    
    logger.info("Training and evaluation completed.")
    logger.info(f"Final evaluation results: {evaluation_results}")
    
    return evaluation_results

if __name__ == "__main__":
    main()