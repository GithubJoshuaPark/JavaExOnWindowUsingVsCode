package examples;

import utils.Utils;
import java.util.*;

public class CollectionsFrameworkOverview {
  public static void demonstrateCollections() {
    Utils.printLine("Collections Framework Overview");

    // List Interface Examples
    System.out.println("\n=== List Interface ===");
    List<String> arrayList = new ArrayList<>();
    arrayList.add("First");
    arrayList.add("Second");
    arrayList.add("Third");
    System.out.println("ArrayList: " + arrayList);

    List<String> linkedList = new LinkedList<>(arrayList);
    linkedList.add(1, "Inserted");
    System.out.println("LinkedList with insertion: " + linkedList);

    // Set Interface Examples
    System.out.println("\n=== Set Interface ===");
    Set<String> hashSet = new HashSet<>();
    hashSet.add("Apple");
    hashSet.add("Banana");
    hashSet.add("Apple"); // Duplicate won't be added
    System.out.println("HashSet (unordered, no duplicates): " + hashSet);

    Set<String> treeSet = new TreeSet<>();
    treeSet.add("Zebra");
    treeSet.add("Cat");
    treeSet.add("Dog");
    System.out.println("TreeSet (sorted naturally): " + treeSet);

    // Queue Interface Examples
    System.out.println("\n=== Queue Interface ===");
    Queue<String> queue = new LinkedList<>();
    queue.offer("First");
    queue.offer("Second");
    queue.offer("Third");
    System.out.println("Queue: " + queue);
    System.out.println("Queue.peek(): " + queue.peek());
    System.out.println("Queue.poll(): " + queue.poll());
    System.out.println("Queue after poll: " + queue);

    // Deque Interface Examples
    System.out.println("\n=== Deque Interface ===");
    Deque<String> deque = new ArrayDeque<>();
    deque.addFirst("Front");
    deque.addLast("Back");
    deque.addFirst("Very Front");
    System.out.println("Deque: " + deque);
    System.out.println("Deque.peekFirst(): " + deque.peekFirst());
    System.out.println("Deque.peekLast(): " + deque.peekLast());

    // Map Interface Examples
    System.out.println("\n=== Map Interface ===");
    Map<String, Integer> hashMap = new HashMap<>();
    hashMap.put("One", 1);
    hashMap.put("Two", 2);
    hashMap.put("Three", 3);
    System.out.println("HashMap: " + hashMap);

    Map<String, Integer> treeMap = new TreeMap<>();
    treeMap.putAll(hashMap);
    System.out.println("TreeMap (sorted by keys): " + treeMap);

    // Collections Utility Methods
    System.out.println("\n=== Collections Utility Methods ===");
    List<Integer> numbers = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3));
    System.out.println("Original list: " + numbers);
    Collections.sort(numbers);
    System.out.println("Sorted list: " + numbers);
    System.out.println("Max: " + Collections.max(numbers));
    System.out.println("Min: " + Collections.min(numbers));
    System.out.println("Frequency of 5: " + Collections.frequency(numbers, 5));

    Utils.printLine("End of Collections Framework Overview");
  }
}