package UtilitiesProblems.semaphore;
import java.util.concurrent.Semaphore;

/*
* Print in order: first → second → third
* Three threads call:
   first(), second(), third()
  They may be called in any order, but must print in this order:
* */

/*
* Expected idea
    second waits for first
    third waits for second
* */

class PrintInOrder {
    private final Semaphore s1 = new Semaphore(0);
    private final Semaphore s2 = new Semaphore(0);

    public void first() {
        System.out.println("first");
        s1.release();
    }

    public void second() {
        try {
            s1.acquire();
            System.out.println("second");
            s2.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void third() {
        try {
            s2.acquire();
            System.out.println("third");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class First implements Runnable {
    private PrintInOrder obj;
    public First(PrintInOrder obj) {
        this.obj = obj;
    }

    public void run() {
        obj.first();
    }
}

class Second implements Runnable {
    private PrintInOrder obj;
    public Second(PrintInOrder obj) {
        this.obj = obj;
    }

    public void run() {
        obj.second();
    }
}

class Third implements Runnable {
    private PrintInOrder obj;
    public Third(PrintInOrder obj) {
        this.obj = obj;
    }

    public void run() {
        obj.third();
    }
}
public class Problem2 {
    public static void main(String[] args) {
        PrintInOrder obj = new PrintInOrder();

        Thread t1 = new Thread(new First(obj));
        Thread t2 = new Thread(new Second(obj));
        Thread t3 = new Thread(new Third(obj));

        // Start in random order
        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
