package UtilitiesProblems.semaphore;

/*
* Print odd and even numbers alternately using semaphores
    Two threads:
     - one prints odd numbers
     - one prints even numbers
* */

import java.util.concurrent.Semaphore;

class PrintOddEven {
    private final Semaphore oddSemaphore = new Semaphore(1);
    private final Semaphore evenSemaphore = new Semaphore(0);

    public void printNum(int num) {
        try {
            if (num % 2 == 0) {
                evenSemaphore.acquire();
            } else {
                oddSemaphore.acquire();
            }
            System.out.println("Thread " + Thread.currentThread().getName() + " is printing number: " + num);
            Thread.sleep(1000);

            if (num % 2 == 0) {
                oddSemaphore.release();
            } else {
                evenSemaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class OddPrinter implements Runnable {
    private PrintOddEven obj;
    public OddPrinter(PrintOddEven obj) {
        this.obj = obj;
    }

    public void run() {
        for (int i = 1; i <= 10; i += 2) {
            obj.printNum(i);
        }
    }
}

class EvenPrinter implements Runnable {
    private PrintOddEven obj;
    public EvenPrinter(PrintOddEven obj) {
        this.obj = obj;
    }

    public void run() {
        for (int i = 2; i <= 10; i += 2) {
            obj.printNum(i);
        }
    }
}
public class Problem3 {
    public static void main(String[] args) {
        PrintOddEven obj = new PrintOddEven();
        Thread t1 = new Thread(new OddPrinter(obj));
        Thread t2 = new Thread(new EvenPrinter(obj));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
