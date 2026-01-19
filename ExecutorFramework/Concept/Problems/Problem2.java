package Concept.Problems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Print Even and Odd Numbers using 2 tasks, ordering does not matter
* */
class EvenPrinter implements Runnable {
    public void run() {
        for (int i = 2; i <= 10; i += 2) {
            System.out.println("Thread " + Thread.currentThread().getName() + " is printing number: " + i);
        }
    }
}
class OddPrinter implements Runnable {
    public void run() {
        for (int i = 1; i <= 10; i += 2) {
            System.out.println("Thread " + Thread.currentThread().getName() + " is printing number: " + i);
        }
    }
}
public class Problem2 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new EvenPrinter());
        executor.execute(new OddPrinter());
        executor.shutdown();
    }
}
