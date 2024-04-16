package com.company;

public class Main {
    public static void main(String[] args) {
        int dim = 10000;
        int threadNum = 12;
        ArrClass arrClass = new ArrClass(dim, threadNum);
        
     
        long startTime = System.nanoTime();
        
        int[] result = arrClass.partMin(0, dim);
        System.out.println("Min num: " + result[0] + " Index: " + result[1]);

        int[] resultThread = arrClass.threadMin();
        System.out.println("Min num: " + resultThread[0] + " Index: " + resultThread[1]);
        
 
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        double seconds = (double) elapsedTime / 1_000_000_000.0;
        
        System.out.println("Execution Time: " + seconds + " seconds");
    }
}
