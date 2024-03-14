package org.example;
public class Main {
    public static void main(String[] args) {
        BreakThread breakThread = new BreakThread();
        int numberOfThreads = 3;

        MainThread[] threads = new MainThread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new MainThread(i + 1, breakThread, i + 2);
            threads[i].start();
        }

        new Thread(breakThread).start();
    }
}