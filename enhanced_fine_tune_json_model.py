#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Enhanced Fine-tune LLM Model for Improved JSON Output Generation

This script fine-tunes an existing model to generate structured JSON outputs
with improved accuracy. It includes:
- More explicit JSON instructions and examples in the training data
- Increased epochs (4 instead of 3) and larger batch size
- Learning rate scheduling for better convergence
- Robust post-processing with JSON validation and template-based correction
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
from train_llm import JavaToStructuredOutputTrainer

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler("enhanced_fine_tuning.log"),
        logging.StreamHandler()
    ]
)
logger = logging.getLogger(__name__)

class EnhancedJSONOutputTrainer(JavaToStructuredOutputTrainer):
    """Class for fine-tuning a model to generate enhanced JSON outputs with improved accuracy."""
    
    def __init__(self, base_dir: str, model_name: str = "./model_output/final_model", output_dir: str = "./enhanced_model_output"):
        """
        Initialize the trainer with an existing model for fine-tuning.
        
        Args:
            base_dir: Base directory containing training data
            model_name: Path to the pre-trained model to fine-tune (default: previously trained model)
            output_dir: Directory to save the fine-tuned model
        """
        super().__init__(base_dir, model_name, output_dir)
        
        # Load enhanced output template for reference
        self.enhanced_template_path = Path("./enhanced_output.json")
        if self.enhanced_template_path.exists():
            with open(self.enhanced_template_path, 'r', encoding='utf-8') as f:
                self.enhanced_template = json.load(f)
            logger.info(f"Loaded enhanced output template from {self.enhanced_template_path}")
        else:
            logger.warning(f"Enhanced output template not found at {self.enhanced_template_path}")
            self.enhanced_template = None
            
        # JSON schema for validation
        self.json_schema = {
            "type": "object",
            "required": ["job_name", "input_components", "output_components"],
            "properties": {
                "job_name": {"type": "string"},
                "job_version": {"type": "string"},
                "project_name": {"type": "string"},
                "author": {"type": "string"},
                "description": {"type": "string"},
                "input_components": {"type": "array"},
                "transformation_components": {"type": "array"},
                "output_components": {"type": "array"},
                "processing_logic": {"type": "object"},
                "data_structures": {"type": "object"}
            }
        }
    
    def preprocess_java_code(self, code: str) -> str:
        """
        Enhanced preprocessing for Java code to better extract information for JSON generation.
        Focuses on component structure and data flow.
        """
        # Start with the base preprocessing
        preprocessed = super().preprocess_java_code(code)
        
        # Extract additional information relevant to enhanced JSON structure
        
        # Look for component definitions and connections
        # Modified pattern to better match component references in error handlers and method names
        component_pattern = r'(?:t[A-Z][a-zA-Z0-9]+_\d+)'
        
        # Also look for component error handlers which indicate component existence
        error_handler_pattern = r'public void (t[A-Z][a-zA-Z0-9]+_\d+)_error'
        error_handlers = re.findall(error_handler_pattern, code)
        
        # Find components from both patterns
        components = re.findall(component_pattern, code)
        components.extend(error_handlers)
        unique_components = list(set(components))
        
        # Identify input/output/transformation components based on naming patterns
        input_components = [c for c in unique_components if any(pattern in c.lower() for pattern in ['input', 'file', 'source', 'read'])]
        output_components = [c for c in unique_components if any(pattern in c.lower() for pattern in ['output', 'target', 'write'])]
        transform_components = [c for c in unique_components if any(pattern in c.lower() for pattern in ['map', 'transform', 'convert'])]
        
        # If no components were found with specific patterns, try to categorize by common naming conventions
        if not input_components and not output_components and not transform_components and unique_components:
            for comp in unique_components:
                if 'input' in comp.lower() or 'file' in comp.lower() and 'in' in comp.lower():
                    input_components.append(comp)
                elif 'output' in comp.lower() or 'file' in comp.lower() and 'out' in comp.lower():
                    output_components.append(comp)
                elif 'map' in comp.lower():
                    transform_components.append(comp)
        
        # Look for data structures and field definitions
        struct_pattern = r'(?:public|private)\s+(?:static)?\s*class\s+(\w+)\s*(?:implements|extends)?\s*[\w,\s]*\{([^}]*)\}'
        struct_matches = re.findall(struct_pattern, code, re.DOTALL)
        
        # Extract field information from structs
        struct_info = []
        for struct_name, struct_body in struct_matches:
            field_pattern = r'(?:public|private)\s+(?:static|final)?\s*([\w<>\[\],\s]+)\s+(\w+)\s*;'
            fields = re.findall(field_pattern, struct_body)
            
            struct_info.append({
                'name': struct_name,
                'fields': [{'name': field_name, 'type': field_type.strip()} for field_type, field_name in fields]
            })
        
        # Look for flow connections between components
        flow_pattern = r'([a-zA-Z0-9_]+)\s*\.\s*connect\s*\(\s*([a-zA-Z0-9_]+)\s*\)'
        flow_connections = re.findall(flow_pattern, code)
        
        # Add the enhanced information to the preprocessed code
        enhanced_info = "\n\nENHANCED COMPONENT INFO:\n"
        if input_components:
            enhanced_info += "INPUT COMPONENTS:\n" + "\n".join(input_components) + "\n\n"
        if transform_components:
            enhanced_info += "TRANSFORMATION COMPONENTS:\n" + "\n".join(transform_components) + "\n\n"
        if output_components:
            enhanced_info += "OUTPUT COMPONENTS:\n" + "\n".join(output_components) + "\n\n"
        
        enhanced_info += "DATA STRUCTURES:\n"
        for struct in struct_info:
            enhanced_info += f"Structure: {struct['name']}\n"
            for field in struct['fields']:
                enhanced_info += f"  Field: {field['name']} ({field['type']})\n"
            enhanced_info += "\n"
        
        enhanced_info += "FLOW CONNECTIONS:\n"
        for source, target in flow_connections:
            enhanced_info += f"{source} → {target}\n"
        
        return preprocessed + enhanced_info
    
    def prepare_datasets(self, data_pairs: List[Dict[str, Any]], test_size: float = 0.2):
        """
        Prepare datasets specifically for enhanced JSON output generation.
        Includes more explicit JSON instructions and examples.
        """
        logger.info("Preparing datasets for enhanced JSON output generation...")
        
        # Prepare data for enhanced JSON generation task
        enhanced_data = []
        for pair in data_pairs:
            # Preprocess Java code with enhanced preprocessing
            preprocessed_code = self.preprocess_java_code(pair['java_code'])
            
            # Create enhanced annotation as target if available
            # Otherwise use the standard annotation with enhanced structure
            enhanced_annotation = self._create_enhanced_annotation(pair)
            enhanced_json = json.dumps(enhanced_annotation)
            
            # Create more explicit instruction with JSON schema guidance
            input_text = (
                f"Generate a valid JSON structure following the enhanced template format for this Java code. "  
                f"The output should be a well-formed JSON object with the following required fields: "  
                f"'job_name', 'input_components', 'output_components', and 'processing_logic'. "  
                f"Here is the Java code:\n{preprocessed_code}"
            )
            
            # Add example of expected output format
            target_text = f"JSON Output: {enhanced_json}"
            
            enhanced_data.append({
                'input_text': input_text,
                'target_text': target_text
            })
        
        # Split into train and evaluation sets
        train_data, eval_data = train_test_split(enhanced_data, test_size=test_size, random_state=42)
        
        # Convert to HuggingFace datasets
        self.train_dataset = Dataset.from_pandas(pd.DataFrame(train_data))
        self.eval_dataset = Dataset.from_pandas(pd.DataFrame(eval_data))
        
        logger.info(f"Prepared {len(self.train_dataset)} training samples and {len(self.eval_dataset)} evaluation samples")
    
    def _determine_data_type(self, component: Dict[str, Any]) -> str:
        """
        Determine the data type of a component based on its properties.
        """
        component_name = component.get("component", "").lower()
        
        if "xml" in component_name:
            return "xml"
        elif "csv" in component_name or "delimited" in component_name:
            return "csv"
        elif "excel" in component_name:
            return "excel"
        elif "positional" in component_name:
            return "positional"
        elif "json" in component_name:
            return "json"
        else:
            # Try to determine from file extension if available
            source_file = component.get("source_file", "")
            if source_file:
                if source_file.endswith(".xml"):
                    return "xml"
                elif source_file.endswith(".csv"):
                    return "csv"
                elif source_file.endswith(".xls") or source_file.endswith(".xlsx"):
                    return "excel"
                elif source_file.endswith(".pos"):
                    return "positional"
                elif source_file.endswith(".json"):
                    return "json"
            
            return "unknown"
    
    def _create_enhanced_annotation(self, data_pair: Dict[str, Any]) -> Dict[str, Any]:
        """
        Create an enhanced annotation based on the template and available data.
        """
        # Start with the base annotation
        base_annotation = data_pair['annotation']
        
        # Create enhanced structure following the template
        enhanced = {
            "job_name": base_annotation.get("job_name", ""),
            "job_version": base_annotation.get("job_version", "0.1"),
            "project_name": base_annotation.get("project_name", "MIGRATION"),
            "author": base_annotation.get("author", "user@talend.com"),
            "description": base_annotation.get("description", f"Converts {base_annotation.get('input_data', '')} to {base_annotation.get('output_data', '')}"),
            "input_components": [],
            "transformation_components": [],
            "output_components": [],
            "processing_logic": {
                "description": "",
                "main_flow": "",
                "error_handling": {
                    "component_error_handlers": [],
                    "error_logging": ""
                },
                "performance_tracking": {
                    "start_time_tracking": "start_Hash map",
                    "end_time_tracking": "end_Hash map",
                    "status_tracking": "ok_Hash map"
                }
            },
            "data_structures": {
                "input": {}
            }
        }
        
        # Process input components
        if "input_components" in base_annotation:
            for comp in base_annotation["input_components"]:
                enhanced_comp = {
                    "component": comp.get("component", ""),
                    "name": comp.get("component", ""),
                    "type": "input",
                    "source_file": comp.get("source_file", ""),
                    "data_structure": {
                        "type": self._determine_data_type(comp),
                        "fields": []
                    }
                }
                
                # Process fields
                if "data_structure" in comp and "fields" in comp["data_structure"]:
                    fields = comp["data_structure"]["fields"]
                    if isinstance(fields, list):
                        for field in fields:
                            if isinstance(field, dict):
                                enhanced_comp["data_structure"]["fields"].append(field)
                            else:
                                enhanced_comp["data_structure"]["fields"].append({"name": field, "type": "STRING"})
                    
                    # Add additional data structure properties if available
                    for prop in ["delimiter", "encoding"]:
                        if prop in comp["data_structure"]:
                            enhanced_comp["data_structure"][prop] = comp["data_structure"][prop]
                
                enhanced["input_components"].append(enhanced_comp)
        
        # Process transformation components from transformation_steps
        if "transformation_steps" in base_annotation:
            for step in base_annotation["transformation_steps"]:
                enhanced_step = {
                    "component": step.get("component", ""),
                    "name": step.get("component", ""),
                    "type": "transformation",
                    "mapping": []
                }
                
                # Process mappings
                if "mapping" in step:
                    for mapping in step["mapping"]:
                        enhanced_mapping = {
                            "source_field": mapping.get("source_field", ""),
                            "target_field": mapping.get("target_field", ""),
                            "type": "direct"
                        }
                        enhanced_step["mapping"].append(enhanced_mapping)
                
                enhanced["transformation_components"].append(enhanced_step)
        
        # Process output components
        if "output_components" in base_annotation:
            for comp in base_annotation["output_components"]:
                enhanced_comp = {
                    "component": comp.get("component", ""),
                    "name": comp.get("component", ""),
                    "type": "output",
                    "destination_file": comp.get("destination_file", ""),
                    "data_structure": {
                        "type": self._determine_data_type(comp),
                        "fields": []
                    }
                }
                
                # Process fields
                if "data_structure" in comp and "fields" in comp["data_structure"]:
                    fields = comp["data_structure"]["fields"]
                    if isinstance(fields, list):
                        for field in fields:
                            if isinstance(field, dict):
                                enhanced_comp["data_structure"]["fields"].append(field)
                            else:
                                enhanced_comp["data_structure"]["fields"].append({"name": field, "type": "STRING"})
                    
                    # Add additional data structure properties if available
                    for prop in ["delimiter", "encoding"]:
                        if prop in comp["data_structure"]:
                            enhanced_comp["data_structure"][prop] = comp["data_structure"][prop]
                
                enhanced["output_components"].append(enhanced_comp)
        
        # Process processing logic
        if "notes" in base_annotation:
            enhanced["processing_logic"]["description"] = base_annotation["notes"]
        
        # Create main flow from components
        components = []
        if enhanced["input_components"]:
            components.extend([comp["name"] for comp in enhanced["input_components"]])
        if enhanced["transformation_components"]:
            components.extend([comp["name"] for comp in enhanced["transformation_components"]])
        if enhanced["output_components"]:
            components.extend([comp["name"] for comp in enhanced["output_components"]])
        
        if components:
            enhanced["processing_logic"]["main_flow"] = " → ".join(components)
            
            # Add error handlers for each component
            enhanced["processing_logic"]["error_handling"]["component_error_handlers"] = [
                f"{comp}_error" for comp in components
            ]
            enhanced["processing_logic"]["error_handling"]["error_logging"] = "Uses globalMap variables for error tracking and component status monitoring"
        
        # Add data structures from Java code if available
        if "csvStruct" in data_pair.get('java_code', ""):
            # Extract fields from csvStruct in Java code
            struct_pattern = r'public\s+class\s+csvStruct\s+implements.*?\{(.*?)\}'
            struct_match = re.search(struct_pattern, data_pair.get('java_code', ""), re.DOTALL)
            if struct_match:
                field_pattern = r'public\s+String\s+(\w+);'
                fields = re.findall(field_pattern, struct_match.group(1))
                
                if fields:
                    enhanced["data_structures"]["input"]["csvStruct"] = {
                        "fields": []
                    }
                    
                    for field in fields:
                        enhanced["data_structures"]["input"]["csvStruct"]["fields"].append({
                            "name": field,
                            "type": "String"
                        })
        
        return enhanced
    
    def train(self, batch_size: int = 8, num_epochs: int = 4, learning_rate: float = 2e-5):
        """
        Fine-tune the model with improved training parameters:
        - Increased batch size (8 instead of 4)
        - More epochs (4 instead of 3)
        - Learning rate scheduling
        """
        logger.info("Starting enhanced fine-tuning...")
        
        # Tokenize datasets
        tokenized_train_dataset = self.train_dataset.map(self.tokenize_function, batched=True)
        tokenized_eval_dataset = self.eval_dataset.map(self.tokenize_function, batched=True)
        
        # Data collator
        data_collator = DataCollatorForSeq2Seq(
            tokenizer=self.tokenizer,
            model=self.model,
            padding=True
        )
        
        # Training arguments with improved parameters
        training_args = Seq2SeqTrainingArguments(
            output_dir=str(self.output_dir),
            evaluation_strategy="epoch",
            learning_rate=learning_rate,  # Slightly higher initial learning rate
            lr_scheduler_type="cosine",  # Add learning rate schedule
            per_device_train_batch_size=batch_size,  # Increased batch size
            per_device_eval_batch_size=batch_size,
            weight_decay=0.01,
            save_total_limit=3,
            num_train_epochs=num_epochs,  # Increased epochs
            predict_with_generate=True,
            logging_dir=str(self.output_dir / "logs"),
            logging_steps=50,  # More frequent logging
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
        
        logger.info("Fine-tuning completed successfully")
        
        # Evaluate the model
        eval_results = trainer.evaluate()
        logger.info(f"Evaluation results: {eval_results}")
        
        return eval_results
    
    def generate_enhanced_json(self, java_code: str) -> Dict[str, Any]:
        """
        Generate enhanced JSON for a given Java code with improved post-processing.
        """
        # Store the Java code for later use in post-processing
        self.last_java_code = java_code
        
        preprocessed_code = self.preprocess_java_code(java_code)
        
        # Create more explicit instruction with JSON schema guidance
        input_text = (
            f"Generate a valid JSON structure following the enhanced template format for this Java code. "  
            f"The output should be a well-formed JSON object with the following required fields: "  
            f"'job_name', 'input_components', 'output_components', and 'processing_logic'. "  
            f"Here is the Java code:\n{preprocessed_code}"
        )
        
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
        json_part = decoded_output.replace("JSON Output: ", "")
        
        # Post-process the output to ensure valid JSON
        try:
            return json.loads(json_part)
        except json.JSONDecodeError:
            logger.warning("Failed to parse generated JSON, falling back to enhanced post-processing")
            return self.enhanced_post_process_output(json_part)
    
    def enhanced_post_process_output(self, text: str) -> Dict[str, Any]:
        """
        Enhanced post-processing for model output to ensure valid JSON with template-based correction.
        """
        # Try direct parsing first
        try:
            return json.loads(text)
        except json.JSONDecodeError:
            logger.info("Direct JSON parsing failed, attempting enhanced post-processing")
        
        # Step 1: Try to fix common JSON syntax issues
        fixed_text = self._fix_json_syntax(text)
        try:
            return json.loads(fixed_text)
        except json.JSONDecodeError:
            logger.info("Syntax fixing failed, attempting template-based reconstruction")
        
        # Step 2: Template-based reconstruction
        # Start with a template based on the enhanced structure
        template = {
            "job_name": "",
            "job_version": "0.1",
            "project_name": "MIGRATION",
            "author": "user@talend.com",
            "description": "",
            "input_components": [],
            "transformation_components": [],
            "output_components": [],
            "processing_logic": {
                "description": "",
                "main_flow": "",
                "error_handling": {
                    "component_error_handlers": [],
                    "error_logging": ""
                }
            },
            "data_structures": {
                "input": {}
            }
        }
        
        # Try to extract key information from the malformed JSON
        # Job name
        job_name_match = re.search(r'"job_name"\s*:\s*"([^"]+)"', text)
        if job_name_match:
            template["job_name"] = job_name_match.group(1)
        
        # Description
        desc_match = re.search(r'"description"\s*:\s*"([^"]+)"', text)
        if desc_match:
            template["description"] = desc_match.group(1)
        
        # Try to extract components
        # This is a simplified approach - a more sophisticated parser would be better
        component_pattern = r'\{\s*"component"\s*:\s*"([^"]+)"[^\}]*\}'
        component_matches = re.findall(component_pattern, text)
        
        # If no components found in the JSON text, try to extract from the Java code
        if not component_matches and hasattr(self, 'last_java_code'):
            # Look for error handler methods which indicate component existence
            error_handler_pattern = r'public void (t[A-Z][a-zA-Z0-9]+_\d+)_error'
            error_handlers = re.findall(error_handler_pattern, self.last_java_code)
            
            # Add components from error handlers
            for comp_name in error_handlers:
                if 'input' in comp_name.lower() or 'file' in comp_name.lower() and 'in' in comp_name.lower():
                    template["input_components"].append({"component": comp_name, "name": comp_name, "type": "input"})
                elif 'output' in comp_name.lower() or 'file' in comp_name.lower() and 'out' in comp_name.lower():
                    template["output_components"].append({"component": comp_name, "name": comp_name, "type": "output"})
                elif 'map' in comp_name.lower() or 'transform' in comp_name.lower():
                    template["transformation_components"].append({"component": comp_name, "name": comp_name, "type": "transformation"})
        else:
            # Categorize components based on naming patterns
            for comp_name in component_matches:
                comp = {"component": comp_name, "name": comp_name}
                
                if any(pattern in comp_name.lower() for pattern in ['input', 'source', 'read']):
                    template["input_components"].append(comp)
                elif any(pattern in comp_name.lower() for pattern in ['output', 'target', 'write']):
                    template["output_components"].append(comp)
                else:
                    template["transformation_components"].append(comp)
        
        # Create main flow if components were found
        if any([template["input_components"], template["transformation_components"], template["output_components"]]):
            components = []
            if template["input_components"]:
                components.extend([comp["name"] for comp in template["input_components"]])
            if template["transformation_components"]:
                components.extend([comp["name"] for comp in template["transformation_components"]])
            if template["output_components"]:
                components.extend([comp["name"] for comp in template["output_components"]])
            
            template["processing_logic"]["main_flow"] = " → ".join(components)
        
        logger.info("Created template-based JSON structure")
        return template
    
    def _fix_json_syntax(self, text: str) -> str:
        """
        Fix common JSON syntax issues.
        """
        # Remove any text before the first '{' and after the last '}'
        start_idx = text.find('{')
        end_idx = text.rfind('}')
        
        if start_idx != -1 and end_idx != -1:
            text = text[start_idx:end_idx+1]
        
        # Fix unbalanced quotes
        quote_count = text.count('"')
        if quote_count % 2 != 0:
            # Find unbalanced quotes and fix them
            in_string = False
            fixed_text = ""
            for char in text:
                if char == '"':
                    in_string = not in_string
                fixed_text += char
            
            # If we end up still in a string, add a closing quote
            if in_string:
                fixed_text += '"'
            
            text = fixed_text
        
        # Fix missing commas between objects in arrays
        text = re.sub(r'\}\s*\{', '},{', text)
        
        # Fix trailing commas in objects and arrays
        text = re.sub(r',\s*\}', '}', text)
        text = re.sub(r',\s*\]', ']', text)
        
        # Balance brackets and braces
        open_braces = text.count('{')
        close_braces = text.count('}')
        open_brackets = text.count('[')
        close_brackets = text.count(']')
        
        # Add missing closing braces
        if open_braces > close_braces:
            text += '}' * (open_braces - close_braces)
        
        # Add missing closing brackets
        if open_brackets > close_brackets:
            text += ']' * (open_brackets - close_brackets)
        
        return text

def main():
    """Main function to run the enhanced fine-tuning process."""
    import argparse
    
    # Parse command line arguments
    parser = argparse.ArgumentParser(description='Fine-tune a model to generate enhanced JSON outputs')
    parser.add_argument('--data_dir', type=str, default='./training_data', help='Directory containing training data')
    parser.add_argument('--model_path', type=str, default='./model_output/final_model', 
                        help='Path to the pre-trained model to fine-tune')
    parser.add_argument('--output_dir', type=str, default='./enhanced_model_output', 
                        help='Directory to save the fine-tuned model')
    parser.add_argument('--batch_size', type=int, default=8, help='Batch size for training')
    parser.add_argument('--epochs', type=int, default=4, help='Number of training epochs')
    parser.add_argument('--learning_rate', type=float, default=2e-5, 
                        help='Learning rate with cosine scheduling')
    parser.add_argument('--test_size', type=float, default=0.2, 
                        help='Proportion of data to use for evaluation')
    
    args = parser.parse_args()
    
    # Initialize trainer
    trainer = EnhancedJSONOutputTrainer(
        base_dir=args.data_dir,
        model_name=args.model_path,
        output_dir=args.output_dir
    )
    
    # Load data
    data_pairs = trainer.load_data()
    
    # Prepare datasets
    trainer.prepare_datasets(data_pairs, test_size=args.test_size)
    
    # Fine-tune model
    trainer.train(
        batch_size=args.batch_size,
        num_epochs=args.epochs,
        learning_rate=args.learning_rate
    )
    
    # Evaluate JSON structure generation
    test_data = [pair for pair in data_pairs if pair['base_name'] in 
                [item.split('_')[-1] for item in trainer.eval_dataset['input_text']]]
    evaluation_results = trainer.evaluate_json_structure(test_data)
    
    logger.info("Fine-tuning and evaluation completed.")
    logger.info(f"Final evaluation results: {evaluation_results}")
    
    return evaluation_results

if __name__ == "__main__":
    main()