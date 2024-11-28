import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;


class UserInterfaceTest {

  private UserInterface userInterface;

  @BeforeEach
  void setUp() {
    userInterface = new UserInterface();
  }

  @Test
  void start() {
    NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
      userInterface.start();
    });
    assertEquals("No line found", thrown.getMessage());

  }

  @Test
  void testEquals() {
    UserInterface userInterface1 = new UserInterface();
    UserInterface userInterface2 = new UserInterface();
    assertNotEquals(userInterface1, userInterface2);
    assertNotEquals(userInterface1, null);
    assertEquals(userInterface1, userInterface1);
  }

  @Test
  void testHashCode() {
    UserInterface userInterface1 = new UserInterface();
    UserInterface userInterface2 = new UserInterface();
    assertNotEquals(userInterface1.hashCode(), userInterface2.hashCode());
  }

  @Test
  void testToString() {
    UserInterface userInterface1 = new UserInterface();
    assertEquals(userInterface1.toString(), userInterface1.toString());
  }
}