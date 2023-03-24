package primeGaps;

import java.lang.Math;
import java.util.*;
import static primeGaps.shortWheelFactoring.*;

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
    public short[] createGapArray(int numOfGaps) {
        startCount = 1;
        int currentLargestPrimeIndex = 1;
        short[] gapArray = new short[(numOfGaps)];

        short[] wheelMod6 = makeWheel();
        short[] wheelMod30 = biggerWheel(wheelMod6, 6);
        short[] wheelMod210 = biggerWheel(wheelMod30, 30);
        short[] wheelMod2310 = biggerWheel(wheelMod210, 210);
        int[] wheelMod30030 = biggerIntWheel(wheelMod2310, 2310);
        int[] wheelMod510510 = biggerIntWheel(wheelMod30030, 30030);
//        int[] wheelMod9699690 = biggerIntWheel(wheelMod510510, 510510);

//        finally no longer efficient lol
//        int[] wheelMod223092870 = biggerIntWheel(wheelMod9699690, 9699690);

        int[] wheelInUse = wheelMod510510;
        int wheelMod = 510510;
        int wheelArrayLength = wheelInUse.length;
        // boolean list for all odd numbers
        // if index = n, actual number = 2n+1
        boolean[] oddIsPrime = new boolean[(num + 1) / 2];
//        these guys are here because the wheel does not count all the primes inside of it as primes
//        this is my jank solution for now
        oddIsPrime[2] = true;
        oddIsPrime[3] = true;
        oddIsPrime[5] = true;
        oddIsPrime[6] = true;
        oddIsPrime[8] = true;
        oddIsPrime[9] = true;

        gapArray[0] = 1;

//         bool[0] is true because 1 is not prime
//
//         starting with prime = 3
//
//         if (number = prime){
//         all multiples of number are now not prime
//         }
//         repeat for all odd ints below sqrt(n)


//        turns wheel mod 6 into the odd indexes to use less space
        int[] indexedWheel = new int[wheelArrayLength];
        int something = 0;
        for(int num: wheelInUse){
            indexedWheel[something] = (num-1)/2;
            something++;
        }

//        System.out.println("indexed wheel mod " + wheelMod);
//        for (short p: indexedWheel){
//            System.out.print(p + " | ");
//        }
//        System.out.println();
//        first lets see if i can roll a wheel and if that makes a difference
//        oh shit the wheel turns things to potential primes, not primes to composites
//        i gotta switch the booleans on everything
        int wheelRoll = 0;
        int indexedWheelMod = wheelMod/2;
//        wheel displacement will be needed for segmented sieve
//        when I start at like 1mil or whatever, 1mil%30 isnt 0 so I'll need to do some stuff
        int wheelDisplacement = 0;

//        this rolls the wheel along our thing from 0 to 1mil
        boolean done = false;
        while(!done){
            for (int primeIndex: indexedWheel){
                if ((primeIndex+(indexedWheelMod*wheelRoll))>= oddIsPrime.length){
                    done = true;
                    break;
                }
                oddIsPrime[primeIndex+(indexedWheelMod*wheelRoll)]=true;
            }
            wheelRoll++;
        }



//        create wheel gap thingy
        short[] indexedGapWheel = new short[wheelArrayLength];
        for(int i = 1; i<indexedGapWheel.length; i++){
            indexedGapWheel[i] =  (short)(indexedWheel[i]-indexedWheel[i-1]);
        }
        indexedGapWheel[0] = (short)(indexedWheelMod - indexedWheel[wheelArrayLength-1]);

//        System.out.println("indexed Gap Wheel mod " + wheelMod);
//        for(short indexGap: indexedGapWheel){
//            System.out.print(indexGap + " | ");
//        }
//        System.out.println();


//        gotta remake 1 to not prime
        oddIsPrime[0] = false;


//        this is where serious optimizations can be made
        short direction = 1;
        for (int oddIndex = 3; 2 * oddIndex + 1 < (int) (Math.sqrt(num)) + 1; oddIndex++) {
            if (oddIsPrime[oddIndex]) {
                int actualPrime = (2*oddIndex)+1;
                int compositeIndex = 2*oddIndex * (oddIndex+1);
                int counter = 0;
                for (int i = 0; i<wheelArrayLength; i++){
                    if((actualPrime%wheelMod)==wheelInUse[i]){
                        counter = i+1;
                        break;
                    }
                }

                while(compositeIndex<oddIsPrime.length){
                    oddIsPrime[compositeIndex] = false;
                    int nextGap = indexedGapWheel[counter%wheelArrayLength];
//                    System.out.print("Turning " + compositeIndex + " to ");
                    compositeIndex += actualPrime*nextGap;
//                    System.out.println(compositeIndex);
                    counter++;
                }
            }
        }

//        this makes 2 not prime so that i can have all even gaps i think
//        oddIsPrime[1] = false;

        // if(number = prime)
        // put number in primeArray
        for (int oddIndex = 2; oddIndex < ((num + 1) / 2); oddIndex++) {
            if (oddIsPrime[oddIndex]) {
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
        boolean[] boolAddArray = new boolean[(int) ((add + 1) / 2)];
        long basePrime = findBasePrime(start, gapArray);
        int baseIndex;
        int biggestNeededPrime = (int) Math.sqrt(start + add) + 1;

        if (start == 0){
            startIsZero = true;
            boolAddArray[0] = true;
            boolAddArray[1] = true;
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
                boolAddArray[(int) (composite - start - 1) / 2] = true;
            }
        }




        for (int oddIndex = 0; oddIndex < boolAddArray.length ; oddIndex++) {
            if (!boolAddArray[oddIndex]) {
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
