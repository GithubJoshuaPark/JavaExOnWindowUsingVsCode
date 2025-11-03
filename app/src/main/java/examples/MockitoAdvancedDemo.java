package examples;

import utils.Utils;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MockitoAdvancedDemo {
  // Interface for caching service
  public interface CacheService {
    void put(String key, String value);

    String get(String key);

    void remove(String key);

    void clear();
  }

  // Interface for async operations
  public interface AsyncService {
    CompletableFuture<String> fetchData(String id);

    CompletableFuture<Boolean> processData(String data);
  }

  // Interface for retrying operations
  public interface RetryService {
    <T> T executeWithRetry(RetryableOperation<T> operation);
  }

  // Interface for retryable operations
  @FunctionalInterface
  public interface RetryableOperation<T> {
    T execute() throws Exception;
  }

  // Service that demonstrates complex interactions
  public static class ComplexService {
    private final CacheService cacheService;
    private final AsyncService asyncService;
    private final RetryService retryService;
    private final Map<String, Integer> retryCount;

    public ComplexService(
        CacheService cacheService,
        AsyncService asyncService,
        RetryService retryService) {
      this.cacheService = cacheService;
      this.asyncService = asyncService;
      this.retryService = retryService;
      this.retryCount = new HashMap<>();
    }

    public String fetchDataWithCache(String id) throws Exception {
      // Try to get from cache first
      String cachedData = cacheService.get(id);
      if (cachedData != null) {
        return cachedData;
      }

      // Fetch data asynchronously with retry
      return retryService.executeWithRetry(() -> {
        String data = asyncService.fetchData(id)
            .thenApply(result -> {
              cacheService.put(id, result);
              return result;
            })
            .get(); // Wait for completion

        incrementRetryCount(id);
        return data;
      });
    }

    public boolean processDataWithRetry(String data) throws Exception {
      return retryService.executeWithRetry(() -> asyncService.processData(data).get());
    }

    private void incrementRetryCount(String id) {
      retryCount.merge(id, 1, Integer::sum);
    }

    public int getRetryCount(String id) {
      return retryCount.getOrDefault(id, 0);
    }
  }

  public static void demonstrateMockitoAdvanced() {
    Utils.printLine("Advanced Mockito Examples");
    System.out.println("This class demonstrates advanced Mockito features.");
    System.out.println("Please check the test files in the test directory:");
    System.out.println("1. ComplexServiceTest - Testing async operations");
    System.out.println("\nAdvanced features demonstrated:");
    System.out.println("- Testing asynchronous operations");
    System.out.println("- Complex verification scenarios");
    System.out.println("- Answer callbacks with delays");
    System.out.println("- Retry mechanism testing");
    System.out.println("- Exception handling with matchers");
  }
}