package twosemestr.philosophers;

import java.util.concurrent.Semaphore;

/**
 * Created by User on 28.05.2017.
 */
public class Philosophers {
    public static void main(String[] args) {
        int philosopher = 4;
        Semaphore[] s = new Semaphore[philosopher];
        for (int i = 0; i < philosopher; i++) {
            s[i] = new Semaphore(1);
        }
        for (int i = 0; i < philosopher; i++) {
            try {
                new Philosopher(s[i + 1], s[i], i + 1).start();
            } catch (ArrayIndexOutOfBoundsException e) {
                new Philosopher(s[i], s[0], i + 1).start();
            }
        }
    }
}

class Philosopher extends Thread {

    private boolean hungry = true;
    private Semaphore left;
    private Semaphore right;
    private int id;

    public Philosopher(Semaphore left, Semaphore right, int id) {
        this.left = left;
        this.right = right;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            if (hungry) {
                System.out.println("Философ " + id + " начинает.");
                left.acquire();
                Thread.sleep(1000);
                System.out.println("Философ " + id + " взял левую вилку.");
                right.acquire();
                Thread.sleep(1000);
                System.out.println("Философ " + id + " взял правую вилку.");
                System.out.println("Философ " + id + " ест.");
                Thread.sleep(1000);
                hungry = false;
                System.out.println("Философ " + id + " поел.");
                right.release();
                System.out.println("Философ " + id + " кладет правую вилку.");
                left.release();
                System.out.println("Философ " + id + " кладет левую вилку.");
            }
        } catch (InterruptedException e) {
            System.err.println("Fail");
        }

    }
}
