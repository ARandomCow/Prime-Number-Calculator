package primeGaps;

import java.io.*;

//vague instructions
//primeMax, add, and repeat are the two important variables to mess around with
//primeMax determines to what number you find the first prime numbers (0, MAX)
//numOfPrimes will be the size of your starting array that'll be used for the rest of the sieves
//add determines the interval that you'll be finding more prime numbers in (start, start+add)
//primeArrayLength determines the size of your prime carrying array. Make sure it's big enough.
//repeat is the simplest one, it just determines how many times to repeat the add function
//aka how many times you increase your prime number interval by 'add'



public class primeGapCalc {

    public static void main( String[] args ) throws FileNotFoundException
    {

        final long startTime = System.currentTimeMillis();

        //--------------------------------------------------------------------------------------------------
        int primeMax = 100000;
        int numOfPrimes = 200000;
        //its the number of primes - 1 (because this algorithm doesnt worry about 2 or multiples of it)
        //primeMax = 100, numOfPrimes = 24
        //if primeMax = 1,000 then numOfPrimes = 167
        //if primeMax = 10,000 then numOfPrimes = 1228
        //if primeMax = 1,000,000, then numOfPrimes = 78,497
        //if 100,000,000, then 5761454
        //if primeMax = 1,000,000,000 then numOfPrimes = 50847533
        //--------------------------------------------------------------------------------------------------

        sieveGapMethods erat = new sieveGapMethods(primeMax);

        short[] gapArray = erat.createGapArray(numOfPrimes);

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
        final long firstEndTime = System.currentTimeMillis();
        System.out.println("Initial execution time: " + (firstEndTime - startTime) + " milliseconds");

        long start = 0;
        int add = 1_000;
        int primeArrayLength = 1000;
        //repeat should be initialized as 1 + (primeMax/add)
        int repeat = 1;
        int numOfRepeats = 1;
        int intervalCount;
        long totalCount = erat.getTotalCount();
        int checkInterval = 200;
        long bufferstart = start;
/**/
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
        int prime = 2;
/**/
                out.println(2);
                for(int i=0; i<gapArray.length; i++){
                    prime += gapArray[i];
                    out.println(prime);
                }
/**/

        while (repeat<=numOfRepeats)
        {
            short[] gapIntervalArray = erat.sieveFindInterval(start, add, gapArray, primeArrayLength);
            //            System.out.println(primeList.length);
            //          for(int i =0; i<primeList.length; i++){
            //              System.out.println(primeList[i]);
            //          }
        /*
                    for (int i = 0; i< primeList.size(); i++){
                        System.out.println(primeList.get(i));
                    }
        */
            start+=add;
            if(repeat%checkInterval==0)
            {

                System.out.println("interval " + bufferstart + " to " + (bufferstart+(add*(checkInterval))) + " complete");

                System.out.println("Total primes calculated: " + totalCount);
                bufferstart = start;
                long intervalEndTime = System.currentTimeMillis();
                System.out.println("Time taken = " + (intervalEndTime-intervalStartTime) + " milliseconds");

                intervalStartTime = System.currentTimeMillis();
            }
            //            if(repeat == 9998){
            //                for(int i =0; i<primeList.length; i++){
            //                    System.out.println(primeList[i]);
            //                }
            //            }

/*
                    for(int i=0; i < primeList.length; i++){
                        out.println((totalCount+i+2) + " | " + primeList[i]);
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
            intervalCount = erat.getIntervalCount()+20000;
            primeArrayLength = intervalCount;

            for (short gap: gapIntervalArray){
                System.out.print(prime + ", ");
                prime += gap;
            }
            System.out.println();

        }

                out.close();


//        System.out.println("prime: " + prime);

//        System.out.println("gap[0] = " + gapIntervalArray[0]);

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
