package examples;

import utils.Utils;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HashMapUsageExamples {
  public static void demonstrateHashMap() {
    Utils.printLine("HashMap Usage Examples");

    // Basic HashMap Operations
    System.out.println("\n=== Basic HashMap Operations ===");
    Map<String, Integer> scores = new HashMap<>();

    // Adding elements
    scores.put("John", 95);
    scores.put("Alice", 87);
    scores.put("Bob", 92);
    scores.put("Carol", 98);

    System.out.println("Initial scores: " + scores);

    // Updating value
    scores.put("John", 97); // Overwrites previous value
    System.out.println("\nAfter updating John's score: " + scores);

    // Get value with default
    System.out.println("David's score (default 0): " + scores.getOrDefault("David", 0));

    // Checking existence
    System.out.println("\n=== Checking Map Contents ===");
    System.out.println("Contains Bob? " + scores.containsKey("Bob"));
    System.out.println("Contains score 87? " + scores.containsValue(87));

    // Advanced Operations
    System.out.println("\n=== Advanced HashMap Operations ===");

    // computeIfAbsent - add value only if key doesn't exist
    scores.computeIfAbsent("Eva", k -> 85);
    System.out.println("After computeIfAbsent for Eva: " + scores);

    // computeIfPresent - modify value if key exists
    scores.computeIfPresent("Bob", (k, v) -> v + 3);
    System.out.println("After increasing Bob's score: " + scores);

    // merge - combine values
    scores.merge("Alice", 5, (oldValue, value) -> oldValue + value);
    System.out.println("After merging Alice's score with 5: " + scores);

    // Iteration Methods
    System.out.println("\n=== Different Ways to Iterate ===");

    System.out.println("1. Iterate over entries:");
    for (Map.Entry<String, Integer> entry : scores.entrySet()) {
      System.out.printf("  %s scored %d%n", entry.getKey(), entry.getValue());
    }

    System.out.println("\n2. Iterate over keys:");
    for (String name : scores.keySet()) {
      System.out.printf("  Student: %s%n", name);
    }

    System.out.println("\n3. Iterate over values:");
    for (Integer score : scores.values()) {
      System.out.printf("  Score: %d%n", score);
    }

    // Using forEach with lambda
    System.out.println("\n4. Using forEach:");
    scores.forEach((name, score) -> System.out.printf("  %s => %d%n", name, score));

    // Practical Example: Grade Distribution
    System.out.println("\n=== Practical Example: Grade Distribution ===");
    Map<String, String> grades = new HashMap<>();
    scores.forEach((name, score) -> {
      if (score >= 90)
        grades.put(name, "A");
      else if (score >= 80)
        grades.put(name, "B");
      else if (score >= 70)
        grades.put(name, "C");
      else
        grades.put(name, "D");
    });

    // Sort by name using TreeMap
    Map<String, String> sortedGrades = new TreeMap<>(grades);
    System.out.println("Grade distribution (sorted by name):");
    sortedGrades.forEach((name, grade) -> System.out.printf("  %s: %s%n", name, grade));

    // Clean up
    System.out.println("\n=== Cleanup Operations ===");
    System.out.println("Original size: " + scores.size());
    scores.clear();
    System.out.println("After clearing: " + scores.size());

    Utils.printLine("End of HashMap Usage Examples");
  }
}