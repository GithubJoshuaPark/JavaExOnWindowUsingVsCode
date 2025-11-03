package examples;

import utils.Utils;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

public class FunctionalInterfacesDemo {

  static class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
      this.name = name;
      this.price = price;
    }

    public String getName() {
      return name;
    }

    public double getPrice() {
      return price;
    }

    @Override
    public String toString() {
      return String.format("%s ($%.2f)", name, price);
    }
  }

  public static void demonstrateFunctionalInterfaces() {
    Utils.printLine("Functional Interfaces Examples");

    // Predicate<T> - test method returns boolean
    System.out.println("Predicate Examples:");
    Predicate<String> isLongString = str -> str.length() > 5;
    Predicate<String> startsWithA = str -> str.startsWith("A");

    String testString = "Apple";
    System.out.println("Is '" + testString + "' longer than 5 chars? " +
        isLongString.test(testString));

    // Combining predicates
    Predicate<String> combinedPredicate = isLongString.and(startsWithA);
    System.out.println("Is '" + testString +
        "' long AND starts with 'A'? " +
        combinedPredicate.test(testString));

    // Function<T,R> - transforms input into output
    System.out.println("\nFunction Examples:");
    Function<String, Integer> stringLength = String::length;
    Function<Integer, String> intToString = num -> "Number: " + num;

    // Function composition
    Function<String, String> composed = intToString.compose(stringLength);
    System.out.println("Composed function: " +
        composed.apply("Hello World"));

    // Consumer<T> - accepts input, returns nothing
    System.out.println("\nConsumer Examples:");
    Consumer<String> printer = System.out::println;
    Consumer<String> logger = msg -> System.out.println("Log: " + msg);

    // Chaining consumers
    Consumer<String> printAndLog = printer.andThen(logger);
    printAndLog.accept("Testing consumer chain");

    // Supplier<T> - no input, produces output
    System.out.println("\nSupplier Examples:");
    Supplier<Double> randomSupplier = Math::random;
    System.out.println("Random number: " + randomSupplier.get());

    // BinaryOperator<T> - two inputs, one output of same type
    System.out.println("\nBinaryOperator Examples:");
    BinaryOperator<Integer> adder = (a, b) -> a + b;
    System.out.println("5 + 3 = " + adder.apply(5, 3));

    // UnaryOperator<T> - one input, one output of same type
    System.out.println("\nUnaryOperator Examples:");
    UnaryOperator<String> toUpperCase = String::toUpperCase;
    System.out.println("Uppercase: " + toUpperCase.apply("hello"));

    // Practical example combining multiple functional interfaces
    System.out.println("\nPractical Example with Products:");
    List<Product> products = Arrays.asList(
        new Product("Laptop", 1200.00),
        new Product("Mouse", 25.00),
        new Product("Keyboard", 100.00));

    // Predicate to filter expensive products
    Predicate<Product> isExpensive = p -> p.getPrice() > 500;

    // Function to apply discount
    Function<Product, Double> applyDiscount = p -> p.getPrice() * 0.9;

    // Consumer to display product
    Consumer<Product> displayProduct = p -> System.out.println(p.getName() +
        " - Original: $" + p.getPrice() +
        ", Discounted: $" +
        String.format("%.2f", applyDiscount.apply(p)));

    System.out.println("Expensive products with 10% discount:");
    products.stream()
        .filter(isExpensive)
        .forEach(displayProduct);

    // BiFunction example
    System.out.println("\nBiFunction Example:");
    BiFunction<String, Double, Product> productCreator = Product::new;
    Product newProduct = productCreator.apply("Tablet", 299.99);
    System.out.println("Created new product: " + newProduct);

    // Custom functional interface example
    System.out.println("\nCustom Functional Interface Example:");
    TriFunction<String, Double, Boolean, String> priceFormatter = (name, price, includeSymbol) -> name + ": "
        + (includeSymbol ? "$" : "") +
        String.format("%.2f", price);

    System.out.println(priceFormatter.apply("Monitor", 199.99, true));
  }

  @FunctionalInterface
  interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
  }
}