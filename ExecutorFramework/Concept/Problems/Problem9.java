package Concept.Problems;

/*
* Backpressure Using CallerRunsPolicy
  Demonstrate how CallerRunsPolicy slows task submission.
* */

import java.util.concurrent.*;

class BackpressureTask implements Runnable {
    private final int taskId;

    public BackpressureTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println(
            "[" + System.currentTimeMillis() + "] " +
            "Task " + taskId + " STARTED on " + Thread.currentThread().getName()
        );
        try {
            Thread.sleep(2000); // Simulate slow work (2 seconds)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(
            "[" + System.currentTimeMillis() + "] " +
            "Task " + taskId + " FINISHED on " + Thread.currentThread().getName()
        );
    }
}

public class Problem9 {
    public static void main(String[] args) throws InterruptedException {
        // Create bounded executor with CallerRunsPolicy
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,                              // core threads
            2,                              // max threads
            0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(2),    // BOUNDED queue (capacity = 2)
            new ThreadPoolExecutor.CallerRunsPolicy()  // Backpressure policy
        );

        long startTime = System.currentTimeMillis();

        // Submit 10 tasks rapidly
        for (int i = 1; i <= 10; i++) {
            System.out.println(
                "[" + System.currentTimeMillis() + "] " +
                "Main thread submitting Task " + i
            );
            executor.execute(new BackpressureTask(i));
            System.out.println(
                "[" + System.currentTimeMillis() + "] " +
                "Main thread finished submitting Task " + i +
                " | Queue size: " + executor.getQueue().size() + "\n"
            );
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\n=== All tasks submitted ===");
        System.out.println("Total submission time: " + (endTime - startTime) + "ms");

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        System.out.println("\n=== All tasks completed ===");
    }
}
