import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * User interface for the Commission Data Parser.
 */
public class UserInterface {

  private final Scanner scanner;

  /**
   * Creates a new UserInterface object.
   */
  public UserInterface() {
    this.scanner = new Scanner(System.in);
  }

  /**
   * Starts the user interface.
   */
  public void start() {
    System.out.print(Constants.MSG_START);
    String inputDir = getInputDir();
    String outputDir = getOutput() + "/" + Constants.DEFAULT_OUTPUT_FILE;
    String commissionPeriod = getCommissionPeriod();

    System.out.println(Constants.MSG_PROCESSING);

    // Process the data
    process(inputDir, outputDir, commissionPeriod);

    System.out.println(Constants.MSG_PROCESSING_COMPLETE);

    scanner.close();
  }

  /**
   * Gets the input directory from the user.
   *
   * @return the input directory
   */
  private String getInputDir() {
    String inputDir;
    // Loop to handle invalid directory and missing files
    while (true) {
      System.out.print(
          Constants.MSG_INPUT_DIR);
      inputDir = scanner.nextLine();

      if (inputDir.isEmpty()) {
        inputDir = Constants.DEFAULT_INPUT_DIR;
      }

      if (isValidInputDir(inputDir)) {
        break;
      } else {
        System.out.println(Constants.ERROR_INVALID_INPUT_DIRECTORY);
      }
    }
    return inputDir;
  }

  private String getOutput() {
    String outputDir;

    while (true) {
      System.out.print(Constants.MSG_OUTPUT_DIR);
      outputDir = scanner.nextLine();
      if (outputDir.isEmpty()) {
        outputDir = Constants.DEFAULT_OUTPUT_DIR;
      }
      if (isValidOutputDir(outputDir)) {
        break;
      } else {
        System.out.println(Constants.ERROR_INVALID_OUTPUT_DIRECTORY);
      }
    }
    return outputDir;
  }

  private String getCommissionPeriod() {
    String commissionPeriod;
    while (true) {
      System.out.print(
          Constants.MSG_COMMISSION_PERIOD);
      commissionPeriod = scanner.nextLine();
      if (commissionPeriod.isEmpty()) {
        commissionPeriod = Constants.DEFAULT_COMMISSION_PERIOD;
      }
      if (commissionPeriod.matches("^(0[1-9]|1[0-2])/(20)\\d{2}$")) {
        break;
      } else {
        System.out.println(Constants.ERROR_PERIOD);
      }
    }
    return commissionPeriod;
  }

  /**
   * Processes the data.
   *
   * @param inputDir  the input directory
   * @param outputDir the output directory
   */
  public void process(String inputDir, String outputDir, String commissionPeriod) {
    // Parse data concurrently
    CommissionDataParser parser = new CommissionDataParser(inputDir);
    List<CommissionRecord> records = parser.parseData();

    // Write to CSV
    CsvWriter writer = new CsvWriter(outputDir, records);
    writer.write();

    // Calculate top performers
    TopPerformersCalculator calculator = new TopPerformersCalculator();
    List<Map.Entry<String, Double>> agentRankList = calculator.getTopPerformersByEntity(records,
        true, commissionPeriod);

    // Display results
    calculator.displayTopPerformers(agentRankList, true);

    scanner.close();
  }

  /**
   * Checks if the input directory is valid.
   *
   * @param inputDir input directory
   * @return true if the input directory is valid, false otherwise
   */
  public static boolean isValidInputDir(String inputDir) {
    File directory = new File(inputDir);
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isFile() && (file.getName().endsWith(Constants.EXCEL_EXTENSION_XLS)
            || file.getName()
            .endsWith(Constants.EXCEL_EXTENSION_XLSX))) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean isValidOutputDir(String outputDir) {
    File directory = new File(outputDir);
    return directory.exists() && directory.isDirectory();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInterface that = (UserInterface) o;
    return Objects.equals(scanner, that.scanner);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(scanner);
  }

  @Override
  public String toString() {
    return "UserInterface{" +
        "scanner=" + scanner +
        '}';
  }
}
