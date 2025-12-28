package LockInterfaceFeatures;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/*
* tryLock(long time, TimeUnit unit)
  Unlike tryLock() (which returns immediately),
  This one will wait up to given time for the lock.
  If lock becomes available in that period → acquires and returns true.
  If still not available → returns false.
  It can also throw InterruptedException if the thread is interrupted while waiting.
  *
  * Example Use Cases:
  * Distributed systems: In microservices, if service A tries to lock a shared resource, it may wait up to X seconds before failing fast to keep the system responsive.
    Load balancing: If a server thread is busy, wait for a short time; if still not free, redirect request to another server.
* */
public class TryLockWithTimeoutDemo {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("T1 acquired lock, holding for 5s...");
                Thread.sleep(5000); // simulating work
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("T1 released lock");
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                System.out.println("T2 attempting tryLock for 2 seconds...");
                if (lock.tryLock(2, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("T2 acquired lock!");
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("T2 could not acquire lock in 2s, giving up...");
                }
            } catch (InterruptedException e) {
                System.out.println("T2 interrupted while waiting for lock!");
            }
        });

        t1.start();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        t2.start();
    }
}
