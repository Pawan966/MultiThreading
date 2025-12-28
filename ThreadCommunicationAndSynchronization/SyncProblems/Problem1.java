package SyncProblems;

/*Print numbers from 1 to 10 using two threads:
Thread A prints odd numbers
Thread B prints even numbers
Constraint: Must use wait() and notify() to alternate turns.
* */

class Printer {
    private String threadName = "Odd";
    public synchronized void printNum(int num, String threadName) {
        while (!threadName.equals(this.threadName)) {
            try {
                wait();
            } catch (InterruptedException e) {
                // log the exception
            }
        }
        System.out.println("Thread " + threadName + " is running and printing number: " + num);
        this.threadName = threadName.equals("Odd") ? "Even" : "Odd";
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            // log the exception
        }
        notifyAll();
    }
}
class OddPrinter implements Runnable {
    private Printer printer;
    public OddPrinter(Printer printer) {
        this.printer = printer;
    }

    public void run() {
        for (int i = 1; i <= 10; i += 2) {
            printer.printNum(i, "Odd");
        }
    }
}

class EvenPrinter implements Runnable {
    private Printer printer;
    public EvenPrinter(Printer printer) {
        this.printer = printer;
    }

    public void run() {
        for (int i = 2; i <= 10; i += 2) {
            printer.printNum(i, "Even");
        }
    }
}

public class Problem1 {
    public static  void main(String[] args) {
        Printer printer = new Printer();
        Thread t1 = new Thread(new OddPrinter(printer));
        Thread t2 = new Thread(new EvenPrinter(printer));
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            // log the exception
        }
    }
}
