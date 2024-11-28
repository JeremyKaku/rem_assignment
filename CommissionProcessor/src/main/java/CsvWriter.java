import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Writes the data to a CSV file.
 */
public class CsvWriter implements Writable {

  private final String outputCsvPath;
  private final List<CommissionRecord> records;

  /**
   * Constructs a CsvWriter object.
   *
   * @param outputCsvPath The output CSV file path
   * @param records       The list of CommissionRecord objects
   */
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
        writer.append(
                Utils.formatValue(record.agentName() != null ? record.agentName().toString() : ""))
            .append(',')
            .append(Utils.formatValue(record.agencyName()))
            .append(',')
            .append(Utils.formatValue(record.carrierName()))
            .append(',')
            .append(Utils.formatValue(record.commissionPeriod()))
            .append(',')
            .append(Utils.formatValue(String.valueOf(record.commissionAmount())))
            .append(',')
            .append(Utils.formatValue(
                record.memberName() != null ? record.memberName().toString() : ""))
            .append(',')
            .append(Utils.formatValue(record.enrollmentType()))
            .append(',')
            .append(Utils.formatValue(record.planName()))
            .append(',')
            .append(Utils.formatValue(record.effectiveDate()))
            .append(',')
            .append(Utils.formatValue(record.termDate()))
            .append("\n");
      }
    } catch (IOException e) {
      System.err.println("Error writing CSV file: " + e.getMessage());
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CsvWriter csvWriter = (CsvWriter) o;
    return Objects.equals(outputCsvPath, csvWriter.outputCsvPath)
        && Objects.equals(records, csvWriter.records);
  }

  @Override
  public int hashCode() {
    return Objects.hash(outputCsvPath, records);
  }

  @Override
  public String toString() {
    return "CsvWriter{" +
        "outputCsvPath='" + outputCsvPath + '\'' +
        ", records=" + records +
        '}';
  }
}
