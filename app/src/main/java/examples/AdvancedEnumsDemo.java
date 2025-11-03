package examples;

import utils.Utils;
import java.util.*;
import java.util.function.Supplier;

public class AdvancedEnumsDemo {

  // Enum with fields and methods
  enum DayOfWeek {
    MONDAY("Monday", "Start of work week", false),
    TUESDAY("Tuesday", "Second day", false),
    WEDNESDAY("Wednesday", "Mid-week", false),
    THURSDAY("Thursday", "Almost weekend", false),
    FRIDAY("Friday", "TGIF", false),
    SATURDAY("Saturday", "Weekend!", true),
    SUNDAY("Sunday", "Rest day", true);

    private final String displayName;
    private final String description;
    private final boolean isWeekend;

    DayOfWeek(String displayName, String description, boolean isWeekend) {
      this.displayName = displayName;
      this.description = description;
      this.isWeekend = isWeekend;
    }

    public String getDisplayName() {
      return displayName;
    }

    public String getDescription() {
      return description;
    }

    public boolean isWeekend() {
      return isWeekend;
    }

    public DayOfWeek nextDay() {
      int nextOrdinal = (ordinal() + 1) % values().length;
      return values()[nextOrdinal];
    }
  }

  // Enum implementing interface
  interface Operation {
    double apply(double x, double y);
  }

  enum MathOperation implements Operation {
    PLUS("+") {
      @Override
      public double apply(double x, double y) {
        return x + y;
      }
    },
    MINUS("-") {
      @Override
      public double apply(double x, double y) {
        return x - y;
      }
    },
    MULTIPLY("*") {
      @Override
      public double apply(double x, double y) {
        return x * y;
      }
    },
    DIVIDE("/") {
      @Override
      public double apply(double x, double y) {
        if (y == 0)
          throw new ArithmeticException("Division by zero");
        return x / y;
      }
    };

    private final String symbol;

    MathOperation(String symbol) {
      this.symbol = symbol;
    }

    public String getSymbol() {
      return symbol;
    }
  }

  // Enum with abstract methods
  enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS(4.869e+24, 6.0518e6),
    EARTH(5.976e+24, 6.37814e6),
    MARS(6.421e+23, 3.3972e6),
    JUPITER(1.9e+27, 7.1492e7),
    SATURN(5.688e+26, 6.0268e7),
    URANUS(8.686e+25, 2.5559e7),
    NEPTUNE(1.024e+26, 2.4746e7);

    private final double mass; // in kilograms
    private final double radius; // in meters
    private static final double G = 6.67300E-11;

    Planet(double mass, double radius) {
      this.mass = mass;
      this.radius = radius;
    }

    public double getMass() {
      return mass;
    }

    public double getRadius() {
      return radius;
    }

    public double surfaceGravity() {
      return G * mass / (radius * radius);
    }

    public double surfaceWeight(double otherMass) {
      return otherMass * surfaceGravity();
    }
  }

  // Enum with strategy pattern
  enum PaymentType {
    CREDIT_CARD(payment -> {
      System.out.println("Processing credit card payment of $" + payment);
      return "CC-" + UUID.randomUUID().toString();
    }),
    DEBIT_CARD(payment -> {
      System.out.println("Processing debit card payment of $" + payment);
      return "DC-" + UUID.randomUUID().toString();
    }),
    PAYPAL(payment -> {
      System.out.println("Processing PayPal payment of $" + payment);
      return "PP-" + UUID.randomUUID().toString();
    });

    private final PaymentStrategy strategy;

    PaymentType(PaymentStrategy strategy) {
      this.strategy = strategy;
    }

    public String processPayment(double amount) {
      return strategy.process(amount);
    }
  }

  @FunctionalInterface
  interface PaymentStrategy {
    String process(double payment);
  }

  // Enum with singleton pattern
  enum Configuration {
    INSTANCE;

    private final Map<String, String> properties;

    Configuration() {
      properties = new HashMap<>();
      // Initialize default properties
      properties.put("version", "1.0");
      properties.put("name", "Advanced Enum Demo");
    }

    public String getProperty(String key) {
      return properties.get(key);
    }

    public void setProperty(String key, String value) {
      properties.put(key, value);
    }
  }

  public static void demonstrateAdvancedEnums() {
    Utils.printLine("Advanced Enums Examples");

    // 1. Enum with fields and methods
    System.out.println("Days of Week Example:");
    for (DayOfWeek day : DayOfWeek.values()) {
      System.out.printf("%s (%s) - %s, Weekend? %s%n",
          day.name(),
          day.getDisplayName(),
          day.getDescription(),
          day.isWeekend());
    }

    // Demonstrate next day functionality
    DayOfWeek today = DayOfWeek.FRIDAY;
    System.out.println("\nNext day after " + today + " is " + today.nextDay());

    // 2. Enum implementing interface
    System.out.println("\nMath Operations Example:");
    double x = 10, y = 5;
    for (MathOperation op : MathOperation.values()) {
      System.out.printf("%f %s %f = %f%n",
          x, op.getSymbol(), y, op.apply(x, y));
    }

    // 3. Complex enum with calculations
    System.out.println("\nPlanet Calculations Example:");
    double earthWeight = 75.0; // kg
    double mass = earthWeight / Planet.EARTH.surfaceGravity();
    for (Planet planet : Planet.values()) {
      double weight = planet.surfaceWeight(mass);
      System.out.printf("Weight on %s is %.2f N%n",
          planet.name(), weight);
    }

    // 4. Enum with strategy pattern
    System.out.println("\nPayment Processing Example:");
    double paymentAmount = 99.99;
    for (PaymentType type : PaymentType.values()) {
      String confirmation = type.processPayment(paymentAmount);
      System.out.println("Confirmation ID: " + confirmation);
    }

    // 5. Enum singleton configuration
    System.out.println("\nConfiguration Singleton Example:");
    Configuration config = Configuration.INSTANCE;
    System.out.println("Initial version: " +
        config.getProperty("version"));

    config.setProperty("version", "2.0");
    System.out.println("Updated version: " +
        config.getProperty("version"));

    // 6. EnumSet and EnumMap examples
    System.out.println("\nEnumSet Example:");
    EnumSet<DayOfWeek> weekends = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    System.out.println("Weekend days: " + weekends);

    EnumMap<DayOfWeek, String> activities = new EnumMap<>(DayOfWeek.class);
    activities.put(DayOfWeek.MONDAY, "Start new projects");
    activities.put(DayOfWeek.FRIDAY, "Review weekly progress");
    System.out.println("\nEnumMap Example:");
    activities.forEach((day, activity) -> System.out.println(day + ": " + activity));
  }
}