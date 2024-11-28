# Commission Data Reconciliation Tool

The goal of the tool is to process insurance commission data for agents and agencies, normalize it into a unified format, and identify top performers based on commission payouts. The tool parses real-world anonymized commission data provided in multiple Excel files, normalizes the data for future extensibility, and outputs insights such as the top 10 agents or agencies by commission payouts for the specified period. Additionally, the normalized data is stored as a CSV file, which could be easily imported into a database.


## Usage

Command-line Arguments

Input Directory: Path to the folder containing commission data Excel files. Defaults to the resources/input folder.

Output Directory: Path to the folder where the normalized CSV file will be saved. Defaults to the resources/output folder.

Commission Period: The specific period to process data for (e.g., 06/2024). Defaults to 06/2024.

```
Welcome to the Commission Data Parser!
Please enter the input directory: (Press Enter to use the default directory) 

Please enter the output directory: (Press Enter to use the default directory) 

Please enter the commission period (MM/YYYY): (Press Enter to use the default period) 

Processing...

Top 10 Agents:

Commission Period: 06/2024

Rank, Name, Commission Amount
No.1 , Kirk Baker DBA Carter-Thomas , 30984.50
No.2 , Chelsea Butler Good Leach Senior Services Corp , 29467.09
No.3 , Brittany L Smith, 24335.44
No.4 , Patrick DE LA Evans , 19811.64
No.5 , Laurie Lee, 16490.27
No.6 , Jennifer Jackson, 13209.94
No.7 , Samuel K Ochoa, 12889.79
No.8 , Javier Meyers, 12872.46
No.9 , Kevin V Parks, 12172.78
No.10 , Tracy g Perez DBA Miller PLC , 11523.42

Processing complete.
```

