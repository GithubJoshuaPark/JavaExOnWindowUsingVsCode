package examples;

import utils.Utils;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class StringManipulationExamples {
  public static void demonstrateStringManipulation() {
    Utils.printLine("String Manipulation Examples");

    // String creation and comparison
    String str1 = "Hello";
    String str2 = new String("Hello");
    System.out.println("str1 == str2: " + (str1 == str2));
    System.out.println("str1.equals(str2): " + str1.equals(str2));

    // String methods
    String text = "  Java Programming  ";
    System.out.println("Original: '" + text + "'");
    System.out.println("Trimmed: '" + text.trim() + "'");
    System.out.println("Uppercase: " + text.toUpperCase());
    System.out.println("Lowercase: " + text.toLowerCase());

    // StringBuilder for efficient string manipulation
    StringBuilder builder = new StringBuilder();
    builder.append("Hello")
        .append(" ")
        .append("World")
        .append("!");
    System.out.println("StringBuilder result: " + builder.toString());

    // Regular expressions
    String pattern = "\\w+@\\w+\\.\\w+";
    String email = "user@example.com";
    System.out.println("Email matches pattern: " + email.matches(pattern));

    // Advanced regex with groups
    Pattern p = Pattern.compile("(\\w+)@(\\w+)\\.(\\w+)");
    Matcher m = p.matcher(email);
    if (m.matches()) {
      System.out.println("Username: " + m.group(1));
      System.out.println("Domain: " + m.group(2));
      System.out.println("TLD: " + m.group(3));
    }

    // String splitting and joining
    String csvLine = "apple,banana,orange";
    String[] fruits = csvLine.split(",");
    String joined = String.join(" | ", fruits);
    System.out.println("Split and joined: " + joined);

    Utils.printLine("End of String Manipulation Examples");
  }
}