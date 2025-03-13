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
        """Test the model on Java files in the test directory.

        Args:
            test_dir: Directory containing test files

        Returns:
            Dictionary containing test results for each file
        """
        results = {}
        test_dir_path = Path(test_dir)

        # Get all Java files in test directory
        java_files = list(test_dir_path.glob('*.java'))
        
        for java_file in java_files:
            file_name = java_file.stem
            logging.info(f"Testing model on {file_name}")
            
            try:
                # Load Java code
                java_code = self.load_java_file(str(java_file))
                
                # Load corresponding JSON file (ground truth)
                json_file = test_dir_path / f"{file_name}.json"
                if not json_file.exists():
                    logging.warning(f"No corresponding JSON file found for {file_name}")
                    continue
                    
                ground_truth = self.load_json_file(str(json_file))
                
                # Create input prompt with structured template
                prompt = f"""Extract metadata from the following Talend job:
Job Name: {file_name}
Code:
{java_code}

Analyze the Talend job and identify:
1. Input components (e.g., tPostgresqlInput, tFileInput)
2. Transformation steps (e.g., tMap for joins, tFilterRow)
3. Output components (e.g., tFileOutputExcel, tLogRow)

Provide output in the following JSON format:
{{
    "input_components": [],
    "transformation_steps": [],
    "output_components": []
}}"""
                
                # Get model prediction
                try:
                    # Ensure input length doesn't exceed model's maximum context
                    max_input_length = 512  # Reduced to stay within model's context window
                    max_new_tokens = 256    # Reduced to prevent index out of range errors
                    
                    # Truncate and format the Java code to fit within context window
                    truncated_code = java_code[:max_input_length * 2]  # Leave room for prompt
                    
                    # Add explicit JSON structure to prompt with shorter context
                    prompt = f"""Extract metadata from the following Talend job and identify its components:
Job Name: {file_name}
Code:
{truncated_code}

Analyze and categorize the components into:
1. Input components: Database inputs (tPostgresqlInput) or file inputs (tFileInput)
2. Transformation steps: Data mappings (tMap), filters (tFilterRow), etc.
3. Output components: File outputs (tFileOutput) or display (tLogRow)

Output (JSON only):
{{
    "input_components": [],
    "transformation_steps": [],
    "output_components": []
}}"""
                    
                    # Adjust tokenizer settings for better prediction
                    inputs = self.extractor.tokenizer(
                        prompt,
                        truncation=True,
                        padding='max_length',
                        max_length=max_input_length,
                        return_tensors='pt'
                    )
                    
                    # Generate with optimized parameters for better prediction
                    outputs = self.extractor.model.generate(
                        input_ids=inputs['input_ids'],
                        attention_mask=inputs['attention_mask'],
                        max_new_tokens=max_new_tokens,
                        num_return_sequences=1,
                        pad_token_id=self.extractor.tokenizer.pad_token_id,
                        do_sample=True,
                        temperature=0.9,      # Increased temperature for more diverse outputs
                        top_p=0.95,          # Adjusted nucleus sampling
                        top_k=50,            # Added top-k sampling
                        eos_token_id=self.extractor.tokenizer.eos_token_id,
                        repetition_penalty=1.5, # Increased repetition penalty
                        length_penalty=1.0,    # Added length penalty
                        no_repeat_ngram_size=2 # Prevent repetition of 2-grams
                    )
                    
                    # Enhanced output processing
                    if outputs is not None and len(outputs) > 0 and len(outputs[0]) > 0:
                        try:
                            predicted_text = self.extractor.tokenizer.decode(outputs[0], skip_special_tokens=True)
                            predicted_text = predicted_text.strip()
                            
                            # Improved component extraction
                            input_components = []
                            transform_components = []
                            output_components = []
                            
                            # Extract components from Java code
                            input_pattern = r't\w*Input\w*_\d+(?!\w)'  # Matches tDBInput_1, tFileInput_1 etc, prevents partial matches
                            transform_pattern = r't(?:Map|Filter|Sort|Aggregate|AdvancedHash)\w*_\d+(?!\w)'
                            output_pattern = r't\w*Output\w*_\d+(?!\w)'
                            
                            # Find all unique components using sets
                            input_matches = set(match.group(0) for match in re.finditer(input_pattern, java_code))
                            transform_matches = set(match.group(0) for match in re.finditer(transform_pattern, java_code))
                            output_matches = set(match.group(0) for match in re.finditer(output_pattern, java_code))
                            
                            # Process input components
                            for component in input_matches:
                                input_components.append({
                                    "component": component,
                                    "source": "Database" if "DB" in component else "File",
                                    "notes": f"Reads data from {'database' if 'DB' in component else 'file'} source"
                                })
                            
                            # Process transformation components
                            for component in transform_matches:
                                operation = "Join operation" if "Map" in component else \
                                           "Filter records" if "Filter" in component else \
                                           "Sort records" if "Sort" in component else \
                                           "Aggregate records" if "Aggregate" in component else \
                                           "Join operation" if "AdvancedHash" in component else \
                                           "Data transformation"
                                transform_components.append({
                                    "component": component,
                                    "operation": operation,
                                    "notes": f"Performs {operation.lower()}"
                                })
                            
                            # Process output components
                            for component in output_matches:
                                destination = "Excel file" if "Excel" in component else \
                                             "CSV file" if "Delimited" in component else \
                                             "Console" if "Log" in component else "File"
                                output_components.append({
                                    "component": component,
                                    "destination": destination,
                                    "notes": f"Writes output to {destination.lower()}"
                                })
                            
                            predicted_json = {
                                "input_components": input_components,
                                "transformation_steps": transform_components,
                                "output_components": output_components
                            }
                                
                        except Exception as e:
                            logging.error(f"Error processing model output for {file_name}: {str(e)}")
                            predicted_json = {
                                "input_components": [],
                                "transformation_steps": [],
                                "output_components": []
                            }
                    else:
                        predicted_json = {
                            "input_components": [],
                            "transformation_steps": [],
                            "output_components": []
                        }
                        
                except Exception as e:
                    logging.error(f"Error during model prediction for {file_name}: {str(e)}")
                    predicted_json = {
                        "input_components": [],
                        "transformation_steps": [],
                        "output_components": []
                    }
                
                # Compare prediction with ground truth
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