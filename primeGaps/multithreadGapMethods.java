

package primeGaps;


import java.io.*;

public class multithreadGapMethods implements Runnable {

    long start;
    int add;
    short[] gapArray;
    int listLengthOfGapInterval;
    int i;
    short[] gapInterval;
    sieveGapMethods multiSieve = new sieveGapMethods();
    int numThreads;
    long totalInterval;
    int repetitions;
    long totalPrimes;

    public multithreadGapMethods() {

    }

    public multithreadGapMethods(long startingNum, int addNum,
                                 short[] baseGapArray, int newGapArrayLength, int threadNumber,
                                 int numOfThreads, long totalIntervalNum) {
        start = startingNum;
        add = addNum;
        listLengthOfGapInterval = newGapArrayLength;
        gapArray = baseGapArray;
        i = threadNumber;
        numThreads = numOfThreads;
        totalInterval = totalIntervalNum;
        repetitions = (int) (totalInterval/add);

        multiSieve = new sieveGapMethods(gapArray);
    }

    //@Override
    public void run() {
        try {
            final long startTime = System.currentTimeMillis();
            String csvName = i + "primeList.csv";
            String binName = i + "primeList.bin";

            File csvFile = new File(csvName);
            PrintWriter out = new PrintWriter(csvFile);
            FileOutputStream fileOs = new FileOutputStream(binName);
            ObjectOutputStream oos = new ObjectOutputStream(fileOs);

            System.out.println("Running thread " + i);
            int count = 0;
            for (int j = 0; j < repetitions; j++)
            {
                //creates new array of primes
                short[] gapInterval = multiSieve.sieveFindInterval(start + ((long) add * j), add, gapArray,
                        listLengthOfGapInterval);

                int numPrimes = multiSieve.getIntervalCount();
                //put primes in .csv file
                /**/
                for(short gap: gapInterval){
                    out.println(gap);
                }
                /**/

                //put primes in .bin file
                /**/
                for (short gap: gapInterval) {
                    oos.writeShort(gap);
                }
                /* */

                listLengthOfGapInterval = numPrimes + 10000;
                long numOfRepeats=100;
                long intTime = System.currentTimeMillis();
                if (j%numOfRepeats == 0){
                    System.out.println((start+ (long) add *numOfRepeats *count) +
                            " to " + ((start+ (long) add *numOfRepeats * count)+add* numOfRepeats) +
                            " has been calculated in " + (intTime-startTime)/1000 + " seconds");
                    count++;
                }
            }
            out.close();
            oos.close();

            totalPrimes = multiSieve.getTotalCount();
            System.out.println("Total primes calculated for thread " + i + ": " + totalPrimes);
//            System.out.println("Last prime in list: " + primeInterval[primeInterval.length-1]);
            /**/



        } catch (IOException e) {
            e.printStackTrace();}
        /* */




    }

    public short[] getGaps() {
        return gapInterval;
    }

    public long getTotalCount() {
        return multiSieve.getTotalCount();
    }
}



