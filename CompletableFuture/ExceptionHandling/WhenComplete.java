package ExceptionHandling;

import java.util.concurrent.CompletableFuture;

/*
* whenComplete() is for side effects only (like logging).
  It does NOT change the result or recover from exceptions.
  Runs on both success/failure, CANNOT change result
  whenComplete() does NOT catch or suppress exceptions - they still propagate to join()
* */
public class WhenComplete {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
                    System.out.println("Stage 1: Fetch user");
                    return "user123";
                })
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("  [LOG] Stage 1 completed: " + result);
                    }
                })
                .thenApply(userId -> {
                    System.out.println("Stage 2: Fetch details for " + userId);
                    return "John Doe";
                })
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("  [LOG] Stage 2 completed: " + result);
                    }
                })
                .thenApply(userName -> {
                    System.out.println("Stage 3: Format name");
                    return userName.toUpperCase();
                })
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("  [LOG] Stage 3 completed: " + result);
                    }
                })
                .thenAccept(finalName -> {
                    System.out.println("Final: " + finalName);
                })
                .join();
    }
}

/*
* Comparison Table
Feature	                     join()	                                     get()
Exception type	         CompletionException (unchecked)	ExecutionException, InterruptedException (checked)
Try-catch required?	        ❌ No	                               ✅ Yes
Timeout support?	        ❌ No	                               ✅ Yes (get(timeout, unit))
Clean in lambdas?	        ✅ Yes	                               ❌ No (verbose)
Interruption handling?	    ❌ No explicit handling	               ✅ Yes (InterruptedException)
Use case	                Modern code, streams, lambdas	       When you need timeout or explicit interruption handling
* */