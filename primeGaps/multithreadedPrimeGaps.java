package primeGaps;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class multithreadedPrimeGaps {

    public static void main(String[] args) throws FileNotFoundException {

        final long billion = 1_000_000_000;

        Scanner input = new Scanner(System.in);

        System.out.println("Creating base prime array \n");
        System.out.println("What is the upper bound of your initial array of prime numbers?");
        int myPrimeMax = input.nextInt();

//        System.out.println("What is the number of primes you think is in this list?");
        int myNumOfPrimes = myPrimeMax/2;



        //--------------------------------------------------------------------------------------------------
        int primeMax = myPrimeMax;
        int numOfPrimes = myNumOfPrimes;
        // if primeMax = 100, numOfPrimes = 24
        // if 1,000 then 167
        // if 10,000 then 1228
        // if 1,000,000, then  78,497
        // if 100,000,000, then 5,761,454
        // if primeMax = 1,000,000,000 then numOfPrimes = 50,847,533
        // --------------------------------------------------------------------------------------------------

        final long startTime = System.currentTimeMillis();

        sieveGapMethods erat = new sieveGapMethods(primeMax);


        short[] primeArray = erat.createGapArray(numOfPrimes);

        System.out.println("Base prime array counted");

        int prime = 2;
//        for (short gap: primeArray){
//            prime += gap;
//            System.out.println(prime);
//        }

        final long firstEndTime = System.currentTimeMillis();
        System.out.println("Initial execution time: " + (firstEndTime - startTime) + " milliseconds");

        System.out.println("Creating full prime array \n");


//        System.out.println("What do you want the upper bound of your prime list to be?");
//        long newPrimeMax = input.nextInt();

        System.out.println("How many threads do you want to run?");
        int myNumOfThreads = input.nextInt();

        System.out.println("How big do you want each segmented sieve to be? " +
                "\n (pick like a million-ish)");
        int myIntervalSize = input.nextInt();

        System.out.println("What do you want the interval contained in each csv to be?" +
                "\n in multiples of a billion");
        long distanceBetweenThreads = input.nextLong() * billion;

        System.out.println("what is the upper bound of your prime numbers?" +
                "\n should be a multiple of the number of threads and the distance between threads" +
                "\n also in multiples of a billion");
        long upperBound = input.nextLong() * billion;



        long mainStartTime = System.currentTimeMillis();


        long start = 0;
        int intervalSize = myIntervalSize;
        int newPrimeLength = intervalSize / 2;
        int numOfThreads = myNumOfThreads;
        long startDifference = distanceBetweenThreads;
        int threadNumber = 1;
        int totalThreadsRun = 0;

            multithreadGapMethods threadi = new multithreadGapMethods(start, 1, primeArray,
                    newPrimeLength, threadNumber, numOfThreads, startDifference);
            Thread t1 = new Thread(threadi);


            while (totalThreadsRun < upperBound / (distanceBetweenThreads)) {
                long intervalStartTime = System.currentTimeMillis();

                for (int threadNum = totalThreadsRun; threadNum < numOfThreads + totalThreadsRun; threadNum++) {
                    threadi = new multithreadGapMethods(start + (startDifference * threadNum),
                            intervalSize, primeArray, newPrimeLength, threadNum, numOfThreads, startDifference);
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
        System.out.println("Total Time Elapsed: " + (endTime - mainStartTime) + " milliseconds");
    }


}

