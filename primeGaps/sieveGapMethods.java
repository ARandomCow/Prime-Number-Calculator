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
    public short[] createGapArray(int numOfGaps, short[] gapWheelInUse, long wheelModulus) {
        startCount = 1;
        int currentLargestPrimeIndex = 1;
        short[] gapArray = new short[(numOfGaps)];
        int wheelArrayLength = gapWheelInUse.length;
        long timeCalculating = 0;

/*
        smallestPrimeToCheck only exists so that the sieve of Eratosthenes doesnt
        look at primes that have already 100% been knocked out by the wheel
*/
        short[] smallGapArray = new short[]{1, 2, 2, 4, 2, 4, 2, 4, 6, 2};
        int smallestPrimeIndexToCheck = 14;
        for(int i = 1; i<16; i++){
            if( ((wheelModulus%((2*i)+1))==0) && isPrime(((2*i)+1), smallGapArray) ){
                smallestPrimeIndexToCheck=i;
            }
        }
        smallestPrimeIndexToCheck++;

/*
        oddIsPrime = boolean list for all odd numbers
        if index = n, actual number = 2n+1
        oddIsPrime is initialized to all false because we initially that all odd numbers are not prime
        and then we used the wheel factorization to find possible primes

 */
        int boolLength = (num + 1) / 2;
        boolean[] oddIsPrime = new boolean[boolLength];
//        these guys are here because the wheel does not count all the primes inside of it as primes
//        this is my jank solution for now
        oddIsPrime[2] = true;
        oddIsPrime[3] = true;
        oddIsPrime[5] = true;
        oddIsPrime[6] = true;
        oddIsPrime[8] = true;
        oddIsPrime[9] = true;
        oddIsPrime[11] = true;
        oddIsPrime[14] = true;

        gapArray[0] = 1;



/*
        This here block of code basically rolls the wheel
        it finds every number n such that n%wheelMod is part of the factor wheel
        meaning that it can be prime
        and then it switches that number from false to true
        it will label some composite numbers as true
 */
        int probablePrime = gapWheelInUse[0]*-1;
        long safetyNet = boolLength-(wheelModulus/2);

//        first while loop gets like 99% of numbers in the interval without spending too much computation time checking
        while(probablePrime < safetyNet){
            for (short gapBetweenPrimes : gapWheelInUse) {
                probablePrime += gapBetweenPrimes;
                oddIsPrime[probablePrime] = true;
            }
        }

//        second loop checks if the prime is bigger than the interval more often
        for (short gapBetweenPrimes : gapWheelInUse){
                probablePrime += gapBetweenPrimes;
            if ((probablePrime) >= boolLength) {
                break;
            }
            oddIsPrime[probablePrime] = true;
        }

//        gotta remake 1 to not prime
        oddIsPrime[0] = false;



/*
        sieve of Eratosthenes
        it starts at the smallest prime not completely checked by the factor wheel
        it finds every multiple of that prime that was labeled as prime by the factor wheel
        it switches that boolean back to not prime
 */

        int biggestPrime = (int) (Math.sqrt(num)/2)+1;
        for (int oddIndex = smallestPrimeIndexToCheck; oddIndex < biggestPrime; oddIndex++) {
            if (!oddIsPrime[oddIndex]) {
                continue;
            }
            int actualPrime = (2*oddIndex)+1;
            int compositeIndex = 2*oddIndex * (oddIndex+1);
            int counter = 0;
            int indexModChecker = gapWheelInUse[0]*-1;

            for (int i = 0; i<wheelArrayLength; i++){
                indexModChecker+=gapWheelInUse[i];
                if((actualPrime%wheelModulus)== ((indexModChecker*2)+1)){
                    counter = i+1;
                    break;
                }
            }
            long sieveSafetyNet = boolLength - (actualPrime*wheelModulus);

//            long startTime = System.currentTimeMillis();

            boolean completelyDone = false;
            for(int i = counter; i < wheelArrayLength; i++){
                if (compositeIndex >= boolLength){
                    completelyDone = true;
                    break;
                }
                oddIsPrime[compositeIndex] = false;
                compositeIndex += actualPrime * gapWheelInUse[i];
            }

            while(compositeIndex < sieveSafetyNet){
                for(short gap: gapWheelInUse){
                    oddIsPrime[compositeIndex] = false;
                    compositeIndex += actualPrime*gap;
                }
            }

            while (!completelyDone) {
                for (short gap : gapWheelInUse) {
                    if (compositeIndex >= boolLength) {
                        completelyDone = true;
                        break;
                    }
                    oddIsPrime[compositeIndex] = false;
                    compositeIndex += (actualPrime * gap);
                }
            }
//            long endTime = System.currentTimeMillis();
//            timeCalculating += (endTime - startTime);
        }


/*
        if(number == prime)
        put number in primeArray
        it starts on oddIndex=2 because when I say that gapArray[0]=1,
        im already saying that 2 and 3 are prime, so i start at 5
*/

        for (int oddIndex = 2; oddIndex < boolLength; oddIndex++) {
            if (oddIsPrime[oddIndex]) {
                gapArray[startCount] = (short)((oddIndex - currentLargestPrimeIndex)*2);
                currentLargestPrimeIndex = oddIndex;
                startCount++;
            }
        }

//        System.out.println("Time spent on that one block of code = " + timeCalculating);
        return Arrays.copyOf(gapArray, startCount);
    }



    // returns an array of primes in between start and start+add
    public short[] sieveFindInterval(long start, int add, short[] gapArray,
                                     int numOfNewGaps, short[] gapWheelInUse, int wheelModulus){
        if (start == 0){
            sieveGapMethods firstThing = new sieveGapMethods(add);
            short[] result = firstThing.createGapArray(numOfNewGaps, gapWheelInUse, wheelModulus);
            totalCount += firstThing.getStartCount();
            intervalCount = firstThing.getStartCount();
            return result;
        }

        long prime = 2;
        long multiple;
        short[] gapIntervalArray = new short[(numOfNewGaps)];
        boolean[] oddIsPrimeInterval = new boolean[(int) ((add + 1) / 2)];
        long basePrime = findBasePrime(start, gapArray);
        int baseIndex = (int)((basePrime - start-1)/2);
        long biggestNeededPrime = (long) Math.sqrt(start + add) + 1;
        int boolLength = oddIsPrimeInterval.length;
        int wheelArrayLength = gapWheelInUse.length;
        intervalCount = 0;
        int calcTime = 0;
/*
        smallestPrimeToCheck only exists so that the sieve of Eratosthenes doesnt
        look at primes that have already 100% been knocked out by the wheel
*/
        short[] smallGapArray = new short[]{1, 2, 2, 4, 2, 4, 2, 4, 6, 2};
        int smallestPrimeIndexToCheck = 1;
        for(int i = 1; i<35; i+=2){
            if(((wheelModulus%i)==0) && isPrime(i, smallGapArray)){
                smallestPrimeIndexToCheck=i;
            }
        }
        smallestPrimeIndexToCheck++;

/*
        rolls the wheel along the interval
        first finds where the starting number lies on the wheel
        then uses that to find the first probable prime
        and rolls the wheel from the initial starting point all the way along the interval
 */
//        long rollingStartTime = System.currentTimeMillis();

        int whereTheFirstNumIsOnTheWheel = (int)(start%wheelModulus);
        int actualWheelNum = -1;
        int firstGapIndex = 1;

        for(short gap: gapWheelInUse){
            actualWheelNum += 2*gap;
            if (actualWheelNum > whereTheFirstNumIsOnTheWheel){
                break;
            }
            firstGapIndex++;
        }

        int indexDistanceBetweenStartAndFirstWheelNum = (actualWheelNum - whereTheFirstNumIsOnTheWheel-1)/2;
        int probablePrime = indexDistanceBetweenStartAndFirstWheelNum;

/*
        This rolls the wheel once starting at some point and rolling to a wheel position of 0
 */
        for(int gapIndex = firstGapIndex; gapIndex < wheelArrayLength; gapIndex++){
            oddIsPrimeInterval[probablePrime] = true;
            probablePrime += gapWheelInUse[gapIndex];
        }
        oddIsPrimeInterval[probablePrime] = true;

/*
        This rolls the wheel without any costly if statements and gets us like 90% of the way there
 */
        int safetyNet = boolLength - (wheelModulus/2);
        while(probablePrime < safetyNet){
            for(short gap: gapWheelInUse){
                probablePrime += gap;
                oddIsPrimeInterval[probablePrime] = true;
            }
        }

/*
        This rolls the wheel to the very end of the list
 */
        for (short gapBetweenPrimes : gapWheelInUse){
            probablePrime += gapBetweenPrimes;
            if ((probablePrime) >= boolLength) {
                break;
            }
            oddIsPrimeInterval[probablePrime] = true;
        }

//        long rollingEndTime = System.currentTimeMillis();
//        calcTime += rollingEndTime - rollingStartTime;

//        System.out.println("wheelCount = " + wheelCount);



/*
        Sieve of Eratosthenes
        finds multiples of all primes that have not already been checked by the wheel
        and labels them all as not prime
 */
        long actualPrime = 2;
        for(short primeGap: gapArray){
            actualPrime += primeGap;
            if (actualPrime < smallestPrimeIndexToCheck){
                continue;
            }

            if (actualPrime > biggestNeededPrime){
                break;
            }

            multiple = (start - ((start) % actualPrime)) + actualPrime;
            if (multiple % 2 == 0) {
                multiple += actualPrime;
            }

            int compositeIndex = (int) ((multiple-start-1)/2);
            int counter = 0;
            int indexModChecker = -1;
            long wheelCheckerThingy = (multiple/actualPrime);
            long modulusThingy= wheelCheckerThingy%wheelModulus;

//            long indexStartTime = System.currentTimeMillis();

            for (int i = 0; i < wheelArrayLength; i++){
            indexModChecker += 2*gapWheelInUse[i];
                if( indexModChecker >= modulusThingy ){
                    counter = i+1;
                    break;
                }
            }

            compositeIndex += actualPrime*(indexModChecker - modulusThingy)/2;

//            long indexEndTime = System.currentTimeMillis();
//            calcTime += indexEndTime - indexStartTime;

//            long sieveStartTime = System.currentTimeMillis();

            boolean completelyDone = false;
            long sieveSafetyNet = boolLength - (actualPrime*wheelModulus);

            for(int i = counter; i < wheelArrayLength; i++){
                if (compositeIndex >= boolLength){
                    completelyDone = true;
                    break;
                }
                oddIsPrimeInterval[compositeIndex] = false;
                compositeIndex += actualPrime * gapWheelInUse[i];

            }

            /**/
            /**/
            while(compositeIndex < sieveSafetyNet){
                for(short gap: gapWheelInUse){
                    oddIsPrimeInterval[compositeIndex] = false;
                    compositeIndex += actualPrime*gap;
                }
            }

            /**/
            /**/
            while (!completelyDone) {
                for (short gap : gapWheelInUse) {
                    if (compositeIndex >= boolLength) {
                        completelyDone = true;
                        break;
                    }
                    oddIsPrimeInterval[compositeIndex] = false;
                    compositeIndex += (actualPrime * gap);
                }
            }

//            long sieveEndTime = System.currentTimeMillis();
//            calcTime += sieveEndTime - sieveStartTime;
        }

/*
        if(number == prime)
        put number in primeArray
*/
        for (int oddIndex = 0; oddIndex < boolLength ; oddIndex++) {
            if (oddIsPrimeInterval[oddIndex]) {
                gapIntervalArray[intervalCount] = (short) ((oddIndex - baseIndex)*2);
                baseIndex = oddIndex;
                intervalCount++;
            }
        }
        totalCount += intervalCount;

//        System.out.println("calc time for the one spot = " + calcTime);
        return Arrays.copyOf(gapIntervalArray, intervalCount);
    }


    public static long findBasePrime(long start, short[] gapArray){
        if (start == 0){
            return 2;
        }
        long numToTest = start;
        for (int i = 0; i< 1000; i++){
            if (isPrime(numToTest, gapArray)){
                return numToTest;
            }
            numToTest--;
        }
        return 3;
    }

    public static boolean isPrime(long number, short[] gapArray){
        long prime = 2;
        if (number%prime== 0) {
            return false;
        }
        long biggestPrime = (long)Math.sqrt(number);
        for (short gap: gapArray)
        {
            prime += gap;
            if (number%prime == 0){
                return false;
            }
            if (prime > biggestPrime){
                return true;
            }
        }
        return true;
    }
}
