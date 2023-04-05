package compressor;


import java.io.*;
import compressor.*; 
import java.nio.file.*;
import java.util.stream.*;
import java.nio.file.*;
import java.util.Arrays;
import compressor.Compressor; 

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

            //initialize csv writer
            /**/
            String csvName = i + "0bil_to_" + (i+1) + "0bil.csv";
            File csvFile = new File(csvName);
            PrintWriter out = new PrintWriter(csvFile);
            Path filePath = Paths.get(csvName);
            /**/

            //initialize bin writer
            /*
            String binName = i + "0bil_to_" + (i+1) + "0bil.bin";
            FileOutputStream fileOs = new FileOutputStream(binName);
            ObjectOutputStream oos = new ObjectOutputStream(fileOs);
            /**/

            long intStartTime = System.currentTimeMillis();
            System.out.println("Running thread " + i);
            int count = 0;
            for (int j = 1; j <= repetitions; j++)
            {
                //creates new array of primes
                short[] gapInterval = multiSieve.sieveFindInterval(start + ((long) add * (j-1)), add, gapArray,
                        listLengthOfGapInterval);

                int numPrimes = multiSieve.getIntervalCount();
                //put primes in .csv file
                /**/
                for(short gap: gapInterval){
                    out.println(gap);
                }

           /* try {
              Files.write(filePath, Arrays.stream(gapInterval).mapToObj(Short::toString).toArray(String[]::new));
            } 

            catch (Exception e) {
              e.printStackTrace();
            }*/


                /**/

                //put primes in .bin file
                /*
                for (short gap: gapInterval) {
                    oos.writeShort(gap);
                }
                /* */

                listLengthOfGapInterval = numPrimes + 10000;
                long numOfRepeats=1000;
                if (j%numOfRepeats == 0){
                    long intTime = System.currentTimeMillis();
                    System.out.println(
                            "thread " + (i%numThreads) + " calculated " +
                            (start+ (long) add *numOfRepeats *count) +
                            " to " + ((start+ (long) add *numOfRepeats * count)+add* numOfRepeats) +
                            " in " + ((float) (intTime-intStartTime)/1000.0) + " seconds");
                    count++;
                    intStartTime = System.currentTimeMillis();
                }
            }
            //closes csv writer
            out.close();

            //closes bin writer
//            oos.close();

            totalPrimes = multiSieve.getTotalCount();
            System.out.println("Total primes calculated for thread " + i + ": " + totalPrimes);

            System.out.println("\nCompressing Prime File for Thread " + this.i + "..."); 

            Compressor compressor = new Compressor(); 
            compressor.compressFile(csvName, csvName); 

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



