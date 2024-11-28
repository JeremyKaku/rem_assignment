import java.util.Locale;
import org.apache.poi.ss.formula.atp.Switch;
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
import java.text.*;


public class CommissionDataParser {

  private final String directoryPath;

  public CommissionDataParser(String directoryPath) {
    this.directoryPath = directoryPath;
  }

  public List<CommissionRecord> parseData() {
    File directory = new File(directoryPath);
    File[] excelFiles = directory.listFiles((dir, name) -> name.endsWith(".xlsx"));
    if (excelFiles == null || excelFiles.length == 0) {
      System.out.println("No Excel files found in the specified directory.");
      throw new IllegalArgumentException("No Excel files found in the specified directory.");
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

    return allRecords;
  }

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
        Map<String, Integer> headerMap = mapHeaders(headerRow, fileName);

        while (rowIterator.hasNext()) {
          Row row = rowIterator.next();
          SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

          Name agentName = Name.parse(getAgentName(row, headerMap, fileName));
          String agencyName = getAgencyName(row, headerMap, fileName);
          String carrierName = extractCarrierName(fileName);
          String commissionPeriod = sdf.format(getCommissionPeriod(row, headerMap, fileName));
          double commissionAmount = getCommissionAmount(row, headerMap, fileName);
          Name memberName = Name.parse(getMemberName(row, headerMap, fileName));
          String enrollmentType = getEnrollmentType(row, headerMap, fileName);
          String planName = getPlanName(row, headerMap, fileName);
          String effectiveDate = getEffectiveDate(row, headerMap, fileName);
          String termDate = getTermDate(row, headerMap, fileName);

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

  private Map<String, Integer> mapHeaders(Row headerRow, String fileName) {
    Map<String, Integer> headerMap = new HashMap<>();
    for (Cell cell : headerRow) {
      headerMap.put(cell.getStringCellValue().trim(), cell.getColumnIndex());
    }
    return headerMap;
  }

  private String getAgentName(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains("healthfirst")) {
      String producerType = getCellValue(row, headerMap.getOrDefault("Producer Type", -1));
      return producerType.equalsIgnoreCase("Agent") ?
          getCellValue(row, headerMap.getOrDefault("Producer Name", -1)) : null;
    } else if (fileName.toLowerCase().contains("emblem")) {
      return getCellValue(row, headerMap.getOrDefault("Rep Name", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return getCellValue(row, headerMap.getOrDefault("Writing Broker Name", -1));
    }
    return null;
  }

  private String getAgencyName(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains("healthfirst")) {
      String producerType = getCellValue(row, headerMap.getOrDefault("Producer Type", -1));
      return producerType.equalsIgnoreCase("Agent") ?
          null : getCellValue(row, headerMap.getOrDefault("Producer Name", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return getCellValue(row, headerMap.getOrDefault("Payee Name", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return getCellValue(row, headerMap.getOrDefault("Earner Name", -1));
    }
    return null;
  }

  private String getCommissionPeriod(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains("healthfirst")) {
      return getCellValue(row, headerMap.getOrDefault("Period", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return extractDateFromFileName(fileName);
    } else if (fileName.toLowerCase().contains("centene")) {
      return getCellValue(row, headerMap.getOrDefault("Pay Period", -1));
    }
    return null;
  }

  private Double getCommissionAmount(Row row, Map<String, Integer> headerMap, String fileName) {
    String amount = null;
    if (fileName.toLowerCase().contains("healthfirst")) {
      amount = getCellValue(row, headerMap.getOrDefault("Amount", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      amount = getCellValue(row, headerMap.getOrDefault("Payment", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      amount = getCellValue(row, headerMap.getOrDefault("Payment Amount", -1));
    }
    return amount == null ? 0 : Double.parseDouble(amount);
  }

  private String getMemberName(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains("healthfirst")) {
      return getCellValue(row, headerMap.getOrDefault("Member Name", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return getCellValue(row, headerMap.getOrDefault("Member First Name", -1)) + " "
          + getCellValue(row,
          headerMap.getOrDefault("Member Last Name", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return getCellValue(row, headerMap.getOrDefault("Member Name", -1));
    }
    return null;
  }

  private String getEnrollmentType(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains("healthfirst")) {
      return getCellValue(row, headerMap.getOrDefault("Enrollment Type", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return getCellValue(row, headerMap.getOrDefault("Payment Type", -1));
    }
    return null;
  }

  private String getPlanName(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains("healthfirst")) {
      return getCellValue(row, headerMap.getOrDefault("Product", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return getCellValue(row, headerMap.getOrDefault("Plan", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return getCellValue(row, headerMap.getOrDefault("Plan Name", -1));
    }
    return null;
  }

  private String getEffectiveDate(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains("healthfirst")) {
      return getCellValue(row, headerMap.getOrDefault("Member Effective Date", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return getCellValue(row, headerMap.getOrDefault("Effective Date", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return getCellValue(row, headerMap.getOrDefault("Effective Date", -1));
    }
    return null;
  }

  private String getTermDate(Row row, Map<String, Integer> headerMap, String fileName) {
    if (fileName.toLowerCase().contains("healthfirst")) {
      return getCellValue(row, headerMap.getOrDefault("Disenrolled Date", -1));
    } else if (fileName.toLowerCase().contains("emblem")) {
      return getCellValue(row, headerMap.getOrDefault("Term Date", -1));
    } else if (fileName.toLowerCase().contains("centene")) {
      return getCellValue(row, headerMap.getOrDefault("Member Term Date", -1));
    }
    return null;
  }

  private String getCellValue(Row row, int cellIndex) {
    if (cellIndex == -1 || row.getCell(cellIndex) == null) {
      return "";
    }
    return row.getCell(cellIndex).toString().trim();
  }

  private String extractCarrierName(String fileName) {
    if (fileName.contains("Healthfirst")) {
      return "Healthfirst";
    }
    if (fileName.contains("Emblem")) {
      return "Emblem";
    }
    if (fileName.contains("Centene")) {
      return "Centene";
    }
    return "Unknown";
  }

  private String extractDateFromFileName(String fileName) {
    return fileName.replaceAll(".*(\\d{2}\\.\\d{4}).*", "$1");
  }
}
