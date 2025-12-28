package TypesOfLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* ReentrantLock is a type of lock that can be acquired multiple times by the same thread.
   ReentrantLock is reentrant.
   The lock keeps track of TWO things:
     - Which thread owns the lock (the owner thread)
     - How many times that thread has locked it (the hold count)

   IF lock is free:
    - Set current thread as owner
    - Set hold count = 1
    - Proceed
   ELSE IF lock is held by CURRENT thread (same thread):
    - Increment hold count (1 → 2 → 3...)
    - Proceed (NO BLOCKING!)
   ELSE (lock is held by DIFFERENT thread):
    - BLOCK and wait
*/

class SharedResource {
    private int amount = 0;
    private Lock lock = new ReentrantLock();

    public void depositAmount(int amount) {
        lock.lock();
        try {
            this.amount += amount;
        } finally {
            lock.unlock();
        }
    }

    public int getAmount() {
        return amount;
    }
}

class DepositTask implements Runnable {
    private final SharedResource sharedResource;
    public DepositTask(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.depositAmount(50);
        }
    }
}
public class ReentrantLockDemo {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        Thread t1 = new Thread(new DepositTask(sharedResource));
        Thread t2 = new Thread(new DepositTask(sharedResource));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            // log the exception
        }
        System.out.println(sharedResource.getAmount());
    }
}
