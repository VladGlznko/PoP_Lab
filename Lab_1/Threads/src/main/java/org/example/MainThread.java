package org.example;

public class MainThread extends Thread{
    private final int id;
    private final BreakThread breakThread;
    private int step;

    public MainThread(int id, BreakThread breakThread, int step) {
        this.id = id;
        this.breakThread = breakThread;
        this.step = step;
    }

    @Override
    public void run() {
        long sum = 0;
        int counter = 0;
        boolean isStop = false;
        do{
            sum += step;
            counter++;
            isStop = breakThread.isCanBreak();
        } while (!isStop);
        System.out.println(id + " - " + sum + "-" + counter);
    }
}
