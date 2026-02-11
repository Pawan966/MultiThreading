package UtilitiesProblems.semaphore;
import java.util.concurrent.Semaphore;

/*
* You have a service that can process at most 3 requests at a time.
  Other requests must wait.
* */
class RequestProcessor {
    private Semaphore semaphore = new Semaphore(3);

    public void processRequest(int requestId) {
        try {
            semaphore.acquire();
            // Process the request
            System.out.println("Processing request: " + requestId + " by " + Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // log the exception
        } finally {
            semaphore.release();
        }
    }
}

class RequestProcessorUser implements Runnable {
    private RequestProcessor requestProcessor;
    private int requestId;

    public RequestProcessorUser(RequestProcessor requestProcessor, int requestId) {
        this.requestProcessor = requestProcessor;
        this.requestId = requestId;
    }

    public void run() {
        requestProcessor.processRequest(requestId);
    }
}

public class Problem1 {
    public static void main(String[] args) {
        RequestProcessor requestProcessor = new RequestProcessor();
        for (int i = 0; i < 10; i++) {
            new Thread(new RequestProcessorUser(requestProcessor, i)).start();
        }
    }
}
