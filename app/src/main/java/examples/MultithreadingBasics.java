package examples;

import utils.Utils;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class MultithreadingBasics {

  // Example class extending Thread
  static class CounterThread extends Thread {
    private final int id;
    private final int maxCount;

    public CounterThread(int id, int maxCount) {
      this.id = id;
      this.maxCount = maxCount;
    }

    @Override
    public void run() {
      for (int i = 1; i <= maxCount; i++) {
        System.out.printf("Thread %d: Count %d%n", id, i);
        try {
          Thread.sleep(100); // Simulate some work
        } catch (InterruptedException e) {
          System.out.println("Thread " + id + " interrupted");
          return;
        }
      }
    }
  }

  // Example class implementing Runnable
  static class TaskRunner implements Runnable {
    private final String taskName;

    public TaskRunner(String taskName) {
      this.taskName = taskName;
    }

    @Override
    public void run() {
      System.out.println(taskName + " starting...");
      try {
        // Simulate variable length task
        Thread.sleep((long) (Math.random() * 1000));
      } catch (InterruptedException e) {
        System.out.println(taskName + " interrupted");
        return;
      }
      System.out.println(taskName + " completed");
    }
  }

  // Example class with shared resource
  static class SharedCounter {
    private int count = 0;

    public void increment() {
      count++;
    }

    public int getCount() {
      return count;
    }
  }

  public static void demonstrateMultithreading() {
    Utils.printLine("Multithreading Basics Examples");

    // 1. Creating threads by extending Thread class
    System.out.println("\n=== Extending Thread Class ===");
    CounterThread thread1 = new CounterThread(1, 5);
    CounterThread thread2 = new CounterThread(2, 5);

    thread1.start();
    thread2.start();

    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      System.out.println("Main thread interrupted");
    }

    // 2. Creating threads with Runnable interface
    System.out.println("\n=== Using Runnable Interface ===");
    Thread runnableThread1 = new Thread(new TaskRunner("Task 1"));
    Thread runnableThread2 = new Thread(new TaskRunner("Task 2"));

    runnableThread1.start();
    runnableThread2.start();

    try {
      runnableThread1.join();
      runnableThread2.join();
    } catch (InterruptedException e) {
      System.out.println("Main thread interrupted");
    }

    // 3. Using Lambda expressions
    System.out.println("\n=== Lambda Expression Threads ===");
    Thread lambdaThread = new Thread(() -> {
      System.out.println("Lambda thread starting...");
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        System.out.println("Lambda thread interrupted");
        return;
      }
      System.out.println("Lambda thread completed");
    });

    lambdaThread.start();
    try {
      lambdaThread.join();
    } catch (InterruptedException e) {
      System.out.println("Main thread interrupted");
    }

    // 4. Thread States and Control
    System.out.println("\n=== Thread States and Control ===");
    Thread controlThread = new Thread(() -> {
      for (int i = 1; i <= 5; i++) {
        System.out.println("Control thread: " + i);
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          System.out.println("Control thread interrupted");
          return;
        }
      }
    });

    System.out.println("Thread state before start: " + controlThread.getState());
    controlThread.start();
    System.out.println("Thread state after start: " + controlThread.getState());

    try {
      Thread.sleep(100);
      System.out.println("Thread state while running: " + controlThread.getState());
      controlThread.join();
    } catch (InterruptedException e) {
      System.out.println("Main thread interrupted");
    }

    // 5. Race Condition Demonstration
    System.out.println("\n=== Race Condition Demonstration ===");
    SharedCounter sharedCounter = new SharedCounter();
    List<Thread> threads = new ArrayList<>();

    // Create 10 threads that increment the counter
    for (int i = 0; i < 10; i++) {
      Thread t = new Thread(() -> {
        for (int j = 0; j < 1000; j++) {
          sharedCounter.increment();
        }
      });
      threads.add(t);
      t.start();
    }

    // Wait for all threads to complete
    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
        System.out.println("Main thread interrupted");
      }
    }

    System.out.println("Expected count: 10000");
    System.out.println("Actual count: " + sharedCounter.getCount());
    System.out.println("Note: The actual count might be less due to race condition!");

    Utils.printLine("End of Multithreading Basics Examples");
  }
}