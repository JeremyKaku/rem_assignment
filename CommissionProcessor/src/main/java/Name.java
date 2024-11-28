import java.util.Arrays;

/**
 * A record class to represent a name with first, middle, and last name.
 * @param firstName
 * @param middleName
 * @param lastName
 */
public record Name(String firstName, String middleName, String lastName) {

  /**
   * Parses a full name string and returns a Name object.
   * Handles formats like "<First Name> <Last Name>" or "<Last Name>, <First Name>".
   *
   * @param fullName The full name string
   * @return A normalized Name object
   */
  public static Name parse(String fullName) {
//    if (fullName == null || fullName.isBlank()) {
//      throw new IllegalArgumentException("Full name cannot be null or blank");
//    }

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
      String middleName = parts.length > 2 ? String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1)) : null;
      return new Name(firstName, middleName, lastName);
    }
  }

  /**
   * Normalizes the Name by removing middle names/initials for comparison purposes.
   *
   * @return A normalized version of the Name
   */
  public Name normalize() {
    return new Name(this.firstName, null, this.lastName);
  }

  @Override
  public String toString() {
    if (middleName != null && !middleName.isBlank()) {
      return firstName + " " + middleName + " " + lastName;
    }
    return firstName + " " + lastName;
  }
}
