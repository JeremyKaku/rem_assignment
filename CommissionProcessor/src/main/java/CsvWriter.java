import org.etsi.uri.x01903.v13.CertIDListType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter implements Writable {

  private final String outputCsvPath;
  private final List<CommissionRecord> records;

  public CsvWriter(String outputCsvPath, List<CommissionRecord> records) {
    this.outputCsvPath = outputCsvPath;
    this.records = records;
  }

  /**
   * Writes the data to the output file.
   */
  @Override
  public void write() {
    try (FileWriter writer = new FileWriter(outputCsvPath)) {
      // Write header
      writer.append(
          "Agent Name,Agency Name,Carrier Name,Commission Period,Commission Amount,Member Name,Enrollment Type,Plan Name,Effective Date,Term Date\n");

      // Write records
      for (CommissionRecord record : records) {
        writer.append(formatValue(record.agentName() != null ? record.agentName().toString() : ""))
            .append(',')
            .append(formatValue(record.agencyName()))
            .append(',')
            .append(formatValue(record.carrierName()))
            .append(',')
            .append(formatValue(record.commissionPeriod()))
            .append(',')
            .append(formatValue(String.valueOf(record.commissionAmount())))
            .append(',')
            .append(formatValue(record.memberName() != null ? record.memberName().toString() : ""))
            .append(',')
            .append(formatValue(record.enrollmentType()))
            .append(',')
            .append(formatValue(record.planName()))
            .append(',')
            .append(formatValue(record.effectiveDate()))
            .append(',')
            .append(formatValue(record.termDate()))
            .append("\n");
      }
    } catch (IOException e) {
      System.err.println("Error writing CSV file: " + e.getMessage());
    }
  }

  private String formatValue(String value) {
    if (value == null) {
      return "";
    }
    // Escape double quotes and wrap the value in quotes if it contains special characters
    String escapedValue = value.replace("\"", "\"\"");
    if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
      return '"' + escapedValue + '"';
    }
    return escapedValue;
  }


}
