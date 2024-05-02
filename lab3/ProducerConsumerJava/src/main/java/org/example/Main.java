package org.example;

public class Main {

    public static void main(String[] args) {
        int storageCapacity = 3;
        int itemCount = 12;
        int consumerCount = 4;
        int producerCount = 2;

        Storage storage = new Storage(storageCapacity);
        for(int i = 0; i < consumerCount; i++){
            Thread consumerThread = new Thread(new Consumer(storage, (itemCount / consumerCount)));
        }
        for(int i = 0; i < producerCount; i++){
            Thread producerThread = new Thread(new Producer(storage, (itemCount / producerCount) ));
        }
    }
}
