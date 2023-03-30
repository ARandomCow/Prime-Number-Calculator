package primeGaps;

import java.io.*;

import static primeGaps.shortWheelFactoring.*;

/*
    vague instructions
    primeMax, add, and repeat are the two important variables to mess around with
    primeMax determines to what number you find the first prime numbers (0, MAX)
    numOfPrimes will be the size of your starting array that'll be used for the rest of the sieves
    add determines the interval that you'll be finding more prime numbers in (start, start+add)
    primeArrayLength determines the size of your prime carrying array. Make sure it's big enough.
    repeat is the simplest one, it just determines how many times to repeat the add function
    aka how many times you increase your prime number interval by 'add'
 */



public class primeGapCalc {

    public static void main( String[] args )
//            throws FileNotFoundException
    {
        int primeMax = 1_000_000_000;
        int numOfPrimes = 100_000_000;
/*
        its the number of primes - 1 (because this algorithm doesnt worry about 2 or multiples of it)
        primeMax = 100, numOfPrimes = 24
        if primeMax = 1,000 then numOfPrimes = 167
        if primeMax = 10,000 then numOfPrimes = 1228
        if primeMax = 1,000,000, then numOfPrimes = 78,497
        if 100,000,000, then 5_761_454
        if primeMax = 1,000,000,000 then numOfPrimes = 50847533
*/


/*
        These create larger and larger wheels for wheel factorization
        these wheels essentially say that only certain numbers in an interval can possibly be prime
        for example, indexWheelMod6 contains 0 and 2 which reference the primes 1 and 5
        wheelMod6 says that for any prime p, p%6 will either equal 1 or 5
        therefore we can immediately say that every number whose mod 6 != 1 or 5 is not prime
        if number%wheelMod is not in the factor wheel, then it IS NOT PRIME NO MATTER WHAT
        if number%wheelMod IS in the factor wheel, then is CAN be prime, but it doesn't have to be
        https://en.wikipedia.org/wiki/Wheel_factorization
        https://www.youtube.com/watch?v=F5l_DCGBIrg
 */
        short[] indexWheelMod6 = new short[]{0,2};
        short[] gapWheelMod6 = new short[]{1, 2};

        short[] indexWheelMod30 = biggerIndexWheel(indexWheelMod6, 6);
        short[] gapWheelMod30 = biggerIndexGapWheel(indexWheelMod6, 6);

        short[] indexWheelMod210 = biggerIndexWheel(indexWheelMod30, 30);
        short[] gapWheelMod210 = biggerIndexGapWheel(indexWheelMod30, 30);

        short[] gapWheelMod2310 = biggerIndexGapWheel(indexWheelMod210, 210);
        short[] indexWheelMod2310 = biggerIndexWheel(indexWheelMod210, 210);

        short[] indexWheelMod30030 = biggerIndexWheel(indexWheelMod2310, 2310);
        short[] gapWheelMod30030 = biggerIndexGapWheel(indexWheelMod2310, 2310);

        int[] indexWheelMod510510 = biggerIndexWheelInt(indexWheelMod30030, 30030);
        short[] gapWheelMod510510 = biggerIndexGapWheel(indexWheelMod30030, 30030);

        int[] indexWheelMod9699690 = biggerIndexWheelInt(indexWheelMod510510, 510510);
        short[] gapWheelMod9699690 = biggerIndexGapWheel(indexWheelMod510510, 510510);

        short[] gapWheelMod223092870 = biggerIndexGapWheel(indexWheelMod9699690, 9699690);


        final long startTime = System.currentTimeMillis();

        sieveGapMethods erat = new sieveGapMethods(primeMax);

        short[] gapArray = erat.createGapArray(numOfPrimes, gapWheelMod223092870,223092870);

        long startCount = erat.getStartCount();
        System.out.println(startCount + " is the amount of primes below " + primeMax);

/*
        System.out.println("gaps: ");
        for (int i = 1; i<=10; i++){
        System.out.print(gapArray[i-1] + " | ");
        }
        System.out.println();
        System.out.println("primes: ");
/**/
        int prime = 2;
/*
        for(int i = 0; i< gapArray.length; i++){
            prime += gapArray[i];
            System.out.print(prime + " | ");
        }
        System.out.println("");

        System.out.println(prime);
/**/
        final long firstEndTime = System.currentTimeMillis();
        System.out.println("Initial execution time: " + (firstEndTime - startTime) + " milliseconds");



        long start = 1_000_000_000L;
        int add = 50_000_000;
        int primeArrayLength = 5_000_000;
        int repeat = 1;
        int numOfRepeats = 0;
        int intervalCount;
        long totalCount = erat.getTotalCount();
        int checkInterval = 4;
        long bufferstart = start;
/*
                File csvFile = new File("primeList.csv");
                PrintWriter out = new PrintWriter(csvFile);
/* */
        long intervalStartTime = System.currentTimeMillis();



/*
                try {
                    FileOutputStream fileOs = new FileOutputStream("primes.bin");
                    ObjectOutputStream oos = new ObjectOutputStream(fileOs);
                    for(int i = 0; i<primeArray.length; i++){
                        oos.writeInt(primeArray[i]);
                    }
                    }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
/* */

        int intervalPrime = 2;
        while (repeat<=numOfRepeats)
        {
            short[] gapIntervalArray = erat.sieveFindInterval(start, add, gapArray,
                    primeArrayLength, gapWheelMod2310, 2310);

            start+=add;
            if(repeat%checkInterval==0)
            {

                System.out.println("interval " + bufferstart + " to " + (bufferstart+(add*(checkInterval))) + " complete");

                System.out.println("Total primes calculated: " + erat.getTotalCount());
                bufferstart = start;
                long intervalEndTime = System.currentTimeMillis();
                System.out.println("Time taken = " + (intervalEndTime-intervalStartTime) + " milliseconds");

                intervalStartTime = System.currentTimeMillis();
            }


/*
                    try {
                            FileOutputStream fileOs = new FileOutputStream("primes.bin");
                            ObjectOutputStream loos = new ObjectOutputStream(fileOs);
                            for(int i=0; i < primeList.length; i++){
                                loos.writeLong(primeList[i]);
                                }
                        }
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
/* */

            repeat++;
            totalCount = erat.getTotalCount();
            intervalCount = erat.getIntervalCount()+10000;
            primeArrayLength = intervalCount;


//            for(int i = 0; i< 10; i++){
//                System.out.print(gapIntervalArray[i] + " | ");
//            }
//            System.out.println();
//            for(int i = 0; i< gapIntervalArray.length; i++){
//                prime += gapIntervalArray[i];
//                System.out.print(prime + " | ");
//            }
//            System.out.println("");
        }

//                out.close();




        final long finalEndTime = System.currentTimeMillis();
        System.out.println("Final execution time: " + (finalEndTime - startTime) + " milliseconds");

        totalCount = erat.getTotalCount();
        System.out.println("Total primes calculated: " + totalCount);
/*
                for(int i=0; i<primeList.length; i++){
                System.out.println(primeList[i]);
                }
/* */
    }
}
