# Enhanced JSON Output Model

This repository contains a fine-tuned model for generating structured JSON outputs from Talend Java job files. The model is specifically trained to produce enhanced JSON representations with detailed component information, data structures, and processing logic.

## Model Overview

The enhanced model builds upon a base model with specialized training for JSON structure accuracy. It's designed to extract information from Java code and generate comprehensive JSON representations that capture:

- Component details (input, transformation, output)
- Data structures and field mappings
- Processing logic and error handling
- Project metadata

## Training Process

The enhanced model was trained using the following process:

1. **Base Model**: Started with a pre-trained T5 model that was previously fine-tuned on basic JSON generation tasks
2. **Enhanced Fine-tuning**: Further fine-tuned with specialized training data focusing on structured JSON outputs
3. **Training Parameters**:
   - Learning rate: Scheduled with warmup and decay
   - Batch size: Increased for better gradient estimation
   - Epochs: 4 (increased from 3 in the base model)
   - Optimizer: AdamW with weight decay

4. **Training Data**: Used annotated Java code samples paired with their corresponding JSON representations from:
   - `training_data/java_jobs/`: Source Java code files
   - `training_data/annotations/`: Target JSON structures
   - `training_data/mappings/`: Field mapping information

5. **Validation**: Model was validated on a held-out test set to ensure generalization

## Model Files

The trained model is stored in the `enhanced_model_output/final_model/` directory and includes:

- `model.safetensors`: The model weights
- `config.json`: Model configuration
- `tokenizer.json` and related files: Tokenizer configuration
- `generation_config.json`: Parameters for text generation

## Testing the Model

You can test the enhanced model using the provided testing script:

```bash
python test_enhanced_model.py --model_dir ./enhanced_model_output/final_model --java_file ./training_data/java_jobs/xml_to_csv.java --output_file ./enhanced_test_output.json
```

Parameters:
- `--model_dir`: Directory containing the fine-tuned model (default: ./enhanced_model_output/final_model)
- `--java_file`: Path to a Java file to test (required)
- `--output_file`: Path to save the generated enhanced JSON (default: ./enhanced_output_test.json)
- `--compare_with`: Path to the reference enhanced JSON for comparison (default: ./enhanced_output.json)

## Comparing Model Outputs

To compare outputs from different model versions, use the comparison script:

```bash
python compare_models.py --reference ./enhanced_output.json --outputs ./enhanced_test_output.json ./test_output.json
```

Parameters:
- `--reference`: Path to the reference JSON file (ground truth)
- `--outputs`: One or more model output files to compare
- `--report`: Path to save the comparison report (default: comparison_report.md)

The script generates:
- A detailed markdown report with similarity metrics
- Analysis of missing and extra keys in each model output
- Raw comparison data in JSON format

## Performance Metrics

The enhanced model shows improved performance over the base model in several areas:

- Better structural accuracy (more complete JSON schema)
- Improved field mapping representation
- More detailed component descriptions
- Better handling of nested data structures

## Requirements

To use the model and scripts, you need:

- Python 3.8+
- PyTorch
- Transformers library
- pandas
- tabulate (for comparison reports)

Install dependencies with: `pip install torch transformers pandas tabulate`