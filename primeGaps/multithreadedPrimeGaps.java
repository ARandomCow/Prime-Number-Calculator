package primeGaps;

import java.util.Scanner;


public class multithreadedPrimeGaps {

    public static void main(String[] args) {

        final long startTime = System.currentTimeMillis();

        int primeMax = 1_000_000_000;
        int numOfPrimes = 51_000_000;
        // its the number of primes - 1 (because this algorithm doesnt worry about 2 or
        // multiples of it)
        // if primeMax = 100, numOfPrimes = 24
        // if 1,000 then 167
        // if 10,000 then 1228
        // if 1,000,000, then  78,497
        // if 100,000,000, then 5,761,454
        // if primeMax = 1,000,000,000 then numOfPrimes = 50,847,533
        // --------------------------------------------------------------------------------------------------

        sieveGapMethods erat = new sieveGapMethods(primeMax);

        short[] primeArray = erat.createGapArray(numOfPrimes);
        int thingy =2;
        for(int i = 0; i< 100; i++){
            thingy += primeArray[i];
            System.out.println(thingy);
        }
        // for(int i =0; i<primeArray.length; i++){
        // System.out.println(primeArray[i]);
        // }

        System.out.println("Base prime array counted");

        final long firstEndTime = System.currentTimeMillis();
        System.out.println("Initial execution time: " + (firstEndTime - startTime) + " milliseconds");

        long start = 0;
        int add = 1_000_000;
        int newPrimeLength = 100_000;
        // optimal numOfThreads ~ the amount of cores you CPU has (for my laptop I used 5 threads)
        int numOfThreads = 1;
        long startDifference = 10_000_000_000L;

        multithreadGapMethods threadi = new multithreadGapMethods(start, 1, primeArray,
                newPrimeLength, 1, numOfThreads, startDifference);
        Thread t1 = new Thread(threadi);
<<<<<<< Updated upstream
        String thing = "beans";
        int something = 0;
        while (!thing.equals("STOP")) {

            for (int i = something; i < numOfThreads+something; i++) {
=======
        int totalThreadsRun = 0;
        while (totalThreadsRun <= 0) {
            long intervalStartTime = System.currentTimeMillis();
            for (int i = totalThreadsRun; i < numOfThreads+totalThreadsRun; i++) {
>>>>>>> Stashed changes
                threadi = new multithreadGapMethods(start + (startDifference * i),
                        add, primeArray, newPrimeLength, i, numOfThreads, startDifference);
                t1 = new Thread(threadi);
                t1.start();
            }

            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            totalThreadsRun += numOfThreads;

            long intervalTime = System.currentTimeMillis();
            System.out.println("time elapsed for " + totalThreadsRun + " threads: " + (intervalTime - intervalStartTime) + " milliseconds");
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total Time Elapsed: " + (endTime - startTime));
    }


}

