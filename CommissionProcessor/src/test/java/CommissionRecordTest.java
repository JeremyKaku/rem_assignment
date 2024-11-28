import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommissionRecordTest {

  private CommissionRecord commissionRecord;

  @BeforeEach
  void setUp() {
    Name name = new Name("John", "Michael", "Doe");
    commissionRecord = new CommissionRecord(name, "Agent", "Agency", "Carrier", 1000, name,
        "Normal", "A", "2021-12-31", "Monthly");
  }

  @Test
  void testEquals() {
    Name name = new Name("John", "Michael", "Doe");
    CommissionRecord commissionRecord1 = new CommissionRecord(name, "Agent", "Agency", "Carrier", 1000, name,
        "Normal", "A", "2021-12-31", "Monthly");
    CommissionRecord commissionRecord2 = new CommissionRecord(name, "Agent", "Agency", "Carrier", 1000, name,
        "Normal", "A", "2021-12-31", "Monthly");
    assertEquals(commissionRecord1, commissionRecord2);
    assertEquals(commissionRecord1, commissionRecord1);
    assertNotEquals(commissionRecord1, null);
  }

  @Test
  void testHashCode() {
    Name name = new Name("John", "Michael", "Doe");
    CommissionRecord commissionRecord1 = new CommissionRecord(name, "Agent", "Agency", "Carrier", 1000, name,
        "Normal", "A", "2021-12-31", "Monthly");
    CommissionRecord commissionRecord2 = new CommissionRecord(name, "Agent", "Agency", "Carrier", 1000, name,
        "Normal", "A", "2021-12-31", "Monthly");
    assertEquals(commissionRecord1.hashCode(), commissionRecord2.hashCode());
  }

  @Test
  void testToString() {
    Name name = new Name("John", "Michael", "Doe");
    CommissionRecord commissionRecord1 = new CommissionRecord(name, "Agent", "Agency", "Carrier", 1000, name,
        "Normal", "A", "2021-12-31", "Monthly");
    assertEquals(commissionRecord1.toString(), commissionRecord1.toString());
  }

  @Test
  void agentName() {
     Name name = new Name("John", "Michael", "Doe");
    CommissionRecord commissionRecord1 = new CommissionRecord(name, "Agent", "Agency", "Carrier", 1000, name,
        "Normal", "A", "2021-12-31", "Monthly");
    assertEquals(name, commissionRecord1.agentName());
  }

  @Test
  void agencyName() {
    String agencyName = "Agent";
    assertEquals(agencyName, commissionRecord.agencyName());
  }

  @Test
  void carrierName() {
    String carrierName = "Agency";
    assertEquals(carrierName, commissionRecord.carrierName());
  }

  @Test
  void commissionPeriod() {
    String commissionPeriod = "Carrier";
    assertEquals(commissionPeriod, commissionRecord.commissionPeriod());
  }

  @Test
  void commissionAmount() {
    double commissionAmount = 1000;
    assertEquals(commissionAmount, commissionRecord.commissionAmount());
  }

  @Test
  void memberName() {
    Name name = new Name("John", "Michael", "Doe");
    assertEquals(name, commissionRecord.memberName());
  }

  @Test
  void enrollmentType() {
    String enrollmentType = "Normal";
    assertEquals(enrollmentType, commissionRecord.enrollmentType());
  }

  @Test
  void planName() {
    String planName = "A";
    assertEquals(planName, commissionRecord.planName());
  }

  @Test
  void effectiveDate() {
    String effectiveDate = "2021-12-31";
    assertEquals(effectiveDate, commissionRecord.effectiveDate());
  }

  @Test
  void termDate() {
    String termDate = "Monthly";
    assertEquals(termDate, commissionRecord.termDate());
  }
}