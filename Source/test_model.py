import os
import json
import logging
import re
from pathlib import Path
from typing import Dict, List
from train_llm import TalendMetadataExtractor

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler('testing.log'),
        logging.StreamHandler()
    ]
)

class ModelTester:
    def __init__(self, model_path: str = 'gpt2'):
        """Initialize the model tester.

        Args:
            model_path: Path to the trained model
        """
        self.model_path = model_path
        self.extractor = TalendMetadataExtractor(model_name=model_path)

    def load_java_file(self, file_path: str) -> str:
        """Load and read a Java file.

        Args:
            file_path: Path to the Java file

        Returns:
            Content of the Java file
        """
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                return f.read()
        except Exception as e:
            logging.error(f"Error reading Java file {file_path}: {str(e)}")
            raise

    def load_json_file(self, file_path: str) -> Dict:
        """Load and parse a JSON file.

        Args:
            file_path: Path to the JSON file

        Returns:
            Parsed JSON content
        """
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                return json.load(f)
        except Exception as e:
            logging.error(f"Error reading JSON file {file_path}: {str(e)}")
            raise

    def test_model(self, test_dir: str) -> Dict[str, Dict]:
        results = {}
        test_dir_path = Path(test_dir)
        java_files = list(test_dir_path.glob('*.java'))
        
        for java_file in java_files:
            file_name = java_file.stem
            logging.info(f"Testing model on {file_name}")
            
            try:
                java_code = self.load_java_file(str(java_file))
                json_file = test_dir_path / f"{file_name}.json"
                
                if not json_file.exists():
                    logging.warning(f"No corresponding JSON file found for {file_name}")
                    continue
                    
                ground_truth = self.load_json_file(str(json_file))
                
                # Enhanced prompt for comprehensive output
                prompt = f"""Extract metadata and generate documentation from the following Talend job:
Job Name: {file_name}
Code:
{java_code}

Generate three types of documentation:
1. Component annotations (input/output/transformation components with their properties)
2. Data flow diagram (component connections and relationships)
3. Field-level mappings (source to target field mappings)

Provide output in the following JSON format:
{{
    "annotations": {{
        "input_components": [],
        "transformation_steps": [],
        "output_components": []
    }},
    "flowchart": {{
        "cells": []
    }},
    "mapping": {{
        "cells": []
    }}
}}"""
                
                try:
                    # Increased context window for handling larger outputs
                    max_input_length = 512  # Reduced from 2048 to avoid memory issues
                    max_new_tokens = 512    # Reduced from 1024
                    
                    # Process Java code in smaller chunks
                    chunk_size = 400  # Smaller chunk size
                    code_chunks = [java_code[i:i + chunk_size] for i in range(0, len(java_code), chunk_size)]
                    predictions = []
                    
                    for chunk in code_chunks:
                        inputs = self.extractor.tokenizer(
                            prompt.replace(java_code, chunk),
                            truncation=True,
                            padding='max_length',
                            max_length=max_input_length,
                            return_tensors='pt'
                        )
                        
                        outputs = self.extractor.model.generate(
                            input_ids=inputs['input_ids'],
                            attention_mask=inputs['attention_mask'],
                            max_new_tokens=max_new_tokens,
                            num_return_sequences=1,
                            pad_token_id=self.extractor.tokenizer.pad_token_id,
                            do_sample=True,
                            temperature=0.7,      # Adjusted for more focused outputs
                            top_p=0.95,
                            top_k=50,
                            repetition_penalty=1.2,
                            length_penalty=1.0,
                            no_repeat_ngram_size=3
                        )
                        
                        if outputs is not None and len(outputs) > 0:
                            predicted_text = self.extractor.tokenizer.decode(outputs[0], skip_special_tokens=True)
                            predictions.append(predicted_text.strip())
                    
                    # Combine and process predictions
                    combined_prediction = '\n'.join(predictions)
                    
                    try:
                        predicted_json = json.loads(combined_prediction)
                    except json.JSONDecodeError:
                        # Attempt to extract JSON from the text
                        json_pattern = r'\{[^}]*\}'
                        json_matches = re.finditer(json_pattern, combined_prediction)
                        predicted_json = {}
                        
                        for match in json_matches:
                            try:
                                json_part = json.loads(match.group())
                                predicted_json.update(json_part)
                            except:
                                continue
                    
                    # Ensure all required sections are present
                    if not all(key in predicted_json for key in ['annotations', 'flowchart', 'mapping']):
                        predicted_json = {
                            'annotations': predicted_json if 'input_components' in predicted_json else {
                                'input_components': [],
                                'transformation_steps': [],
                                'output_components': []
                            },
                            'flowchart': {'cells': []},
                            'mapping': {'cells': []}
                        }
                    
                except Exception as e:
                    logging.error(f"Error during model prediction for {file_name}: {str(e)}")
                    predicted_json = {
                        'annotations': {
                            'input_components': [],
                            'transformation_steps': [],
                            'output_components': []
                        },
                        'flowchart': {'cells': []},
                        'mapping': {'cells': []}
                    }
                
                # Compare predictions with ground truth
                results[file_name] = {
                    'ground_truth': ground_truth,
                    'prediction': predicted_json,
                    'success': self._compare_outputs(ground_truth, predicted_json)
                }
                
            except Exception as e:
                logging.error(f"Error processing {file_name}: {str(e)}")
                results[file_name] = {'error': str(e)}
        
        return results

    def _compare_outputs(self, ground_truth: Dict, prediction: Dict) -> bool:
        """Compare predicted output with ground truth.

        Args:
            ground_truth: Ground truth annotations
            prediction: Model predictions

        Returns:
            True if prediction matches ground truth structure
        """
        required_keys = ['input_components', 'transformation_steps', 'output_components']
        
        # Check if all required keys are present
        if not all(key in prediction for key in required_keys):
            return False
            
        # Check if the types match
        return all(
            isinstance(prediction[key], type(ground_truth.get(key, []))) 
            for key in required_keys
        )

def main():
    # Initialize tester
    tester = ModelTester()
    
    # Run tests
    results = tester.test_model('Test')
    
    # Log results
    success_count = sum(1 for r in results.values() if r.get('success', False))
    total_count = len(results)
    
    logging.info(f"Testing completed. Success rate: {success_count}/{total_count}")
    
    # Save detailed results
    with open('test_results.json', 'w', encoding='utf-8') as f:
        json.dump(results, f, indent=2)

if __name__ == "__main__":
    main()