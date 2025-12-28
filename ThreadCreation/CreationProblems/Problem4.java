package CreationProblems;

/*
* Use Thread Names to Track Execution
Objective: Name threads like "Worker-1", "Worker-2", etc., and log their execution.
* */
public class Problem4 {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                System.out.println("Thread " + Thread.currentThread().getName() + " is running");
            }, "Worker-" + i);
            thread.start();
        }
    }
}
