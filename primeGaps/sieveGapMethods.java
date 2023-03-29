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
    public short[] createGapArray(int numOfGaps, short[] gapWheelInUse, int wheelModulus) {
        startCount = 1;
        int currentLargestPrimeIndex = 1;
        short[] gapArray = new short[(numOfGaps)];
        int wheelArrayLength = gapWheelInUse.length;

/*
        smallestPrimeToCheck only exists so that the sieve of Eratosthenes doesnt
        look at primes that have already 100% been knocked out by the wheel
*/
        short[] smallGapArray = new short[]{1, 2, 2, 4, 2, 4, 2, 4, 6, 2};
        int smallestPrimeIndexToCheck = 1;
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
        boolean done = false;
        int safetyNet = boolLength-wheelModulus;
//        first while loop gets like 99% of numbers in the interval without spending too much computation time checking
        while(probablePrime < safetyNet){
            for (short gapBetweenPrimes : gapWheelInUse) {
                probablePrime += gapBetweenPrimes;
                oddIsPrime[probablePrime] = true;
            }
        }

//        second while loop checks if the prime is bigger than the interval more often
        while(!done) {
            for (short gapBetweenPrimes : gapWheelInUse){
                    probablePrime += gapBetweenPrimes;
                if ((probablePrime) >= boolLength) {
                    done = true;
                    break;
                }
                oddIsPrime[probablePrime] = true;
            }
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

            while(compositeIndex<boolLength){
                oddIsPrime[compositeIndex] = false;
                int nextGap = gapWheelInUse[counter%wheelArrayLength];
                compositeIndex += actualPrime*nextGap;
                counter++;
            }
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

        return Arrays.copyOf(gapArray, startCount);
    }



    // returns an array of primes in between start and start+add
    public short[] sieveFindInterval(long start, int add, short[] gapArray,
                                     int numOfNewGaps, short[] gapWheelInUse, int wheelModulus) {
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
//          long basePrime = start-3;
        int baseIndex = (int)((basePrime - start-1)/2);
        int biggestNeededPrime = (int) Math.sqrt(start + add) + 1;
        int boolLength = oddIsPrimeInterval.length;
        int wheelArrayLength = gapWheelInUse.length;
        intervalCount = 0;
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
        how to roll the gosh darn wheel over an interval
        okay the wheel works im pretty sure
 */
        int whereTheFirstNumIsOnTheWheel = (int)(start%wheelModulus);
//        System.out.println("(start%wheelModulus) = " + whereTheFirstNumIsOnTheWheel);
        int actualWheelNum = -1;
        int firstGapIndex = 1;

        for(short gap: gapWheelInUse){
            actualWheelNum += 2*gap;
            if (actualWheelNum > whereTheFirstNumIsOnTheWheel){
                break;
            }
            firstGapIndex++;
        }

//        int wheelCount = 0;
//        System.out.println("start mod(wheelMod) = " + whereTheFirstNumIsOnTheWheel);
//        System.out.println("firstGapIndex = " + firstGapIndex);
//        System.out.println("actualWheelNum = " + actualWheelNum);

        int indexDistanceBetweenStartAndFirstWheelNum = (actualWheelNum - whereTheFirstNumIsOnTheWheel-1)/2;
        int probablePrime = indexDistanceBetweenStartAndFirstWheelNum;
//        System.out.println("probable Prime = " + probablePrime);

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
        int safetyNet = boolLength - wheelModulus;
        while(probablePrime < safetyNet){
            for(short gap: gapWheelInUse){
                probablePrime += gap;
                oddIsPrimeInterval[probablePrime] = true;
//                wheelCount++;
            }
        }

/*
        This rolls the wheel to the very end of the list
 */
        boolean done = false;
        while(!done) {
            for (short gapBetweenPrimes : gapWheelInUse){
                probablePrime += gapBetweenPrimes;
                if ((probablePrime) >= boolLength) {
                    done = true;
                    break;
                }
                oddIsPrimeInterval[probablePrime] = true;
            }
        }


//        System.out.println("wheelCount = " + wheelCount);
/*
        Sieve of Eratosthenes
        Takes the multiples of all the primes and labels them as not prime

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

            while(compositeIndex<boolLength){
                oddIsPrime[compositeIndex] = false;
                int nextGap = gapWheelInUse[counter%wheelArrayLength];
                compositeIndex += actualPrime*nextGap;
                counter++;
            }
        }
 */
/*
        for (short gap : gapArray)
        {
            prime += gap;
            if(prime < smallestPrimeIndexToCheck){
                continue;
            }
            if (prime > biggestNeededPrime) {
                break;
            }
            multiple = (start - ((start) % prime)) + prime;
            if (multiple % 2 == 0) {
                multiple += prime;
            }

            for (long composite = multiple; composite < start + add; composite += 2 * prime) {
                oddIsPrimeInterval[(int) (composite - start - 1) / 2] = false;
            }
        }
*/


/*
        The thing that does work here right now is i will sometimes start on a number that the wheel has checked and
        then everything's fucked
        so I need to start on whatever number is a multiple of that prime and also labeled prime by the wheel
 */
        long actualPrime = 2;

        for(short primeGap: gapArray){
            actualPrime += primeGap;
//            System.out.println("next prime = " + actualPrime);
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
            int wheelCheckerThingy = (int) (multiple/actualPrime);
            int modulusThingy= wheelCheckerThingy%wheelModulus;

            for (int i = 0; i < wheelArrayLength; i++){
            indexModChecker += 2*gapWheelInUse[i];
                if( indexModChecker >= modulusThingy ){
                    counter = i+1;
                    break;
                }
            }

            compositeIndex += actualPrime*(indexModChecker - modulusThingy)/2;

            while(compositeIndex < boolLength){
                oddIsPrimeInterval[compositeIndex] = false;
                int nextGap = gapWheelInUse[counter % wheelArrayLength];
                compositeIndex += actualPrime*nextGap;
                counter++;
            }
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
