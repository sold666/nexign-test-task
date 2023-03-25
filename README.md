# CDR Report Generator

This project is a CDR (Call Detail Record)
report generator that reads data from a CSV file containing call records, processes the data, and generates a report for
each phone number in the file.

## Getting Started

1. To get started with this project, follow these steps:
2. Clone the repository to your local machine.
3. Open the project in your preferred IDE.
4. Make sure that you have the following dependencies added to your project
5. Run the Main class to generate the reports.

## Usage

This project is designed to generate a report for each phone number found in the input file. To use the project, follow
these steps:

Create a `*.txt` file containing call records. Each record should be on a separate line and should contain the following
fields:

* Call type (in the form of an integer)
* Phone number (in the form of a string)
* Start time (in the format `yyyyMMddHHmmss`)
* End time (in the format `yyyyMMddHHmmss`)
* Tariff index (in the form of an integer)
* Save the file as cdr.txt in the project directory.

Run the Main class to generate the reports.

The reports will be saved as text files in the `reports` directory.
