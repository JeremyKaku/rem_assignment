import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a person's name.
 */
public class Name {

  private final String firstName;
  private String middleName;
  private final String lastName;

  /**
   * Constructor for Name.
   *
   * @param firstName  The first name
   * @param middleName The middle name
   * @param lastName   The last name
   */
  public Name(String firstName, String middleName, String lastName) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
  }

  /**
   * Gets the first name.
   *
   * @return The first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Gets the middle name.
   *
   * @return The middle name
   */
  public String getMiddleName() {
    return middleName;
  }

  /**
   * Sets the middle name.
   *
   * @param middleName The middle name
   */
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  /**
   * Gets the last name.
   *
   * @return The last name
   */
  public String getLastName() {
    return lastName;
  }


  /**
   * Parses a full name into a Name object.
   *
   * @param fullName The full name
   * @return The Name object
   */
  public static Name parse(String fullName) {

    if (fullName == null || fullName.isBlank()) {
      return null;
    }

    fullName = fullName.trim();
    String[] parts;

    if (fullName.contains(",")) {
      // Format: <Last Name>, <First Name> [Middle Name/Initial]
      parts = fullName.split(",", 2);
      String lastName = parts[0].trim();
      String[] firstMiddle = parts[1].trim().split(" ", 2);
      String firstName = firstMiddle[0];
      String middleName = firstMiddle.length > 1 ? firstMiddle[1] : "";
      return new Name(firstName, middleName, lastName);
    } else {
      // Format: <First Name> [Middle Name/Initial] <Last Name>
      parts = fullName.split(" ");

      if (parts.length < 2 || parts.length > 3) {
        // Treat the entire name as the first name
        return new Name(fullName, "", "");
      }

      String firstName = parts[0];
      String lastName = parts[parts.length - 1];
      String middleName =
          parts.length > 2 ? String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1))
              : null;
      return new Name(firstName, middleName, lastName);
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
    Name name = (Name) o;
    return Objects.equals(firstName, name.firstName) && Objects.equals(middleName,
        name.middleName) && Objects.equals(lastName, name.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, middleName, lastName);
  }

  @Override
  public String toString() {
    if (middleName != null && !middleName.isBlank()) {
      return firstName + " " + middleName + " " + lastName;
    }
    return firstName + " " + lastName;
  }
}
