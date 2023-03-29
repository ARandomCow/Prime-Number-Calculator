package primeGaps;

import primeGaps.*;

public class multithreadedPrimeGaps {

    public static void main(String[] args) {

        final long startTime = System.currentTimeMillis();

        int primeMax = 1_000_000_000;
        int numOfPrimes = 51_000_000;
        // if primeMax = 100, numOfPrimes = 24
        // if 1,000 then 167
        // if 10,000 then 1228
        // if 1,000,000, then  78,497
        // if 100,000,000, then 5,761,454
        // if primeMax = 1,000,000,000 then numOfPrimes = 50,847,533
        // --------------------------------------------------------------------------------------------------

        sieveGapMethods erat = new sieveGapMethods(primeMax); 


        short[] primeArray = erat.createGapArray(numOfPrimes);

        System.out.println("Base prime array counted");

        System.out.println("Testing");

//        int prime =2;
//        for (short gap: primeArray){
//            prime += gap;
//            System.out.println(prime);
//        }

        final long firstEndTime = System.currentTimeMillis();
        System.out.println("Initial execution time: " + (firstEndTime - startTime) + " milliseconds");

        long start = 0;
        int add = 1_000_000;
        int newPrimeLength = 100_000;
        int numOfThreads = 1;
        long startDifference = 10_000_000_000L;

        multithreadGapMethods threadi = new multithreadGapMethods(start, 1, primeArray,
                newPrimeLength, 1, numOfThreads, startDifference);
        Thread t1 = new Thread(threadi);

        int totalThreadsRun = 0;
        while (totalThreadsRun <= 0) {
            long intervalStartTime = System.currentTimeMillis();

            for (int threadNum = totalThreadsRun; threadNum < numOfThreads+totalThreadsRun; threadNum++) {
                threadi = new multithreadGapMethods(start + (startDifference * threadNum),
                        add, primeArray, newPrimeLength, threadNum, numOfThreads, startDifference);
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
        System.out.println("Total Time Elapsed: " + (endTime - startTime) + " milliseconds");
    }


}

