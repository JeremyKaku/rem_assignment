/**
 * The Constants class contains all the constants used in the application. It contains file
 * extensions, directories, error messages, messages, date formats, and default values. The class is
 * final and cannot be extended.
 */
public final class Constants {

  // Files
  /**
   * The extension for CSV files.
   */
  public static final String EXCEL_EXTENSION_XLS = ".xls";
  /**
   * The extension for Excel files.
   */
  public static final String EXCEL_EXTENSION_XLSX = ".xlsx";

  //File Names
  /**
   * The name of the output file.
   */
  public static final String CARRIER_HEALTHFIRST = "healthfirst";
  /**
   * The carrier name for Healthfirst.
   */
  public static final String CARRIER_HEALTHFIRST_REAL = "Healthfirst";
  /**
   * The carrier name for Emblem.
   */
  public static final String CARRIER_EMBLEM = "emblem";
  /**
   * The carrier name for Emblem.
   */
  public static final String CARRIER_EMBLEM_REAL = "Emblem";
  /**
   * The carrier name for Centene.
   */
  public static final String CARRIER_CENTENE = "centene";
  /**
   * The carrier name for Centene.
   */
  public static final String CARRIER_CENTENE_REAL = "Centene";

  // Directories
  /**
   * The default input directory.
   */
  public static final String DEFAULT_INPUT_DIR = "src/main/resource/input";
  /**
   * The default output directory.
   */
  public static final String DEFAULT_OUTPUT_DIR = "src/main/resource/output";
  /**
   * The default output file name.
   */
  public static final String DEFAULT_OUTPUT_FILE = "commission.csv";

  // Error messages
  /**
   * The message displayed when the input directory is invalid.
   */
  public static final String ERROR_INVALID_INPUT_DIRECTORY = "Invalid input directory or missing files. Please try again.(Press Enter key to use the default path)\n";
  /**
   * The message displayed when the output directory is invalid.
   */
  public static final String ERROR_INVALID_OUTPUT_DIRECTORY = "Invalid output directory. Please try again.(Press Enter key to use the default path)\n";
  /**
   * The message displayed when the commission period is invalid.
   */
  public static final String ERROR_PERIOD = "Invalid commission period. Please try again.(Press Enter key to use the default period)\n";
  /**
   * The message displayed when the agent name and agency name are both null or empty.
   */
  public static final String ERROR_INVALID_AGENT_NAME = "Agent name and agency name cannot be both null or empty";
  /**
   * The message displayed when no Excel files are found in the specified directory.
   */
  public static final String ERROR_NO_EXCEL = "No Excel files found in the specified directory.";
  /**
   * The message displayed there is unsupported date format.
   */
  public static final String ERROR_UNSUPPORTED_FORMAT = "Unsupported date format: ";

  // Messages
  /**
   * The message displayed when the processing starts.
   */
  public static final String MSG_PROCESSING = "Processing...\n";
  /**
   * The message displayed when the processing is complete.
   */
  public static final String MSG_PROCESSING_COMPLETE = "\nProcessing complete.\n";
  /**
   * The message displayed when the application starts.
   */
  public static final String MSG_START = "\nWelcome to the Commission Data Parser!\n";
  /**
   * The message displayed for the input directory.
   */
  public static final String MSG_INPUT_DIR = "Please enter the input directory: (Press Enter to use the default directory) \n";
  /**
   * The message displayed for the output directory.
   */
  public static final String MSG_OUTPUT_DIR = "Please enter the output directory: (Press Enter to use the default directory) \n";
  /**
   * The message displayed for the commission period.
   */
  public static final String MSG_COMMISSION_PERIOD = "Please enter the commission period (MM/YYYY): (Press Enter to use the default period) \n";
  /**
   * The message displayed top 10 agents.
   */
  public static final String MSG_TOP_AGENT = "Top 10 Agents:\n";
  /**
   * The message displayed top 10 agencies.
   */
  public static final String MSG_TOP_AGENCY = "Top 10 Agencies:\n";
 /**
   * The message displayed Header for the top performers.
   */
  public static final String MSG_TOP_HERADER = "\nRank, Name, Commission Amount";
  /**
   *  The message displayed when no top performers are found.
   */
  public static final String MSG_NO_TOP_PERFORMERS = "No top performers found.";
  /**
   * The date format used in the application.
   */
  public static final String MSG_PERIOD = "Commission Period: ";


  //Default values
  /**
   * The default commission period.
   */
  public static final String DEFAULT_COMMISSION_PERIOD = "06/2024";

  // Other
  /**
   * The rank number.
   */
  public static final String RANK_NUM = "No.";
}