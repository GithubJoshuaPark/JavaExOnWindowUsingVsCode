package examples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import examples.MockitoAdvancedDemo.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ExtendWith(MockitoExtension.class)
class MockitoAdvancedDemoTest {

  @Mock
  private CacheService cacheService;

  @Mock
  private AsyncService asyncService;

  @Mock
  private RetryService retryService;

  @InjectMocks
  private ComplexService complexService;

  @Test
  void whenDataInCache_thenReturnFromCache() throws Exception {
    // Arrange
    when(cacheService.get("testId")).thenReturn("cachedData");

    // Act
    String result = complexService.fetchDataWithCache("testId");

    // Assert
    assertEquals("cachedData", result);
    verify(asyncService, never()).fetchData(anyString());
  }

  @Test
  void whenDataNotInCache_thenFetchAndCache() throws Exception {
    // Arrange
    when(cacheService.get("testId")).thenReturn(null);
    when(asyncService.fetchData("testId"))
        .thenReturn(CompletableFuture.completedFuture("newData"));
    when(retryService.executeWithRetry(any()))
        .thenAnswer(inv -> ((RetryableOperation<?>) inv.getArgument(0)).execute());

    // Act
    String result = complexService.fetchDataWithCache("testId");

    // Assert
    assertEquals("newData", result);
    verify(cacheService).put("testId", "newData");
    assertEquals(1, complexService.getRetryCount("testId"));
  }

  @Test
  void whenAsyncOperationDelayed_thenEventuallySucceed() throws Exception {
    // Arrange
    when(cacheService.get("testId")).thenReturn(null);
    when(asyncService.fetchData("testId"))
        .thenAnswer(inv -> CompletableFuture.supplyAsync(() -> {
          try {
            TimeUnit.MILLISECONDS.sleep(100);
            return "delayedData";
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }));
    when(retryService.executeWithRetry(any()))
        .thenAnswer(inv -> ((RetryableOperation<?>) inv.getArgument(0)).execute());

    // Act
    String result = complexService.fetchDataWithCache("testId");

    // Assert
    assertEquals("delayedData", result);
    verify(cacheService, timeout(1000)).put("testId", "delayedData");
  }

  @Test
  void whenProcessingFails_thenRetryMechanism() throws Exception {
    // Arrange
    AtomicInteger callCount = new AtomicInteger(0);
    when(asyncService.processData("testData"))
        .thenAnswer(inv -> {
          if (callCount.incrementAndGet() == 1) {
            return CompletableFuture.completedFuture(false);
          }
          return CompletableFuture.completedFuture(true);
        });
    when(retryService.executeWithRetry(any()))
        .thenAnswer(inv -> {
          RetryableOperation<?> operation = inv.getArgument(0);
          Object result = operation.execute();
          if (result instanceof Boolean && !(Boolean) result) {
            return operation.execute(); // Retry once
          }
          return result;
        });

    // Act
    boolean result = complexService.processDataWithRetry("testData");

    // Assert
    assertTrue(result);
    verify(asyncService, times(2)).processData("testData");
  }

  @Test
  void whenAsyncOperationFails_thenThrowException() {
    // Arrange
    when(cacheService.get("testId")).thenReturn(null);
    RuntimeException asyncError = new RuntimeException("Async operation failed");

    doAnswer(inv -> {
      throw asyncError;
    }).when(retryService).executeWithRetry(any());

    // Act & Assert
    Exception exception = assertThrows(RuntimeException.class, () -> complexService.fetchDataWithCache("testId"));
    assertEquals(asyncError, exception);
  }

  @Test
  void whenMultipleRetries_thenTrackCount() throws Exception {
    // Arrange
    when(cacheService.get("testId")).thenReturn(null);
    when(asyncService.fetchData("testId"))
        .thenReturn(CompletableFuture.completedFuture("attempt1"))
        .thenReturn(CompletableFuture.completedFuture("attempt2"));
    when(retryService.executeWithRetry(any()))
        .thenAnswer(inv -> ((RetryableOperation<?>) inv.getArgument(0)).execute());

    // Act
    complexService.fetchDataWithCache("testId");
    complexService.fetchDataWithCache("testId");

    // Assert
    assertEquals(2, complexService.getRetryCount("testId"));
    verify(cacheService).put(eq("testId"), eq("attempt1"));
    verify(cacheService).put(eq("testId"), eq("attempt2"));
  }
}