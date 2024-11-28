/**
 * The Constants class contains all the constants used in the application. It contains file
 * extensions, directories, error messages, messages, date formats, and default values. The class is
 * final and cannot be extended.
 */
public final class Constants {
  // Files
  public static final String EXCEL_EXTENSION_XLS = ".xls";
  public static final String EXCEL_EXTENSION_XLSX = ".xlsx";

  //File Names
  public static final String CARRIER_HEALTHFIRST = "healthfirst";
  public static final String CARRIER_HEALTHFIRST_REAL = "Healthfirst";
  public static final String CARRIER_EMBLEM= "emblem";
  public static final String CARRIER_EMBLEM_REAL = "Emblem";
  public static final String CARRIER_CENTENE= "centene";
  public static final String CARRIER_CENTENE_REAL = "Centene";

  // Directories
  public static final String DEFAULT_INPUT_DIR = "src/main/resource/input";
  public static final String DEFAULT_OUTPUT_DIR = "src/main/resource/output";
  public static final String DEFAULT_OUTPUT_FILE = "commission.csv";

  // Error messages
  public static final String ERROR_INVALID_INPUT_DIRECTORY = "Invalid input directory or missing files. Please try again.(Press Enter key to use the default path)\n";
  public static final String ERROR_INVALID_OUTPUT_DIRECTORY = "Invalid output directory. Please try again.(Press Enter key to use the default path)\n";
  public static final String ERROR_PERIOD = "Invalid commission period. Please try again.(Press Enter key to use the default period)\n";
  public static final String ERROR_INVALID_AGENT_NAME = "Agent name and agency name cannot be both null or empty";
  public static final String ERROR_NO_EXCEL = "No Excel files found in the specified directory.";
  public static final String ERROR_UNSUPPORTED_FORMAT = "Unsupported date format: ";

  // Messages
  public static final String MSG_PROCESSING = "Processing...\n";
  public static final String MSG_PROCESSING_COMPLETE = "\nProcessing complete.\n";
  public static final String MSG_START = "\nWelcome to the Commission Data Parser!\n";
  public static final String MSG_INPUT_DIR = "Please enter the input directory: (Press Enter to use the default directory) \n";
  public static final String MSG_OUTPUT_DIR = "Please enter the output directory: (Press Enter to use the default directory) \n";
  public static final String MSG_COMMISSION_PERIOD = "Please enter the commission period (MM/YYYY): (Press Enter to use the default period) \n";
  public static  final  String MSG_TOP_AGENT ="Top 10 Agents:\n";
  public static  final  String MSG_TOP_AGENCY ="Top 10 Agencies:\n";
  public static final String MSG_TOP_HERADER = "Rank, Name, Commission Amount";
  public static final String MSG_NO_TOP_PERFORMERS = "No top performers found.\n";

  //Default values
  public static final String DEFAULT_COMMISSION_PERIOD = "06/2024";

  // Other
  public static final String RANK_NUM = "No.";
}