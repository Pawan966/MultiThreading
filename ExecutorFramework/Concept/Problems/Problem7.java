package Concept.Problems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/*
* Rate Limiter Using Executor + Semaphore
  Allow only 5 tasks to execute concurrently, even if many are submitted.
* */
class RateLimitedTask implements Runnable {

    private final int taskId;
    private final Semaphore semaphore;

    RateLimitedTask(int taskId, Semaphore semaphore) {
        this.taskId = taskId;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire(); // blocks if limit reached

            System.out.println(
                    Thread.currentThread().getName() +
                            " START task " + taskId +
                            " | Available permits: " + semaphore.availablePermits()
            );

            // Simulate work
            Thread.sleep(3000);

            System.out.println(
                    Thread.currentThread().getName() +
                            " END task " + taskId
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release(); // MUST release
        }
    }
}

public class Problem7 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        ExecutorService executor =
                Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 15; i++) {
            executor.submit(new RateLimitedTask(i, semaphore));
        }

        executor.shutdown();
    }
}
