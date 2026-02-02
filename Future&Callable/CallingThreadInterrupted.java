import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class LongRunningTask implements Callable<String> {
    public String call() throws Exception {
        System.out.println("Task started, will take 10 seconds...");
        Thread.sleep(10000); // Long running task
        return "Task completed";
    }
}

public class CallingThreadInterrupted {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new LongRunningTask());
        
        // Create a thread that will call future.get()
        Thread callingThread = new Thread(() -> {
            try {
                System.out.println("Calling thread: Waiting for result...");
                String result = future.get(); // This will be interrupted
                System.out.println("Result: " + result);
            } catch (InterruptedException e) {
                System.out.println("Calling thread was INTERRUPTED while waiting!");
                System.out.println("Exception: " + e.getClass().getSimpleName());
            } catch (Exception e) {
                System.out.println("Other exception: " + e.getClass().getSimpleName());
            }
        });
        
        callingThread.start();
        
        try {
            // Give the calling thread time to start waiting
            Thread.sleep(2000);
            
            // Now interrupt the CALLING THREAD (not the task)
            System.out.println("Main thread: Interrupting the calling thread...");
            callingThread.interrupt();
            
            callingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
    }
}

