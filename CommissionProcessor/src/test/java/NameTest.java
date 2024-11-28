import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NameTest {

  private Name name;

  @BeforeEach
  void setUp() {
    name = new Name("John", "Michael", "Doe");
  }

  @Test
  void getFirstName() {
    assertEquals("John", name.getFirstName(), "First name should be 'John'");
  }

  @Test
  void getMiddleName() {
    assertEquals("Michael", name.getMiddleName(), "Middle name should be 'Michael'");
  }

  @Test
  void setMiddleName() {
    name.setMiddleName("David");
    assertEquals("David", name.getMiddleName(), "Middle name should be updated to 'David'");
  }

  @Test
  void getLastName() {
    assertEquals("Doe", name.getLastName(), "Last name should be 'Doe'");
  }

  @Test
  void parseWithCommaSeparatedName() {
    Name parsedName = Name.parse("Doe, John Michael");
    assertNotNull(parsedName);
    assertEquals("John", parsedName.getFirstName(), "First name should be 'John'");
    assertEquals("Michael", parsedName.getMiddleName(), "Middle name should be 'Michael'");
    assertEquals("Doe", parsedName.getLastName(), "Last name should be 'Doe'");
  }

  @Test
  void parseWithSpaceSeparatedName() {
    Name parsedName = Name.parse("John Michael Doe");
    assertNotNull(parsedName);
    assertEquals("John", parsedName.getFirstName(), "First name should be 'John'");
    assertEquals("Michael", parsedName.getMiddleName(), "Middle name should be 'Michael'");
    assertEquals("Doe", parsedName.getLastName(), "Last name should be 'Doe'");
  }

  @Test
  void parseWithOnlyFirstAndLastName() {
    Name parsedName = Name.parse("John Doe");
    assertNotNull(parsedName);
    assertEquals("John", parsedName.getFirstName(), "First name should be 'John'");
    assertNull(parsedName.getMiddleName(), "Middle name should be null");
    assertEquals("Doe", parsedName.getLastName(), "Last name should be 'Doe'");
  }

  @Test
  void parseWithSingleName() {
    Name parsedName = Name.parse("John");
    assertNotNull(parsedName);
    assertEquals("John", parsedName.getFirstName(), "First name should be 'John'");
    assertEquals("", parsedName.getLastName(), "Last name should be empty");
    assertEquals("", parsedName.getMiddleName(), "Middle name should be empty");
  }

  @Test
  void parseNullOrBlankName() {
    assertNull(Name.parse(null), "Parsing null should return null");
    assertNull(Name.parse("   "), "Parsing blank string should return null");
  }

  @Test
  void testEquals() {
    Name sameName = new Name("John", "Michael", "Doe");
    Name differentName = new Name("Jane", "Michael", "Doe");
    assertEquals(name, sameName, "Names should be equal");
    assertNotEquals(name, differentName, "Names should not be equal");
  }

  @Test
  void testHashCode() {
    Name sameName = new Name("John", "Michael", "Doe");
    assertEquals(name.hashCode(), sameName.hashCode(), "Hash codes should be equal");
  }

  @Test
  void testToString() {
    assertEquals("John Michael Doe", name.toString(), "toString should return 'John Michael Doe'");

    // Test without middle name
    Name noMiddleName = new Name("John", null, "Doe");
    assertEquals("John Doe", noMiddleName.toString(), "toString should return 'John Doe'");

    // Test empty middle name
    Name emptyMiddleName = new Name("John", "", "Doe");
    assertEquals("John Doe", emptyMiddleName.toString(), "toString should return 'John Doe'");
  }
}
