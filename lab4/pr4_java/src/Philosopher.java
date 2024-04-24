import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {
    final private int id;
    final  private Semaphore leftFork;
    final private Semaphore rightFork;
    final private Random random;
    final  private  int time;

    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.random = new Random();
        time =100;
    }

    public void run() {
        try {
            for (int i = 1; i<=Main.getNumMeals(); i++) {
                think();
                pickUpLeftFork();
                pickUpRightFork();
                eat();
                putDownRightFork();
                putDownLeftFork();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking");
        Thread.sleep(random.nextInt(time));
    }

    private void pickUpLeftFork() throws InterruptedException {
        leftFork.acquire();
        System.out.println("Philosopher " + id + " is picking up left fork");
    }

    private void pickUpRightFork() throws InterruptedException {
        rightFork.acquire();
        System.out.println("Philosopher " + id + " is picking up right fork");
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating");
        Thread.sleep(random.nextInt(time));
    }

    private void putDownRightFork() {
        System.out.println("Philosopher " + id + " is putting down right fork");
        rightFork.release();
    }

    private void putDownLeftFork() {
        System.out.println("Philosopher " + id + " is putting down left fork");
        leftFork.release();
    }
}
