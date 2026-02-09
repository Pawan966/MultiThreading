import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Order {
    int id;
    String status;

    Order(int id, String status) {
        this.id = id;
        this.status = status;
    }
}
public class ChainingAsync {
    // Custom executors
    static ExecutorService apiExecutor = Executors.newFixedThreadPool(4);
    static ExecutorService dbExecutor  = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws Exception {

        CompletableFuture<Void> pipeline =
                CompletableFuture
                        // STEP 1: External API call
                        .supplyAsync(() -> callExternalApi(), apiExecutor)

                        // STEP 2: Cheap parsing (no executor needed, but async shown for clarity)
                        .thenApplyAsync(response -> {
                            return parse(response);
                        })

                        // STEP 3: Blocking DB call (MUST use dbExecutor)
                        .thenApplyAsync(parsed -> saveToDb(parsed), dbExecutor)

                        // STEP 4: Notification (API executor again)
                        .thenAcceptAsync(result -> sendNotification(result), apiExecutor);

        // Wait for completion (only for demo)
        pipeline.join();

        // Shutdown executors
        apiExecutor.shutdown();
        dbExecutor.shutdown();
    }

    // ------------------ Methods ------------------

    static String callExternalApi() {
        log("Calling external API");
        sleep(1000);
        return "{ \"orderId\": 123, \"status\": \"PAID\" }";
    }

    static Order parse(String response) {
        log("Parsing response");
        sleep(100);
        return new Order(123, "PAID");
    }

    static Boolean saveToDb(Order order) {
        log("Saving to DB");
        sleep(1500);
        return true;
    }

    static void sendNotification(Boolean saved) {
        log("Sending notification, DB saved = " + saved);
        sleep(500);
    }

    // ------------------ Utilities ------------------

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static void log(String msg) {
        System.out.println(
                Thread.currentThread().getName() + " | " + msg
        );
    }
}


/*
* thenApply runs in the same thread that completed the previous stage.
  thenApplyAsync runs in a different thread (from a thread pool) -> this is useful because with it you can pass custom executor service unlike thenApply().
  In a CompletableFuture pipeline, cheap transformations should run inline, while blocking or slow operations must be offloaded using thenApplyAsync with a dedicated executor to avoid thread starvation.
* */