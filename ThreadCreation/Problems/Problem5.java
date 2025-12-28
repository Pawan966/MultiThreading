package Problems;

/*
* Build a Mini Multi-threaded Logger
Create 3 daemon threads that write different log levels (INFO, DEBUG, ERROR) every second.
Main thread ends in 5 seconds. Observe how daemon threads behave.
* */
class LoggerTask implements Runnable {
    private String logLevel;
    public LoggerTask(String logLevel) {
        this.logLevel = logLevel;
    }

    public void run() {
        while (true) {
            System.out.println(logLevel + ": Log message");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // log the exception
            }
        }
    }
}
public class Problem5 {
    public static void main(String[] args) {
        Thread infoThread = new Thread(new LoggerTask("INFO"));
        Thread debugThread = new Thread(new LoggerTask("DEBUG"));
        Thread errorThread = new Thread(new LoggerTask("ERROR"));

        infoThread.setDaemon(true);
        debugThread.setDaemon(true);
        errorThread.setDaemon(true);

        infoThread.start();
        debugThread.start();
        errorThread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // log the exception
        }
    }
}
