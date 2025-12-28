package CreationProblems;

/*
* Daemon threads are background threads that do not prevent the JVM from exiting.
JVM exits once all user threads finish, even if daemon threads are running.
Example use cases: logging, monitoring, garbage collection
* */

/*
* Create Daemon Thread That Logs Continuously
Objective: Create a daemon thread that logs "Heartbeat..." every second.
Requirements:
Main thread should end after 3 seconds.
The daemon thread should stop when main exits.
* */
public class Problem2 {
    public static void main(String[] args) {
        Thread daemon = new Thread(() -> {
            while (true) {
                System.out.println("Heartbeat...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                   // log the exception
                }
            }
        });
        daemon.setDaemon(true);
        daemon.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // log the exception
        }
    }
}

/*
* In the above code, after main thread ends, the daemon thread also ends.
* But if we remove the daemon.setDaemon(true) line, the daemon thread will continue to run even after main thread ends.
* */
