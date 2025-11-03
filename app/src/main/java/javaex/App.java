/*
 * Java Learning Examples Application
 */

package javaex;

import utils.Utils;
import examples.*;
import java.util.Scanner;

public class App {
    private static void showMenu() {
        Utils.printLine("Java Learning Examples");
        System.out.println(" 1. Advanced Data Types");
        System.out.println(" 2. String Manipulation");
        System.out.println(" 3. Collections Framework");
        System.out.println(" 4. ArrayList Implementation");
        System.out.println(" 5. HashMap Usage");
        System.out.println(" 6. Exception Handling");
        System.out.println(" 7. Custom Exceptions");
        System.out.println(" 8. File I/O Operations");
        System.out.println(" 9. Serialization");
        System.out.println("10. Multithreading Basics");
        System.out.println("11. Synchronization");
        System.out.println("12. Executor Framework");
        System.out.println("13. Generic Classes");
        System.out.println("14. Wildcards in Generics");
        System.out.println("15. Lambda Expressions");
        System.out.println("16. Stream API");
        System.out.println("17. Optional Class");
        System.out.println("18. Functional Interfaces");
        System.out.println("19. Method References");
        System.out.println("20. Custom Annotations");
        System.out.println("21. Reflection API");
        System.out.println("22. Date/Time API");
        System.out.println("23. Singleton Pattern");
        System.out.println("24. Factory Pattern");
        System.out.println("25. Observer Pattern");
        System.out.println("26. Advanced Enums");
        System.out.println("27. JUnit Testing");
        System.out.println("28. Mockito Framework");
        System.out.println("29. JDBC Operations(Todo with sqlite, json)");
        System.out.println("30. Simple Application(Snake Game)");
        System.out.println(" 0. Exit");
        System.out.print("\nSelect an example to run (0-30): ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            if (!scanner.hasNextLine()) {
                System.out.println("No console input available. Exiting.");
                scanner.close();
                return;
            }
            String choice = scanner.nextLine();
            System.out.println(); // Add a blank line for better readability

            switch (choice) {
                case "1":
                    DataTypesAdvanced.demonstrateDataTypes();
                    break;
                case "2":
                    StringManipulationExamples.demonstrateStringManipulation();
                    break;
                case "3":
                    CollectionsFrameworkOverview.demonstrateCollections();
                    break;
                case "4":
                    ArrayListImplementation.demonstrateArrayList();
                    break;
                case "5":
                    HashMapUsageExamples.demonstrateHashMap();
                    break;
                case "6":
                    ExceptionHandlingExamples.demonstrateExceptionHandling();
                    break;
                case "7":
                    CustomExceptionsCreation.demonstrateCustomExceptions();
                    break;
                case "8":
                    FileIOOperations.demonstrateFileIO();
                    break;
                case "9":
                    SerializationExample.demonstrateSerialization();
                    break;
                case "10":
                    MultithreadingBasics.demonstrateMultithreading();
                    break;
                case "11":
                    SynchronizationTechniques.demonstrateSynchronization();
                    break;
                case "12":
                    ExecutorFrameworkUsage.demonstrateExecutorFramework();
                    break;
                case "13":
                    GenericClassesDemo.demonstrateGenerics();
                    break;
                case "14":
                    WildcardsInGenerics.demonstrateWildcards();
                    break;
                case "15":
                    LambdaExpressionsDemo.demonstrateLambdas();
                    break;
                case "16":
                    StreamAPIDemo.demonstrateStreamAPI();
                    break;
                case "17":
                    OptionalClassDemo.demonstrateOptional();
                    break;
                case "18":
                    FunctionalInterfacesDemo.demonstrateFunctionalInterfaces();
                    break;
                case "19":
                    MethodReferencesDemo.demonstrateMethodReferences();
                    break;
                case "20":
                    CustomAnnotationsDemo.demonstrateCustomAnnotations();
                    break;
                case "21":
                    ReflectionAPIDemo.demonstrateReflection();
                    break;
                case "22":
                    DateTimeAPIDemo.demonstrateDateTime();
                    break;
                case "23":
                    SingletonPatternDemo.demonstrateSingleton();
                    break;
                case "24":
                    FactoryPatternDemo.demonstrateFactory();
                    break;
                case "25":
                    ObserverPatternDemo.demonstrateObserver();
                    break;
                case "26":
                    AdvancedEnumsDemo.demonstrateAdvancedEnums();
                    break;
                case "27":
                    JUnitTestingDemo.demonstrateJUnitTesting();
                    break;
                case "28":
                    MockitoFrameworkDemo.demonstrateMockito();
                    break;
                case "29":
                    JDBCOperations.demonstrateJDBC();
                    break;
                case "30":
                    // Simple application: Snake game
                    examples.SnakeGame.launch();
                    break;
                case "0":
                    Utils.printLine("Thank you for learning Java!");
                    scanner.close();
                    System.exit(0);
                default:
                    if (choice.matches("\\d+")) {
                        System.out.println("This example is coming soon!");
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 0 and 30.");
                    }
            }
            System.out.println("\nPress Enter to continue...");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }
    }
}
