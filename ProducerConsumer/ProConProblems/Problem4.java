package ProConProblems;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/*
* Producer–Consumer Using ExecutorService
  Implement producer-consumer where:
  Producer generates numbers
  Consumer processes numbers
  Use a blocking queue
  Use ExecutorService
* */
class SharedResourceQueue {
    private BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
    public void put(int num) {
        try {
            System.out.println("Producer produced item: " + num);
            queue.put(num);
        } catch (InterruptedException e) {
            // log the exception
        }
    }
    public int get() {
        try {
            int num = queue.take();
            System.out.println("Consumer consumed item: " + num);
            return num;
        } catch (InterruptedException e) {
            // log the exception
            return -1;
        }
    }
}
class QueueProducer implements Runnable {
    private SharedResourceQueue sharedResource;
    public QueueProducer(SharedResourceQueue sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.put(i);
        }
    }
}
class QueueConsumer implements Runnable {
    private SharedResourceQueue sharedResource;
    public QueueConsumer(SharedResourceQueue sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.get();
        }
    }
}
public class Problem4 {
    public static void main(String[] args) {
        SharedResourceQueue sharedResource = new SharedResourceQueue();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new QueueProducer(sharedResource));
        executor.execute(new QueueConsumer(sharedResource));
        executor.shutdown();
    }
}

/*
* BlockingQueue is a thread-safe queue that handles all synchronization internally:
put(item) - Adds item to queue, blocks if queue is full (waits automatically)
take() - Removes item from queue, blocks if queue is empty (waits automatically)
* */
