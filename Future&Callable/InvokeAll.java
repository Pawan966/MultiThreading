import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class PrintNumber implements Callable<String> {
    private int number;
    public PrintNumber(int number) {
        this.number = number;
    }

    public String call() throws Exception {
        return "Number: " + number;
    }
}
public class InvokeAll {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(new PrintNumber(i));
        }

        try {
            // invokeAll() submits all tasks and waits for ALL to complete
            // Returns a list of Futures representing the tasks, in the same sequential order as produced by the iterator for the given task list.

            List<Future<String>> futures = executor.invokeAll(tasks); // blocking call

            // All tasks are already completed at this point
            // After invokeAll() returns, all futures are guaranteed to be done, so future.get() will NOT block.
            for (Future<String> future : futures) {
                try {
                    System.out.println(future.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) { // invokeAll() can throw InterruptedException if interrupted while waiting.
            e.printStackTrace();
        }

        executor.shutdown();
    }
}


/*
* Note: invokeAny() submits all tasks and returns the result of the first task that completes successfully, then cancels all other tasks.
* */