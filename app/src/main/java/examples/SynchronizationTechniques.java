package examples;

import utils.Utils;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.ArrayList;
import java.util.List;

public class SynchronizationTechniques {
  // Example of synchronized method
  static class BankAccount {
    private double balance;
    private final String accountNumber;

    public BankAccount(String accountNumber, double initialBalance) {
      this.accountNumber = accountNumber;
      this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
      System.out.printf("Depositing %.2f to account %s%n", amount, accountNumber);
      double newBalance = balance + amount;
      try {
        Thread.sleep(100); // Simulate processing time
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      balance = newBalance;
      System.out.printf("New balance for account %s: %.2f%n", accountNumber, balance);
    }

    public synchronized void withdraw(double amount) {
      if (balance >= amount) {
        System.out.printf("Withdrawing %.2f from account %s%n", amount, accountNumber);
        double newBalance = balance - amount;
        try {
          Thread.sleep(100); // Simulate processing time
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        balance = newBalance;
        System.out.printf("New balance for account %s: %.2f%n", accountNumber, balance);
      } else {
        System.out.printf("Insufficient funds in account %s%n", accountNumber);
      }
    }

    public double getBalance() {
      return balance;
    }
  }

  // Example using synchronized block
  static class Inventory {
    private final List<String> items = new ArrayList<>();
    private final Object lock = new Object(); // Lock object for synchronization

    public void addItem(String item) {
      synchronized (lock) {
        System.out.println("Adding item: " + item);
        items.add(item);
        System.out.println("Current inventory: " + items);
      }
    }

    public void removeItem(String item) {
      synchronized (lock) {
        System.out.println("Removing item: " + item);
        items.remove(item);
        System.out.println("Current inventory: " + items);
      }
    }

    public List<String> getItems() {
      synchronized (lock) {
        return new ArrayList<>(items);
      }
    }
  }

  // Example using ReentrantLock
  static class Counter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
      lock.lock();
      try {
        count++;
        System.out.println("Counter incremented to: " + count);
      } finally {
        lock.unlock();
      }
    }

    public int getCount() {
      lock.lock();
      try {
        return count;
      } finally {
        lock.unlock();
      }
    }
  }

  // Example using ReadWriteLock
  static class SharedResource {
    private String data = "Initial Data";
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public String read() {
      readLock.lock();
      try {
        System.out.println("Reading data: " + data);
        return data;
      } finally {
        readLock.unlock();
      }
    }

    public void write(String newData) {
      writeLock.lock();
      try {
        System.out.println("Writing data: " + newData);
        this.data = newData;
      } finally {
        writeLock.unlock();
      }
    }
  }

  public static void demonstrateSynchronization() {
    Utils.printLine("Synchronization Techniques Examples");

    // 1. Synchronized Methods Example
    System.out.println("\n=== Synchronized Methods Example ===");
    BankAccount account = new BankAccount("ACC001", 1000.0);

    Thread depositThread = new Thread(() -> {
      for (int i = 0; i < 3; i++) {
        account.deposit(100.0);
      }
    });

    Thread withdrawThread = new Thread(() -> {
      for (int i = 0; i < 3; i++) {
        account.withdraw(100.0);
      }
    });

    depositThread.start();
    withdrawThread.start();

    try {
      depositThread.join();
      withdrawThread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    System.out.println("Final balance: " + account.getBalance());

    // 2. Synchronized Block Example
    System.out.println("\n=== Synchronized Block Example ===");
    Inventory inventory = new Inventory();

    Thread addThread = new Thread(() -> {
      inventory.addItem("Item 1");
      inventory.addItem("Item 2");
    });

    Thread removeThread = new Thread(() -> {
      inventory.addItem("Item 3");
      inventory.removeItem("Item 1");
    });

    addThread.start();
    removeThread.start();

    try {
      addThread.join();
      removeThread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    // 3. ReentrantLock Example
    System.out.println("\n=== ReentrantLock Example ===");
    Counter counter = new Counter();

    Thread[] threads = new Thread[5];
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(() -> {
        for (int j = 0; j < 3; j++) {
          counter.increment();
        }
      });
      threads[i].start();
    }

    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    System.out.println("Final count: " + counter.getCount());

    // 4. ReadWriteLock Example
    System.out.println("\n=== ReadWriteLock Example ===");
    SharedResource resource = new SharedResource();

    // Create multiple reader threads
    Thread[] readers = new Thread[3];
    for (int i = 0; i < readers.length; i++) {
      readers[i] = new Thread(() -> {
        resource.read();
      });
      readers[i].start();
    }

    // Create writer thread
    Thread writer = new Thread(() -> {
      resource.write("Updated Data");
    });
    writer.start();

    try {
      for (Thread reader : readers) {
        reader.join();
      }
      writer.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    Utils.printLine("End of Synchronization Examples");
  }
}