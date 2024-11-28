import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.poi.ss.usermodel.Row;

/**
 * Utility class for common methods.
 */
public class Utils {

  /**
   * Extracts the date from the file name.
   *
   * @param fileName the name of the file
   * @return the date string
   */
  public static String extractDateFromFileName(String fileName) {
    return fileName.replaceAll(".*(\\d{2}\\.\\d{4}).*", "$1");
  }

  /**
   * Checks if two strings are similar.
   *
   * @param name1 the first string
   * @param name2 the second string
   * @return true if the strings are similar, false otherwise
   */
  public static boolean areSimilar(String name1, String name2) {
    JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
    double similarityScore = similarity.apply(name1, name2);
    return similarityScore >= 0.85; // Threshold for similarity
  }


  /**
   * Extracts the carrier name from the file name.
   *
   * @param fileName the name of the file
   * @return the carrier name
   */
  public static String extractCarrierName(String fileName) {
    if (fileName.toLowerCase().contains(Constants.CARRIER_HEALTHFIRST)) {
      return Constants.CARRIER_HEALTHFIRST_REAL;
    }
    if (fileName.toLowerCase().contains(Constants.CARRIER_EMBLEM)) {
      return Constants.CARRIER_EMBLEM_REAL;
    }
    if (fileName.toLowerCase().contains(Constants.CARRIER_CENTENE)) {
      return Constants.CARRIER_CENTENE_REAL;
    }
    return "";
  }

  /**
   * Gets the cell value from the row at the specified index.
   *
   * @param row       the row to get the cell value from
   * @param cellIndex the index of the cell
   * @return the cell value
   */
  public static String getCellValue(Row row, int cellIndex) {
    if (cellIndex == -1 || row.getCell(cellIndex) == null) {
      return "";
    }
    return row.getCell(cellIndex).toString().trim();
  }


  /**
   * Formats the value for CSV output.
   *
   * @param value the value
   * @return the formatted value
   */
  public static String formatValue(String value) {
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
