package examples;

import utils.Utils;
import java.util.*;

// Class to demonstrate JUnit testing
public class JUnitTestingDemo {
  // Calculator class for basic arithmetic operations
  public static class Calculator {
    public int add(int a, int b) {
      return a + b;
    }

    public int subtract(int a, int b) {
      return a - b;
    }

    public int multiply(int a, int b) {
      return a * b;
    }

    public double divide(int a, int b) {
      if (b == 0) {
        throw new IllegalArgumentException("Cannot divide by zero");
      }
      return (double) a / b;
    }
  }

  // String utility class for text manipulation
  public static class StringUtils {
    public String reverse(String input) {
      if (input == null) {
        return null;
      }
      return new StringBuilder(input).reverse().toString();
    }

    public boolean isPalindrome(String input) {
      if (input == null || input.isEmpty()) {
        return false;
      }
      String cleaned = input.toLowerCase().replaceAll("[^a-z0-9]", "");
      return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }

    public String concatenate(String... strings) {
      if (strings == null) {
        return "";
      }
      return String.join("", strings);
    }
  }

  // User management class for demonstrating more complex testing
  public static class UserManager {
    private final Map<String, User> users = new HashMap<>();

    public void addUser(User user) {
      if (user == null || user.getUsername() == null || user.getEmail() == null) {
        throw new IllegalArgumentException("User and its properties cannot be null");
      }
      if (users.containsKey(user.getUsername())) {
        throw new IllegalArgumentException("Username already exists");
      }
      users.put(user.getUsername(), user);
    }

    public User getUser(String username) {
      return users.get(username);
    }

    public void updateEmail(String username, String newEmail) {
      User user = users.get(username);
      if (user == null) {
        throw new IllegalArgumentException("User not found");
      }
      user.setEmail(newEmail);
    }

    public boolean deleteUser(String username) {
      return users.remove(username) != null;
    }

    public List<User> getAllUsers() {
      return new ArrayList<>(users.values());
    }
  }

  // User class
  public static class User {
    private final String username;
    private String email;

    public User(String username, String email) {
      this.username = username;
      this.email = email;
    }

    public String getUsername() {
      return username;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }

  public static void demonstrateJUnitTesting() {
    Utils.printLine("JUnit Testing Examples");
    System.out.println("This class contains code that is tested using JUnit.");
    System.out.println("Please check the test files in the test directory:");
    System.out.println("1. CalculatorTest - Basic arithmetic operations testing");
    System.out.println("2. StringUtilsTest - String manipulation testing");
    System.out.println("3. UserManagerTest - Complex object testing");
    System.out.println("\nTest features demonstrated:");
    System.out.println("- Basic assertions");
    System.out.println("- Exception testing");
    System.out.println("- Parameterized tests");
    System.out.println("- Test lifecycle annotations");
    System.out.println("- Test suites");
    System.out.println("- Assumptions");
    System.out.println("- Custom test annotations");
  }
}