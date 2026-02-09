import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

// completable future is a better version of future.
public class BasicExample {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello World");
        System.out.println("Main thread is free");
        future.thenAccept(result -> System.out.println(result));
    }
}

/*
What happens
  - Task runs asynchronously
  - Main thread does NOT block
  - Callback executes when result is ready
* */

/*
* A CompletableFuture completes when it reaches a terminal state - meaning it's done, one way or another.
      Three Ways a CompletableFuture Can Complete:
              Completion Type	        Meaning	            Example
              Completed successfully	Has a result value	Returns "Hello"
              Completed exceptionally	Threw an exception	Throws RuntimeException
              Cancelled	Was cancelled	future.cancel(true)
All three are considered "completed" - the future is no longer running.
* */