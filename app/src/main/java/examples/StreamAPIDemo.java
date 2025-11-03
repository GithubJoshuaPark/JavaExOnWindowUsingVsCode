package examples;

import utils.Utils;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class StreamAPIDemo {
  static class Product {
    private String name;
    private double price;
    private String category;

    public Product(String name, double price, String category) {
      this.name = name;
      this.price = price;
      this.category = category;
    }

    public String getName() {
      return name;
    }

    public double getPrice() {
      return price;
    }

    public String getCategory() {
      return category;
    }

    @Override
    public String toString() {
      return String.format("%s ($%.2f)", name, price);
    }
  }

  public static void demonstrateStreamAPI() {
    Utils.printLine("Stream API Examples");

    // Creating streams
    System.out.println("Different ways to create streams:");

    // From collection
    List<String> words = Arrays.asList("Java", "Stream", "API", "Examples");
    Stream<String> streamFromCollection = words.stream();
    System.out.println("From Collection: ");
    streamFromCollection.forEach(word -> System.out.print(word + " "));
    System.out.println();

    // From array
    String[] array = { "One", "Two", "Three" };
    Stream<String> streamFromArray = Arrays.stream(array);
    System.out.println("\nFrom Array: ");
    streamFromArray.forEach(s -> System.out.print(s + " "));
    System.out.println();

    // Stream operations with numbers
    System.out.println("\nNumeric Stream Operations:");
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // Filter and Map
    System.out.println("Even numbers doubled: ");
    numbers.stream()
        .filter(n -> n % 2 == 0)
        .map(n -> n * 2)
        .forEach(n -> System.out.print(n + " "));
    System.out.println();

    // Reduce
    int sum = numbers.stream()
        .reduce(0, (a, b) -> a + b);
    System.out.println("Sum using reduce: " + sum);

    // Complex object streaming
    List<Product> products = Arrays.asList(
        new Product("Laptop", 1200.00, "Electronics"),
        new Product("Book", 29.99, "Books"),
        new Product("Phone", 800.00, "Electronics"),
        new Product("Desk", 150.00, "Furniture"),
        new Product("Tablet", 400.00, "Electronics"));

    System.out.println("\nProduct Stream Operations:");

    // Filter and collect
    List<Product> electronics = products.stream()
        .filter(p -> p.getCategory().equals("Electronics"))
        .collect(Collectors.toList());
    System.out.println("Electronics products: " + electronics);

    // Average price
    double avgPrice = products.stream()
        .mapToDouble(Product::getPrice)
        .average()
        .orElse(0.0);
    System.out.printf("Average price: $%.2f%n", avgPrice);

    // Grouping
    Map<String, List<Product>> byCategory = products.stream()
        .collect(Collectors.groupingBy(Product::getCategory));
    System.out.println("\nProducts by category: " + byCategory);

    // Parallel streams
    System.out.println("\nParallel Stream Example:");
    long startTime = System.currentTimeMillis();
    IntStream.range(1, 1000)
        .parallel()
        .mapToObj(i -> String.valueOf(i))
        .collect(Collectors.joining(","));
    System.out.println("Parallel processing time: " +
        (System.currentTimeMillis() - startTime) + "ms");

    // String operations
    System.out.println("\nString Stream Operations:");
    String result = words.stream()
        .map(String::toLowerCase)
        .filter(s -> s.length() > 3)
        .sorted()
        .collect(Collectors.joining(", "));
    System.out.println("Processed strings: " + result);

    // Infinite stream with limit
    System.out.println("\nInfinite Stream with limit:");
    Stream.iterate(0, n -> n + 2)
        .limit(5)
        .forEach(n -> System.out.print(n + " "));
    System.out.println();
  }
}