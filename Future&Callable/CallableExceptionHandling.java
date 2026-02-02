import java.util.concurrent.*;

class ExceptionTask implements Callable<Integer> {
    public Integer call() throws Exception {
        return 1/0;
    }
}
public class CallableExceptionHandling {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new ExceptionTask());
        try {
            System.out.println(future.get());
        } catch (ExecutionException e) {
            System.out.println("Caught exception: " + e.getCause().getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}


/*
* Note: If any task throws an exception, then future.get() will throw an ExecutionException.
*       If any task is interrupted, then future.get() will throw an InterruptedException.
*       If any task is cancelled, then future.get() will throw a CancellationException.
* */