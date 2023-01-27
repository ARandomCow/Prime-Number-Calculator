import javax.rmi.ssl.SslRMIServerSocketFactory;
import javax.sound.sampled.BooleanControl;

import java.util.LinkedList;
import java.lang.Math;

public class primeCalc2
{

//vague instructions
//MAX, add, and repeat are the two important variables to mess around with
//MAX determines to what number you find the first prime numbers (0, MAX)
//add determines the interval that you'll be finding more prime numbers in (start, start+add)
//repeat is the simplest one, it just determines how many times to repeat the add function
//aka how many times you increase your prime number interval by 'add'

    public static void main(String[] args)
    {

    final long startTime = System.currentTimeMillis();
    // find all primes less than n

//--------------------------------------------------------------------------------------------------
        Integer MAX = 10000000;
//--------------------------------------------------------------------------------------------------

        int count = 0;





        //boolean list for all odd numbers
        //0,  1,  2,  3,  4,  5,  6,  7,  8,  9,  10,  11,  12,  13,  14,  15,  16,  17,  18,  19,  20,  21,  22,  23,  24,  25,  26,  27,  28,  29,  30,  31,  32,  33,  34,  35,  36,  37,  38,  39,  40  
        //1,  3,  5,  7,  9,  11, 13, 15, 17, 19, 21,  23,  25,  27,  29,  31,  33,  35,  37,  39,  41,  43,  45,  47,  49,  51,  53,  55,  57,  59,  61,  63,  65,  67,  69,  71,  73,  75,  77,  79,  81
        boolean[] boolOddArray = new boolean[(int)(MAX/2)+1];
        boolOddArray[0] = true;
        
        //for (int y = 0; y < boolOddArray.length; y++)
        //{
        //  System.out.print(boolOddArray[y] + ", ");
        //}

        //1 is true because 1 is not prime

        //starting with prime = 3
        for (Integer i = 1; i < (int) (Math.sqrt(MAX))+1; i++) 
        {
          if (!boolOddArray[i]) {
        //j = i can be optimized but I need to find the algorithm to get i^2
            for (Integer j = (((2*i)+1)*((2*i)+1)-1)/2; j<(MAX/2)+1; j+=(2*i)+1)
            {
                boolOddArray[j] = true;
            }
          }
        }

        LinkedList<Integer> primeList = new LinkedList<Integer>();

        for(Integer y =1; y<(MAX/2); y++){
            if(!boolOddArray[y]){
                primeList.addLast((2*y)+1);
                count+=1;
            }
        }
//        System.out.println(primeList);
        System.out.println(primeList.size());

        final long endTime = System.currentTimeMillis();
        System.out.println("Initial execution time: " + (endTime - startTime) + " milliseconds");


//      now I need to create the same add n function
//      primeList = [3, 5, 7, . . . ]
//      start


/*
 * for i in range(0, len(primeList)):
        if(primeList[i]>int((start+n)**(1/2))+1):
            break
        prime = primeList[i]
        multiple = start - (start%prime) + prime
        if multiple%2==0:
            multiple += prime
        for j in range(multiple, start+n, 2*prime):
                primes[j-start] = False
    return primes
 */

        int start = MAX;
//----------------------------------------------------------------------------------------------------------------
        int add = 10000000;
//----------------------------------------------------------------------------------------------------------------
        int bount = 0;
        long repeatStartTime = System.currentTimeMillis(); 
        
//----------------------------------------------------------------------------------------------------------------
        for(int repeat = 0; repeat < 99; repeat++){
//----------------------------------------------------------------------------------------------------------------


            boolean[] boolAddArray = new boolean[(add/2)];
            for (int l = 0; l<primeList.size(); l++ )
            {
                Integer prime = primeList.get(l);
                if (prime > (int)Math.sqrt(start+add)+1)
                {
                    break;
                }
//let start = 10
//0,  1,  2,  3,  4,  5,  6,  7,  8
//11, 13, 15, 17, 19, 21, 23, 25, 27
//the formula is 2j+1+start
            int multiple = start - (start%prime) + prime;
            if(multiple%2==0)
            {
                multiple += prime;
            }
            for (int j = multiple; j< start+add; j+=2*prime)
            {
                boolAddArray[(int)(j-start-1)/2] = true;
            }
        }

        for(Integer k =0; k<(add/2); k++){
            if(!boolAddArray[k]){
                primeList.addLast((2*k)+1+start);
                count += 1;
            }
        }

        start += add;

        bount ++;
        if (bount%10==0)
        {
            long repeatEndTime = System.currentTimeMillis();
            System.out.println(bount + " repetitions after  " + (repeatEndTime - repeatStartTime) + " total milliseconds");
            System.out.println("primes found up to " + start);
            System.out.println("number of primes = " + count);

        }
//        System.out.println(primeList);
    //    System.out.println(count + " primes have been calculated");
    }
        System.out.println(count + " primes have been calculated");
        final long otherEndTime = System.currentTimeMillis();
        System.out.println("Total execution time:  " + (otherEndTime - startTime));





    }




}