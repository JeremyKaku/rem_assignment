import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TopPerformersCalculatorTest {
  private TopPerformersCalculator topPerformersCalculator;
  private CommissionDataParser commissionDataParser;
  private List<CommissionRecord> records;

  @BeforeEach
  void setUp() {
    commissionDataParser = new CommissionDataParser();
    records = commissionDataParser.parseData(Constants.DEFAULT_INPUT_DIR);
    topPerformersCalculator = new TopPerformersCalculator();
  }

  @Test
  void getTopPerformersByEntity() {
    List<Map.Entry<String, Double>> topPerformer = topPerformersCalculator.getTopPerformersByEntity(records, true,"06/2024");
    assertNotNull(topPerformer);
  }

  @Test
  void displayTopPerformers() {
    List<Map.Entry<String, Double>> topPerformer = topPerformersCalculator.getTopPerformersByEntity(records, true,"06/2024");
    topPerformersCalculator.displayTopPerformers(topPerformer,true,"06/2024");
    assertNotNull(topPerformer);
  }
}