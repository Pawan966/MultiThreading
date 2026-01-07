package ProConProblems;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

// producer consumer using locks
/*
* A Condition is the explicit lock equivalent of the wait/notify mechanism used with intrinsic locks (synchronized).
* */
class SharedResourceLock {
    private int count=0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition(); // Condition object tied to the lock

    public void increment(){
        lock.lock();
        try {
            while(count==5){
                try {
                    condition.await(); // equivalent to wait(), but for ReentrantLock
                } catch (InterruptedException e) {
                    // log the exception
                }
            }
            count++;
            condition.signalAll(); // equivalent to notifyAll()
        } finally {
            lock.unlock(); // always unlock in finally block
        }
    }

    public void decrement(){
        lock.lock();
        try {
            while(count==0){
                try {
                    condition.await(); // equivalent to wait(), but for ReentrantLock
                } catch (InterruptedException e) {
                    // log the exception
                }
            }
            count--;
            condition.signalAll(); // equivalent to notifyAll()
        } finally {
            lock.unlock(); // always unlock in finally block
        }
    }

    public int getCount(){
        return count;
    }
}

class LockProducer implements Runnable {
    private SharedResourceLock sharedResource;
    public LockProducer(SharedResourceLock sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.increment();
        }
    }
}

class LockConsumer implements Runnable {
    private SharedResourceLock sharedResource;
    public LockConsumer(SharedResourceLock sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.decrement();
        }
    }
}

public class Problem3 {
    public static void main(String[] args) {
        SharedResourceLock sharedResource = new SharedResourceLock();
        Thread producer = new Thread(new LockProducer(sharedResource));
        Thread consumer = new Thread(new LockConsumer(sharedResource));
        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            // log the exception
        }
        System.out.println(sharedResource.getCount());
    }
}
