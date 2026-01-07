package UtilitiesProblems;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

// Key based locking problem
class OrderService {
    // One lock per restaurantId
    private final ConcurrentHashMap<Long, ReentrantLock> restaurantLocks =
            new ConcurrentHashMap<>();

    public void placeOrder(long restaurantId, long dishId) {

        // Atomically get or create lock for this restaurant
        ReentrantLock lock = restaurantLocks.computeIfAbsent(
                restaurantId,
                id -> new ReentrantLock()
        );

        lock.lock();
        try {
            // ---- CRITICAL SECTION ----
            System.out.println(
                    Thread.currentThread().getName() +
                            " placing order for dish " + dishId +
                            " at restaurant " + restaurantId
            );

            // Simulate inventory check + DB write
            Thread.sleep(1000);

            System.out.println(
                    Thread.currentThread().getName() +
                            " order placed successfully for restaurant " + restaurantId
            );
            // --------------------------
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();

            // Optional cleanup to avoid memory leak
            if (!lock.hasQueuedThreads()) {
                restaurantLocks.remove(restaurantId, lock);
            }
        }
    }
}
public class Problem1 {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();

        // Same restaurant → should execute sequentially
        Runnable r1 = () -> orderService.placeOrder(1L, 101L);
        Runnable r2 = () -> orderService.placeOrder(1L, 102L);

        // Different restaurant → can run in parallel
        Runnable r3 = () -> orderService.placeOrder(2L, 201L);

        new Thread(r1, "User-1").start();
        new Thread(r2, "User-2").start();
        new Thread(r3, "User-3").start();
    }
}
