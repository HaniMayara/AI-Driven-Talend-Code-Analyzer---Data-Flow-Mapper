import os
import json
import re
import shutil
from pathlib import Path
import logging
from typing import Dict, List, Optional

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler('preprocessing.log'),
        logging.StreamHandler()
    ]
)

class TalendPreprocessor:
    def __init__(self, raw_dir: str, processed_dir: str, training_dir: str):
        """Initialize the Talend code preprocessor.

        Args:
            raw_dir (str): Directory containing raw Talend Java files
            processed_dir (str): Directory for cleaned code output
            training_dir (str): Directory for training data and annotations
        """
        self.raw_dir = Path(raw_dir)
        self.processed_dir = Path(processed_dir)
        self.training_dir = Path(training_dir)
        
        # Create necessary directories
        self.processed_dir.mkdir(parents=True, exist_ok=True)
        (self.training_dir / 'annotations').mkdir(parents=True, exist_ok=True)
        (self.training_dir / 'train').mkdir(parents=True, exist_ok=True)
        (self.training_dir / 'validation').mkdir(parents=True, exist_ok=True)
        (self.training_dir / 'test').mkdir(parents=True, exist_ok=True)

    def clean_code(self, content: str) -> str:
        """Clean Java code by removing comments and extra whitespace.

        Args:
            content (str): Raw Java code content

        Returns:
            str: Cleaned Java code
        """
        # Remove multi-line comments
        content = re.sub(r'/\*[^*]*\*+(?:[^/*][^*]*\*+)*/', '', content)
        
        # Remove single-line comments
        content = re.sub(r'//.*$', '', content, flags=re.MULTILINE)
        
        # Remove empty lines and normalize whitespace
        lines = [line.strip() for line in content.splitlines()]
        lines = [line for line in lines if line]
        
        return '\n'.join(lines)

    def extract_components(self, content: str) -> Dict[str, List[str]]:
        """Extract Talend components and their configurations from the code.

        Args:
            content (str): Cleaned Java code content

        Returns:
            Dict[str, List[str]]: Dictionary of component types and their code blocks
        """
        components = {
            'input_components': [],
            'transformation_steps': [],
            'output_components': []
        }
        
        # Extract input components (e.g., tFileInputDelimited)
        input_matches = re.finditer(r'(tFileInput\w+|tDBInput\w+)\s*\w+\s*=\s*new\s*\1\(\);([^;]*);', content)
        for match in input_matches:
            components['input_components'].append(match.group(0))
        
        # Extract transformation components (e.g., tMap)
        transform_matches = re.finditer(r'(tMap|tAggregateRow|tFilterRow)\s*\w+\s*=\s*new\s*\1\(\);([^;]*);', content)
        for match in transform_matches:
            components['transformation_steps'].append(match.group(0))
        
        # Extract output components (e.g., tFileOutputDelimited)
        output_matches = re.finditer(r'(tFileOutput\w+|tDBOutput\w+)\s*\w+\s*=\s*new\s*\1\(\);([^;]*);', content)
        for match in output_matches:
            components['output_components'].append(match.group(0))
        
        return components

    def generate_metadata(self, file_path: Path, components: Dict[str, List[str]]) -> Dict:
        """Generate JSON metadata for a Talend job.

        Args:
            file_path (Path): Path to the Java file
            components (Dict[str, List[str]]): Extracted components

        Returns:
            Dict: Job metadata in JSON format
        """
        return {
            "job_name": file_path.stem,
            "input_components": [
                {"type": re.search(r'(tFileInput\w+|tDBInput\w+)', comp).group(1),
                 "configuration": comp}
                for comp in components['input_components']
            ],
            "transformation_steps": [
                {"type": re.search(r'(tMap|tAggregateRow|tFilterRow)', comp).group(1),
                 "configuration": comp}
                for comp in components['transformation_steps']
            ],
            "output_components": [
                {"type": re.search(r'(tFileOutput\w+|tDBOutput\w+)', comp).group(1),
                 "configuration": comp}
                for comp in components['output_components']
            ]
        }

    def process_file(self, file_path: Path) -> Optional[Dict]:
        """Process a single Talend Java file.

        Args:
            file_path (Path): Path to the Java file

        Returns:
            Optional[Dict]: Generated metadata if successful, None otherwise
        """
        try:
            # Read and clean the code
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()
            
            cleaned_content = self.clean_code(content)
            
            # Extract components
            components = self.extract_components(cleaned_content)
            
            # Generate metadata
            metadata = self.generate_metadata(file_path, components)
            
            # Save cleaned code
            cleaned_file = self.processed_dir / file_path.name
            with open(cleaned_file, 'w', encoding='utf-8') as f:
                f.write(cleaned_content)
            
            # Save metadata
            metadata_file = self.training_dir / 'annotations' / f'{file_path.stem}.json'
            with open(metadata_file, 'w', encoding='utf-8') as f:
                json.dump(metadata, f, indent=2)
            
            logging.info(f'Successfully processed {file_path.name}')
            return metadata
            
        except Exception as e:
            logging.error(f'Error processing {file_path.name}: {str(e)}')
            return None

    def split_dataset(self, train_ratio: float = 0.7, val_ratio: float = 0.15):
        """Split processed files into train/validation/test sets.

        Args:
            train_ratio (float): Ratio for training set
            val_ratio (float): Ratio for validation set
        """
        import random
        
        # Get all processed files
        processed_files = list(self.processed_dir.glob('*.java'))
        random.shuffle(processed_files)
        
        # Calculate split indices
        n_files = len(processed_files)
        train_idx = int(n_files * train_ratio)
        val_idx = train_idx + int(n_files * val_ratio)
        
        # Split files
        train_files = processed_files[:train_idx]
        val_files = processed_files[train_idx:val_idx]
        test_files = processed_files[val_idx:]
        
        # Move files to respective directories
        for files, subset in [
            (train_files, 'train'),
            (val_files, 'validation'),
            (test_files, 'test')
        ]:
            for file in files:
                # Copy Java file
                shutil.copy2(
                    file,
                    self.training_dir / subset / file.name
                )
                # Copy corresponding annotation
                shutil.copy2(
                    self.training_dir / 'annotations' / f'{file.stem}.json',
                    self.training_dir / subset / f'{file.stem}.json'
                )
        
        logging.info(f'Dataset split complete: {len(train_files)} train, '
                    f'{len(val_files)} validation, {len(test_files)} test')

def main():
    # Initialize preprocessor
    preprocessor = TalendPreprocessor(
        raw_dir='raw_data/talend_code',
        processed_dir='processed_data/cleaned_code',
        training_dir='training_data'
    )
    
    # Process all Java files
    java_files = list(preprocessor.raw_dir.glob('*.java'))
    processed_files = []
    
    for file in java_files:
        if metadata := preprocessor.process_file(file):
            processed_files.append(file)
    
    # Split dataset if files were processed successfully
    if processed_files:
        preprocessor.split_dataset()
        logging.info('Preprocessing pipeline completed successfully')
    else:
        logging.error('No files were processed successfully')

if __name__ == '__main__':
    main()