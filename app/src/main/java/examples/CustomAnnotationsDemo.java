package examples;

import utils.Utils;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CustomAnnotationsDemo {

  // Custom annotation for class level
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @interface ServiceInfo {
    String name();

    String description() default "No description available";

    int version() default 1;
  }

  // Custom annotation for method level with runtime retention
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  @interface MethodInfo {
    String author() default "Unknown";

    String date();

    int revision() default 1;

    String comments();
  }

  // Custom annotation for field level
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @interface FieldInfo {
    String description();

    boolean required() default false;
  }

  // Custom annotation that can be used multiple times
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @Repeatable(Maintainers.class)
  @interface Maintainer {
    String value();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @interface Maintainers {
    Maintainer[] value();
  }

  // Applying custom annotations to a test class
  @ServiceInfo(name = "UserService", description = "Handles user-related operations", version = 2)
  @Maintainer("John Doe")
  @Maintainer("Jane Smith")
  static class UserService {
    @FieldInfo(description = "User's unique identifier", required = true)
    private String userId;

    @FieldInfo(description = "User's email address", required = true)
    private String email;

    @MethodInfo(author = "John Doe", date = "2025-11-03", comments = "Method to validate user credentials")
    public boolean validateUser(String username, String password) {
      // Simulation of user validation
      return username != null && password != null;
    }

    @MethodInfo(author = "Jane Smith", date = "2025-11-03", revision = 2, comments = "Method to update user profile")
    public void updateProfile(String newEmail) {
      this.email = newEmail;
    }
  }

  public static void demonstrateCustomAnnotations() {
    Utils.printLine("Custom Annotations Examples");

    // Get the UserService class
    Class<?> userServiceClass = UserService.class;

    // 1. Reading Class-level annotations
    System.out.println("Class-level Annotations:");

    // Read ServiceInfo annotation
    if (userServiceClass.isAnnotationPresent(ServiceInfo.class)) {
      ServiceInfo serviceInfo = userServiceClass.getAnnotation(ServiceInfo.class);
      System.out.println("Service Name: " + serviceInfo.name());
      System.out.println("Description: " + serviceInfo.description());
      System.out.println("Version: " + serviceInfo.version());
    }

    // Read Maintainer annotations
    System.out.println("\nMaintainers:");
    Maintainer[] maintainers = userServiceClass.getAnnotationsByType(Maintainer.class);
    Arrays.stream(maintainers)
        .map(Maintainer::value)
        .forEach(name -> System.out.println("- " + name));

    // 2. Reading Method-level annotations
    System.out.println("\nMethod-level Annotations:");
    Method[] methods = userServiceClass.getDeclaredMethods();
    for (Method method : methods) {
      if (method.isAnnotationPresent(MethodInfo.class)) {
        MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
        System.out.println("\nMethod: " + method.getName());
        System.out.println("Author: " + methodInfo.author());
        System.out.println("Date: " + methodInfo.date());
        System.out.println("Revision: " + methodInfo.revision());
        System.out.println("Comments: " + methodInfo.comments());
      }
    }

    // 3. Reading Field-level annotations
    System.out.println("\nField-level Annotations:");
    Arrays.stream(userServiceClass.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(FieldInfo.class))
        .forEach(field -> {
          FieldInfo fieldInfo = field.getAnnotation(FieldInfo.class);
          System.out.println("\nField: " + field.getName());
          System.out.println("Description: " + fieldInfo.description());
          System.out.println("Required: " + fieldInfo.required());
        });

    // 4. Practical demonstration of annotation usage
    System.out.println("\nPractical Usage Example:");
    try {
      // Create instance and validate annotations at runtime
      UserService userService = new UserService();
      validateServiceAnnotations(userService);

      // Demonstrate method with annotation
      boolean isValid = userService.validateUser("testUser", "password");
      System.out.println("User validation result: " + isValid);

    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private static void validateServiceAnnotations(Object service) {
    Class<?> serviceClass = service.getClass();
    if (!serviceClass.isAnnotationPresent(ServiceInfo.class)) {
      System.out.println("Warning: Service class missing @ServiceInfo annotation");
    }

    // Validate that all required fields are present
    Arrays.stream(serviceClass.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(FieldInfo.class))
        .filter(field -> field.getAnnotation(FieldInfo.class).required())
        .forEach(field -> {
          System.out.println("Validated required field: " + field.getName());
        });
  }
}