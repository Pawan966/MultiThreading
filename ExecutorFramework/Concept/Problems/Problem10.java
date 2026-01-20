package Concept.Problems;

/*
* Priority Task Execution
  Execute tasks based on priority using ExecutorService.
* */

import java.util.concurrent.*;
import java.util.Comparator;

// Task with priority - NO Comparable implementation needed!
class PriorityTask implements Runnable {
    private final int taskId;
    private final int priority; // Higher number = Higher priority

    public PriorityTask(int taskId, int priority) {
        this.taskId = taskId;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public int getTaskId() {
        return taskId;
    }

    @Override
    public void run() {
        System.out.println(
            "Executing Task " + taskId +
            " (Priority: " + priority + ") on " +
            Thread.currentThread().getName()
        );
        try {
            Thread.sleep(1000); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Problem10 {
    public static void main(String[] args) throws InterruptedException {

        // ========== APPROACH 1: Using Comparator (Lambda) ==========
        System.out.println("=== APPROACH 1: Using Comparator (Lambda) ===\n");

        // Create custom comparator - Higher priority first
        Comparator<Runnable> priorityComparator = (r1, r2) -> {
            PriorityTask task1 = (PriorityTask) r1;
            PriorityTask task2 = (PriorityTask) r2;
            // Higher priority comes first (reverse order)
            return Integer.compare(task2.getPriority(), task1.getPriority());
        };

        // Create PriorityBlockingQueue with Comparator
        // it is unbounded queue
        PriorityBlockingQueue<Runnable> priorityQueue =
            new PriorityBlockingQueue<>(10, priorityComparator);

        // Create executor with custom priority queue
        ThreadPoolExecutor executor1 = new ThreadPoolExecutor(
            2,                                      // core threads
            2,                                      // max threads
            0L, TimeUnit.MILLISECONDS,
            priorityQueue                          // Priority queue with comparator!
        );

        System.out.println("Submitting tasks with different priorities...\n");

        // Submit tasks in random order with different priorities
        executor1.execute(new PriorityTask(1, 3));   // Medium priority
        executor1.execute(new PriorityTask(2, 1));   // Low priority
        executor1.execute(new PriorityTask(3, 5));   // High priority
        executor1.execute(new PriorityTask(4, 2));   // Low-Medium priority
        executor1.execute(new PriorityTask(5, 10));  // Highest priority
        executor1.execute(new PriorityTask(6, 4));   // Medium-High priority
        executor1.execute(new PriorityTask(7, 1));   // Low priority
        executor1.execute(new PriorityTask(8, 8));   // Very high priority

        System.out.println("All tasks submitted. Watch execution order...\n");

        // Shutdown and wait
        executor1.shutdown();
        executor1.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("\n=== All tasks completed ===");
        System.out.println("Notice: Higher priority tasks executed first!\n\n");
    }
}
