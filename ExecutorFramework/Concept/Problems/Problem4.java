package Concept.Problems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* Print ABC in Order

Print:
A B C A B C A B C
(3 times)

Constraints:
Use ExecutorService
3 separate tasks
* */
class PrintA implements Runnable {
    public void run() {
        System.out.print("A ");
    }
}
class PrintB implements Runnable {
    public void run() {
        System.out.print("B ");
    }
}
class PrintC implements Runnable {
    public void run() {
        System.out.print("C ");
    }
}
public class Problem4 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Runnable a = new PrintA();
        Runnable b = new PrintB();
        Runnable c = new PrintC();
        for (int i = 0; i < 3; i++) {
            executor.execute(a);
            executor.execute(b);
            executor.execute(c);
        }
        executor.shutdown();
    }
}
