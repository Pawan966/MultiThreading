package MultiThreading.ProducerConsumer;

class SharedResource {
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
        System.out.println("Producer produced item: " + count);
    }

    public synchronized void decrement() {
        while(count == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                // log the exception
            }
        }
        System.out.println("Consumer consumed item: " + count);
        count--;
        notifyAll();
    }

    public int getCount() {
        return count;
    }
}

class Producer implements Runnable {
    private SharedResource sharedResource;
    public Producer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.increment();
        }
    }
}

class Consumer implements Runnable {
    private SharedResource sharedResource;
    public Consumer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.decrement();
        }
    }
}

public class Problem1 {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        Thread producer = new Thread(new Producer(sharedResource));
        Thread consumer = new Thread(new Consumer(sharedResource));
        producer.start();
        consumer.start();
    }
}
