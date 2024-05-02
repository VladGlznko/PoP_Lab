package org.example;

class Consumer implements Runnable {
    private final Storage storage;
    private final int itemCount;

    public Consumer(Storage storage, int itemCount) {
        this.storage = storage;
        this.itemCount = itemCount;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < itemCount; i++) {
            try {
                storage.consume();
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
