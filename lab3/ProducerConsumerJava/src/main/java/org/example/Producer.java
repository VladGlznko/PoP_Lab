package org.example;

class Producer implements Runnable {
    private final Storage storage;
    private final int itemCount;

    public Producer(Storage storage, int itemCount) {
        this.storage = storage;
        this.itemCount = itemCount;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < itemCount; i++) {
            try {
                storage.produce("Item " + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
