package examples;

import utils.Utils;
import java.util.*;
import java.util.function.*;

public class MethodReferencesDemo {
  // Sample classes for demonstrations
  static class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public Person(String name) {
      this(name, 0);
    }

    public static Person createWithDefaultAge(String name) {
      return new Person(name, 25);
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }

    public void printInfo() {
      System.out.println("Person: " + name + ", Age: " + age);
    }

    @Override
    public String toString() {
      return name + " (" + age + ")";
    }
  }

  public static void demonstrateMethodReferences() {
    Utils.printLine("Method References Examples");

    List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
    List<Person> people = new ArrayList<>();

    // 1. Static Method Reference
    System.out.println("Static Method References:");

    // Traditional lambda
    System.out.println("\nTraditional lambda vs Method reference:");
    names.forEach(name -> System.out.println(name));
    // Method reference equivalent
    names.forEach(System.out::println);

    // Custom static method reference
    System.out.println("\nCreating persons with static method reference:");
    Function<String, Person> personCreator = Person::createWithDefaultAge;
    names.forEach(name -> people.add(personCreator.apply(name)));
    people.forEach(Person::printInfo);

    // 2. Instance Method Reference of a Particular Object
    System.out.println("\nInstance Method Reference of Particular Object:");

    // Create a string processor
    StringProcessor processor = new StringProcessor();
    // Traditional lambda
    names.forEach(name -> processor.processString(name));
    // Method reference equivalent
    names.forEach(processor::processString);

    // 3. Instance Method Reference of an Arbitrary Object of a Particular Type
    System.out.println("\nInstance Method Reference of Arbitrary Object:");

    List<String> wordList = Arrays.asList("Java", "Python", "JavaScript");
    // Traditional lambda for String's length method
    wordList.stream()
        .map(str -> str.length())
        .forEach(System.out::println);
    // Method reference equivalent
    wordList.stream()
        .map(String::length)
        .forEach(System.out::println);

    // 4. Constructor Reference
    System.out.println("\nConstructor References:");

    // Traditional lambda for constructor
    Supplier<List<String>> listSupplier = () -> new ArrayList<>();
    // Constructor reference equivalent
    Supplier<List<String>> listSupplierRef = ArrayList::new;

    // Single parameter constructor reference
    Function<String, Person> personFunction = Person::new;
    Person newPerson = personFunction.apply("Eve");
    System.out.println("Created person: " + newPerson);

    // Two parameter constructor reference
    BiFunction<String, Integer, Person> personBiFunction = Person::new;
    Person anotherPerson = personBiFunction.apply("Frank", 30);
    System.out.println("Created person with age: " + anotherPerson);

    // 5. Array Constructor Reference
    System.out.println("\nArray Constructor Reference:");

    Function<Integer, String[]> arrayCreator = String[]::new;
    String[] stringArray = arrayCreator.apply(5);
    System.out.println("Created array of size: " + stringArray.length);

    // 6. Practical Examples
    System.out.println("\nPractical Examples:");

    // Sorting using method reference
    List<Person> peopleList = Arrays.asList(
        new Person("Zach", 25),
        new Person("Alice", 30),
        new Person("Bob", 20));

    // Sort by name using method reference
    peopleList.sort(Comparator.comparing(Person::getName));
    System.out.println("\nSorted by name:");
    peopleList.forEach(Person::printInfo);

    // Collecting data using method reference
    List<Integer> ages = peopleList.stream()
        .map(Person::getAge)
        .toList();
    System.out.println("\nExtracted ages: " + ages);
  }

  // Helper class for instance method reference demonstration
  static class StringProcessor {
    public void processString(String str) {
      System.out.println("Processing: " + str.toUpperCase());
    }
  }
}