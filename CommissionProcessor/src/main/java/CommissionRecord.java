import java.util.Objects;

/**
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
