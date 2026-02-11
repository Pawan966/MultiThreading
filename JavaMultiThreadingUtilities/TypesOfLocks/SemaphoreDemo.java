package TypesOfLocks;

/*
*  A Semaphore controls how many threads can access a shared resource at the same time.
*  Semaphore is for resource control, not mutual exclusion (ensuring that only ONE thread can access a critical section (shared resource) at a time.)
*
*  Method	            Purpose
   acquire()	        Take a permit (blocks if none available)
   release()	        Return a permit
   tryAcquire()	        Try without blocking
   tryAcquire(timeout)	Wait up to time
   availablePermits()	Number of free permits


   Semaphore vs Lock (Critical Difference)
   Feature	       Semaphore	          Lock
   Access count	   Multiple threads	      Only one
   Ownership	   No owner	              Owner thread
   Use case	       Resource limiting	  Mutual exclusion
   Release	       Any thread can release Only owner
* */

import java.util.concurrent.Semaphore;

class DatabaseConnectionPool {
    Semaphore semaphore = new Semaphore(5); // 5 connections

    public void getConnection() {
        try {
            semaphore.acquire();
            System.out.println("Thread " + Thread.currentThread().getName() + " acquired a connection");
            Thread.sleep(2000);
            releaseConnection();
        } catch (InterruptedException e) {
            // log the exception
        }
    }

    private void releaseConnection() {
        semaphore.release();
        System.out.println("Thread " + Thread.currentThread().getName() + " released a connection");
    }
}
class ConnectionUser implements Runnable {
    private final DatabaseConnectionPool pool;
    public ConnectionUser(DatabaseConnectionPool pool) {
        this.pool = pool;
    }

    public void run() {
        pool.getConnection();
    }
}
public class SemaphoreDemo {
    public static void main(String[] args) {
        DatabaseConnectionPool pool = new DatabaseConnectionPool();
        for (int i = 0; i < 10; i++) {
            new Thread(new ConnectionUser(pool)).start();
        }
    }
}

/*
* Semaphore s = new Semaphore(2);  // Initial: 2 permits
s.acquire();  // 2 → 1 (decrements)
s.acquire();  // 1 → 0 (decrements)
s.acquire();  // 0 → BLOCKS (no permits)

s.release();  // 0 → 1 (increments) ✅
s.release();  // 1 → 2 (increments) ✅
s.release();  // 2 → 3 (increments) ✅ Can go above initial value!
* */
