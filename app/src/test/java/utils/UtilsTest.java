package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class UtilsTest {
  @Test
  void capitalizeWorks() {
    assertEquals("Hello", Utils.capitalize("hello"));
    assertEquals("A", Utils.capitalize("a"));
    assertEquals("", Utils.capitalize(""));
    assertNull(Utils.capitalize(null));
  }

  @Test
  void reverseWorks() {
    assertEquals("olleh", Utils.reverse("hello"));
    assertEquals("a", Utils.reverse("a"));
    assertEquals("", Utils.reverse(""));
    assertNull(Utils.reverse(null));
  }

  @Test
  void getCurrentDateTimeNotNull() {
    assertNotNull(Utils.getCurrentDateTime());
  }

  @Test
  void formatDateTimeWorks() {
    LocalDateTime dt = LocalDateTime.of(2025, 11, 3, 12, 34, 56);
    assertEquals("2025-11-03 12:34:56", Utils.formatDateTime(dt, "yyyy-MM-dd HH:mm:ss"));
    assertNull(Utils.formatDateTime(null, "yyyy"));
    assertNull(Utils.formatDateTime(dt, null));
  }
}
