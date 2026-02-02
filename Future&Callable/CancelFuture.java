import java.util.concurrent.*;

class CancelTask implements Callable<String> {
    public String call() throws Exception {
        System.out.println("Task started");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Task was interrupted!");
            return "Task cancelled";
        }
        return "Task completed";
    }
}
public class CancelFuture {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new CancelTask());

        // Give the task time to start
        Thread.sleep(1000);

        // Cancel the task while it's running
        boolean cancelled = future.cancel(true); // true means interrupt if running
        System.out.println("Cancel called: " + cancelled);

        try {
            System.out.println(future.get()); // This will throw CancellationException because task was cancelled.
        } catch (ExecutionException e) {
            System.out.println("Caught exception: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Exception caught: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        }

        executor.shutdown();
    }
}
