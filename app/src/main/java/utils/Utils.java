package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

  public static void printLine(String message) {
    // Handle wide characters (e.g., Korean) for correct width
    int width = message.codePoints()
        .map(cp -> Character.UnicodeScript.of(cp) == Character.UnicodeScript.HAN ||
            Character.UnicodeScript.of(cp) == Character.UnicodeScript.HANGUL ? 2 : 1)
        .sum();
    System.out.println();
    for (int i = 0; i < width; i++) {
      System.out.print("=");
    }
    System.out.println();
    System.out.println(message);
    for (int i = 0; i < width; i++) {
      System.out.print("=");
    }
    System.out.println();
  }

  // String utility: capitalize first letter
  public static String capitalize(String input) {
    if (input == null || input.isEmpty())
      return input;
    return input.substring(0, 1).toUpperCase() + input.substring(1);
  }

  // String utility: reverse string
  public static String reverse(String input) {
    if (input == null)
      return null;
    return new StringBuilder(input).reverse().toString();
  }

  // Datetime utility: get current datetime as string
  public static String getCurrentDateTime() {
    LocalDateTime now = LocalDateTime.now();
    return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  // Datetime utility: format given datetime
  public static String formatDateTime(LocalDateTime dateTime, String pattern) {
    if (dateTime == null || pattern == null)
      return null;
    return dateTime.format(DateTimeFormatter.ofPattern(pattern));
  }
}
