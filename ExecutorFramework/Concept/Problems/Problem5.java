package Concept.Problems;

import java.util.concurrent.*;

/*
* Execute Tasks with Delay Execute 5 tasks with 1 second delay between each execution.
* Hint: ScheduledExecutorService
* */
class DelayedTask implements Runnable {

    private int count = 0;
    private final ScheduledExecutorService executor;

    DelayedTask(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void run() {
        count++;
        System.out.println("Task " + count +
                " running on " + Thread.currentThread().getName());

        if (count == 5) {
            executor.shutdown();
        }
    }
}

public class Problem5 {
    public static void main(String[] args) {

        ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(1);

        executor.scheduleWithFixedDelay(
                new DelayedTask(executor),
                0,
                1,
                TimeUnit.SECONDS
        );
    }
}

/*
* scheduleAtFixedRate(task, initialDelay, period, unit): starts every task at fixed time interval.
* Case 1: Task finishes before next scheduled start
          period = 1s
          task time = 300ms
          0.0s  → start
          0.3s  → finish
          1.0s  → next start   (waits till clock hits 1s)
   Case 2: Task finishes after next scheduled start
          period = 1s
          task time = 1.5s
          0.0s → start
          1.5s → finish
          1.5s → immediately start again (catch-up)
* scheduleWithFixedDelay(task, initialDelay, delay, unit): starts next task after previous task is completed and apply delay after each task.
*
* Increasing corePoolSize allows multiple scheduled tasks to run concurrently, but each individual scheduled task is executed serially and never overlaps with itself.
* */
