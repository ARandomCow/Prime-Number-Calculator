package primeSieve;


import java.io.*;

public class multithreadMethods implements Runnable {

    long start;
    int add;
    int[] primeArray;
    int listLengthOfNewPrimes;
    int i;
    long[] primeInterval;
    sieveMethods multiSieve = new sieveMethods();
    int numThreads;
    long totalInterval;
    int repetitions;
    long totalPrimes;

    public multithreadMethods() {

    }

    public multithreadMethods(long startingNum, int addNum,
                              int[] primeList, int newPrimeLength, int number,
                              int numOfThreads, long totalIntervalNum) {
        start = startingNum;
        add = addNum;
        listLengthOfNewPrimes = newPrimeLength;
        primeArray = primeList;
        i = number;
        numThreads = numOfThreads;
        totalInterval = totalIntervalNum;
        repetitions = (int) (totalInterval/add);

        multiSieve = new sieveMethods(primeArray);
    }

    //@Override
    public void run() {
        try {
            final long startTime = System.currentTimeMillis();
//            String csvName = i + "primeList.csv";
            String binName = i + "primeList.bin";

//            File csvFile = new File(csvName);
//            PrintWriter out = new PrintWriter(csvFile);
            FileOutputStream fileOs = new FileOutputStream(binName);
            ObjectOutputStream oos = new ObjectOutputStream(fileOs);

            System.out.println("Running thread " + i);
            int count = 0;
            for (int j = 0; j < repetitions; j++)
            {
                //creates new array of primes
                long[] primeInterval = multiSieve.sieveFindInterval(start + ((long) add * j), add, primeArray,
                        listLengthOfNewPrimes);

                int numPrimes = multiSieve.getIntervalCount();
                //put primes in .csv file
                /*
                for(long prime: primeInterval){
                    out.println(prime);
                }
                /**/

                //put primes in .bin file
                /**/
                for (long prime: primeInterval) {
                    oos.writeLong(prime);
                }
                /* */

                listLengthOfNewPrimes = numPrimes + 10000;

                if (j%10000 == 0){
                    System.out.println((start+ (long) add *10000L *count) + " to " + ((start+ (long) add *10000L * count)+add* 10000L) + " has been calculated");
                    count++;
                    long intTime = System.currentTimeMillis();
                    System.out.println((intTime-startTime)/1000 + " seconds have passed");
                }
            }
//            out.close();
            oos.close();

            totalPrimes = multiSieve.getTotalCount();
            System.out.println("Total primes calculated for thread " + i + ": " + totalPrimes);
//            System.out.println("Last prime in list: " + primeInterval[primeInterval.length-1]);
            /**/
        } catch (IOException e) {
            e.printStackTrace();}
        /* */
    }

    public long[] getPrimes() {
        return primeInterval;
    }

    public long getTotalCount() {
        return multiSieve.getTotalCount();
    }
}
