package MultiThreading.ThreadCommunicationAndSynchronization;

// race condition: When multiple threads access shared data and the final outcome depends on the order in which the threads execute.

class SharedResource {
    private int count = 0;

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public int getCount() {
        return count;
    }
}

class Task implements Runnable {
    private SharedResource sharedResource;
    public Task(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            sharedResource.increment();
        }
    }
}
public class SynchronizationProblem {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        Thread t1 = new Thread(new Task(sharedResource));
        Thread t2 = new Thread(new Task(sharedResource));
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
* Above code leads to race condition and the output is not always 2000.
* */
