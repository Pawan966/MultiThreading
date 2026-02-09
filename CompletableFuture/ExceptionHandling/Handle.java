package ExceptionHandling;

import java.util.concurrent.CompletableFuture;

/*
* Handle is the most powerful.
  It runs on both success and failure
  and must return a value

  Recovers from exception
* */
public class Handle {
    public static void main(String[] args) {
                CompletableFuture.supplyAsync(() -> {
                            if (Math.random() > 0.5)
                                throw new RuntimeException("Random failure");
                            return 10;
                        })
                        .handle((result, ex) -> {
                            if (ex != null) {
                                System.out.println("Handling error: " + ex.getMessage());
                                return 0; // fallback
                            } else {
                                System.out.println("Handling success");
                                return result * 2;
                            }
                        })
                        .thenApply(x -> {
                            return x + 2;
                        })
                        .thenAccept(result -> System.out.println("Final: " + result))
                        .join();
    }
}

// we can use multiple handle as well
