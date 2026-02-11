package ProConProblems;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class SharedResourceSemaphore {
    int count = 0;
    private final int capacity = 5;

    private final Semaphore empty = new Semaphore(capacity);
    private final Semaphore full = new Semaphore(0);
    private final Semaphore mutex = new Semaphore(1);

    public void produce(int item) throws InterruptedException {
        empty.acquire();        // wait for empty slot
        mutex.acquire();        // enter critical section

        count++;
        System.out.println(Thread.currentThread().getName() +
                " produced: " + item + " | Count: " + count);

        mutex.release();        // exit critical section
        full.release();         // signal item available
    }

    public void consume() throws InterruptedException {
        full.acquire();         // wait for filled slot
        mutex.acquire();        // enter critical section

        int item = count--;
        System.out.println(Thread.currentThread().getName() +
                " consumed: " + item + " | Count: " + item);

        mutex.release();        // exit critical section
        empty.release();        // signal empty slot available
    }
}

class SemaphoreProducer implements Runnable {
    private SharedResourceSemaphore sharedResource;
    public SemaphoreProducer(SharedResourceSemaphore sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sharedResource.produce(i);
            } catch (InterruptedException e) {
                // log the exception
            }
        }
    }
}

class SemaphoreConsumer implements Runnable {
    private SharedResourceSemaphore sharedResource;
    public SemaphoreConsumer(SharedResourceSemaphore sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sharedResource.consume();
            } catch (InterruptedException e) {
                // log the exception
            }
        }
    }
}

public class Problem6 {
    public static void main(String[] args) {
        SharedResourceSemaphore sharedResource = new SharedResourceSemaphore();
        Thread producer = new Thread(new SemaphoreProducer(sharedResource));
        Thread consumer = new Thread(new SemaphoreConsumer(sharedResource));
        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            // log the exception
        }

        System.out.println(sharedResource.count);
    }
}
