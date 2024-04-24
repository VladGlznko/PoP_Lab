import java.util.concurrent.Semaphore;

public class Main {
    private static final int numPhilosopher = 5;
    private static final int NUM_MEALS = 10;

    public static int getNumMeals() {
        return NUM_MEALS;
    }
    private static final Semaphore[] forks = new Semaphore[numPhilosopher];

    public static void main(String[] args) {
         for (int i = 0; i < numPhilosopher; i++) {
            forks[i] = new Semaphore(1);
        }

        Thread[] philosophers = new Thread[numPhilosopher];
        for (int i = 0; i < numPhilosopher -1; i++) {
            philosophers[i] = new Thread(new Philosopher(i, forks[i], forks[(i+1)% numPhilosopher]));
            philosophers[i].start();
        }
        philosophers[numPhilosopher -1] = new Thread(new Philosopher(numPhilosopher -1,
                forks[(numPhilosopher)% numPhilosopher], forks[numPhilosopher -1]));
        philosophers[numPhilosopher -1].start();
        try {
            for (int i = 0; i < numPhilosopher; i++) {
                philosophers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}