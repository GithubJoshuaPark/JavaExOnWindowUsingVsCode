package examples;

import utils.Utils;
import java.lang.reflect.*;
import java.util.Arrays;

public class ReflectionAPIDemo {
  // Sample classes for reflection demonstration
  @SuppressWarnings("unused")
  private static class Person {
    private String name;
    private int age;
    public String email;

    public Person() {
      this("Unknown", 0);
    }

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
      this.email = name.toLowerCase() + "@example.com";
    }

    private void setName(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    private void setAge(int age) {
      if (age >= 0) {
        this.age = age;
      }
    }

    public int getAge() {
      return age;
    }

    private String getPersonalInfo(String prefix) {
      return prefix + ": " + name + ", " + age + " years old";
    }
  }

  public static void demonstrateReflection() {
    Utils.printLine("Reflection API Examples");

    // 1. Getting Class Information
    System.out.println("Class Information:");
    Class<?> personClass = Person.class;

    System.out.println("Class name: " + personClass.getName());
    System.out.println("Simple name: " + personClass.getSimpleName());
    System.out.println("Package name: " + personClass.getPackage().getName());
    System.out.println("Is interface: " + personClass.isInterface());
    System.out.println("Superclass: " + personClass.getSuperclass().getName());

    // 2. Analyzing Fields
    System.out.println("\nField Analysis:");

    // Get all fields (including private)
    Field[] allFields = personClass.getDeclaredFields();
    System.out.println("\nAll declared fields:");
    for (Field field : allFields) {
      System.out.printf("Field: %s, Type: %s, Access: %s%n",
          field.getName(),
          field.getType().getSimpleName(),
          Modifier.toString(field.getModifiers()));
    }

    // 3. Analyzing Methods
    System.out.println("\nMethod Analysis:");

    Method[] methods = personClass.getDeclaredMethods();
    System.out.println("\nAll declared methods:");
    for (Method method : methods) {
      System.out.printf("Method: %s%nReturn Type: %s%nParameters: %s%nAccess: %s%n%n",
          method.getName(),
          method.getReturnType().getSimpleName(),
          Arrays.toString(method.getParameterTypes()),
          Modifier.toString(method.getModifiers()));
    }

    // 4. Analyzing Constructors
    System.out.println("Constructor Analysis:");

    Constructor<?>[] constructors = personClass.getDeclaredConstructors();
    for (Constructor<?> constructor : constructors) {
      System.out.printf("Constructor Parameters: %s%n",
          Arrays.toString(constructor.getParameterTypes()));
    }

    // 5. Creating Objects using Reflection
    System.out.println("\nCreating Objects:");
    try {
      // Create object using no-arg constructor
      Person person1 = (Person) personClass.getDeclaredConstructor().newInstance();
      System.out.println("Created person1 with default constructor");

      // Create object using parameterized constructor
      Constructor<?> paramConstructor = personClass.getDeclaredConstructor(String.class, int.class);
      Person person2 = (Person) paramConstructor.newInstance("Alice", 30);
      System.out.println("Created person2: " + person2.getName());

    } catch (Exception e) {
      System.out.println("Error creating objects: " + e.getMessage());
    }

    // 6. Accessing and Modifying Fields
    System.out.println("\nAccessing and Modifying Fields:");
    try {
      Person person = new Person("Bob", 25);

      // Access private field
      Field nameField = personClass.getDeclaredField("name");
      nameField.setAccessible(true);
      System.out.println("Original name: " + nameField.get(person));

      // Modify private field
      nameField.set(person, "Robert");
      System.out.println("Modified name: " + person.getName());

    } catch (Exception e) {
      System.out.println("Error accessing fields: " + e.getMessage());
    }

    // 7. Invoking Methods
    System.out.println("\nInvoking Methods:");
    try {
      Person person = new Person("Charlie", 35);

      // Invoke private method
      Method personalInfoMethod = personClass.getDeclaredMethod("getPersonalInfo", String.class);
      personalInfoMethod.setAccessible(true);
      String result = (String) personalInfoMethod.invoke(person, "Info");
      System.out.println("Invoked private method: " + result);

      // Invoke private setter
      Method setAgeMethod = personClass.getDeclaredMethod("setAge", int.class);
      setAgeMethod.setAccessible(true);
      setAgeMethod.invoke(person, 36);
      System.out.println("Age after reflection: " + person.getAge());

    } catch (Exception e) {
      System.out.println("Error invoking methods: " + e.getMessage());
    }

    // 8. Array Creation using Reflection
    System.out.println("\nArray Creation using Reflection:");
    try {
      // Create array of Person objects
      Object array = Array.newInstance(personClass, 3);
      Array.set(array, 0, new Person("Dave", 28));
      Array.set(array, 1, new Person("Eve", 32));
      Array.set(array, 2, new Person("Frank", 45));

      Person[] persons = (Person[]) array;
      for (Person p : persons) {
        System.out.println("Array element: " + p.getName());
      }

    } catch (Exception e) {
      System.out.println("Error creating array: " + e.getMessage());
    }
  }
}