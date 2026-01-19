package Concept.Problems;

import java.util.concurrent.*;

/* Bounded Queue Rejection Handling
Create a pool with:
core=2, max=4
queue size=2
log rejected tasks
*/

class LoggingRejectionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (r instanceof Task) {
            Task task = (Task) r;
            System.out.println("Task ID " + task.getTaskId() + " REJECTED");
        }
    }
}
class Task implements Runnable {
    private final int taskId;
    public Task(int taskId) {
        this.taskId = taskId;
    }
    public int getTaskId() {
        return taskId;
    }
    public void run() {
        System.out.println("Task " + taskId + " is running on " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // log the exception
        }
    }
}
public class Problem6 {
    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(2, 4, 0L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(),
                new LoggingRejectionHandler());

        for (int i = 0; i < 10; i++) {
            executor.execute(new Task(i));
        }
        executor.shutdown();
    }
}
