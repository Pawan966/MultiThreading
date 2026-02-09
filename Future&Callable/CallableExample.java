import java.util.concurrent.*;


/*
Callable – The Smarter Runnable
@FunctionalInterface
public interface Callable<V> {
   V call() throws Exception;
}
* Important: Callable does NOT start a thread by itself. It must be submitted to an ExecutorService.
  The moment you submit:
  Task may run asynchronously
  You immediately get a Future - A Promise of a Result

Core Methods of Future:
Method	        Meaning
get()	        Waits and returns result, blocks if not ready
get(timeout)	Waits with timeout
isDone()	    Is task finished?
isCancelled()	Was task cancelled?
cancel()	    Cancel task
* */
class Task implements Callable<String> {
    public String call() throws Exception {
        return "Task completed";
    }
}
public class CallableExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Task());
        if(future.isDone()) {
            System.out.println("Task is done - pre");
        }
        try {
            System.out.println(future.get()); // blocks the thread which executed this line because Future represents a result that may not exist yet, and get() is defined as a waiting operation.
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if(future.isDone()) {
            System.out.println("Task is done - post");
        }
        executor.shutdown();
    }
}

/*
* Future can complete without a value in 3 ways:
  1. Task throws an exception
  2. Task is cancelled
  3. Task returns void - Callable<Void>
  *
  Comparison table
  Feature	                execute()	submit()
  Returns Future	          ❌	      ✅
  Exception handling	Uncaught handler  Wrapped in Future
  Result	                  ❌	      ✅
  Track/cancel	              ❌	      ✅
  *
  *
  Why do exceptions “disappear” when using submit?
    - Because they are stored inside the Future and thrown only when get() is called.
    - All exceptions (checked or unchecked) are wrapped in ExecutionException
* */

/*
* UncaughtExceptionHandler is a last-resort error handler for exceptions that are:
   thrown inside a thread
   not caught anywhere in that thread
   If an exception escapes run() → this handler gets invoked.

   How execute() uses UncaughtExceptionHandler
   executor.execute(() -> {
       throw new RuntimeException("Boom");
   });

   What happens internally
     - Worker thread runs Runnable.run()
     - RuntimeException escapes run()
     - Executor does NOT catch it
     - Exception reaches thread boundary
     - JVM calls UncaughtExceptionHandler

   Same behavior as manual thread, and worker thread dies so executor service creates new thread.
* */