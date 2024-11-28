import java.util.*;
import java.util.stream.Collectors;

/**
 * Calculates the top performers by entity.
 */
public class TopPerformersCalculator {

  /**
   * Gets the top performers by entity.
   *
   * @param records          the commission records
   * @param isAgent          true if the entity is an agent, false if the entity is an agency
   * @param commissionPeriod the commission period
   * @return the top performers by entity
   */
  public List<Map.Entry<String, Double>> getTopPerformersByEntity(List<CommissionRecord> records,
      Boolean isAgent, String commissionPeriod) {

    // Filter records by commission period
    List<CommissionRecord> filteredRecords = records.stream()
        .filter(record -> commissionPeriod.equals(
            record.commissionPeriod())) // Simplify if a field tracks this
        .toList();

    if (isAgent) {
      filteredRecords = filteredRecords.stream()
          .filter(record -> record.agentName() != null)
          .toList();
    } else {
      filteredRecords = filteredRecords.stream()
          .filter(record -> record.agencyName() != null)
          .toList();
    }

    // Group by agent or agency and sum commission amounts
    Map<String, Double> entityToTotalCommission = new HashMap<>();
    for (CommissionRecord record : filteredRecords) {
      String key = isAgent ? record.agentName().toString() : record.agencyName();
      entityToTotalCommission.put(key,
          entityToTotalCommission.getOrDefault(key, 0.0) + record.commissionAmount());
    }

    // Sort by commission amount in descending order and take the top 10
    return entityToTotalCommission.entrySet().stream()
        .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
        .limit(10)
        .collect(Collectors.toList());
  }

  /**
   * Displays the top performers.
   *
   * @param topPerformers the top performers
   * @param isAgent       true if the entity is an agent, false if the entity is an agency
   */
  public void displayTopPerformers(List<Map.Entry<String, Double>> topPerformers, boolean isAgent) {
    System.out.println(isAgent ? Constants.MSG_TOP_AGENT : Constants.MSG_TOP_AGENCY);
    if (topPerformers.isEmpty()) {
      System.out.println(Constants.MSG_NO_TOP_PERFORMERS);
      return;
    }
    System.out.println(Constants.MSG_TOP_HERADER);
    int rank = 1;
    for (Map.Entry<String, Double> entry : topPerformers) {
      System.out.printf("%s , %s, %.2f\n", Constants.RANK_NUM + rank++, entry.getKey(), entry.getValue());
    }
  }

}
