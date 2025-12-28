package LockInterfaceFeatures;

import java.util.concurrent.locks.ReentrantLock;

/*
Instead of waiting forever, it just tries once:
If lock is free → acquires and returns true.
If lock is busy → immediately returns false.

Example Use Cases:
Non-critical resource access: For example, writing to a log file. If the log file is locked, skip logging rather than stall business logic.
* */
public class TryLockDemo {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("T1 acquired lock, holding for 5s...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("T1 released lock");
            }
        });

        Thread t2 = new Thread(() -> {
            System.out.println("T2 attempting tryLock...");
            if (lock.tryLock()) {
                try {
                    System.out.println("T2 acquired lock!");
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("T2 could not acquire lock, doing something else...");
            }
        });

        t1.start();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        t2.start();
    }
}

/*
  Golden Rule: Always unlock locks in a finally block.
* If you forget to unlock a lock → deadlock risk (other threads starve forever).
  If an exception occurs before unlock → lock never released.
* */