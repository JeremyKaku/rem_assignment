import java.util.Objects;

/**
 * Represents a commission record.
 *
 * @param agentName        agent name
 * @param agencyName       agency name
 * @param carrierName      carrier name
 * @param commissionPeriod commission period
 * @param commissionAmount commission amount
 * @param memberName       member name
 * @param enrollmentType   enrollment type
 * @param planName         plan name
 * @param effectiveDate    effective date
 * @param termDate         term date
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

  /**
   * Constructor for CommissionRecord.
   *
   * @param agentName        The agent name
   * @param agencyName       The agency name
   * @param carrierName      The carrier name
   * @param commissionPeriod The commission period
   * @param commissionAmount The commission amount
   * @param memberName       The member name
   * @param enrollmentType   The enrollment type
   * @param planName         The plan name
   * @param effectiveDate    The effective date
   * @param termDate         The term date
   */
  public CommissionRecord {
    if ((agentName == null) && agencyName == null) {
      throw new IllegalArgumentException(Constants.ERROR_INVALID_AGENT_NAME);
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
    CommissionRecord that = (CommissionRecord) o;
    return Double.compare(commissionAmount, that.commissionAmount) == 0
        && Objects.equals(agentName, that.agentName) && Objects.equals(memberName,
        that.memberName) && Objects.equals(planName, that.planName)
        && Objects.equals(termDate, that.termDate) && Objects.equals(agencyName,
        that.agencyName) && Objects.equals(carrierName, that.carrierName)
        && Objects.equals(effectiveDate, that.effectiveDate) && Objects.equals(
        enrollmentType, that.enrollmentType) && Objects.equals(commissionPeriod,
        that.commissionPeriod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(agentName, agencyName, carrierName, commissionPeriod, commissionAmount,
        memberName, enrollmentType, planName, effectiveDate, termDate);
  }

  @Override
  public String toString() {
    return "CommissionRecord{" +
        "agentName=" + agentName +
        ", agencyName='" + agencyName + '\'' +
        ", carrierName='" + carrierName + '\'' +
        ", commissionPeriod='" + commissionPeriod + '\'' +
        ", commissionAmount=" + commissionAmount +
        ", memberName=" + memberName +
        ", enrollmentType='" + enrollmentType + '\'' +
        ", planName='" + planName + '\'' +
        ", effectiveDate='" + effectiveDate + '\'' +
        ", termDate='" + termDate + '\'' +
        '}';
  }
}
