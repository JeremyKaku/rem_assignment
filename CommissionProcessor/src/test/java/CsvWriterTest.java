import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvWriterTest {
  private CsvWriter csvWriter;
  private CommissionDataParser commissionDataParser;
  @BeforeEach
  void setUp() {
    commissionDataParser = new CommissionDataParser();
    List<CommissionRecord> records =  commissionDataParser.parseData(Constants.DEFAULT_INPUT_DIR);
    csvWriter = new CsvWriter(Constants.DEFAULT_OUTPUT_DIR + '/' + Constants.DEFAULT_OUTPUT_FILE, records);
  }

  @Test
  void write() {
    new File(Constants.DEFAULT_OUTPUT_DIR).mkdirs();
    assertDoesNotThrow(() -> csvWriter.write());
    csvWriter.write();
    File file = new File(Constants.DEFAULT_OUTPUT_DIR);
    assertTrue(file.exists());
    assertTrue(file.length() > 0);

  }

  @Test
  void testEquals() {
    CsvWriter csvWriter1 = new CsvWriter(Constants.DEFAULT_OUTPUT_DIR + '/' + Constants.DEFAULT_OUTPUT_FILE, commissionDataParser.parseData(Constants.DEFAULT_INPUT_DIR));
    CsvWriter csvWriter2 = new CsvWriter(Constants.DEFAULT_OUTPUT_DIR + '/' + Constants.DEFAULT_OUTPUT_FILE, commissionDataParser.parseData(Constants.DEFAULT_INPUT_DIR));
    assertEquals(csvWriter1, csvWriter2);
    assertEquals(csvWriter1, csvWriter1);
    assertNotEquals(csvWriter1, null);
  }

  @Test
  void testHashCode() {
    CsvWriter csvWriter1 = new CsvWriter(Constants.DEFAULT_OUTPUT_DIR + '/' + Constants.DEFAULT_OUTPUT_FILE, commissionDataParser.parseData(Constants.DEFAULT_INPUT_DIR));
    CsvWriter csvWriter2 = new CsvWriter(Constants.DEFAULT_OUTPUT_DIR + '/' + Constants.DEFAULT_OUTPUT_FILE, commissionDataParser.parseData(Constants.DEFAULT_INPUT_DIR));
    assertEquals(csvWriter1.hashCode(), csvWriter2.hashCode());
  }

  @Test
  void testToString() {
    CsvWriter csvWriter1 = new CsvWriter(Constants.DEFAULT_OUTPUT_DIR + '/' + Constants.DEFAULT_OUTPUT_FILE, commissionDataParser.parseData(Constants.DEFAULT_INPUT_DIR));
    assertEquals(csvWriter1.toString(), csvWriter1.toString());

  }
}