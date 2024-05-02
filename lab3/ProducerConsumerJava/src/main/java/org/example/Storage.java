package org.example;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

class Storage {
    private final Semaphore access = new Semaphore(1);
    private final Semaphore empty = new Semaphore(0);
    private final Semaphore full;
    public ArrayList<String> buffer = new ArrayList<>();

    public Storage(int capacity) {
        full = new Semaphore(capacity);
    }

    public void produce(String item) throws InterruptedException {
        full.acquire();
        access.acquire();
        buffer.add(item);
        System.out.println("Produced: " + item + " " + Thread.currentThread().getName());
        access.release();
        empty.release();
    }

    public String consume() throws InterruptedException {
        empty.acquire();
        access.acquire();
        String item = buffer.get(0);
        buffer.remove(0);
        System.out.println("Consumed: " + item + " " + Thread.currentThread().getName());
        access.release();
        full.release();
        return item;
    }
}