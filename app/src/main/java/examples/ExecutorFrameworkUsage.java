package examples;

import utils.Utils;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExecutorFrameworkUsage {
  // Task that returns a result
  static class CalculationTask implements Callable<Integer> {
    private final int number;

    public CalculationTask(int number) {
      this.number = number;
    }

    @Override
    public Integer call() {
      System.out.printf("Calculating factorial for %d%n", number);
      return calculateFactorial(number);
    }

    private int calculateFactorial(int n) {
      int result = 1;
      for (int i = 2; i <= n; i++) {
        result *= i;
        try {
          Thread.sleep(100); // Simulate complex calculation
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      return result;
    }
  }

  // Task that doesn't return a result
  static class DataProcessingTask implements Runnable {
    private final String taskId;
    private final Random random = new Random();

    public DataProcessingTask(String taskId) {
      this.taskId = taskId;
    }

    @Override
    public void run() {
      System.out.printf("Processing data for task %s%n", taskId);
      try {
        Thread.sleep(random.nextInt(1000)); // Simulate variable processing time
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      System.out.printf("Completed processing task %s%n", taskId);
    }
  }

  // Periodic task for scheduling
  static class MonitoringTask implements Runnable {
    private final String resourceName;
    private int executionCount = 0;

    public MonitoringTask(String resourceName) {
      this.resourceName = resourceName;
    }

    @Override
    public void run() {
      executionCount++;
      System.out.printf("Monitoring %s - Execution #%d - Time: %s%n",
          resourceName, executionCount, java.time.LocalTime.now());
    }
  }

  public static void demonstrateExecutorFramework() {
    Utils.printLine("Executor Framework Examples");

    // 1. Single Thread Executor
    System.out.println("\n=== SingleThreadExecutor Example ===");
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    try {
      for (int i = 1; i <= 3; i++) {
        final int taskId = i;
        singleThreadExecutor.submit(new DataProcessingTask("Single-" + taskId));
      }
    } finally {
      shutdownAndAwaitTermination(singleThreadExecutor);
    }

    // 2. Fixed Thread Pool
    System.out.println("\n=== FixedThreadPool Example ===");
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    try {
      List<Future<Integer>> futures = new ArrayList<>();
      for (int i = 1; i <= 5; i++) {
        futures.add(fixedThreadPool.submit(new CalculationTask(i)));
      }

      for (int i = 0; i < futures.size(); i++) {
        try {
          System.out.printf("Factorial of %d is: %d%n", i + 1, futures.get(i).get());
        } catch (InterruptedException | ExecutionException e) {
          System.out.println("Error getting result: " + e.getMessage());
        }
      }
    } finally {
      shutdownAndAwaitTermination(fixedThreadPool);
    }

    // 3. Cached Thread Pool
    System.out.println("\n=== CachedThreadPool Example ===");
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    try {
      for (int i = 1; i <= 5; i++) {
        cachedThreadPool.submit(new DataProcessingTask("Cached-" + i));
      }
    } finally {
      shutdownAndAwaitTermination(cachedThreadPool);
    }

    // 4. Scheduled Executor
    System.out.println("\n=== ScheduledExecutorService Example ===");
    ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
    try {
      // Schedule a task to run after 2 seconds
      scheduledExecutor.schedule(
          new DataProcessingTask("Delayed"), 2, TimeUnit.SECONDS);

      // Schedule a task to run periodically
      MonitoringTask monitoringTask = new MonitoringTask("System Resources");
      scheduledExecutor.scheduleAtFixedRate(
          monitoringTask, 0, 1, TimeUnit.SECONDS);

      // Let the periodic task run for a few iterations
      Thread.sleep(5000);

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      shutdownAndAwaitTermination(scheduledExecutor);
    }

    // 5. CompletableFuture Example
    System.out.println("\n=== CompletableFuture Example ===");
    CompletableFuture.supplyAsync(() -> {
      try {
        System.out.println("Performing async calculation...");
        Thread.sleep(1000);
        return 42;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return -1;
      }
    }).thenAccept(result -> System.out.println("Async calculation result: " + result)).join();

    Utils.printLine("End of Executor Framework Examples");
  }

  private static void shutdownAndAwaitTermination(ExecutorService pool) {
    pool.shutdown(); // Disable new tasks from being submitted
    try {
      // Wait for existing tasks to terminate
      if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
        pool.shutdownNow(); // Cancel currently executing tasks
        // Wait a while for tasks to respond to being cancelled
        if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
          System.err.println("Pool did not terminate");
        }
      }
    } catch (InterruptedException ie) {
      // (Re-)Cancel if current thread also interrupted
      pool.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }
}