package LockInterfaceFeatures;

import java.util.concurrent.locks.ReentrantLock;

/*
* Normal lock() will block forever until the lock is acquired. If the thread is interrupted while waiting → it ignores interruption.
  lockInterruptibly() allows you to respond to interrupts while waiting.
  *
  * Example Use Cases:
    Deadlock recovery: If two threads are waiting for each other, you can interrupt one to break the deadlock.
    User-initiated cancellation: Suppose a long-running background task is waiting for a resource (DB lock, file lock).
    If the user cancels the operation, you interrupt the waiting thread to stop wasting resources.
**/
public class LockInterruptiblyDemo {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
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
            try {
                System.out.println("T2 trying to acquire lock interruptibly...");
                lock.lockInterruptibly();
                System.out.println("T2 acquired lock!");
            } catch (InterruptedException e) {
                System.out.println("T2 was interrupted while waiting for lock!");
            }
        });

        t1.start();
        Thread.sleep(500); // ensure T1 gets lock first
        t2.start();

        Thread.sleep(2000);
        System.out.println("Main interrupts T2...");
        t2.interrupt();
    }
}

/*
* T1 gets the lock.
  T2 tries to get it but is waiting.
  Main interrupts T2 → T2 throws InterruptedException and exits gracefully.
* */