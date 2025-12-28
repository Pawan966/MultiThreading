package CreationProblems;

// creating two threads and printing odd and even numbers
public class Problem1 {
    public static void main(String[] args) {
        Thread even = new Thread(() -> {
            for (int i = 0; i < 10; i += 2) {
                System.out.println(i);
            }
        });

        Thread odd = new Thread(() -> {
            for (int i = 1; i < 10; i += 2) {
                System.out.println(i);
            }
        });

        even.start();
        odd.start();
    }
}
