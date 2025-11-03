package examples;

import utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

public class ArrayListImplementation {
  // Custom class for demonstration
  static class Student {
    private String name;
    private int id;
    private double gpa;

    public Student(String name, int id, double gpa) {
      this.name = name;
      this.id = id;
      this.gpa = gpa;
    }

    @Override
    public String toString() {
      return String.format("Student(id=%d, name='%s', gpa=%.2f)", id, name, gpa);
    }

    public double getGpa() {
      return gpa;
    }

    public String getName() {
      return name;
    }
  }

  public static void demonstrateArrayList() {
    Utils.printLine("ArrayList Implementation Examples");

    // Creating and Adding Elements
    System.out.println("\n=== Basic ArrayList Operations ===");
    ArrayList<Student> students = new ArrayList<>();

    // Adding elements
    students.add(new Student("John Doe", 1001, 3.8));
    students.add(new Student("Jane Smith", 1002, 3.9));
    students.add(new Student("Bob Johnson", 1003, 3.5));
    students.add(new Student("Alice Brown", 1004, 4.0));

    System.out.println("Initial list of students:");
    students.forEach(System.out::println);

    // ArrayList size and capacity
    System.out.println("\n=== Size and Access ===");
    System.out.println("Number of students: " + students.size());
    System.out.println("First student: " + students.get(0));
    System.out.println("Last student: " + students.get(students.size() - 1));

    // Different ways to iterate
    System.out.println("\n=== Different Iteration Methods ===");

    System.out.println("1. Using enhanced for loop:");
    for (Student student : students) {
      System.out.println(student);
    }

    System.out.println("\n2. Using iterator:");
    Iterator<Student> iterator = students.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }

    System.out.println("\n3. Using forEach method with lambda:");
    students.forEach(student -> System.out.println(student));

    // Sorting
    System.out.println("\n=== Sorting Examples ===");

    System.out.println("Sorting by GPA (using Comparator):");
    students.sort(Comparator.comparingDouble(Student::getGpa).reversed());
    students.forEach(System.out::println);

    System.out.println("\nSorting by name:");
    students.sort(Comparator.comparing(Student::getName));
    students.forEach(System.out::println);

    // Searching and Modifying
    System.out.println("\n=== Searching and Modifying ===");

    // Finding student with highest GPA using Stream API
    Student topStudent = students.stream()
        .max(Comparator.comparingDouble(Student::getGpa))
        .orElse(null);
    System.out.println("Top student: " + topStudent);

    // Removing elements
    System.out.println("\nRemoving first student:");
    students.remove(0);
    students.forEach(System.out::println);

    // SubList
    System.out.println("\n=== SubList Operation ===");
    List<Student> topTwo = students.subList(0, Math.min(2, students.size()));
    System.out.println("Top 2 students:");
    topTwo.forEach(System.out::println);

    // Clear the list
    System.out.println("\n=== Clearing ArrayList ===");
    students.clear();
    System.out.println("After clearing, size: " + students.size());

    Utils.printLine("End of ArrayList Implementation Examples");
  }
}