package examples;

import utils.Utils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationExample {
  // Serializable class with version control
  static class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private transient String temporaryData; // transient field won't be serialized

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
      this.temporaryData = "This won't be saved";
    }

    @Override
    public String toString() {
      return String.format("Person{name='%s', age=%d, tempData='%s'}",
          name, age, temporaryData);
    }
  }

  // Serializable class with composition
  static class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    private String teamName;
    private List<Person> members;

    public Team(String teamName) {
      this.teamName = teamName;
      this.members = new ArrayList<>();
    }

    public void addMember(Person person) {
      members.add(person);
    }

    @Override
    public String toString() {
      return String.format("Team{name='%s', members=%s}", teamName, members);
    }
  }

  // Custom serializable class with writeObject and readObject
  static class SecurityCredentials implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public SecurityCredentials(String username, String password) {
      this.username = username;
      this.password = password;
    }

    // Custom serialization
    private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeObject(username);
      // Encrypt password before serializing
      out.writeObject(encryptPassword(password));
    }

    // Custom deserialization
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      username = (String) in.readObject();
      // Decrypt password after deserializing
      password = decryptPassword((String) in.readObject());
    }

    private String encryptPassword(String pwd) {
      // Simple "encryption" for demonstration
      return new StringBuilder(pwd).reverse().toString();
    }

    private String decryptPassword(String encrypted) {
      // Simple "decryption" for demonstration
      return new StringBuilder(encrypted).reverse().toString();
    }

    @Override
    public String toString() {
      return String.format("Credentials{username='%s', password='%s'}",
          username, "********");
    }
  }

  public static void demonstrateSerialization() {
    Utils.printLine("Serialization Examples");

    try {
      // Create temporary directory for our serialization files
      File tempDir = new File(System.getProperty("java.io.tmpdir"), "serialization-demo");
      tempDir.mkdir();

      // Basic Serialization
      System.out.println("\n=== Basic Serialization ===");
      Person person = new Person("John Doe", 30);
      System.out.println("Original Person: " + person);

      // Serialize
      File personFile = new File(tempDir, "person.ser");
      try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(personFile))) {
        out.writeObject(person);
      }

      // Deserialize
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(personFile))) {
        Person deserializedPerson = (Person) in.readObject();
        System.out.println("Deserialized Person: " + deserializedPerson);
      }

      // Serialization with Object Graph
      System.out.println("\n=== Object Graph Serialization ===");
      Team team = new Team("Dream Team");
      team.addMember(new Person("Alice", 25));
      team.addMember(new Person("Bob", 30));
      System.out.println("Original Team: " + team);

      // Serialize team
      File teamFile = new File(tempDir, "team.ser");
      try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(teamFile))) {
        out.writeObject(team);
      }

      // Deserialize team
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(teamFile))) {
        Team deserializedTeam = (Team) in.readObject();
        System.out.println("Deserialized Team: " + deserializedTeam);
      }

      // Custom Serialization
      System.out.println("\n=== Custom Serialization ===");
      SecurityCredentials creds = new SecurityCredentials("admin", "secret123");
      System.out.println("Original Credentials: " + creds);

      // Serialize credentials
      File credsFile = new File(tempDir, "creds.ser");
      try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(credsFile))) {
        out.writeObject(creds);
      }

      // Deserialize credentials
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(credsFile))) {
        SecurityCredentials deserializedCreds = (SecurityCredentials) in.readObject();
        System.out.println("Deserialized Credentials: " + deserializedCreds);
      }

      // Cleanup
      personFile.delete();
      teamFile.delete();
      credsFile.delete();
      tempDir.delete();

    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Error during serialization: " + e.getMessage());
    }

    Utils.printLine("End of Serialization Examples");
  }
}