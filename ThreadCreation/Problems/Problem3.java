package Problems;

/*
* Show NEW → RUNNABLE → TERMINATED Lifecycle
* NEW → start() → RUNNABLE ⟷ BLOCKED (waiting for lock)
                    ↕
                 WAITING (wait/join/park)
                    ↕
              TIMED_WAITING (sleep/timed wait)
                    ↓
               TERMINATED
Objective: Create a thread that prints "Running" and ends. Print its state at different times:
* */
public class Problem3 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("Running");
        });
        System.out.println(thread.getState()); // NEW

        thread.start();
        System.out.println(thread.getState()); // RUNNABLE

        try {
            thread.join();
        } catch (InterruptedException e) {
            // log the exception
        }

        System.out.println(thread.getState()); // TERMINATED
    }
}

/*
* join() makes the caller (main) thread wait for the worker thread to complete before continuing.
* */
