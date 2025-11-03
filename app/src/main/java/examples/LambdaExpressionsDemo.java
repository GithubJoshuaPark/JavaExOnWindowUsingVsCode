package examples;

import utils.Utils;
import java.util.*;

public class LambdaExpressionsDemo {
  // Functional interface for arithmetic operations
  @FunctionalInterface
  interface MathOperation {
    int operate(int a, int b);
  }

  // Functional interface with generic types
  @FunctionalInterface
  interface Formatter<T> {
    String format(T t);
  }

  public static void demonstrateLambdas() {
    Utils.printLine("Lambda Expressions Examples");

    // Basic lambda expressions
    System.out.println("Basic Lambda Examples:");

    // Lambda with multiple parameters
    MathOperation addition = (a, b) -> a + b;
    MathOperation subtraction = (a, b) -> a - b;
    MathOperation multiplication = (x, y) -> {
      return x * y;
    }; // With block

    System.out.println("10 + 5 = " + addition.operate(10, 5));
    System.out.println("10 - 5 = " + subtraction.operate(10, 5));
    System.out.println("10 * 5 = " + multiplication.operate(10, 5));

    // Lambda with collections
    List<String> names = Arrays.asList("John", "Jane", "Bob", "Alice", "Mike");

    System.out.println("\nSorting with Lambda:");
    // Sort by length
    names.sort((s1, s2) -> s1.length() - s2.length());
    System.out.println("Sorted by length: " + names);

    // Sort alphabetically
    names.sort((s1, s2) -> s1.compareTo(s2));
    System.out.println("Sorted alphabetically: " + names);

    // Lambda with forEach
    System.out.println("\nforEach with Lambda:");
    names.forEach(name -> System.out.print(name + " "));
    System.out.println();

    // Generic lambda with custom functional interface
    Formatter<Integer> numberFormatter = num -> "Number: " + num;
    Formatter<String> stringFormatter = str -> "String: " + str.toUpperCase();

    System.out.println("\nGeneric Lambda Examples:");
    System.out.println(numberFormatter.format(42));
    System.out.println(stringFormatter.format("hello"));

    // Lambda as method parameter
    System.out.println("\nLambda as Method Parameter:");
    processNumbers(Arrays.asList(1, 2, 3, 4, 5),
        (n, m) -> n * n,
        "Square of numbers");

    // Variable capture
    int multiplier = 10;
    System.out.println("\nVariable Capture Example:");
    names.forEach(name -> System.out.println(name + " multiplied length: " +
        (name.length() * multiplier)));

    // Runnable with lambda
    System.out.println("\nRunnable with Lambda:");
    Runnable task = () -> System.out.println("Running in " +
        Thread.currentThread().getName());
    new Thread(task).start();
  }

  private static void processNumbers(List<Integer> numbers,
      MathOperation operation,
      String description) {
    System.out.println(description + ":");
    numbers.forEach(n -> System.out.print(operation.operate(n, n) + " "));
    System.out.println();
  }
}