package primeGaps;

import java.io.*;
import java.util.Scanner;

//vague instructions
//primeMax, add, and repeat are the important variables to mess around with
//primeMax determines to what number you find the first prime numbers (0, MAX)
//numOfPrimes will be the size of your starting array that'll be used for the rest of the sieves
//add determines the interval that you'll be finding more prime numbers in (start, start+add)
//primeArrayLength determines the size of your prime carrying array. Make sure it's big enough.
//repeat is the simplest one, it just determines how many times to repeat the add function
//aka how many times you increase your prime number interval by 'add'



public class primeGapCalc {

    public static void main( String[] args ) throws FileNotFoundException
    {

        Scanner input = new Scanner(System.in);

        System.out.println("Creating base prime array \n");
        System.out.println("What is the upper bound of your initial array of prime numbers?");
        int myPrimeMax = input.nextInt();

//        System.out.println("What is the number of primes you think is in this list?");
        int myNumOfPrimes = myPrimeMax/2;



        //--------------------------------------------------------------------------------------------------
        int primeMax = myPrimeMax;
        int numOfPrimes = myNumOfPrimes;
        //its the number of primes - 1 (because this algorithm doesnt worry about 2 or multiples of it)
        //primeMax = 100, numOfPrimes = 24
        //if primeMax = 1,000 then numOfPrimes = 167
        //if primeMax = 10,000 then numOfPrimes = 1228
        //if primeMax = 1,000,000, then numOfPrimes = 78,497
        //if 100,000,000, then 5761454
        //if primeMax = 1,000,000,000 then numOfPrimes = 50847533
        //--------------------------------------------------------------------------------------------------
        long startTime = System.currentTimeMillis();
        sieveGapMethods erat = new sieveGapMethods(primeMax);

        short[] gapArray = erat.createGapArray(numOfPrimes);

        final long firstEndTime = System.currentTimeMillis();
        System.out.println("Initial execution time: " + (firstEndTime - startTime) + " milliseconds");
        //        for(int i =0; i<primeArray.length; i++){
        //            System.out.println(primeArray[i]);
        //        }

//        long startCount = erat.getStartCount();
//        System.out.println(startCount + " is the amount of primes below " + primeMax);

/*
        for (int i = 0; i<gapArray.length; i++){
        System.out.print(gapArray[i] + " | ");
        }
        System.out.println("");
        int prime = 2;
        for(int i = 0; i<gapArray.length; i++){
            prime += gapArray[i];
            System.out.print(prime + " | ");
        }
        System.out.println("");
*/
        System.out.println("Creating full prime array \n");


        System.out.println("What do you want the upper bound of your prime list to be?");
        long newPrimeMax = input.nextInt();

        System.out.println("What interval of prime numbers do you want to find? " +
                "\n (let it be a factor of your largest prime");
        int myIntervalSize = input.nextInt();

        System.out.println("After how many intervals do you want an update?");
        int myCheckInterval = input.nextInt();


        long mainStartTime = System.currentTimeMillis();

        long start = 0;
        int intervalSize = myIntervalSize;
        int primeArrayLength = intervalSize/2;
        //repeat should be initialized as 1 + (primeMax/add) (??? when did I write this)
        int repeat = 1 ;
        int numOfRepeats = (int) (newPrimeMax/intervalSize);
        int intervalCount;
        int checkInterval = myCheckInterval;
        long bufferstart = start;
/**/
                File csvFile = new File("primeList.csv");
                PrintWriter out = new PrintWriter(csvFile);
/* */
        long intervalStartTime = System.currentTimeMillis();



        int prime = 2;
/*
//        ngl not sure what this does

                out.println(2);
                for(int i=0; i<gapArray.length; i++){
                    prime += gapArray[i];
                    out.println(prime);
                }
/**/

        while (repeat<=numOfRepeats)
        {
            short[] gapIntervalArray = erat.sieveFindInterval(start, intervalSize, gapArray, primeArrayLength);

            start+=intervalSize;
            if(repeat%checkInterval==0)
            {

                System.out.println("interval " + bufferstart + " to " + (bufferstart+(intervalSize*(checkInterval))) + " complete");

                System.out.println("Total primes calculated: " + erat.getTotalCount());
                bufferstart = start;
                long intervalEndTime = System.currentTimeMillis();
                System.out.println("Time taken = " + (intervalEndTime-intervalStartTime) + " milliseconds");

                intervalStartTime = System.currentTimeMillis();
            }


            repeat++;
            intervalCount = erat.getIntervalCount()+(intervalSize * checkInterval);
            primeArrayLength = intervalCount;
/**/
            for (short gap: gapIntervalArray){
                out.println(prime);
                prime += gap;
            }
            out.println(prime);
 /**/

        }

                out.close();


        System.out.println("largest prime: " + prime);

//        System.out.println("gap[0] = " + gapIntervalArray[0]);

        final long finalEndTime = System.currentTimeMillis();
        System.out.println("prime execution time: " + (finalEndTime - mainStartTime) + " milliseconds");

        System.out.println("Total primes calculated: " + (erat.getTotalCount()));

    }
}
