package SyncConcept;

class SharedResourceCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class SynchronizedTask implements Runnable {
    private SharedResourceCounter sharedResource;
    public SynchronizedTask(SharedResourceCounter sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            sharedResource.increment();
        }
    }
}

public class SynchronizedBlock {
    public static void main(String[] args) {
        SharedResourceCounter sharedResource = new SharedResourceCounter();
        Thread t1 = new Thread(new SynchronizedTask(sharedResource));
        Thread t2 = new Thread(new SynchronizedTask(sharedResource));
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
* the synchronized keyword takes a monitor lock (also called intrinsic lock or mutex).
* The monitor lock is associated with an object.
* Lock object: The instance of  SharedResourceCounter (the this object)
* If we have multiple objects of SharedResourceCounter, each object will have its own lock, so no synchronization.
*
*
* Every Java object has an intrinsic lock (monitor) built into it. When you use synchronized:
  You're acquiring that object's monitor
  Other threads trying to acquire the same object's monitor will block
  Different objects = different monitors = no blocking

*
* Below example shows block on a different object.
* Object lock = new Object();
  public void methodA() {
    synchronized(lock) {  // Lock on specific object
        // code
    }
  }
* */