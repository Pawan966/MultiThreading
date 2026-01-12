package Concept;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Executor service basic example
class Task implements Runnable {
    public void run() {
        System.out.println("Task is running: " + Thread.currentThread().getName());
    }
}
public class BasicUsage {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(new Task());
        executor.execute(new Task());
        executor.execute(new Task());

        executor.shutdown();
    }
}


/*
* execute() vs submit()
  execute()
   - Accepts Runnable
   - No return value
   - Exceptions go to thread’s UncaughtExceptionHandler

  submit()
   - Accepts Runnable or Callable
   - Returns Future
   - Exceptions captured inside Future
     eg: Future<?> future = executor.submit(new Task());
         try {
             future.get(); // throws ExecutionException if task threw an exception
         } catch (InterruptedException | ExecutionException e) {
             // handle exception
         }
* */