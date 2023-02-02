package VScodeProjects;

import VScodeProjects.*;

import java.util.*;
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
        try 
        {
//            String csvName = i + "primeList.csv";
            String binName = i + "primeList.bin";

//             File csvFile = new File(csvName);
//             PrintWriter out = new PrintWriter(csvFile);
            FileOutputStream fileOs = new FileOutputStream(binName);
            ObjectOutputStream oos = new ObjectOutputStream(fileOs);

            System.out.println("Running thread " + i);

            for (int j = 0; j < repetitions; j++) 
            {
                //creates new array of primes
                primeInterval = multiSieve.sieveFindInterval(start + (add * j), add, primeArray,
                        listLengthOfNewPrimes);

                //put primes in .csv file
                /*
                  for(int y = 0; y<primeInterval.length; y++){
                  out.println(primeInterval[y]);
                  }
                /**/

                //put primes in .bin file
                /**/ 
                for (int i = 0; i < primeInterval.length; i++) {
                    oos.writeLong(primeInterval[i]);
                }
                /* */
                
            }
//            out.close();
            oos.close();

            totalPrimes = multiSieve.getTotalCount();
            System.out.println("Total primes calculated for thread " + i + ": " + totalPrimes);
            System.out.println("Last prime in list: " + primeInterval[primeInterval.length-1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long[] getPrimes() {
        return primeInterval;
    }

    public long getTotalCount() {
        return multiSieve.getTotalCount();
    }
}
