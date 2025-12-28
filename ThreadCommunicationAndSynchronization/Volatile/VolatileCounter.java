package Volatile;

class SharedResourceCounter {
    private volatile int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
public class VolatileCounter {
    public static void main(String[] args) {
        SharedResourceCounter sharedResource = new SharedResourceCounter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedResource.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedResource.increment();
            }
        });
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
* Even with volatile, two threads can read the same value at the same time
* before either writes it back — leading to lost updates.
* */