package Concept.Problems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*Print Task Name with Thread Name
  Submit 5 tasks to a pool of 2 threads.
  Each task prints its name and the thread it's running on.
* */
class NameTask implements Runnable {
    private String taskName;
    public NameTask(String taskName) {
        this.taskName = taskName;
    }

    public void run() {
        System.out.println(taskName + " is running on " + Thread.currentThread().getName());
    }
}
public class Problem3 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for(int i=0; i<5; i++) {
            executor.execute(new NameTask("Task-" + i));
        }
        executor.shutdown();
    }
}
