package Concept.Problems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Print numbers from 1 to 10 in order using an ExecutorService.
Constraints:
  - Each number printed by a task
  - Ensure executor shuts down properly
* */
class NumberPrinter implements Runnable {
    private int number;
    public NumberPrinter(int number) {
        this.number = number;
    }

    public void run() {
        System.out.println("Thread " + Thread.currentThread().getName() + " is printing number: " + number);
    }
}

public class Problem1 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= 10; i++) {
            executor.execute(new NumberPrinter(i));
        }
        executor.shutdown();
    }
}

// Since there is only one thread that's why the numbers are printed in order.
