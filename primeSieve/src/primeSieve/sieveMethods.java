package primeSieve;

import java.lang.Math;
import java.util.Arrays;

public class sieveMethods {
    int num;
    int startCount;
    int intervalCount;
    long totalCount;
    int[] primeArray;


    // ignore
    public sieveMethods() {
        num = 10000;
        totalCount = 0;
    }

    //create new object with a built in array
    public sieveMethods(int[] arrayOfPrimes) {
        primeArray = arrayOfPrimes;
    }

    //create new object to build a starting array
    public sieveMethods(int startingNum) {
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
    public int[] sieveOfEratosthenes(int numOfPrimes) {
        startCount = 0;
        int[] primeArray = new int[(numOfPrimes)];

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

        for (Integer oIndex = 1; 2 * oIndex + 1 < (int) (Math.sqrt(num)) + 1; oIndex++) {
            if (!boolOddArray[oIndex]) {

                for (int comIndex = (((2 * oIndex) + 1) * ((2 * oIndex) + 1) - 1) / 2; comIndex < (num + 1)
                        / 2; comIndex += (2 * oIndex) + 1) {
                    boolOddArray[comIndex] = true;
                }
            }
        }

        // if(number = prime)
        // put number in primeArray
        for (int oddIndex = 0; oddIndex < ((num + 1) / 2); oddIndex++) {
            if (!boolOddArray[oddIndex]) {
                primeArray[startCount] = 2 * oddIndex + 1;
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
        return primeArray;
    }

    // returns an array of primes in between start and start+add
    // be warned the array is too big so the last multiple entries are going to be 0
    //use sieve.getIntervalCount to find the true amount of primes so that you dont print 0's
    public long[] sieveFindInterval(long start, int add, int[] primeArray, int numOfNewPrimes) {



        boolean[] boolAddArray = new boolean[(int) ((add + 1) / 2)];
        for (Integer prime : primeArray) {
            if (prime > (int) Math.sqrt(start + add) + 1) {
                break;
            }

            long multiple = (start - ((start) % prime)) + prime;
            if (multiple % 2 == 0) {
                multiple += prime;
            }

            for (long composite = multiple; composite < start + add; composite += 2L * prime) {
                boolAddArray[(int) (composite - start - 1) / 2] = true;
            }
        }

        long[] primeList = new long[(numOfNewPrimes)];

        intervalCount = 0;

        for (Integer oddIndex = 0; oddIndex < boolAddArray.length ; oddIndex++) {
            if (!boolAddArray[oddIndex]) {
                primeList[intervalCount] = (2L * oddIndex) + 1 + start;
                intervalCount++;
                totalCount++;
            }
        }


        return Arrays.copyOf(primeList, intervalCount);
    }

}
