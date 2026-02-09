package ExceptionHandling;

import java.util.concurrent.CompletableFuture;

/*
* If any stage throws an exception, the future becomes exceptionally completed
  and normal stages after it do NOT run.
  Recovers from exception

  CompletableFuture.supplyAsync(() -> {
    throw new RuntimeException("Error!");
  })
  .thenApply(x -> x * 2)  // ❌ This will NOT run
  .thenApply(x -> x + 1)  // ❌ This will NOT run
  .join();  // ❌ Throws exception

  So Java gives you three hooks to deal with this:
    - exceptionally
    - handle
    - whenComplete
* */
public class Exceptionally {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
                    System.out.println("Stage 1");
                    throw new RuntimeException("Error in stage 1");
                })
                .exceptionally(ex -> {
                    System.out.println("Caught: " + ex.getMessage());
                    return 10;  // Recover
                })
                .thenApply(x -> {
                    System.out.println("Stage 2: " + x);
                    throw new RuntimeException("Error in stage 2");
                })
                .exceptionally(ex -> {
                    System.out.println("Caught: " + ex.getMessage());
                    return 20;  // Recover again
                })
                .thenApply(x -> {
                    System.out.println("Stage 3: " + x);
                    return (int)x * 2;
                })
                .thenAccept(result -> System.out.println("Final: " + result))
                .join();
    }
}

/*
* What happened
   - API failed
   - exceptionally caught the exception
   - Returned a value
   - Pipeline continued normally
* */