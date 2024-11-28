import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
//    List<String> inputFiles = Arrays.asList(
//        "Centene 06.2024 Commission.xlsx",
//        "Emblem 06.2024 Commission.xlsx",
//        "Healthfirst 06.2024 Commission.xlsx"
//    );
//

    Scanner scanner = new Scanner(System.in);

    // Prompt the user for the input file path
    System.out.print("Please enter the input directory: (Press Enter to use the default directory) ");
    String inputFilePath = scanner.nextLine();

    if (inputFilePath.isEmpty()) {
      inputFilePath = "src/main/resource/input";
    }

    // Prompt the user for the output CSV file path
    System.out.print("Please enter the output CSV file path: (Press Enter to use the default path) ");
    String outputCsvPath = scanner.nextLine();

    if (outputCsvPath.isEmpty()) {
      outputCsvPath = "src/main/resource/output/commission.csv";
    }

    // Parse data concurrently
    CommissionDataParser parser = new CommissionDataParser(inputFilePath);
    List<CommissionRecord> records = parser.parseData();

    // Normalize data
//    CommissionNormalizer normalizer = new CommissionNormalizer();
//    List<NormalizedRecord> normalizedRecords = normalizer.normalize(records);

    // Write to CSV
    CsvWriter writer = new CsvWriter(outputCsvPath,records);
    writer.write();

    // Calculate top performers
//    TopPerformersCalculator calculator = new TopPerformersCalculator();
//    List<TopPerformer> topPerformers = calculator.calculateTopAgents(normalizedRecords, 10);

    // Display results
//    System.out.println("Top 10 Agents by Payout:");
//    for (TopPerformer performer : topPerformers) {
//      System.out.println(performer);
//    }

      scanner.close();
  }
}