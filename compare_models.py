#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Model Comparison Script

This script compares the output of different models on the same input data.
It generates side-by-side comparisons and calculates metrics for JSON structure accuracy.
"""

import os
import json
import argparse
import logging
from pathlib import Path
from typing import Dict, List, Any
import difflib
import pandas as pd
from tabulate import tabulate

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler("model_comparison.log"),
        logging.StreamHandler()
    ]
)
logger = logging.getLogger(__name__)

class ModelOutputComparator:
    """Class for comparing outputs from different models."""
    
    def __init__(self, output_dir: str = "./comparison_results"):
        """
        Initialize the comparator.
        
        Args:
            output_dir: Directory to save comparison results
        """
        self.output_dir = Path(output_dir)
        self.output_dir.mkdir(exist_ok=True, parents=True)
        
    def load_json_file(self, file_path: str) -> Dict:
        """
        Load a JSON file.
        
        Args:
            file_path: Path to the JSON file
            
        Returns:
            Loaded JSON data as a dictionary
        """
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                return json.load(f)
        except json.JSONDecodeError:
            logger.error(f"Error decoding JSON from {file_path}")
            return {}
        except FileNotFoundError:
            logger.error(f"File not found: {file_path}")
            return {}
    
    def calculate_structure_similarity(self, json1: Dict, json2: Dict) -> float:
        """
        Calculate structural similarity between two JSON objects.
        
        Args:
            json1: First JSON object
            json2: Second JSON object
            
        Returns:
            Similarity score between 0 and 1
        """
        if not json1 or not json2:
            return 0.0
            
        # Compare top-level keys
        keys1 = set(json1.keys())
        keys2 = set(json2.keys())
        
        if not keys1 or not keys2:
            return 0.0
            
        # Jaccard similarity for top-level keys
        key_similarity = len(keys1.intersection(keys2)) / len(keys1.union(keys2))
        
        # Compare nested structure for common keys
        common_keys = keys1.intersection(keys2)
        nested_similarity = 0.0
        
        for key in common_keys:
            if isinstance(json1[key], dict) and isinstance(json2[key], dict):
                nested_similarity += self.calculate_structure_similarity(json1[key], json2[key])
            elif isinstance(json1[key], list) and isinstance(json2[key], list):
                # For lists, compare lengths as a simple metric
                if len(json1[key]) > 0 and len(json2[key]) > 0:
                    len_ratio = min(len(json1[key]), len(json2[key])) / max(len(json1[key]), len(json2[key]))
                    nested_similarity += len_ratio
                else:
                    nested_similarity += 0.0
            else:
                # For scalar values, check if they're the same type
                if type(json1[key]) == type(json2[key]):
                    nested_similarity += 1.0
                else:
                    nested_similarity += 0.0
        
        # Average the nested similarity
        if common_keys:
            nested_similarity /= len(common_keys)
            return (key_similarity + nested_similarity) / 2
        else:
            return key_similarity
    
    def compare_json_files(self, reference_file: str, *model_output_files: str) -> Dict[str, Any]:
        """
        Compare multiple model outputs against a reference.
        
        Args:
            reference_file: Path to the reference JSON file
            *model_output_files: Paths to model output JSON files
            
        Returns:
            Dictionary with comparison results
        """
        # Load reference file
        reference_json = self.load_json_file(reference_file)
        if not reference_json:
            logger.error(f"Reference file {reference_file} is empty or invalid")
            return {}
            
        results = {
            "reference": Path(reference_file).name,
            "models": []
        }
        
        # Compare each model output
        for model_file in model_output_files:
            model_json = self.load_json_file(model_file)
            if not model_json:
                logger.warning(f"Model output file {model_file} is empty or invalid")
                continue
                
            # Calculate metrics
            similarity = self.calculate_structure_similarity(reference_json, model_json)
            
            # Find missing and extra keys
            ref_keys = self._get_all_keys(reference_json)
            model_keys = self._get_all_keys(model_json)
            
            missing_keys = ref_keys - model_keys
            extra_keys = model_keys - ref_keys
            
            model_result = {
                "file": Path(model_file).name,
                "similarity": similarity,
                "missing_keys": list(missing_keys),
                "extra_keys": list(extra_keys)
            }
            
            results["models"].append(model_result)
            
        return results
    
    def _get_all_keys(self, json_obj: Dict, prefix: str = "") -> set:
        """
        Get all keys in a nested JSON object with their path.
        
        Args:
            json_obj: JSON object to extract keys from
            prefix: Prefix for nested keys
            
        Returns:
            Set of all keys with their paths
        """
        keys = set()
        
        for key, value in json_obj.items():
            full_key = f"{prefix}.{key}" if prefix else key
            keys.add(full_key)
            
            if isinstance(value, dict):
                keys.update(self._get_all_keys(value, full_key))
            elif isinstance(value, list) and value and isinstance(value[0], dict):
                # For lists of objects, check the first item's structure
                keys.update(self._get_all_keys(value[0], f"{full_key}[0]"))
                
        return keys
    
    def generate_comparison_report(self, results: Dict[str, Any], output_file: str = None) -> str:
        """
        Generate a human-readable comparison report.
        
        Args:
            results: Comparison results
            output_file: Optional file to save the report
            
        Returns:
            Report as a string
        """
        if not results or "models" not in results or not results["models"]:
            return "No valid comparison results available."
            
        # Create a summary table
        table_data = []
        for model in results["models"]:
            table_data.append([
                model["file"],
                f"{model['similarity']:.2f}",
                len(model["missing_keys"]),
                len(model["extra_keys"])
            ])
            
        summary_table = tabulate(
            table_data,
            headers=["Model Output", "Similarity", "Missing Keys", "Extra Keys"],
            tablefmt="grid"
        )
        
        # Generate detailed report
        report = f"# Model Comparison Report\n\n"
        report += f"Reference: {results['reference']}\n\n"
        report += f"## Summary\n\n{summary_table}\n\n"
        
        # Add details for each model
        for model in results["models"]:
            report += f"## {model['file']}\n\n"
            report += f"Similarity Score: {model['similarity']:.2f}\n\n"
            
            if model["missing_keys"]:
                report += "### Missing Keys\n\n"
                for key in sorted(model["missing_keys"]):
                    report += f"- `{key}`\n"
                report += "\n"
                
            if model["extra_keys"]:
                report += "### Extra Keys\n\n"
                for key in sorted(model["extra_keys"]):
                    report += f"- `{key}`\n"
                report += "\n"
        
        # Save report if requested
        if output_file:
            with open(output_file, 'w', encoding='utf-8') as f:
                f.write(report)
            logger.info(f"Comparison report saved to {output_file}")
        
        return report

def main():
    """Main function to compare model outputs."""
    parser = argparse.ArgumentParser(description='Compare outputs from different models')
    parser.add_argument('--reference', type=str, required=True,
                        help='Path to the reference JSON file')
    parser.add_argument('--outputs', type=str, nargs='+', required=True,
                        help='Paths to model output JSON files')
    parser.add_argument('--report', type=str, default='comparison_report.md',
                        help='Path to save the comparison report')
    
    args = parser.parse_args()
    
    comparator = ModelOutputComparator()
    results = comparator.compare_json_files(args.reference, *args.outputs)
    
    if results:
        report = comparator.generate_comparison_report(results, args.report)
        print(f"\nComparison complete. Report saved to {args.report}")
        
        # Save raw results as JSON
        results_file = Path(args.report).with_suffix('.json')
        with open(results_file, 'w', encoding='utf-8') as f:
            json.dump(results, f, indent=2)
        print(f"Raw comparison data saved to {results_file}")
    else:
        print("Comparison failed. Check the log for details.")

if __name__ == "__main__":
    main()