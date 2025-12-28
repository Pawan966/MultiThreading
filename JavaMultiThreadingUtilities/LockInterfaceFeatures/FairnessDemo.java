package LockInterfaceFeatures;

import java.util.concurrent.locks.ReentrantLock;

/*
* Fair Lock (true):
  Threads acquire the lock in the order they requested it (first-come-first-served → FIFO).
  Prevents starvation (no thread waits forever).
  Slightly slower, because the lock needs to maintain a waiting queue.
  Example: Ticket booking systems, job schedulers, where starvation is unacceptable.

  Non-Fair Lock (false or default):
  No ordering guarantee. If a thread releases the lock, any waiting thread or even a new arriving thread may acquire it.
  Better throughput (faster), because threads don’t always wait in line.
  Risk of starvation: a thread may be waiting for a long time if new threads keep arriving.
  Example: High-throughput applications (web servers, caching systems) where occasional starvation is tolerable.
* */
public class FairnessDemo {
    private static final ReentrantLock fairLock = new ReentrantLock(true); // fair
    private static final ReentrantLock unfairLock = new ReentrantLock(false); // non-fair

    public static void main(String[] args) {
        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 3; i++) {
                try {
                    fairLock.lock();
                    Thread.sleep(100);
                    System.out.println(name + " acquired lock (fair)");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    fairLock.unlock();
                }
            }
        };

        for (int i = 1; i <= 5; i++) {
            new Thread(task, "Thread-" + i).start();
        }
    }
}

/*
* Why are non-fair locks default in Java?
  Because in most real-world apps, throughput (speed) is more important than strict fairness.
  Fair locks add overhead due to queue management.
* */
