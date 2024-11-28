import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

class MainTest {


  @Test
  void mainTest() {
    NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
      Main.main(new String[0]);
    });
    assertEquals("No line found", thrown.getMessage());
  }
}