#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Test Script for Enhanced JSON Output Model

This script loads a fine-tuned model and tests its ability to generate enhanced JSON
outputs similar to enhanced_output.json from Java code.
"""

import os
import json
import logging
import argparse
from pathlib import Path
from fine_tune_json_model_complete import EnhancedJSONOutputTrainer

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler("test_enhanced_model.log"),
        logging.StreamHandler()
    ]
)
logger = logging.getLogger(__name__)

def load_java_file(file_path):
    """Load a Java file for testing."""
    with open(file_path, 'r', encoding='utf-8') as f:
        return f.read()

def main():
    """Main function to test the enhanced model."""
    parser = argparse.ArgumentParser(description='Test the fine-tuned model on Java code')
    parser.add_argument('--model_dir', type=str, default='./enhanced_model_output/final_model', 
                        help='Directory containing the fine-tuned model')
    parser.add_argument('--java_file', type=str, required=True,
                        help='Path to a Java file to test')
    parser.add_argument('--output_file', type=str, default='./enhanced_output_test.json',
                        help='Path to save the generated enhanced JSON')
    parser.add_argument('--compare_with', type=str, default='./enhanced_output.json',
                        help='Path to the reference enhanced JSON for comparison')
    
    args = parser.parse_args()
    
    # Check if model directory exists
    model_dir = Path(args.model_dir)
    if not model_dir.exists():
        logger.error(f"Model directory {model_dir} does not exist")
        return
    
    # Check if Java file exists
    java_file = Path(args.java_file)
    if not java_file.exists():
        logger.error(f"Java file {java_file} does not exist")
        return
    
    logger.info(f"Loading model from {model_dir}")
    
    # Initialize trainer with the fine-tuned model
    trainer = EnhancedJSONOutputTrainer(
        base_dir="./training_data",  # Not used for inference
        model_name=str(model_dir),
        output_dir="./test_output"
    )
    
    # Load Java code
    java_code = load_java_file(java_file)
    logger.info(f"Loaded Java file: {java_file}")
    
    # Generate enhanced JSON
    logger.info("Generating enhanced JSON...")
    enhanced_json = trainer.generate_enhanced_json(java_code)
    
    # Save the generated enhanced JSON
    output_file = Path(args.output_file)
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(enhanced_json, f, indent=2)
    
    logger.info(f"Generated enhanced JSON saved to {output_file}")
    
    # Compare with reference if provided
    if args.compare_with and Path(args.compare_with).exists():
        with open(args.compare_with, 'r', encoding='utf-8') as f:
            reference_json = json.load(f)
        
        # Calculate structure similarity
        ref_keys = set(reference_json.keys())
        gen_keys = set(enhanced_json.keys())
        structure_similarity = len(ref_keys.intersection(gen_keys)) / len(ref_keys)
        
        logger.info(f"Structure similarity with reference: {structure_similarity:.2f}")
        
        # Print a summary of the differences
        missing_keys = ref_keys - gen_keys
        extra_keys = gen_keys - ref_keys
        
        if missing_keys:
            logger.info(f"Missing keys in generated JSON: {missing_keys}")
        if extra_keys:
            logger.info(f"Extra keys in generated JSON: {extra_keys}")
    
    # Print a summary of the enhanced JSON
    logger.info("Enhanced JSON summary:")
    logger.info(f"Job name: {enhanced_json.get('job_name', 'Not found')}")
    logger.info(f"Project name: {enhanced_json.get('project_name', 'Not found')}")
    logger.info(f"Number of input components: {len(enhanced_json.get('input_components', []))}")
    logger.info(f"Number of transformation components: {len(enhanced_json.get('transformation_components', []))}")
    logger.info(f"Number of output components: {len(enhanced_json.get('output_components', []))}")
    
    return enhanced_json

if __name__ == "__main__":
    main()