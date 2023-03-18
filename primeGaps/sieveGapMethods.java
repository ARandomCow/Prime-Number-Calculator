package primeGaps;

import java.lang.Math;
import java.util.Arrays;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

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
        totalCount = 0;
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
    public short[] sieveOfEratosthenes(int numOfGaps) {
        startCount = 0;
        int currentLargestPrime = 2;
        short[] gapArray = new short[(numOfGaps)];
        gapArray[0] = 2;

        // boolean list for all odd numbers
        // if index = n, actual number = 2n+1
        boolean[] boolOddArray = new boolean[(num + 1) / 2];
        boolOddArray[0] = true;

        // bool[0] is true because 1 is not prime

        // starting with prime = 3

        // if (number = prime){
        // all multiples of number are now not prime
        // }
        // repeat for all odd ints below sqrt(n)

        for (Integer oddIndex = 1; 2 * oddIndex + 1 < (int) (Math.sqrt(num)) + 1; oddIndex++) {
            if (!boolOddArray[oddIndex]) {
                for (int compositeIndex = (((2 * oddIndex) + 1) * ((2 * oddIndex) + 1) - 1) / 2;
                     compositeIndex < (num + 1) / 2;
                     compositeIndex += (2 * oddIndex) + 1) {
                    boolOddArray[compositeIndex] = true;
                }
            }
        }

        // if(number = prime)
        // put number in primeArray
        for (int oddIndex = 0; oddIndex < ((num + 1) / 2); oddIndex++) {
            if (!boolOddArray[oddIndex]) {
                gapArray[startCount] = (short)((2 * oddIndex + 1) - currentLargestPrime);
                currentLargestPrime += gapArray[startCount];
                startCount++;
                totalCount++;
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
//        System.out.println("sieveFindInterval is working");
        long basePrime = findBasePrime(start, gapArray);
//        System.out.println("basePrime = " + basePrime);
        int biggestNeededPrime = (int) Math.sqrt(start + add) + 1;

        boolean[] boolAddArray = new boolean[(int) ((add + 1) / 2)];
        for (short gap : gapArray)
        {
            prime += gap;
            if (prime > biggestNeededPrime) {
                break;
            }

            long multiple = (start - ((start) % prime)) + prime;
            if (multiple % 2 == 0) {
                multiple += prime;
            }

            for (long composite = multiple; composite < start + add; composite += 2 * prime) {
                boolAddArray[(int) (composite - start - 1) / 2] = true;
            }
        }

        short[] gapIntervalArray = new short[(numOfNewGaps)];

        intervalCount = 0;

        for (Integer oddIndex = 0; oddIndex < boolAddArray.length ; oddIndex++) {
            if (!boolAddArray[oddIndex]) {
                gapIntervalArray[intervalCount] = (short) ((2 * oddIndex) + 1 + start- basePrime);
                basePrime += gapIntervalArray[intervalCount];
                intervalCount++;
                totalCount++;
            }
        }


        return Arrays.copyOf(gapIntervalArray, intervalCount);
    }


    public static long findBasePrime(long start, short[] gapArray){
//        System.out.println("findBasePrime is working");
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
