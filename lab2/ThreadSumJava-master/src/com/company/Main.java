package com.company;

public class Main {
    public static void main(String[] args) {
        int dim = 10000;
        int threadNum = 9;
        ArrClass arrClass = new ArrClass(dim, threadNum);
        int[] result = arrClass.partMin(0,dim);
        System.out.println("Min num: " + result[0] + " Index: " + result[1]);

        int[] resultThread = arrClass.threadMin();
        System.out.println("Min num: " + resultThread[0] + " Index: " + resultThread[1]);
    }
}
