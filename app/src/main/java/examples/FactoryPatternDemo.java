package examples;

import utils.Utils;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FactoryPatternDemo {

  // Simple Factory Pattern Example
  // Product interface
  interface Animal {
    String makeSound();

    String getType();
  }

  // Concrete products
  static class Dog implements Animal {
    @Override
    public String makeSound() {
      return "Woof!";
    }

    @Override
    public String getType() {
      return "Dog";
    }
  }

  static class Cat implements Animal {
    @Override
    public String makeSound() {
      return "Meow!";
    }

    @Override
    public String getType() {
      return "Cat";
    }
  }

  static class Bird implements Animal {
    @Override
    public String makeSound() {
      return "Tweet!";
    }

    @Override
    public String getType() {
      return "Bird";
    }
  }

  // Simple factory
  static class SimpleAnimalFactory {
    public static Animal createAnimal(String type) {
      return switch (type.toLowerCase()) {
        case "dog" -> new Dog();
        case "cat" -> new Cat();
        case "bird" -> new Bird();
        default -> throw new IllegalArgumentException("Invalid animal type");
      };
    }
  }

  // Factory Method Pattern Example
  // Abstract creator
  static abstract class VehicleFactory {
    abstract Vehicle createVehicle(String model);

    // Factory method
    public Vehicle orderVehicle(String model) {
      Vehicle vehicle = createVehicle(model);
      vehicle.assemble();
      vehicle.test();
      return vehicle;
    }
  }

  // Concrete creators
  static class CarFactory extends VehicleFactory {
    @Override
    Vehicle createVehicle(String model) {
      return switch (model.toLowerCase()) {
        case "sports" -> new SportsCar();
        case "family" -> new FamilyCar();
        default -> throw new IllegalArgumentException("Invalid car model");
      };
    }
  }

  static class MotorcycleFactory extends VehicleFactory {
    @Override
    Vehicle createVehicle(String model) {
      return switch (model.toLowerCase()) {
        case "cruiser" -> new CruiserMotorcycle();
        case "sport" -> new SportMotorcycle();
        default -> throw new IllegalArgumentException("Invalid motorcycle model");
      };
    }
  }

  // Product interface
  interface Vehicle {
    void assemble();

    void test();

    String getDescription();
  }

  // Concrete products
  static class SportsCar implements Vehicle {
    @Override
    public void assemble() {
      System.out.println("Assembling Sports Car");
    }

    @Override
    public void test() {
      System.out.println("Testing Sports Car: High-speed performance test");
    }

    @Override
    public String getDescription() {
      return "Sports Car: High-performance vehicle";
    }
  }

  static class FamilyCar implements Vehicle {
    @Override
    public void assemble() {
      System.out.println("Assembling Family Car");
    }

    @Override
    public void test() {
      System.out.println("Testing Family Car: Safety features test");
    }

    @Override
    public String getDescription() {
      return "Family Car: Comfortable and safe";
    }
  }

  static class CruiserMotorcycle implements Vehicle {
    @Override
    public void assemble() {
      System.out.println("Assembling Cruiser Motorcycle");
    }

    @Override
    public void test() {
      System.out.println("Testing Cruiser Motorcycle: Comfort test");
    }

    @Override
    public String getDescription() {
      return "Cruiser Motorcycle: Comfortable for long rides";
    }
  }

  static class SportMotorcycle implements Vehicle {
    @Override
    public void assemble() {
      System.out.println("Assembling Sport Motorcycle");
    }

    @Override
    public void test() {
      System.out.println("Testing Sport Motorcycle: Agility test");
    }

    @Override
    public String getDescription() {
      return "Sport Motorcycle: Built for speed and agility";
    }
  }

  // Abstract Factory Pattern Example
  // Abstract product interfaces
  interface Button {
    void render();

    void onClick();
  }

  interface TextField {
    void render();

    void onType();
  }

  // Concrete products for Light theme
  static class LightButton implements Button {
    @Override
    public void render() {
      System.out.println("Rendering Light Button");
    }

    @Override
    public void onClick() {
      System.out.println("Light Button clicked");
    }
  }

  static class LightTextField implements TextField {
    @Override
    public void render() {
      System.out.println("Rendering Light TextField");
    }

    @Override
    public void onType() {
      System.out.println("Typing in Light TextField");
    }
  }

  // Concrete products for Dark theme
  static class DarkButton implements Button {
    @Override
    public void render() {
      System.out.println("Rendering Dark Button");
    }

    @Override
    public void onClick() {
      System.out.println("Dark Button clicked");
    }
  }

  static class DarkTextField implements TextField {
    @Override
    public void render() {
      System.out.println("Rendering Dark TextField");
    }

    @Override
    public void onType() {
      System.out.println("Typing in Dark TextField");
    }
  }

  // Abstract factory interface
  interface GUIFactory {
    Button createButton();

    TextField createTextField();
  }

  // Concrete factories
  static class LightThemeFactory implements GUIFactory {
    @Override
    public Button createButton() {
      return new LightButton();
    }

    @Override
    public TextField createTextField() {
      return new LightTextField();
    }
  }

  static class DarkThemeFactory implements GUIFactory {
    @Override
    public Button createButton() {
      return new DarkButton();
    }

    @Override
    public TextField createTextField() {
      return new DarkTextField();
    }
  }

  // Modern Factory Implementation using Functional Interface
  static class ModernFactory<T> {
    private final Map<String, Supplier<T>> factories = new HashMap<>();

    public void register(String key, Supplier<T> factory) {
      factories.put(key, factory);
    }

    public T create(String key) {
      Supplier<T> factory = factories.get(key);
      if (factory == null) {
        throw new IllegalArgumentException("Unknown factory: " + key);
      }
      return factory.get();
    }
  }

  public static void demonstrateFactory() {
    Utils.printLine("Factory Pattern Examples");

    // 1. Simple Factory
    System.out.println("Simple Factory Example:");
    Animal dog = SimpleAnimalFactory.createAnimal("dog");
    Animal cat = SimpleAnimalFactory.createAnimal("cat");
    Animal bird = SimpleAnimalFactory.createAnimal("bird");

    System.out.println(dog.getType() + " says: " + dog.makeSound());
    System.out.println(cat.getType() + " says: " + cat.makeSound());
    System.out.println(bird.getType() + " says: " + bird.makeSound());

    // 2. Factory Method
    System.out.println("\nFactory Method Example:");
    VehicleFactory carFactory = new CarFactory();
    VehicleFactory motorcycleFactory = new MotorcycleFactory();

    // Creating different types of vehicles
    Vehicle sportsCar = carFactory.orderVehicle("sports");
    Vehicle familyCar = carFactory.orderVehicle("family");
    Vehicle cruiserMotorcycle = motorcycleFactory.orderVehicle("cruiser");
    Vehicle sportMotorcycle = motorcycleFactory.orderVehicle("sport");

    System.out.println("\nVehicle Descriptions:");
    System.out.println(sportsCar.getDescription());
    System.out.println(familyCar.getDescription());
    System.out.println(cruiserMotorcycle.getDescription());
    System.out.println(sportMotorcycle.getDescription());

    // 3. Abstract Factory
    System.out.println("\nAbstract Factory Example:");

    // Create UI with Light theme
    System.out.println("Light Theme:");
    GUIFactory lightFactory = new LightThemeFactory();
    Button lightButton = lightFactory.createButton();
    TextField lightTextField = lightFactory.createTextField();

    lightButton.render();
    lightButton.onClick();
    lightTextField.render();
    lightTextField.onType();

    // Create UI with Dark theme
    System.out.println("\nDark Theme:");
    GUIFactory darkFactory = new DarkThemeFactory();
    Button darkButton = darkFactory.createButton();
    TextField darkTextField = darkFactory.createTextField();

    darkButton.render();
    darkButton.onClick();
    darkTextField.render();
    darkTextField.onType();

    // 4. Modern Factory using Functional Interface
    System.out.println("\nModern Factory Example:");
    ModernFactory<Animal> modernAnimalFactory = new ModernFactory<>();

    // Register factories
    modernAnimalFactory.register("dog", Dog::new);
    modernAnimalFactory.register("cat", Cat::new);
    modernAnimalFactory.register("bird", Bird::new);

    // Create animals using modern factory
    Animal modernDog = modernAnimalFactory.create("dog");
    Animal modernCat = modernAnimalFactory.create("cat");

    System.out.println("Modern factory created animals:");
    System.out.println(modernDog.getType() + " says: " + modernDog.makeSound());
    System.out.println(modernCat.getType() + " says: " + modernCat.makeSound());
  }
}