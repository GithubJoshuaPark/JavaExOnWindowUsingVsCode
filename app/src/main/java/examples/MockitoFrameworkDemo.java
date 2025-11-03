package examples;

import utils.Utils;
import java.util.*;
import java.time.LocalDateTime;

// Classes to demonstrate Mockito testing
public class MockitoFrameworkDemo {
  // Interface for external payment service
  public interface PaymentService {
    boolean processPayment(String orderId, double amount);

    boolean refundPayment(String orderId);

    double getBalance(String userId);
  }

  // Interface for notification service
  public interface NotificationService {
    void sendEmail(String to, String subject, String body);

    void sendSMS(String to, String message);
  }

  // Interface for order repository
  public interface OrderRepository {
    void save(Order order);

    Order findById(String orderId);

    List<Order> findByUserId(String userId);

    void update(Order order);
  }

  // Order processing service that uses multiple dependencies
  public static class OrderService {
    private final PaymentService paymentService;
    private final NotificationService notificationService;
    private final OrderRepository orderRepository;

    public OrderService(
        PaymentService paymentService,
        NotificationService notificationService,
        OrderRepository orderRepository) {
      this.paymentService = paymentService;
      this.notificationService = notificationService;
      this.orderRepository = orderRepository;
    }

    public boolean placeOrder(Order order) {
      // Validate order
      if (order == null || order.getAmount() <= 0) {
        throw new IllegalArgumentException("Invalid order");
      }

      // Process payment
      boolean paymentSuccess = paymentService.processPayment(
          order.getOrderId(),
          order.getAmount());

      if (paymentSuccess) {
        // Save order
        order.setStatus(OrderStatus.CONFIRMED);
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);

        // Send confirmation
        notificationService.sendEmail(
            order.getUserEmail(),
            "Order Confirmed",
            "Your order " + order.getOrderId() + " has been confirmed.");

        return true;
      } else {
        order.setStatus(OrderStatus.FAILED);
        orderRepository.save(order);
        return false;
      }
    }

    public boolean cancelOrder(String orderId) {
      Order order = orderRepository.findById(orderId);
      if (order == null) {
        throw new IllegalArgumentException("Order not found");
      }

      if (order.getStatus() != OrderStatus.CONFIRMED) {
        throw new IllegalStateException("Order cannot be cancelled");
      }

      boolean refundSuccess = paymentService.refundPayment(orderId);
      if (refundSuccess) {
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.update(order);

        notificationService.sendEmail(
            order.getUserEmail(),
            "Order Cancelled",
            "Your order " + orderId + " has been cancelled.");

        return true;
      }
      return false;
    }

    public List<Order> getUserOrders(String userId) {
      return orderRepository.findByUserId(userId);
    }

    public double getUserBalance(String userId) {
      return paymentService.getBalance(userId);
    }
  }

  // Order class
  public static class Order {
    private final String orderId;
    private final String userId;
    private final String userEmail;
    private final double amount;
    private OrderStatus status;
    private LocalDateTime orderDate;

    public Order(String orderId, String userId, String userEmail, double amount) {
      this.orderId = orderId;
      this.userId = userId;
      this.userEmail = userEmail;
      this.amount = amount;
      this.status = OrderStatus.PENDING;
    }

    // Getters and setters
    public String getOrderId() {
      return orderId;
    }

    public String getUserId() {
      return userId;
    }

    public String getUserEmail() {
      return userEmail;
    }

    public double getAmount() {
      return amount;
    }

    public OrderStatus getStatus() {
      return status;
    }

    public void setStatus(OrderStatus status) {
      this.status = status;
    }

    public LocalDateTime getOrderDate() {
      return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
      this.orderDate = orderDate;
    }
  }

  // Order status enum
  public enum OrderStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    FAILED
  }

  public static void demonstrateMockito() {
    Utils.printLine("Mockito Framework Examples");
    System.out.println("This class contains code that is tested using Mockito.");
    System.out.println("Please check the test files in the test directory:");
    System.out.println("1. OrderServiceTest - Testing with mock dependencies");
    System.out.println("\nMockito features demonstrated:");
    System.out.println("- Creating mocks");
    System.out.println("- Stubbing method calls");
    System.out.println("- Verifying behavior");
    System.out.println("- Argument matchers");
    System.out.println("- Capturing arguments");
    System.out.println("- Spying on real objects");
    System.out.println("- BDD style testing with BDDMockito");
  }
}