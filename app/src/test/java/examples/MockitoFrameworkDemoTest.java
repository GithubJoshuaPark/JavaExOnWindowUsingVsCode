package examples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import examples.MockitoFrameworkDemo.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MockitoFrameworkDemoTest {

  @Mock
  private PaymentService paymentService;

  @Mock
  private NotificationService notificationService;

  @Mock
  private OrderRepository orderRepository;

  @InjectMocks
  private OrderService orderService;

  @Captor
  private ArgumentCaptor<Order> orderCaptor;

  private Order testOrder;

  @BeforeEach
  void setUp() {
    testOrder = new Order("order123", "user123", "user@test.com", 100.0);
  }

  @Test
  void whenPlaceOrder_thenOrderIsProcessedAndSaved() {
    // Arrange
    when(paymentService.processPayment("order123", 100.0)).thenReturn(true);

    // Act
    boolean result = orderService.placeOrder(testOrder);

    // Assert
    assertTrue(result);
    verify(orderRepository).save(orderCaptor.capture());
    verify(notificationService).sendEmail(
        eq("user@test.com"),
        eq("Order Confirmed"),
        contains("order123"));

    Order savedOrder = orderCaptor.getValue();
    assertEquals(OrderStatus.CONFIRMED, savedOrder.getStatus());
    assertNotNull(savedOrder.getOrderDate());
  }

  @Test
  void givenFailedPayment_whenPlaceOrder_thenOrderIsMarkedAsFailed() {
    // Given
    given(paymentService.processPayment(anyString(), anyDouble()))
        .willReturn(false);

    // When
    boolean result = orderService.placeOrder(testOrder);

    // Then
    assertFalse(result);
    verify(orderRepository).save(orderCaptor.capture());

    Order savedOrder = orderCaptor.getValue();
    assertEquals(OrderStatus.FAILED, savedOrder.getStatus());
    verify(notificationService, never()).sendEmail(anyString(), anyString(), anyString());
  }

  @Test
  void whenCancelOrder_thenRefundIsProcessedAndOrderCancelled() {
    // Arrange
    testOrder.setStatus(OrderStatus.CONFIRMED);
    when(orderRepository.findById("order123")).thenReturn(testOrder);
    when(paymentService.refundPayment("order123")).thenReturn(true);

    // Act
    boolean result = orderService.cancelOrder("order123");

    // Assert
    assertTrue(result);
    verify(orderRepository).update(orderCaptor.capture());

    Order updatedOrder = orderCaptor.getValue();
    assertEquals(OrderStatus.CANCELLED, updatedOrder.getStatus());
  }

  @Test
  void whenGetUserOrders_thenReturnsOrderList() {
    // Arrange
    List<Order> expectedOrders = Arrays.asList(
        new Order("order1", "user123", "user@test.com", 100.0),
        new Order("order2", "user123", "user@test.com", 200.0));
    when(orderRepository.findByUserId("user123")).thenReturn(expectedOrders);

    // Act
    List<Order> actualOrders = orderService.getUserOrders("user123");

    // Assert
    assertEquals(expectedOrders, actualOrders);
    verify(orderRepository).findByUserId("user123");
  }

  @Test
  void whenGetUserBalance_thenReturnsBalance() {
    // Arrange
    when(paymentService.getBalance("user123")).thenReturn(500.0);

    // Act
    double balance = orderService.getUserBalance("user123");

    // Assert
    assertEquals(500.0, balance);
    verify(paymentService).getBalance("user123");
  }

  @Test
  void givenInvalidOrder_whenPlaceOrder_thenThrowsException() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class,
        () -> orderService.placeOrder(new Order("order123", "user123", "user@test.com", -100.0)));
  }

  @Test
  void givenNonExistentOrder_whenCancelOrder_thenThrowsException() {
    // Arrange
    when(orderRepository.findById("nonexistent")).thenReturn(null);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> orderService.cancelOrder("nonexistent"));
  }

  @Test
  void spyExample() {
    // Create a spy of a real Order object
    Order realOrder = new Order("spy123", "user123", "user@test.com", 150.0);
    Order orderSpy = Mockito.spy(realOrder);

    // Stub a method while keeping others real
    when(orderSpy.getAmount()).thenReturn(200.0);

    // The stubbed method returns the mock value
    assertEquals(200.0, orderSpy.getAmount());

    // Other methods work normally
    assertEquals("spy123", orderSpy.getOrderId());
    assertEquals("user@test.com", orderSpy.getUserEmail());
  }

  @Test
  void whenProcessPayment_thenConsecutiveCallsReturnDifferentResults() {
    // Demonstrate consecutive call results
    when(paymentService.processPayment(anyString(), anyDouble()))
        .thenReturn(true) // First call returns true
        .thenReturn(false) // Second call returns false
        .thenReturn(true); // Third call returns true

    // Execute and verify consecutive calls
    assertTrue(paymentService.processPayment("order1", 100.0));
    assertFalse(paymentService.processPayment("order2", 200.0));
    assertTrue(paymentService.processPayment("order3", 300.0));
  }

  @Test
  void whenProcessPayment_thenCustomAnswerBasedOnAmount() {
    // Demonstrate Answer interface for dynamic responses
    when(paymentService.processPayment(anyString(), anyDouble()))
        .thenAnswer(invocation -> {
          double amount = invocation.getArgument(1);
          return amount <= 1000.0; // Only approve payments up to 1000
        });

    assertTrue(paymentService.processPayment("order1", 500.0));
    assertFalse(paymentService.processPayment("order2", 1500.0));
  }

  @Test
  void whenSendNotification_thenVerifyTimeout() {
    // Demonstrate timeout verification
    doNothing().when(notificationService).sendEmail(anyString(), anyString(), anyString());

    notificationService.sendEmail("test@test.com", "Test", "Message");

    verify(notificationService, timeout(1000))
        .sendEmail(anyString(), anyString(), anyString());
  }

  @Test
  void whenProcessOrder_thenVerifyInOrder() {
    // Arrange
    when(paymentService.processPayment(anyString(), anyDouble())).thenReturn(true);

    // Act
    orderService.placeOrder(testOrder);

    // Assert - verify the exact order of method calls
    InOrder inOrder = Mockito.inOrder(paymentService, orderRepository, notificationService);
    inOrder.verify(paymentService).processPayment(anyString(), anyDouble());
    inOrder.verify(orderRepository).save(any(Order.class));
    inOrder.verify(notificationService).sendEmail(anyString(), anyString(), anyString());
    inOrder.verifyNoMoreInteractions();
  }
}