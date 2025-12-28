package SyncProblems;

class BankAccount {
    private int balance = 1000;
    private String accountHolder = "John Doe";

    // Separate lock objects for different resources
    private final Object balanceLock = new Object();
    private final Object accountInfoLock = new Object();

    // Operations on balance use balanceLock
    public void deposit(int amount) {
        synchronized(balanceLock) {
            balance += amount;
            System.out.println("Deposited: " + amount + ", New Balance: " + balance);
        }
    }

    public void withdraw(int amount) {
        synchronized(balanceLock) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrew: " + amount + ", New Balance: " + balance);
            }
        }
    }

    public int getBalance() {
        synchronized(balanceLock) {
            return balance;
        }
    }

    // Operations on account info use accountInfoLock
    public void updateAccountHolder(String name) {
        synchronized(accountInfoLock) {
            accountHolder = name;
            System.out.println("Account holder updated to: " + name);
        }
    }

    public String getAccountHolder() {
        synchronized(accountInfoLock) {
            return accountHolder;
        }
    }
}
public class Problem2 {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        // Thread 1: Deposits money
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.deposit(100);
            }
        });

        // Thread 2: Withdraws money
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.withdraw(50);
            }
        });

        // Thread 3: Updates account holder (independent operation)
        Thread t3 = new Thread(() -> {
            account.updateAccountHolder("Jane Doe");
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
