import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static java.lang.Thread.sleep;

class SideEffectTask implements Runnable {
    public void run() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Side effect task");
    }
}

class HelloWorldTask implements Callable<String> {
    @Override
    public String call() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello World";
    }
}

public class RunVsSupply {
    public static void main(String[] args) {
        // runAsync (no return value)
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(new SideEffectTask());

        // supplyAsync (return value)
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            return new HelloWorldTask().call();
        });
        cf2.thenAccept(result -> System.out.println(result));

        // Wait for the entire chain
        // allOf() creates a new combined CompletableFuture that completes when all these complete
        // join() blocks until the combined future completes.
        CompletableFuture.allOf(cf1, cf2).join();
    }
}

/*
   CompletableFuture Uses Daemon Threads
   By default, CompletableFuture.runAsync() and CompletableFuture.supplyAsync() use the ForkJoinPool.commonPool(),
   which uses daemon threads which JVM kills as soon as main thread ends.
   So if we don't use join then main thread will end and daemon threads will be terminated, hence no nothing will be printed.
*/


