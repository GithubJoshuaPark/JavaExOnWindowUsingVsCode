package examples;

import utils.Utils;

public class DataTypesAdvanced {
  public static void demonstrateDataTypes() {
    Utils.printLine("Advanced Data Types Demonstration");

    // BigInteger example
    java.math.BigInteger bigInt = new java.math.BigInteger("123456789012345678901234567890");
    System.out.println("BigInteger: " + bigInt);

    // BigDecimal for precise decimal calculations
    java.math.BigDecimal decimal1 = new java.math.BigDecimal("0.1");
    java.math.BigDecimal decimal2 = new java.math.BigDecimal("0.2");
    System.out.println("BigDecimal precise addition: " + decimal1.add(decimal2));

    // Wrapper classes and autoboxing
    Integer wrapped = 100; // autoboxing
    int primitive = wrapped; // unboxing
    System.out.println("Wrapper class conversion: " + wrapped + " -> " + primitive);

    // Character methods
    char ch = 'A';
    System.out.println("Character info for '" + ch + "':");
    System.out.println("- isLetter: " + Character.isLetter(ch));
    System.out.println("- isDigit: " + Character.isDigit(ch));
    System.out.println("- isWhitespace: " + Character.isWhitespace(ch));

    // Binary literals
    byte binary = 0b1010;
    System.out.println("Binary literal (0b1010): " + binary);

    // Underscores in numeric literals
    long bigNumber = 1_234_567_890L;
    System.out.println("Large number with underscores: " + bigNumber);

    Utils.printLine("End of Advanced Data Types Demo");
  }
}