# AI-Driven Talend Code Analyzer & Data Flow Mapper
A Python-based tool for preprocessing and analyzing Talend Java code to extract data flow information and prepare it for LLM-based processing.

## Features

- Automated preprocessing of Talend Java code
- Component extraction (Input, Transformation, Output)
- Code cleaning and standardization
- Metadata generation in JSON format
- Automatic dataset splitting (train/validation/test)
- Comprehensive logging system

## Setup

1. Clone the repository
2. Place your Talend Java files in `raw_data/talend_code/`
3. Ensure Python 3.7+ is installed

## Usage

Run the preprocessing pipeline:
```bash
python Source/preprocess_talend.py
```

## Output Format

The preprocessor generates JSON metadata files with the following structure:
{
    "job_name": "example_job",
    "input_components": [
        {
            "type": "tFileInputDelimited",
            "configuration": "..."
        }
    ],
    "transformation_steps": [
        {
            "type": "tMap",
            "configuration": "..."
        }
    ],
    "output_components": [
        {
            "type": "tFileOutputDelimited",
            "configuration": "..."
        }
    ]
}