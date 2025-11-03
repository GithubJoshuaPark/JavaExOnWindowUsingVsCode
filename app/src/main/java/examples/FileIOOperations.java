package examples;

import utils.Utils;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileIOOperations {
  public static void demonstrateFileIO() {
    Utils.printLine("File I/O Operations Examples");

    // Create a temporary directory for our examples
    Path tempDir = null;
    try {
      tempDir = Files.createTempDirectory("java-io-demo");
      System.out.println("Created temporary directory: " + tempDir);

      // Basic File Operations
      demonstrateBasicFileOperations(tempDir);

      // Stream-based I/O
      demonstrateStreamIO(tempDir);

      // NIO.2 Operations
      demonstrateNIO2Operations(tempDir);

      // File Attributes
      demonstrateFileAttributes(tempDir);

      // Directory Operations
      demonstrateDirectoryOperations(tempDir);

    } catch (IOException e) {
      System.out.println("Error in file operations: " + e.getMessage());
    } finally {
      // Cleanup
      if (tempDir != null) {
        try {
          deleteDirectory(tempDir.toFile());
          System.out.println("\nCleaned up temporary directory");
        } catch (IOException e) {
          System.out.println("Error cleaning up: " + e.getMessage());
        }
      }
    }

    Utils.printLine("End of File I/O Operations Examples");
  }

  private static void demonstrateBasicFileOperations(Path tempDir) throws IOException {
    System.out.println("\n=== Basic File Operations ===");

    // Create a new file
    File file = new File(tempDir.toString(), "sample.txt");
    if (file.createNewFile()) {
      System.out.println("Created new file: " + file.getName());
    }

    // Write to file using FileWriter
    try (FileWriter writer = new FileWriter(file)) {
      writer.write("Hello, Java I/O!\n");
      writer.write("This is a test file.\n");
    }

    // Read from file using FileReader
    System.out.println("\nReading file using FileReader:");
    try (FileReader reader = new FileReader(file)) {
      int character;
      while ((character = reader.read()) != -1) {
        System.out.print((char) character);
      }
    }
  }

  private static void demonstrateStreamIO(Path tempDir) throws IOException {
    System.out.println("\n=== Stream-based I/O ===");

    File file = tempDir.resolve("stream.txt").toFile();

    // Write using BufferedWriter
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("Line 1: Using BufferedWriter\n");
      writer.write("Line 2: More efficient than FileWriter\n");
      writer.write("Line 3: Supports buffering\n");
    }

    // Read using BufferedReader
    System.out.println("\nReading using BufferedReader:");
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    }
  }

  private static void demonstrateNIO2Operations(Path tempDir) throws IOException {
    System.out.println("\n=== NIO.2 Operations ===");

    Path file = tempDir.resolve("nio2.txt");

    // Write using Files.write
    List<String> lines = List.of(
        "NIO.2 makes file operations easier",
        "It provides more functional approaches",
        "And better exception handling");
    Files.write(file, lines, StandardCharsets.UTF_8);

    // Read using Files.readAllLines
    System.out.println("\nReading all lines at once:");
    List<String> readLines = Files.readAllLines(file);
    readLines.forEach(System.out::println);

    // Using Path operations
    System.out.println("\nPath operations:");
    System.out.println("File name: " + file.getFileName());
    System.out.println("Absolute path: " + file.toAbsolutePath());
    System.out.println("Parent directory: " + file.getParent());
  }

  private static void demonstrateFileAttributes(Path tempDir) throws IOException {
    System.out.println("\n=== File Attributes ===");

    Path file = tempDir.resolve("attributes.txt");
    Files.writeString(file, "Testing file attributes");

    // Basic attributes
    System.out.println("Size: " + Files.size(file) + " bytes");
    System.out.println("Last modified: " + Files.getLastModifiedTime(file));
    System.out.println("Is readable: " + Files.isReadable(file));
    System.out.println("Is writable: " + Files.isWritable(file));
    System.out.println("Is executable: " + Files.isExecutable(file));

    // Using BasicFileAttributes
    BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
    System.out.println("Creation time: " + attrs.creationTime());
    System.out.println("Is directory: " + attrs.isDirectory());
    System.out.println("Is regular file: " + attrs.isRegularFile());
  }

  private static void demonstrateDirectoryOperations(Path tempDir) throws IOException {
    System.out.println("\n=== Directory Operations ===");

    // Create subdirectories
    Path subDir = Files.createDirectory(tempDir.resolve("subdir"));
    Files.createFile(subDir.resolve("file1.txt"));
    Files.createFile(subDir.resolve("file2.txt"));

    // List directory contents
    System.out.println("\nListing directory contents:");
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir)) {
      for (Path entry : stream) {
        System.out.println(entry.getFileName());
      }
    }

    // Using walk to show directory tree
    System.out.println("\nDirectory tree:");
    try (var paths = Files.walk(tempDir)) {
      paths.forEach(path -> System.out.println(tempDir.relativize(path)));
    }
  }

  private static void deleteDirectory(File directory) throws IOException {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          deleteDirectory(file);
        } else {
          if (!file.delete()) {
            throw new IOException("Failed to delete file: " + file);
          }
        }
      }
    }
    if (!directory.delete()) {
      throw new IOException("Failed to delete directory: " + directory);
    }
  }
}