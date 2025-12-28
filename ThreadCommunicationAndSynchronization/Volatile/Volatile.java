package Volatile;

/*
* Create a flag like volatile boolean running = true;
  One thread runs in a loop while this is true. Main thread sets it to false after 2 seconds.
* */
public class Volatile {
    // if we remove this volatile, the worker thread will not stop even after 2 seconds
    private static volatile boolean running = true;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (running) {
                // do something
                System.out.println("Worker Thread Running");
            }
        });
        t1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // log the exception
        }
        running = false;
    }
}

/*
* In Java, volatile is a variable modifier that ensures:
Visibility → Changes made to a variable in one thread are immediately visible to all other threads.
No caching in threads → Normally, each thread may cache variables in CPU registers or thread-local memory;
*                       volatile forces it to always read/write from main memory.
No atomicity guarantee → It does not make compound operations like count++ atomic.
*
*
*
* volatile is enough when:
    Only one thread modifies the variable.
    Operations are simple read/write (no compound updates like count++).
  Not enough when:
    Multiple threads update the variable in a non-atomic way.
    You need mutual exclusion — then use synchronized or locks.
* */
