import java.util.Locale;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.Iterator;
import java.util.concurrent.Future;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.YearMonth;

/**
 * Parses commission data from Excel files in a given directory.
 */
public class CommissionDataParser {

  /**
   * Parses the commission data from the Excel files in the given directory.
   *
   * @param directoryPath the path to the directory containing the Excel files
   * @return a list of CommissionRecord objects
   */
  public List<CommissionRecord> parseData(String directoryPath) {
    File directory = new File(directoryPath);
    File[] excelFiles = directory.listFiles(
        (dir, name) -> name.endsWith(Constants.EXCEL_EXTENSION_XLSX) || name.endsWith(
            Constants.EXCEL_EXTENSION_XLS));
    if (excelFiles == null || excelFiles.length == 0) {
      throw new IllegalArgumentException(Constants.ERROR_NO_EXCEL);
    }

    ExecutorService executor = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors());
    List<Future<List<CommissionRecord>>> futures = new ArrayList<>();

    for (File file : excelFiles) {
      futures.add(executor.submit(() -> parseFile(file)));
    }

    List<CommissionRecord> allRecords = new ArrayList<>();
    for (Future<List<CommissionRecord>> future : futures) {
      try {
        allRecords.addAll(future.get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    executor.shutdown();

    updateMiddleNames(allRecords);
    return allRecords;
  }

  /**
   * Parses the commission data from an Excel file.
   *
   * @param file the Excel file to parse
   * @return a list of CommissionRecord objects
   * @throws IOException if an I/O error occurs
   */
  private List<CommissionRecord> parseFile(File file) throws IOException {
    List<CommissionRecord> records = new ArrayList<>();
    String fileName = file.getName();

    try (FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis)) {

      for (Sheet sheet : workbook) {
        Iterator<Row> rowIterator = sheet.rowIterator();

        if (!rowIterator.hasNext()) {
          continue;
        }
        Row headerRow = rowIterator.next();
        Map<String, Integer> headerMap = mapHeaders(headerRow);

        while (rowIterator.hasNext()) {
          Row row = rowIterator.next();

          Name agentName = Name.parse(getAgentName(row, headerMap, fileName));
          String agencyName = getAgencyName(row, headerMap, fileName);
          String carrierName = Utils.extractCarrierName(fileName);
          String commissionPeriod = convertToMonthYear(
              getCommissionPeriod(row, headerMap, fileName));
          double commissionAmount = getCommissionAmount(row, headerMap, fileName);
          Name memberName = Name.parse(getMemberName(row, headerMap, fileName));
          String enrollmentType = getEnrollmentType(row, headerMap, fileName);
          String planName = getPlanName(row, headerMap, fileName);
          String effectiveDate = convertToDate(getEffectiveDate(row, headerMap, fileName));
          String termDate = convertToDate(getTermDate(row, headerMap, fileName));

          records.add(new CommissionRecord(
              agentName,
              agencyName,
              carrierName,
              commissionPeriod,
              commissionAmount,
              memberName,
              enrollmentType,
              planName,
              effectiveDate,
              termDate
          ));
        }
      }
    }
    return records;
  }

  /**
   * Maps the headers of the Excel file to their respective column indices.
   *
   * @param headerRow the header row of the Excel file
   * @return a map of headers to column indices
   */
  private Map<String, Integer> mapHeaders(Row headerRow) {
    Map<String, Integer> headerMap = new HashMap<>();
    for (Cell cell : headerRow) {
      headerMap.put(cell.getStringCellValue().trim(), cell.getColumnIndex());
    }
    return headerMap;
  }

  /**
   * Extracts the agent name from the row based on the file name.
   *
   * @param row       the row to extract the agent name from
   * @param headerMap the map of headers to column indices
   * @param fileName  the name of the file
   * @return the agent name
   */
  private String getAgentName(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains(Constants.CARRIER_HEALTHFIRST)) {
      String producerType = Utils.getCellValue(row, headerMap.getOrDefault("Producer Type", -1));
      return producerType.equalsIgnoreCase("Agent") || producerType.equalsIgnoreCase("Broker") ?
          Utils.getCellValue(row, headerMap.getOrDefault("Producer Name", -1)) : null;
    } else if (fileName.toLowerCase().contains("emblem")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Rep Name", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Writing Broker Name", -1));
    }
    return null;
  }

  /**
   * Extracts the agency name from the row based on the file name.
   *
   * @param row       the row to extract the agency name from
   * @param headerMap the map of headers to column indices
   * @param fileName  the name of the file
   * @return the agency name
   */
  private String getAgencyName(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains(Constants.CARRIER_HEALTHFIRST)) {
      String producerType = Utils.getCellValue(row, headerMap.getOrDefault("Producer Type", -1));
      return producerType.equalsIgnoreCase("Agent") || producerType.equalsIgnoreCase("Broker") ?
          null : Utils.getCellValue(row, headerMap.getOrDefault("Producer Name", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Payee Name", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Earner Name", -1));
    }
    return null;
  }

  /**
   * Extracts the commission period from the row based on the file name.
   *
   * @param row       the row to extract the commission period from
   * @param headerMap the map of headers to column indices
   * @param fileName  the name of the file
   * @return the commission period
   */
  private String getCommissionPeriod(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains(Constants.CARRIER_HEALTHFIRST)) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Period", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return Utils.extractDateFromFileName(fileName);
    } else if (fileName.toLowerCase().contains("centene")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Pay Period", -1));
    }
    return null;
  }

  /**
   * Extracts the commission amount from the row based on the file name.
   *
   * @param row       the row to extract the commission amount from
   * @param headerMap the map of headers to column indices
   * @param fileName  the name of the file
   * @return the commission amount
   */
  private Double getCommissionAmount(Row row, Map<String, Integer> headerMap, String fileName) {
    String amount = null;
    if (fileName.toLowerCase().contains(Constants.CARRIER_HEALTHFIRST)) {
      amount = Utils.getCellValue(row, headerMap.getOrDefault("Amount", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      amount = Utils.getCellValue(row, headerMap.getOrDefault("Payment", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      amount = Utils.getCellValue(row, headerMap.getOrDefault("Payment Amount", -1));
    }
    return amount == null ? 0 : Double.parseDouble(amount);
  }

  /**
   * Extracts the member name from the row based on the file name.
   *
   * @param row       the row to extract the member name from
   * @param headerMap the map of headers to column indices
   * @param fileName  the name of the file
   * @return the member name
   */
  private String getMemberName(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains(Constants.CARRIER_HEALTHFIRST)) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Member Name", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Member First Name", -1)) + " "
          + Utils.getCellValue(row,
          headerMap.getOrDefault("Member Last Name", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Member Name", -1));
    }
    return null;
  }

  /**
   * Extracts the enrollment type from the row based on the file name.
   *
   * @param row       the row to extract the enrollment type from
   * @param headerMap the map of headers to column indices
   * @param fileName  the name of the file
   * @return the enrollment type
   */
  private String getEnrollmentType(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains(Constants.CARRIER_HEALTHFIRST)) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Enrollment Type", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Payment Type", -1));
    }
    return null;
  }

  /**
   * Extracts the plan name from the row based on the file name.
   *
   * @param row       the row to extract the plan name from
   * @param headerMap the map of headers to column indices
   * @param fileName  the name of the file
   * @return the plan name
   */
  private String getPlanName(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains(Constants.CARRIER_HEALTHFIRST)) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Product", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Plan", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Plan Name", -1));
    }
    return null;
  }

  /**
   * Extracts the effective date from the row based on the file name.
   *
   * @param row       the row to extract the effective date from
   * @param headerMap the map of headers to column indices
   * @param fileName  the name of the file
   * @return the effective date
   */
  private String getEffectiveDate(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains(Constants.CARRIER_HEALTHFIRST)) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Member Effective Date", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Effective Date", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Effective Date", -1));
    }
    return null;
  }

  /**
   * Extracts the term date from the row based on the file name.
   *
   * @param row       the row to extract the term date from
   * @param headerMap the map of headers to column indices
   * @param fileName  the name of the file
   * @return the term date
   */
  private String getTermDate(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains(Constants.CARRIER_HEALTHFIRST)) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Disenrolled Date", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Term Date", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return Utils.getCellValue(row, headerMap.getOrDefault("Member Term Date", -1));
    }
    return null;
  }

  /**
   * Converts the date string to the format MM/yyyy.
   *
   * @param dateStr the date string to convert
   * @return the date string in the format MM/yyyy
   */
  private String convertToMonthYear(String dateStr) {
    if (dateStr == null || dateStr.isBlank()) {
      return null;
    }
    try {
      if (dateStr.matches("\\d{1,2}\\.\\d{4}")) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M.yyyy", Locale.ENGLISH);
        YearMonth yearMonth = YearMonth.parse(dateStr, formatter);
        return yearMonth.format(DateTimeFormatter.ofPattern("MM/yyyy"));
      }

      if (dateStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
      }

      if (dateStr.matches("\\d{2}/\\d{4}")) {
        return dateStr;
      }

      if (dateStr.matches("\\d{2}-[a-zA-Z]{3}-\\d{4}")) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
      }

    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(Constants.ERROR_UNSUPPORTED_FORMAT + dateStr, e);
    }
    throw new IllegalArgumentException(Constants.ERROR_UNSUPPORTED_FORMAT + dateStr);
  }

  /**
   * Converts the date string to the format MM/dd/yyyy.
   *
   * @param dateStr the date string to convert
   * @return the date string in the format MM/dd/yyyy
   */
  private String convertToDate(String dateStr) {
    if (dateStr == null || dateStr.isBlank()) {
      return null;
    }
    try {
      if (dateStr.matches("\\d{1,2}\\.\\d{4}")) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M.yyyy", Locale.ENGLISH);
        YearMonth yearMonth = YearMonth.parse(dateStr, formatter);
        return yearMonth.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
      }

      if (dateStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
      }

      if (dateStr.matches("\\d{2}/\\d{4}")) {
        return dateStr;
      }

      if (dateStr.matches("\\d{2}-[a-zA-Z]{3}-\\d{4}")) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
      }

    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(Constants.ERROR_UNSUPPORTED_FORMAT + dateStr, e);
    }
    throw new IllegalArgumentException(Constants.ERROR_UNSUPPORTED_FORMAT + dateStr);
  }

  /**
   * Updates the middle names of the agent and member names in the list of CommissionRecord
   * objects.
   *
   * @param records the list of CommissionRecord objects
   */
  private void updateMiddleNames(List<CommissionRecord> records) {
    Map<String, Name> agentNameMap = new HashMap<>();
    Map<String, Name> memberNameMap = new HashMap<>();
    Map<String, String> agencyNameMap = new HashMap<>();

    // First pass: populate the maps with existing names and middle names
    for (CommissionRecord record : records) {
      if (record.agentName() != null) {
        String agentKey = generateKey(record.agentName().getFirstName(),
            record.agentName().getLastName());
        agentNameMap.putIfAbsent(agentKey, record.agentName());
        Name existingAgentName = agentNameMap.get(agentKey);

        // Update the middle name if it's missing
        if (existingAgentName.getMiddleName() == null
            && record.agentName().getMiddleName() != null) {
          existingAgentName.setMiddleName(record.agentName().getMiddleName());
        }
      }

      // Update the middle name if it's missing
      if (record.memberName() != null) {
        String memberKey = generateKey(record.memberName().getFirstName(),
            record.memberName().getLastName());
        memberNameMap.putIfAbsent(memberKey, record.memberName());
        Name existingMemberName = memberNameMap.get(memberKey);

        // Update the middle name if it's missing
        if (existingMemberName.getMiddleName() == null
            && record.memberName().getMiddleName() != null) {
          existingMemberName.setMiddleName(record.memberName().getMiddleName());
        }
      }

      // Update the agency name if it's missing
      if (record.agencyName() != null) {
        for (String key : agencyNameMap.keySet()) {
          if (Utils.areSimilar(record.agencyName(), key)) {
            agencyNameMap.put(key, record.agencyName());
            break;
          }
        }
        agencyNameMap.putIfAbsent(record.agencyName(), record.agencyName());
      }
    }

    // Second pass: create new CommissionRecord objects with updated names
    for (int i = 0; i < records.size(); i++) {
      CommissionRecord record = records.get(i);

      Name updatedAgentName = null;
      if (record.agentName() != null) {
        String agentKey = generateKey(record.agentName().getFirstName(),
            record.agentName().getLastName());
        updatedAgentName = agentNameMap.get(agentKey);
      }

      Name updatedMemberName = null;
      if (record.memberName() != null) {
        String memberKey = generateKey(record.memberName().getFirstName(),
            record.memberName().getLastName());
        updatedMemberName = memberNameMap.get(memberKey);
      }

      String updatedAgencyName = record.agencyName();
      if (record.agencyName() != null && agencyNameMap.containsKey(record.agencyName())) {
        for (String key : agencyNameMap.keySet()) {
          if (Utils.areSimilar(record.agencyName(), key) || record.agencyName().toLowerCase()
              .contains(key.toLowerCase()) || key.toLowerCase()
              .contains(record.agencyName().toLowerCase())) {
            updatedAgencyName = agencyNameMap.get(key);
            break;
          }
        }
      }

      // Replace the old record with a new one, with updated names
      records.set(i, new CommissionRecord(
          updatedAgentName,
          updatedAgencyName,
          record.carrierName(),
          record.commissionPeriod(),
          record.commissionAmount(),
          updatedMemberName,
          record.enrollmentType(),
          record.planName(),
          record.effectiveDate(),
          record.termDate()
      ));
    }
  }

  /**
   * Generates a key for the given first and last names.
   *
   * @param firstName the first name
   * @param lastName  the last name
   * @return the generated key
   */
  private String generateKey(String firstName, String lastName) {
    return firstName.toLowerCase() + "_" + lastName.toLowerCase();
  }
}
