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

    public multithreadMethods() {

    }

    public multithreadMethods(long startingNum, int addNum, 
    int[] primeList, int newPrimeLength, int number,
            int numOfThreads) {
        start = startingNum;
        add = addNum;
        listLengthOfNewPrimes = newPrimeLength;
        primeArray = primeList;
        i = number;
        numThreads = numOfThreads;

        multiSieve = new sieveMethods(primeArray);
    }

    public void run() {
        try {
            String csvName = i + "primeList.csv";
            String binName = i + "primeList.bin";

            // File csvFile = new File(csvName);
            // PrintWriter out = new PrintWriter(csvFile);
            FileOutputStream fileOs = new FileOutputStream(binName);
            ObjectOutputStream oos = new ObjectOutputStream(fileOs);

            System.out.println("Running thread " + i);

            for (int j = 0; j <= 200; j++) {
                // System.out.println("thread " + i + "running primes from " +
                // (start+(add*numThreads*i)) + " to " + (start+(add*numThreads*i)+add));
                primeInterval = multiSieve.sieveFindInterval(start + (add * numThreads * i), add, primeArray,
                        listLengthOfNewPrimes);

                /*
                 * for(int y = 0; y<primeInterval.length; y++){
                 * out.println(primeInterval[y]);
                 * }
                 */

                for (int i = 0; i < primeInterval.length; i++) {
                    oos.writeLong(primeInterval[i]);
                }
            }
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
