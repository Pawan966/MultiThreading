package Concept.Problems;

/*
* Timeout on Task Execution
Submit a task that sleeps for 5 seconds.
Try to get result with 2-second timeout.
Expected:
Handle TimeoutException
* */

import java.util.concurrent.*;

class SlowTask implements Callable<String> {
    public String call() throws Exception {
        Thread.sleep(5000);
        return "Task completed";
    }
}
public class Problem8 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<String> future = executor.submit(new SlowTask());
        try {
            String result = future.get(2, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (TimeoutException e) {
            System.out.println("Task timed out");
            future.cancel(true);
        } catch (Exception e){
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
