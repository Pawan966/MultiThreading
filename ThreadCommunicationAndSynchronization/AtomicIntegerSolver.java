package MultiThreading.ThreadCommunicationAndSynchronization;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * AtomicInteger provides thread-safe atomic operations without using locks.
 * Atomic: Entire compare-and-swap happens in one CPU instruction
 * CAS (Compare-And-Swap) is a low-level atomic CPU instruction that AtomicInteger uses internally.
 * CAS(memory_location, expected_value, new_value)
 * */
class SharedResourceAtomic {
    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}

class TaskAtomic implements Runnable {
    private SharedResourceAtomic sharedResource;
    public TaskAtomic(SharedResourceAtomic sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.increment();
        }
    }
}

public class AtomicIntegerSolver {
    public static void main(String[] args) {
        SharedResourceAtomic sharedResource = new SharedResourceAtomic();
        Thread t1 = new Thread(new TaskAtomic(sharedResource));
        Thread t2 = new Thread(new TaskAtomic(sharedResource));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            // log the exception
        }
        System.out.println(sharedResource.getCount());
    }
}

/*
Thread 1: Reads count=5, wants to increment to 6
Thread 2: Reads count=5, wants to increment to 6
Thread 1: CAS(count, 5, 6) → SUCCESS (count becomes 6)
Thread 2: CAS(count, 5, 6) → FAILS (expected 5, but found 6)
Thread 2: Retries: Reads count=6, CAS(count, 6, 7) → SUCCESS
* */

