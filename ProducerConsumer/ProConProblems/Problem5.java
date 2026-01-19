package ProConProblems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
* Implement producer-consumer where:
  Producer increment counter
  Consumer decrement counter
  use executor service
* */
class SharedResourceCounter {
    private int count = 0;
    public synchronized void increment() {
        while(count == 5){
            try {
                wait();
            } catch (InterruptedException e) {
                // log the exception
            }
        }
        count++;
        notifyAll();
    }
    public synchronized void decrement() {
        while(count == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                // log the exception
            }
        }
        count--;
        notifyAll();
    }
    public int getCount() {
        return count;
    }
}
class CounterProducer implements Runnable {
    private SharedResourceCounter sharedResource;
    public CounterProducer(SharedResourceCounter sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.increment();
        }
    }
}
class CounterConsumer implements Runnable {
    private SharedResourceCounter sharedResource;
    public CounterConsumer(SharedResourceCounter sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.decrement();
        }
    }
}
public class Problem5 {
    public static void main(String[] args) {
        SharedResourceCounter sharedResource = new SharedResourceCounter();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new CounterProducer(sharedResource));
        executor.execute(new CounterConsumer(sharedResource));
        executor.shutdown(); // Initiates graceful shutdown

        try {
            if (!executor.awaitTermination(6, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Interrupts all threads, forceful shutdown
            }
            System.out.println("Final count: " + sharedResource.getCount());
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}


/*
* awaitTermination() blocks the main thread upto x seconds and only waits if the executor is in shutdown mode.
* Otherwise, it returns immediately because the executor is still accepting tasks!
* */