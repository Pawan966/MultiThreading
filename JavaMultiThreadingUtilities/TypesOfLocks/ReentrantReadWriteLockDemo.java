package TypesOfLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
* Optimized for read-heavy scenarios:
  Multiple readers can acquire lock concurrently.
  Only one writer allowed at a time.
  Writer has exclusive access (blocks readers too).

  Read Write Lock summary:
   Read + Read = ✅ Allowed (concurrent)
   Read + Write = ❌ Writer must wait
   Write + Read = ❌ Reader must wait
   Write + Write = ❌ Second writer must wait
* */
class StockPrice {
    private double price = 100.0;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void updatePrice(double newPrice) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " updating price to " + newPrice);
            price = newPrice;
        } finally {
            writeLock.unlock();
        }
    }

    public double getPrice() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading price: " + price);
            return price;
        } finally {
            readLock.unlock();
        }
    }
}
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        StockPrice stockPrice = new StockPrice();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                stockPrice.getPrice();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                stockPrice.getPrice();
            }
        });
        Thread t3 = new Thread(() -> {
            stockPrice.updatePrice(105.0);
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            // log the exception
        }
        System.out.println("Final price: " + stockPrice.getPrice());
    }
}


/* ReentrantReadWriteLock is also reentrant with below cases:
* Scenario	                            Allowed?
  Read lock → Read lock (same thread)	✅ Yes (reentrant)
  Write lock → Write lock (same thread)	✅ Yes (reentrant)
  Write lock → Read lock	            ✅ Yes (downgrading)
  Read lock → Write lock	            ❌ No (upgrading causes deadlock)
*
* */