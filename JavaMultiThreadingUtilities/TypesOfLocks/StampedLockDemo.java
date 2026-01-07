package TypesOfLocks;

/*
* StampedLock (Java 8+) is a high-performance lock designed for:
   - Read-heavy workloads
   - Situations where most operations are reads
   - You want less contention than ReadWriteLock
  Instead of giving you a Lock object, it returns a stamp (long value) that represents the lock state.

  StampedLock:
   - Introduces Optimistic Read
   - Avoids locking when no write occurs
   - Maximizes throughput
* */

import java.util.concurrent.locks.StampedLock;

class Point {
    private double x, y;
    private final StampedLock lock = new StampedLock();

    void move(double dx, double dy) {
        long stamp = lock.writeLock();  // exclusive
        try {
            x += dx;
            y += dy;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    double distanceFromOrigin() {
        long stamp = lock.readLock();   // shared
        try {
            return Math.sqrt(x * x + y * y);
        } finally {
            lock.unlockRead(stamp);
        }
    }

    double distanceFromOriginOptimistic() {
        long stamp = lock.tryOptimisticRead();
        double currentX = x, currentY = y;
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
public class StampedLockDemo {
    public static void main(String[] args) {
        Point point = new Point();
        point.move(3, 4);
        System.out.println(point.distanceFromOrigin());
        System.out.println(point.distanceFromOriginOptimistic());
    }
}

/*
* Important Limitations (Very Important!)
     ❌ Not Reentrant
        Same thread cannot re-acquire lock
        Leads to deadlock if misused
     ❌ No Condition
        Cannot wait/notify
     ❌ No fairness option
        Writer starvation possible
* */
