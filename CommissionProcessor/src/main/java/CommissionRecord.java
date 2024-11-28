/**
 *
 * @param agentName
 * @param agencyName
 * @param carrierName
 * @param commissionPeriod
 * @param commissionAmount
 * @param memberName
 * @param enrollmentType
 * @param planName
 * @param effectiveDate
 * @param termDate
 */
public record CommissionRecord(
    // Agent Name
    Name agentName,
    // Agency Name
    String agencyName,
    // Carrier Name
    String carrierName,
    // Commission Period
    String commissionPeriod,
    // Commission Amount
    double commissionAmount,
    // Member First Name
    Name memberName,
    // Enrollment Type
    String enrollmentType,
    // Plan Name
    String planName,
    // Effective Date
    String effectiveDate,
    // Term Date
    String termDate
) {

  public CommissionRecord {
    if ((agentName == null) && agencyName == null) {
      throw new IllegalArgumentException("Agent name and agency name cannot be both null or empty");
    }
  }

}
