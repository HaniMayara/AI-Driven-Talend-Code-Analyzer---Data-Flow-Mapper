#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Enhanced Test Script for JSON Output Model

This script tests the fine-tuned model with improved post-processing capabilities
for generating structured JSON outputs with better accuracy.
"""

import os
import json
import logging
import argparse
import re
from pathlib import Path
from enhanced_fine_tune_json_model import EnhancedJSONOutputTrainer

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler("enhanced_test_model.log"),
        logging.StreamHandler()
    ]
)
logger = logging.getLogger(__name__)

def load_java_file(file_path):
    """Load a Java file for testing."""
    with open(file_path, 'r', encoding='utf-8') as f:
        return f.read()

def validate_json_structure(json_obj):
    """
    Validate the JSON structure against the expected schema.
    Returns a tuple of (is_valid, issues).
    """
    required_fields = ["job_name", "input_components", "output_components", "processing_logic"]
    issues = []
    
    # Check required fields
    for field in required_fields:
        if field not in json_obj:
            issues.append(f"Missing required field: {field}")
    
    # Check component arrays
    for component_type in ["input_components", "output_components", "transformation_components"]:
        if component_type in json_obj and not isinstance(json_obj[component_type], list):
            issues.append(f"{component_type} should be an array")
    
    # Check processing_logic structure
    if "processing_logic" in json_obj:
        if not isinstance(json_obj["processing_logic"], dict):
            issues.append("processing_logic should be an object")
        elif "main_flow" not in json_obj["processing_logic"]:
            issues.append("processing_logic should contain main_flow")
    
    return len(issues) == 0, issues

def main():
    """Main function to test the enhanced model."""
    parser = argparse.ArgumentParser(description='Test the enhanced fine-tuned model on Java code')
    parser.add_argument('--model_dir', type=str, default='./enhanced_model_output/final_model', 
                        help='Directory containing the fine-tuned model')
    parser.add_argument('--java_file', type=str, required=True,
                        help='Path to a Java file to test')
    parser.add_argument('--output_file', type=str, default='./enhanced_test_output.json',
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
    
    # Validate JSON structure
    is_valid, issues = validate_json_structure(enhanced_json)
    if is_valid:
        logger.info("Generated JSON has valid structure")
    else:
        logger.warning("Generated JSON has structure issues:")
        for issue in issues:
            logger.warning(f"  - {issue}")
    
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
        
        # Compare components
        for component_type in ["input_components", "output_components", "transformation_components"]:
            if component_type in reference_json and component_type in enhanced_json:
                ref_components = reference_json[component_type]
                gen_components = enhanced_json[component_type]
                
                logger.info(f"{component_type}: Reference has {len(ref_components)}, Generated has {len(gen_components)}")
                
                # Count matched components by name
                ref_names = [comp.get('name', '') for comp in ref_components]
                gen_names = [comp.get('name', '') for comp in gen_components]
                
                matched = sum(1 for name in ref_names if name in gen_names)
                logger.info(f"{component_type}: Matched {matched}/{len(ref_names)} component names")
    
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