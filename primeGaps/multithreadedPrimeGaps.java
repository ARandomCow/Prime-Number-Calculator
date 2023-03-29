package primeGaps;

import static primeGaps.shortWheelFactoring.*;

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

        short[] indexWheelMod6 = new short[]{0,2};
        short[] indexWheelMod30 = biggerIndexWheel(indexWheelMod6, 6);
        short[] indexWheelMod210 = biggerIndexWheel(indexWheelMod30, 30);
        short[] indexWheelMod2310 = biggerIndexWheel(indexWheelMod210, 210);
        short[] indexWheelMod30030 = biggerIndexWheel(indexWheelMod2310, 2310);
        int[] indexWheelMod510510 = biggerIndexWheelInt(indexWheelMod30030, 30030);
        int[] indexWheelMod9699690 = biggerIndexWheelInt(indexWheelMod510510, 510510);
        short[] gapWheelMod223092870 = biggerIndexGapWheel(indexWheelMod9699690, 9699690);

        short[] primeArray = erat.createGapArray(numOfPrimes, gapWheelMod223092870, 223092870);

        System.out.println("Base prime array counted");

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
        long startDifference = 1_000_000_000L;

        multithreadGapMethods threadi = new multithreadGapMethods(start, 1, primeArray,
                newPrimeLength, 1, numOfThreads, startDifference, gapWheelMod223092870,
                223092870);
        Thread t1 = new Thread(threadi);

        int totalThreadsRun = 0;
        while (totalThreadsRun <= -1) {
            long intervalStartTime = System.currentTimeMillis();

            for (int threadNum = totalThreadsRun; threadNum < numOfThreads+totalThreadsRun; threadNum++) {
                threadi = new multithreadGapMethods(start + (startDifference * threadNum),
                        add, primeArray, newPrimeLength, threadNum, numOfThreads, startDifference,
                        gapWheelMod223092870, 223092870);
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

