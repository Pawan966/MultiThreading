package Concept.Problems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Order {
    private int orderId;
    public Order(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
}

class OrderProcessor implements Runnable {
    private Order order;
    public OrderProcessor(Order order) {
        this.order = order;
    }

    public void run() {
        System.out.println("Processing order: " + order.getOrderId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // log the exception
        }
    }
}

// Process 100 orders using a pool of 10 threads with graceful shutdown
public class Problem1 {
    public static  void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executor.execute(new OrderProcessor(new Order(i)));
        }
        executor.shutdown();
    }
}
