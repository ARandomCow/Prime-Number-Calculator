import javax.rmi.ssl.SslRMIServerSocketFactory;
import javax.sound.sampled.BooleanControl;

import java.util.*;
import java.lang.Math;
import java.lang.reflect.Array;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


// Nice inheritanceeeeee
public class FastPrimeCalc extends sieveMethods
{


//vague instructions
//primeMax, add, and repeat are the two important variables to mess around with
//primeMax determines to what number you find the first prime numbers (0, MAX)
//numOfPrimes will be the size of your starting array thatll be used for the rest of the sieves
//add determines the interval that you'll be finding more prime numbers in (start, start+add)
//primeArrayLength determines the size of your prime carrying array. Make sure it's big enough.
//repeat is the simplest one, it just determines how many times to repeat the add function
//aka how many times you increase your prime number interval by 'add'


    public static void main(String[] args)
    {
        final long startTime = System.currentTimeMillis();
//--------------------------------------------------------------------------------------------------
        Integer primeMax = 1000000;
        int numOfPrimes = 78497;
//primeMax = 100, numOfPrimes = 24
//if primeMax = 1,000,000, then numOfPrimes = 78,497
//--------------------------------------------------------------------------------------------------

        sieveMethods erat = new sieveMethods(primeMax);

        int[] primeArray = erat.sieveOfEratosthenes(numOfPrimes);

//        for(int i =0; i<primeArray.length; i++){
//            System.out.println(primeArray[i]);
//        }

        int startCount = erat.getStartCount();
        System.out.println(startCount + " is the amount of primes below " + primeMax);

        final long firstEndTime = System.currentTimeMillis();
        System.out.println("Initial execution time: " + (firstEndTime - startTime) + " milliseconds");

        long start = primeMax;
        long add = 1000000;
        int primeArrayLength = 300000;
        int repeat = 0;
        int intervalCount = 0;

        for(repeat = 0; repeat<9999; repeat++)
        {
            long[] primeList = erat.sieveFindInterval(start, add, primeArray, primeArrayLength);
//            System.out.println(primeList.length);
//          for(int i =0; i<primeList.length; i++){
//              System.out.println(primeList[i]);
//          }
/* 
            for (int i = 0; i< primeList.size(); i++){
                System.out.println(primeList.get(i));
            }
*/              

            if(repeat%500==0)
            {

                System.out.println("interval " + start + " to " + (start+(add*500)) + " complete");
            }
            intervalCount = erat.getIntervalCount()+1000;
            primeArrayLength = intervalCount;
            start+=add;
        }
        final long finalEndTime = System.currentTimeMillis();
        System.out.println("Final execution time: " + (finalEndTime - startTime) + " milliseconds");

        long totalCount = erat.getTotalCount();
        System.out.println("Total primes calculated: " + totalCount);

    

    }
}
