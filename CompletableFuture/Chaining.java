import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

class Task1 implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "Task 1 completed";
    }
}

class Task2 implements Callable<String> {
    private String input;
    public Task2(String input) {
        this.input = input;
    }
    @Override
    public String call() throws Exception {
        return input + " Task 2 completed";
    }
}

public class Chaining {
    public static void main(String[] args) {
        // Using Callable with CompletableFuture
        // Wrap Callable.call() inside supplyAsync lambda
        CompletableFuture.supplyAsync(() -> { // this lambda runs in a background thread
            try {
                return new Task1().call(); // this call() runs in the same background thread
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })
        .thenApply(result -> { // this lambda runs in the same background thread
            try {
                return new Task2(result).call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })
        .thenAccept(result -> System.out.println(result))// this lambda runs in the same background thread and returns CompletableFuture<Void> hence you can still chain operations.
        .join(); // Wait for completion before main exits
    }
}

/* Task2's lambda doesn't execute until Task1 is done. This is asynchronous waiting (not blocking the main thread).
   thenAccept() is a terminal operation that consumes the result without returning anything
   thenRun() is a terminal operation that doesn't consume the result and doesn't return anything
*/

