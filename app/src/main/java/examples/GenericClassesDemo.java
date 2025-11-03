package examples;

import utils.Utils;

// Generic class with a type parameter T
class Box<T> {
  private T content;

  public Box(T content) {
    this.content = content;
  }

  public T getContent() {
    return content;
  }

  public void setContent(T content) {
    this.content = content;
  }
}

// Generic class with multiple type parameters
class Pair<K, V> {
  private K key;
  private V value;

  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }
}

// Generic method example
class GenericMethods {
  public <T> void printArray(T[] array) {
    for (T element : array) {
      System.out.print(element + " ");
    }
    System.out.println();
  }
}

public class GenericClassesDemo {
  public static void demonstrateGenerics() {
    Utils.printLine("Generic Classes Examples");

    // Using Box with different types
    Box<Integer> intBox = new Box<>(42);
    Box<String> stringBox = new Box<>("Hello Generics!");

    System.out.println("Integer Box contains: " + intBox.getContent());
    System.out.println("String Box contains: " + stringBox.getContent());

    // Using Pair with different type combinations
    Pair<Integer, String> pair1 = new Pair<>(1, "One");
    Pair<String, Double> pair2 = new Pair<>("Pi", 3.14159);

    System.out.println("\nPair Examples:");
    System.out.println("Pair 1: Key=" + pair1.getKey() + ", Value=" + pair1.getValue());
    System.out.println("Pair 2: Key=" + pair2.getKey() + ", Value=" + pair2.getValue());

    // Using generic methods
    GenericMethods gm = new GenericMethods();
    Integer[] intArray = { 1, 2, 3, 4, 5 };
    String[] stringArray = { "Hello", "Generic", "Methods" };

    System.out.println("\nGeneric Method Examples:");
    System.out.print("Integer Array: ");
    gm.printArray(intArray);
    System.out.print("String Array: ");
    gm.printArray(stringArray);

    // Type inference example (Diamond operator)
    Box<Double> doubleBox = new Box<>(3.14);
    System.out.println("\nType Inference Example:");
    System.out.println("Double Box contains: " + doubleBox.getContent());
  }
}