package MultiThreading.ThreadCreation;

// this method is more preferable because:
// 1. It is possible to extend another class
// 2. It separates the task from the thread -> composition over inheritance
// 3. It's designed for task submission to thread pools, while Thread extension creates standalone thread objects that can't be reused in executor frameworks
//    Same task can run on different thread types
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Thread is running: " + Thread.currentThread().getName());
    }
}

public class ImplementRunnable {
    public  static void main(String[] args) {
        Thread t1 = new Thread(new MyRunnable());
        t1.start();
    }
}
