package examples;

import java.sql.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JDBCOperations {
  private static final String DB_URL = "jdbc:sqlite:todo.db";
  private static final Scanner scanner = new Scanner(System.in);
  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  static {
    try {
      initializeDatabase();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void initializeDatabase() throws SQLException {
    try (Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement()) {

      String sql = "CREATE TABLE IF NOT EXISTS todos (" +
          "id INTEGER PRIMARY KEY AUTOINCREMENT," +
          "title TEXT NOT NULL," +
          "description TEXT," +
          "due_date TEXT," +
          "completed INTEGER DEFAULT 0," +
          "created_at TEXT DEFAULT CURRENT_TIMESTAMP)";
      stmt.execute(sql);
    }
  }

  public static class Todo {
    private int id;
    private String title;
    private String description;
    private String dueDate;
    private boolean completed;
    private String createdAt;

    public Todo(String title, String description, String dueDate) {
      this.title = title;
      this.description = description;
      this.dueDate = dueDate;
      this.completed = false;
      this.createdAt = LocalDateTime.now().format(formatter);
    }

    @Override
    public String toString() {
      return String.format(
          "ID: %d%nTitle: %s%nDescription: %s%nDue Date: %s%nStatus: %s%nCreated At: %s%n",
          id, title, description, dueDate,
          completed ? "Completed" : "Pending",
          createdAt);
    }
  }

  private static void listTodos() {
    String sql = "SELECT * FROM todos";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      boolean found = false;
      while (rs.next()) {
        found = true;
        Todo todo = new Todo(
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("due_date"));
        todo.id = rs.getInt("id");
        todo.completed = rs.getInt("completed") == 1;
        todo.createdAt = rs.getString("created_at");
        System.out.println("----------------------------------------");
        System.out.println(todo);
      }

      if (!found) {
        System.out.println("No todos found.");
      }
    } catch (SQLException e) {
      System.out.println("Error listing todos: " + e.getMessage());
    }
  }

  private static void addTodo() {
    System.out.println("Enter todo title:");
    String title = scanner.nextLine();

    System.out.println("Enter todo description:");
    String description = scanner.nextLine();

    System.out.println("Enter due date (YYYY-MM-DD HH:mm:ss):");
    String dueDate = scanner.nextLine();

    String sql = "INSERT INTO todos (title, description, due_date) VALUES (?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, title);
      pstmt.setString(2, description);
      pstmt.setString(3, dueDate);

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("Todo added successfully!");
      }
    } catch (SQLException e) {
      System.out.println("Error adding todo: " + e.getMessage());
    }
  }

  private static void deleteTodo() {
    System.out.println("Enter todo ID to delete:");
    int id = Integer.parseInt(scanner.nextLine());

    String sql = "DELETE FROM todos WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, id);

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("Todo deleted successfully!");
      } else {
        System.out.println("No todo found with ID: " + id);
      }
    } catch (SQLException e) {
      System.out.println("Error deleting todo: " + e.getMessage());
    }
  }

  private static void updateTodo() {
    System.out.println("Enter todo ID to update:");
    int id = Integer.parseInt(scanner.nextLine());

    // First check if todo exists
    String checkSql = "SELECT * FROM todos WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

      checkStmt.setInt(1, id);
      ResultSet rs = checkStmt.executeQuery();

      if (!rs.next()) {
        System.out.println("No todo found with ID: " + id);
        return;
      }

      System.out.println("Enter new title (press Enter to skip):");
      String title = scanner.nextLine();

      System.out.println("Enter new description (press Enter to skip):");
      String description = scanner.nextLine();

      System.out.println("Enter new due date (YYYY-MM-DD HH:mm:ss) (press Enter to skip):");
      String dueDate = scanner.nextLine();

      System.out.println("Mark as completed? (yes/no) (press Enter to skip):");
      String completed = scanner.nextLine();

      StringBuilder sql = new StringBuilder("UPDATE todos SET ");
      List<String> updates = new ArrayList<>();
      List<Object> values = new ArrayList<>();

      if (!title.isEmpty()) {
        updates.add("title = ?");
        values.add(title);
      }
      if (!description.isEmpty()) {
        updates.add("description = ?");
        values.add(description);
      }
      if (!dueDate.isEmpty()) {
        updates.add("due_date = ?");
        values.add(dueDate);
      }
      if (!completed.isEmpty()) {
        updates.add("completed = ?");
        values.add(completed.toLowerCase().startsWith("y") ? 1 : 0);
      }

      if (updates.isEmpty()) {
        System.out.println("No updates provided.");
        return;
      }

      sql.append(String.join(", ", updates));
      sql.append(" WHERE id = ?");

      try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
        for (int i = 0; i < values.size(); i++) {
          pstmt.setObject(i + 1, values.get(i));
        }
        pstmt.setInt(values.size() + 1, id);

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
          System.out.println("Todo updated successfully!");
        }
      }
    } catch (SQLException e) {
      System.out.println("Error updating todo: " + e.getMessage());
    }
  }

  private static void exportToJson() {
    List<Todo> todos = new ArrayList<>();
    String sql = "SELECT * FROM todos";

    try (Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Todo todo = new Todo(
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("due_date"));
        todo.id = rs.getInt("id");
        todo.completed = rs.getInt("completed") == 1;
        todo.createdAt = rs.getString("created_at");
        todos.add(todo);
      }

      String json = gson.toJson(todos);
      System.out.println("\nExported JSON:");
      System.out.println(json);

    } catch (SQLException e) {
      System.out.println("Error exporting to JSON: " + e.getMessage());
    }
  }

  public static void demonstrateJDBC() {
    Utils.printLine("SQLite Todo Application");

    while (true) {
      System.out.println("\n1. List Todos");
      System.out.println("2. Add Todo");
      System.out.println("3. Delete Todo");
      System.out.println("4. Update Todo");
      System.out.println("5. Export to JSON");
      System.out.println("6. Return to Main Menu");
      System.out.print("\nSelect an option (1-6): ");

      String choice = scanner.nextLine();
      System.out.println();

      try {
        switch (choice) {
          case "1":
            listTodos();
            break;
          case "2":
            addTodo();
            break;
          case "3":
            deleteTodo();
            break;
          case "4":
            updateTodo();
            break;
          case "5":
            exportToJson();
            break;
          case "6":
            return;
          default:
            System.out.println("Invalid choice. Please try again.");
        }
      } catch (Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
      }
    }
  }
}