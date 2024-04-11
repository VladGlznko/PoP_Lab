package com.company;

import java.util.Random;

public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        arr = RandomInsert();;
        this.threadNum = threadNum;
    }
    private int[] RandomInsert(){
        Random r = new Random();
        int minNum = -10;
        int minIndex = r.nextInt(0,dim);
        int[] arr = new int[dim];
        for(int i = 0; i < dim; i++){
            if(i == minIndex){
                arr[i] = minNum;
            }
            else{
                arr[i] = r.nextInt(0,20);
            }
        }
        return arr;
    }
    public int[] partMin(int startIndex, int finishIndex){
        int[] minNum = new int[2];
        minNum[0] = arr[0];
        for(int i = startIndex; i < finishIndex; i++){
            if(arr[i] < minNum[0]){
                minNum[0] = arr[i];
                minNum[1] = i;
            }
        }
        return minNum;
    }

    private int[] min = new int [2];


    synchronized private int[] getMin() {
        while (getThreadCount()<threadNum){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return min;
    }

    synchronized public void collectMin(int[] min){
        if(this.min[0] > min[0]){
            this.min = min;
        }
    }

    private int threadCount = 0;
    synchronized public void incThreadCount(){
        threadCount++;
        notify();
    }

    private int getThreadCount() {
        return threadCount;
    }

    public int[] threadMin(){
        ThreadMin[] threadMin = new ThreadMin[threadNum];
        int chunkSize = dim / threadNum;
        int startIndex = 0;
        int endIndex;

        for (int i = 0; i < threadNum - 1; i++) {
            endIndex = startIndex + chunkSize;
            threadMin[i] = new ThreadMin(startIndex, endIndex, this);
            threadMin[i].start();
            startIndex = endIndex;
        }

        threadMin[threadNum - 1] = new ThreadMin(startIndex, dim, this);
        threadMin[threadNum - 1].start();

        return getMin();
    }
}
