package examples;

import utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SingletonPatternDemo {

  // Basic Singleton (not thread-safe)
  static class BasicSingleton {
    private static BasicSingleton instance;
    private String data;

    private BasicSingleton() {
      data = "Basic Singleton Data";
    }

    public static BasicSingleton getInstance() {
      if (instance == null) {
        instance = new BasicSingleton();
      }
      return instance;
    }

    public String getData() {
      return data;
    }
  }

  // Thread-safe Singleton using synchronized method
  static class SynchronizedSingleton {
    private static SynchronizedSingleton instance;
    private String data;

    private SynchronizedSingleton() {
      data = "Synchronized Singleton Data";
    }

    public static synchronized SynchronizedSingleton getInstance() {
      if (instance == null) {
        instance = new SynchronizedSingleton();
      }
      return instance;
    }

    public String getData() {
      return data;
    }
  }

  // Thread-safe Singleton using double-checked locking
  static class DoubleCheckedSingleton {
    private static volatile DoubleCheckedSingleton instance;
    private String data;

    private DoubleCheckedSingleton() {
      data = "Double-Checked Singleton Data";
    }

    public static DoubleCheckedSingleton getInstance() {
      if (instance == null) {
        synchronized (DoubleCheckedSingleton.class) {
          if (instance == null) {
            instance = new DoubleCheckedSingleton();
          }
        }
      }
      return instance;
    }

    public String getData() {
      return data;
    }
  }

  // Thread-safe Singleton using static initialization (eager loading)
  static class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();
    private String data;

    private EagerSingleton() {
      data = "Eager Singleton Data";
    }

    public static EagerSingleton getInstance() {
      return instance;
    }

    public String getData() {
      return data;
    }
  }

  // Thread-safe Singleton using static holder class (lazy loading)
  static class LazyHolderSingleton {
    private String data;

    private LazyHolderSingleton() {
      data = "Lazy Holder Singleton Data";
    }

    private static class SingletonHolder {
      private static final LazyHolderSingleton instance = new LazyHolderSingleton();
    }

    public static LazyHolderSingleton getInstance() {
      return SingletonHolder.instance;
    }

    public String getData() {
      return data;
    }
  }

  // Enum Singleton (automatically thread-safe)
  enum EnumSingleton {
    INSTANCE;

    private String data;

    EnumSingleton() {
      data = "Enum Singleton Data";
    }

    public String getData() {
      return data;
    }
  }

  // Practical example: Configuration Manager
  static class ConfigurationManager {
    private static final ConfigurationManager instance = new ConfigurationManager();
    private final List<String> settings;

    private ConfigurationManager() {
      settings = new ArrayList<>();
      // Simulate loading configuration
      settings.add("Database URL: jdbc:mysql://localhost:3306/db");
      settings.add("Max Pool Size: 100");
      settings.add("Timeout: 30000");
    }

    public static ConfigurationManager getInstance() {
      return instance;
    }

    public List<String> getSettings() {
      return new ArrayList<>(settings);
    }

    public void addSetting(String setting) {
      settings.add(setting);
    }
  }

  public static void demonstrateSingleton() {
    Utils.printLine("Singleton Pattern Examples");

    // 1. Basic Singleton Demo
    System.out.println("Basic Singleton Example:");
    BasicSingleton basic1 = BasicSingleton.getInstance();
    BasicSingleton basic2 = BasicSingleton.getInstance();
    System.out.println("Are basic instances same? " + (basic1 == basic2));
    System.out.println("Basic singleton data: " + basic1.getData());

    // 2. Thread-safe Singleton Demo
    System.out.println("\nThread-safe Singleton Examples:");

    // Test with multiple threads
    ExecutorService executor = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 5; i++) {
      executor.submit(() -> {
        SynchronizedSingleton sync = SynchronizedSingleton.getInstance();
        System.out.println("Synchronized: " + sync.getData() +
            " - " + System.identityHashCode(sync));
      });
    }

    // 3. Double-Checked Locking Demo
    System.out.println("\nDouble-Checked Locking Singleton:");
    DoubleCheckedSingleton doubleChecked1 = DoubleCheckedSingleton.getInstance();
    DoubleCheckedSingleton doubleChecked2 = DoubleCheckedSingleton.getInstance();
    System.out.println("Are double-checked instances same? " +
        (doubleChecked1 == doubleChecked2));

    // 4. Eager Singleton Demo
    System.out.println("\nEager Singleton Example:");
    EagerSingleton eager1 = EagerSingleton.getInstance();
    EagerSingleton eager2 = EagerSingleton.getInstance();
    System.out.println("Are eager instances same? " + (eager1 == eager2));
    System.out.println("Eager singleton data: " + eager1.getData());

    // 5. Lazy Holder Singleton Demo
    System.out.println("\nLazy Holder Singleton Example:");
    LazyHolderSingleton lazy1 = LazyHolderSingleton.getInstance();
    LazyHolderSingleton lazy2 = LazyHolderSingleton.getInstance();
    System.out.println("Are lazy holder instances same? " + (lazy1 == lazy2));
    System.out.println("Lazy holder singleton data: " + lazy1.getData());

    // 6. Enum Singleton Demo
    System.out.println("\nEnum Singleton Example:");
    EnumSingleton enumSingleton = EnumSingleton.INSTANCE;
    System.out.println("Enum singleton data: " + enumSingleton.getData());

    // 7. Practical Example with Configuration Manager
    System.out.println("\nConfiguration Manager Example:");
    ConfigurationManager config = ConfigurationManager.getInstance();
    System.out.println("Initial settings:");
    config.getSettings().forEach(setting -> System.out.println("- " + setting));

    config.addSetting("New Setting: Cache Size = 1000");
    System.out.println("\nUpdated settings:");
    config.getSettings().forEach(setting -> System.out.println("- " + setting));

    // Shutdown executor and wait for completion
    executor.shutdown();
    try {
      executor.awaitTermination(2, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}