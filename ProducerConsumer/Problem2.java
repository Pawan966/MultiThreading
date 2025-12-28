package MultiThreading.ProducerConsumer;

class SharedResourceBuffer {
    private int idx = 0;
    private int[] buffer = new int[5];

    public synchronized void put(int num) {
        while (idx == buffer.length) {
            try {
                wait();
            } catch (InterruptedException e) {
                // log the exception
            }
        }
        buffer[idx] = num;
        System.out.println("Producer produced item: " + num + " at index: " + idx);
        idx++;
        notifyAll();
    }

    public synchronized void get() {
        while (idx == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                // log the exception
            }
        }
        int num = buffer[--idx];
        System.out.println("Consumer consumed item: " + num + " at index: " + idx);
        notifyAll();
    }
}

class BufferProducer implements Runnable {
    private SharedResourceBuffer sharedResource;
    public BufferProducer(SharedResourceBuffer sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.put(i);
        }
    }
}

class BufferConsumer implements Runnable {
    private SharedResourceBuffer sharedResource;
    public BufferConsumer(SharedResourceBuffer sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.get();
        }
    }
}
public class Problem2 {
    public static void main(String[] args) {
        SharedResourceBuffer sharedResource = new SharedResourceBuffer();
        Thread producer = new Thread(new BufferProducer(sharedResource));
        Thread consumer = new Thread(new BufferConsumer(sharedResource));
        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            // log the exception
        }
    }
}
