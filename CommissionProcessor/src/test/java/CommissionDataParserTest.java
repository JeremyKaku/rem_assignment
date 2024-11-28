import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class CommissionDataParserTest {
  private CommissionDataParser commissionDataParser;
  @BeforeEach
  void setUp() {
    commissionDataParser = new CommissionDataParser();
  }

  @Test
  void parseData() {
    List<CommissionRecord> records =  commissionDataParser.parseData(Constants.DEFAULT_INPUT_DIR);
    assertNotNull(records);
  }

  @Test
  void parseData2() {
    String inputDir = "test/java";
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      commissionDataParser.parseData(inputDir);
    });
    assertEquals(Constants.ERROR_NO_EXCEL, thrown.getMessage());
    }

}