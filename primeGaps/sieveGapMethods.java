package primeGaps;

import java.lang.Math;
import java.util.Arrays;

public class sieveGapMethods {
    int num;
    int startCount;
    int intervalCount;
    long totalCount;
    short[] gapArray;


    // ignore
    public sieveGapMethods() {
        num = 10000;
        totalCount = 0;
    }

    //create new object with a built in array
    public sieveGapMethods(short[] arrayOfGaps) {
        gapArray = arrayOfGaps;
    }

    //create new object to build a starting array
    public sieveGapMethods(int startingNum) {
        num = startingNum;
        totalCount = 1;
    }

    // returns the amount of calculated primes. . .theoretically at least
    public int getStartCount() {
        return startCount;
    }

    public int getIntervalCount() {
        return intervalCount;
    }

    public long getTotalCount() {
        return totalCount;
    }


    // returns a prime list from 0 to startingNum
    public short[] createGapArray(int numOfGaps) {
        startCount = 1;
        int currentLargestPrimeIndex = 1;
        short[] gapArray = new short[(numOfGaps)];

        // boolean list for all odd numbers
        // if index = n, actual number = 2n+1
        boolean[] falseIfPrime = new boolean[(num + 1) / 2];
        falseIfPrime[0] = true;
        gapArray[0] = 1;

        // bool[0] is true because 1 is not prime

        // starting with prime = 3

        // if (number = prime){
        // all multiples of number are now not prime
        // }
        // repeat for all odd ints below sqrt(n)

        for (int oddIndex = 1; 2 * oddIndex + 1 < (int) (Math.sqrt(num)) + 1; oddIndex++) {
            if (!falseIfPrime[oddIndex]) {
                for (int compositeIndex = ( 2*oddIndex * (oddIndex+1));
                     compositeIndex < (num + 1) / 2;
                     compositeIndex += (2 * oddIndex) + 1) {
                    falseIfPrime[compositeIndex] = true;
                }
            }
        }

        falseIfPrime[1] = true;
        // if(number = prime)
        // put number in primeArray
        for (int oddIndex = 2; oddIndex < ((num + 1) / 2); oddIndex++) {
            if (!falseIfPrime[oddIndex]) {
                gapArray[startCount] = (short)((oddIndex - currentLargestPrimeIndex)*2);
                currentLargestPrimeIndex = oddIndex;
                startCount++;
//                totalCount++;
            }
        }

        // print prime list
        /*
         * for(int n =0; n<count; n++)
         * {
         * System.out.println(primeArray[n]);
         * }
         *
         */
        return Arrays.copyOf(gapArray, startCount);
    }

    // returns an array of primes in between start and start+add
    // be warned the array is too big so the last multiple entries are going to be 0
    //use sieve.getIntervalCount to find the true amount of primes so that you dont print 0's
    public short[] sieveFindInterval(long start, int add, short[] gapArray, int numOfNewGaps) {
        long prime = 2;
        long multiple;
        short[] gapIntervalArray = new short[(numOfNewGaps)];
        boolean startIsZero = false;
        boolean[] intervalFalseIfPrime = new boolean[(int) ((add + 1) / 2)];
        long basePrime = findBasePrime(start, gapArray);
        int baseIndex;
        int biggestNeededPrime = (int) Math.sqrt(start + add) + 1;

        if (start == 0){
            startIsZero = true;
            intervalFalseIfPrime[0] = true;
            intervalFalseIfPrime[1] = true;
            gapIntervalArray[0] = 1;
            intervalCount = 1;
            totalCount++;
            baseIndex = 1;
        } else {
            baseIndex = (int)((basePrime - start-1)/2);
            intervalCount = 0;
        }

        for (short gap : gapArray)
        {
            prime += gap;
            if (prime > biggestNeededPrime) {
                break;
            }


            if (startIsZero){
                multiple = prime*prime;
            } else{
                multiple = (start - ((start) % prime)) + prime;
                if (multiple % 2 == 0) {
                    multiple += prime;
                }
            }

            for (long composite = multiple; composite < start + add; composite += 2 * prime) {
                intervalFalseIfPrime[(int) (composite - start - 1) / 2] = true;
            }
        }




        for (int oddIndex = 0; oddIndex < intervalFalseIfPrime.length ; oddIndex++) {
            if (!intervalFalseIfPrime[oddIndex]) {
                gapIntervalArray[intervalCount] = (short) ((oddIndex - baseIndex)*2);
                baseIndex = oddIndex;
                intervalCount++;
                totalCount++;
            }
        }


        return Arrays.copyOf(gapIntervalArray, intervalCount);
    }


    public static long findBasePrime(long start, short[] gapArray){
//        System.out.println("findBasePrime is working");
        if (start == 0){
            return 2;
        }
        long numToTest = start;
        for (int i = 0; i< 1000; i++){
            if (isPrime(numToTest, gapArray)){
//                System.out.println("Base prime: " + numToTest);
                return numToTest;
            }
//            System.out.println(numToTest + " is not prime");
            numToTest--;
        }
        return 3;
    }

    public static boolean isPrime(long number, short[] gapArray){
//        System.out.println("isPrime is working");
        long prime = 2;
        if (number%prime== 0) {
//            System.out.println(number +" is a multiple of 2");
            return false;
        }
        long biggestPrime = (long)Math.sqrt(number);
//        System.out.println("biggestPrime = " + biggestPrime);
        for (short gap: gapArray)
        {
            prime += gap;
//            System.out.println("prime now equals " + prime);
            if (number%prime == 0){
                return false;
            }
            if (prime > biggestPrime){
                return true;
            }
        }
//        System.out.println(number + " is prime");
        return true;
    }

}
