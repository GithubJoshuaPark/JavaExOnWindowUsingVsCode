package examples;

import utils.Utils;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

public class OptionalClassDemo {
  // Sample User class for demonstrations
  static class User {
    private String name;
    private String email;
    private Address address;

    public User(String name, String email, Address address) {
      this.name = name;
      this.email = email;
      this.address = address;
    }

    public String getName() {
      return name;
    }

    public String getEmail() {
      return email;
    }

    public Optional<Address> getAddress() {
      return Optional.ofNullable(address);
    }
  }

  static class Address {
    private String street;
    private String city;

    public Address(String street, String city) {
      this.street = street;
      this.city = city;
    }

    public String getStreet() {
      return street;
    }

    public String getCity() {
      return city;
    }
  }

  public static void demonstrateOptional() {
    Utils.printLine("Optional Class Examples");

    // Creating Optional objects
    System.out.println("Creating Optional objects:");

    // Empty Optional
    Optional<String> empty = Optional.empty();
    System.out.println("Empty Optional: " + empty);

    // Optional with non-null value
    String name = "John";
    Optional<String> opt = Optional.of(name);
    System.out.println("Optional with value: " + opt);

    // Optional that may hold null value
    String nullableName = null;
    Optional<String> optNullable = Optional.ofNullable(nullableName);
    System.out.println("Optional with nullable value: " + optNullable);

    // Using Optional methods
    System.out.println("\nOptional methods:");

    // isPresent and isEmpty
    System.out.println("isPresent(): " + opt.isPresent());
    System.out.println("isEmpty(): " + optNullable.isEmpty());

    // orElse examples
    String defaultValue = optNullable.orElse("Default");
    System.out.println("orElse(): " + defaultValue);

    // orElseGet with Supplier
    String suppliedValue = optNullable.orElseGet(() -> "Supplied Value");
    System.out.println("orElseGet(): " + suppliedValue);

    // map and flatMap
    Optional<String> upperName = opt.map(String::toUpperCase);
    System.out.println("map(): " + upperName.orElse(""));

    // Real-world example with User class
    System.out.println("\nReal-world example:");

    // Create users with and without addresses
    User user1 = new User("Alice", "alice@email.com",
        new Address("123 Main St", "Boston"));
    User user2 = new User("Bob", "bob@email.com", null);

    // Safely get city using Optional
    String city1 = user1.getAddress()
        .map(Address::getCity)
        .orElse("Unknown City");
    String city2 = user2.getAddress()
        .map(Address::getCity)
        .orElse("Unknown City");

    System.out.println("Alice's city: " + city1);
    System.out.println("Bob's city: " + city2);

    // Filter example
    System.out.println("\nFilter example:");
    Optional<String> longName = opt
        .filter(str -> str.length() > 3)
        .map(str -> str + " passes length check");
    System.out.println(longName.orElse("Name too short"));

    // Exception handling with Optional
    System.out.println("\nException handling:");
    Optional<Integer> number = Optional.of("123")
        .map(Integer::parseInt);
    System.out.println("Parsed number: " + number.orElse(-1));

    Optional<Integer> invalid = Optional.of("abc")
        .map(str -> {
          try {
            return Integer.parseInt(str);
          } catch (NumberFormatException e) {
            return null;
          }
        });
    System.out.println("Invalid number handled: " +
        invalid.orElse(-1));

    // Stream operations with Optional
    System.out.println("\nStream operations:");
    List<Optional<String>> listOfOptionals = Arrays.asList(
        Optional.of("First"),
        Optional.empty(),
        Optional.of("Third"));

    List<String> filteredList = listOfOptionals.stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
    System.out.println("Filtered non-empty values: " + filteredList);
  }
}