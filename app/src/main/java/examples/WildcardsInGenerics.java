package examples;

import utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Animal {
  private String name;

  public Animal(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}

class Dog extends Animal {
  public Dog(String name) {
    super(name);
  }
}

class Cat extends Animal {
  public Cat(String name) {
    super(name);
  }
}

public class WildcardsInGenerics {
  // Upper bounded wildcard
  public static double sumOfList(List<? extends Number> list) {
    double sum = 0.0;
    for (Number n : list) {
      sum += n.doubleValue();
    }
    return sum;
  }

  // Lower bounded wildcard
  public static void addNumbers(List<? super Integer> list) {
    for (int i = 1; i <= 5; i++) {
      list.add(i);
    }
  }

  // Unbounded wildcard
  public static void printList(List<?> list) {
    for (Object elem : list) {
      System.out.print(elem + " ");
    }
    System.out.println();
  }

  public static void demonstrateWildcards() {
    Utils.printLine("Wildcards in Generics Examples");

    // Upper bounded wildcard example
    List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
    List<Double> doubles = Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5);

    System.out.println("Upper Bounded Wildcard Example (? extends Number):");
    System.out.println("Sum of integers: " + sumOfList(integers));
    System.out.println("Sum of doubles: " + sumOfList(doubles));

    // Lower bounded wildcard example
    List<Number> numbers = new ArrayList<>();
    System.out.println("\nLower Bounded Wildcard Example (? super Integer):");
    addNumbers(numbers);
    System.out.println("Added numbers to list: " + numbers);

    // Unbounded wildcard example
    List<String> strings = Arrays.asList("Hello", "Wildcards", "in", "Java");
    System.out.println("\nUnbounded Wildcard Example (?):");
    System.out.print("Printing strings: ");
    printList(strings);
    System.out.print("Printing integers: ");
    printList(integers);

    // PECS (Producer Extends, Consumer Super) example
    List<Animal> animals = new ArrayList<>();
    List<Dog> dogs = Arrays.asList(new Dog("Buddy"), new Dog("Max"));
    List<Cat> cats = Arrays.asList(new Cat("Whiskers"), new Cat("Luna"));

    // Producer - extends (reading)
    System.out.println("\nPECS Example - Producer Extends:");
    System.out.println("Names of dogs:");
    printNames(dogs);
    System.out.println("Names of cats:");
    printNames(cats);

    // Consumer - super (writing)
    System.out.println("\nPECS Example - Consumer Super:");
    addDog(animals);
    System.out.println("Added a dog to animal list. Size: " + animals.size());
  }

  // Producer example (extends) - only reads from the list
  private static void printNames(List<? extends Animal> animals) {
    for (Animal animal : animals) {
      System.out.println(animal.getName());
    }
  }

  // Consumer example (super) - only writes to the list
  private static void addDog(List<? super Dog> animals) {
    animals.add(new Dog("Rex"));
  }
}