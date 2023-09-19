import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadedATM {
    public static void main(String[] args) {
        // Create an ATM instance
        ATM atm = new ATM();

        // Create two customers with initial balances
        Customer customer1 = new Customer("Alice", 1000, atm);
        Customer customer2 = new Customer("Bob", 1500, atm);

        // Simulate ATM transactions by customers
        customer1.startTransaction();
        customer2.startTransaction();
    }
}

class ATM {
    private int balance = 5000; // Initial balance
    private Lock lock = new ReentrantLock();

    public int checkBalance() {
        return balance;
    }

    public boolean withdraw(String customerName, int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}

class Customer {
    private String name;
    private int balance;
    private ATM atm;

    public Customer(String name, int balance, ATM atm) {
        this.name = name;
        this.balance = balance;
        this.atm = atm;
    }

    public void startTransaction() {
        Thread transactionThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                int withdrawalAmount = (int) (Math.random() * 500) + 1;
                boolean success = atm.withdraw(name, withdrawalAmount);
                if (success) {
                    System.out.println(name + " withdrew $" + withdrawalAmount);
                    System.out.println(name + "'s new balance: $" + (balance - withdrawalAmount));
                } else {
                    System.out.println(name + " could not withdraw $" + withdrawalAmount + " (insufficient funds)");
                }

                try {
                    Thread.sleep(1000); // Simulate some time between transactions
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        transactionThread.start();
    }
}
