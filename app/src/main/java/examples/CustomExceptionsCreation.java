package examples;

import utils.Utils;

// Custom checked exception
class InsufficientBalanceException extends Exception {
  private double balance;
  private double withdrawAmount;

  public InsufficientBalanceException(String message, double balance, double withdrawAmount) {
    super(message);
    this.balance = balance;
    this.withdrawAmount = withdrawAmount;
  }

  public double getBalance() {
    return balance;
  }

  public double getWithdrawAmount() {
    return withdrawAmount;
  }
}

// Custom unchecked exception
class InvalidAccountException extends RuntimeException {
  private String accountId;

  public InvalidAccountException(String message, String accountId) {
    super(message + " (Account: " + accountId + ")");
    this.accountId = accountId;
  }

  public String getAccountId() {
    return accountId;
  }
}

// Custom exception with cause
class TransactionFailedException extends Exception {
  public TransactionFailedException(String message, Throwable cause) {
    super(message, cause);
  }
}

public class CustomExceptionsCreation {
  static class BankAccount {
    private String accountId;
    private double balance;
    private boolean isLocked;

    public BankAccount(String accountId, double initialBalance) {
      this.accountId = accountId;
      this.balance = initialBalance;
      this.isLocked = false;
    }

    public void withdraw(double amount) throws InsufficientBalanceException, TransactionFailedException {
      if (isLocked) {
        throw new InvalidAccountException("Account is locked", accountId);
      }

      if (amount <= 0) {
        throw new IllegalArgumentException("Withdrawal amount must be positive");
      }

      if (balance < amount) {
        throw new InsufficientBalanceException(
            "Insufficient balance for withdrawal",
            balance,
            amount);
      }

      try {
        // Simulate some processing that might fail
        processTransaction();
        balance -= amount;
      } catch (Exception e) {
        throw new TransactionFailedException("Transaction processing failed", e);
      }
    }

    private void processTransaction() throws Exception {
      // Simulate a random transaction failure
      if (Math.random() < 0.3) { // 30% chance of failure
        throw new Exception("Network timeout");
      }
    }

    public double getBalance() {
      return balance;
    }

    public void lockAccount() {
      isLocked = true;
    }
  }

  public static void demonstrateCustomExceptions() {
    Utils.printLine("Custom Exceptions Examples");

    // Create a test account
    BankAccount account = new BankAccount("ACC001", 1000.0);

    System.out.println("\n=== Basic Exception Handling ===");
    try {
      System.out.println("Current balance: $" + account.getBalance());
      System.out.println("Attempting to withdraw $800...");
      account.withdraw(800);
      System.out.println("Withdrawal successful. New balance: $" + account.getBalance());
    } catch (InsufficientBalanceException e) {
      System.out.printf("Error: %s%nBalance: $%.2f, Attempted: $%.2f%n",
          e.getMessage(), e.getBalance(), e.getWithdrawAmount());
    } catch (Exception e) {
      System.out.println("Transaction failed: " + e.getMessage());
    }

    System.out.println("\n=== Handling Invalid Amount ===");
    try {
      System.out.println("Attempting to withdraw -$100...");
      account.withdraw(-100);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("Transaction failed: " + e.getMessage());
    }

    System.out.println("\n=== Handling Locked Account ===");
    try {
      account.lockAccount();
      System.out.println("Attempting to withdraw from locked account...");
      account.withdraw(100);
    } catch (InvalidAccountException e) {
      System.out.println("Error: " + e.getMessage());
      System.out.println("Account ID: " + e.getAccountId());
    } catch (Exception e) {
      System.out.println("Transaction failed: " + e.getMessage());
    }

    System.out.println("\n=== Handling Transaction Failure ===");
    BankAccount account2 = new BankAccount("ACC002", 500.0);
    for (int i = 0; i < 3; i++) {
      try {
        System.out.println("\nAttempting withdrawal...");
        account2.withdraw(100);
        System.out.println("Success! New balance: $" + account2.getBalance());
      } catch (TransactionFailedException e) {
        System.out.println("Transaction Error: " + e.getMessage());
        System.out.println("Caused by: " + e.getCause().getMessage());
      } catch (Exception e) {
        System.out.println("Other error: " + e.getMessage());
      }
    }

    Utils.printLine("End of Custom Exceptions Examples");
  }
}