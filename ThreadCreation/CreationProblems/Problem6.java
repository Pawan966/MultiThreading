package CreationProblems;

// program to demonstrate the use of join on thread other than main
class Task implements  Runnable {
    String name;
    Thread otherThread;

    public Task(String name) {
        this.name = name;
    }

    public void setOtherThread(Thread otherThread) {
        this.otherThread = otherThread;
    }

    public void run() {
        System.out.println(name + " is running");
        try {
            if (otherThread != null) {
                otherThread.join();
            }
        } catch (InterruptedException e) {
            // log the exception
        }
        System.out.println(name + " is done");
    }
}
public class Problem6 {
    public static void main(String[] args) {
        Task task1 = new Task("thread1");
        Task task2 = new Task("thread2");
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        task1.setOtherThread(thread2);
        //task2.setOtherThread(thread1); // this will cause deadlock
        thread1.start();
        thread2.start();

        System.out.println(Thread.currentThread().getName() + " is completed");
    }
}
