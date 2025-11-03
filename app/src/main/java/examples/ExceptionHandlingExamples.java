package examples;

import utils.Utils;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ExceptionHandlingExamples {
  // Custom checked exception
  static class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
      super(message);
    }
  }

  // Custom unchecked exception
  static class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
      super(message);
    }
  }

  // Method that throws a checked exception
  private static void validateAge(int age) throws InvalidAgeException {
    if (age < 0) {
      throw new InvalidAgeException("Age cannot be negative");
    }
    if (age > 150) {
      throw new InvalidAgeException("Age seems unrealistic");
    }
  }

  // Method demonstrating nested try-catch
  private static void readFileWithNesting(String filename) {
    FileReader reader = null;
    try {
      reader = new FileReader(filename);
      try {
        int data = reader.read();
        while (data != -1) {
          System.out.print((char) data);
          data = reader.read();
        }
      } finally {
        if (reader != null) {
          reader.close();
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
    }
  }

  // Method demonstrating try-with-resources
  private static void readFileWithResources(String filename) {
    try (FileReader reader = new FileReader(filename)) {
      int data = reader.read();
      while (data != -1) {
        System.out.print((char) data);
        data = reader.read();
      }
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
    }
  }

  // Method demonstrating multiple catch blocks
  private static void multipleCatchExample(String input) {
    try {
      int number = Integer.parseInt(input);
      int result = 100 / number;
      int[] array = new int[result];
      array[result + 1] = 100;
    } catch (NumberFormatException e) {
      System.out.println("Invalid number format: " + e.getMessage());
    } catch (ArithmeticException e) {
      System.out.println("Arithmetic error: " + e.getMessage());
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Array index error: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("General error: " + e.getMessage());
    }
  }

  public static void demonstrateExceptionHandling() {
    Utils.printLine("Exception Handling Examples");
    Scanner scanner = new Scanner(System.in);

    // 1. Basic try-catch
    System.out.println("\n=== Basic Try-Catch ===");
    try {
      System.out.print("Enter a number: ");
      String input = scanner.nextLine();
      int number = Integer.parseInt(input);
      System.out.println("You entered: " + number);
    } catch (NumberFormatException e) {
      System.out.println("That's not a valid number!");
    }

    // 2. Checked Exception (InvalidAgeException)
    System.out.println("\n=== Checked Exception Example ===");
    try {
      System.out.print("Enter age: ");
      int age = Integer.parseInt(scanner.nextLine());
      validateAge(age);
      System.out.println("Age is valid: " + age);
    } catch (InvalidAgeException e) {
      System.out.println("Invalid age: " + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("Please enter a valid number");
    }

    // 3. Unchecked Exception
    System.out.println("\n=== Unchecked Exception Example ===");
    try {
      System.out.print("Enter a name: ");
      String name = scanner.nextLine();
      if (name.trim().isEmpty()) {
        throw new InvalidInputException("Name cannot be empty");
      }
      System.out.println("Hello, " + name + "!");
    } catch (InvalidInputException e) {
      System.out.println("Input error: " + e.getMessage());
    }

    // 4. Multiple catch blocks
    System.out.println("\n=== Multiple Catch Blocks Example ===");
    System.out.print("Enter a number for multiple exception test: ");
    String userInput = scanner.nextLine();
    multipleCatchExample(userInput);

    // 5. Finally block
    System.out.println("\n=== Finally Block Example ===");
    try {
      System.out.println("Attempting risky operation...");
      if (true)
        throw new Exception("Simulated error");
    } catch (Exception e) {
      System.out.println("Caught error: " + e.getMessage());
    } finally {
      System.out.println("This will always execute!");
    }

    // 6. Try-with-resources
    System.out.println("\n=== Try-with-resources Example ===");
    File tempFile = null;
    try {
      tempFile = File.createTempFile("example", ".txt");
      System.out.println("Created temporary file: " + tempFile.getAbsolutePath());
      readFileWithResources(tempFile.getAbsolutePath());
    } catch (IOException e) {
      System.out.println("Error with file operations: " + e.getMessage());
    } finally {
      if (tempFile != null) {
        tempFile.delete();
      }
    }

    Utils.printLine("End of Exception Handling Examples");
  }
}