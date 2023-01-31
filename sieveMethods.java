
import java.lang.Math;


public class sieveMethods
{
    int num;
    int startCount;
    int intervalCount;
    long totalCount;
//ignore
    public sieveMethods()
    {
        num = 10000;
        totalCount=0;
    }

    public sieveMethods(int startingNum)
    {
        num = startingNum;
        totalCount=0;
    }


//returns the amount of calculated primes. . .theoretically at least
    public int getStartCount(){
        return startCount;
    }

    public int getIntervalCount(){
        return intervalCount;
    }

    public long getTotalCount(){
        return totalCount;
    }

    //returns a prime list from 0 to startingNum
    public int[] sieveOfEratosthenes(int numOfPrimes)
    {
        startCount = 0;
        int[] primeArray = new int[(numOfPrimes)];

        //boolean list for all odd numbers
        //if index = n, actual number = 2n+1
        boolean[] boolOddArray = new boolean[(int)(num+1)/2];
        boolOddArray[0] = true;


        //bool[0] is true because 1 is not prime

        //starting with prime = 3

        //if (number = prime){
        //   all multiples of number are now not prime
        //  }
        //repeat for all odd ints below sqrt(n)

        for (Integer oIndex = 1; 2*oIndex+1 < (int) (Math.sqrt(num))+1; oIndex++) 
        {
          if (!boolOddArray[oIndex]) {
  
            for (int comIndex = (((2*oIndex)+1)*((2*oIndex)+1)-1)/2; comIndex<(num+1)/2; comIndex+=(2*oIndex)+1)
            {
                boolOddArray[comIndex] = true;
            }
          }
        }



        //if(number = prime)
        // put number in primeArray
        for(int oddIndex = 0; oddIndex<((num+1)/2); oddIndex++){
            if(!boolOddArray[oddIndex]){
                primeArray[startCount] = 2*oddIndex+1;
                startCount++;
                totalCount++;
            }
        }

        

        //print prime list
/*
        for(int n =0; n<count; n++)
        {
            System.out.println(primeArray[n]);
        }
       
*/
        return primeArray;
    }
    
//returns a LinkedList of primes in between start and start+add
    public long[] sieveFindInterval(long start, int add, int[] primeArray, int primeArrayLength)
    {

        intervalCount = 0;

        boolean[] boolAddArray = new boolean[(int)(add+1/2)];
        for (int pIndex = 0; pIndex<primeArray.length; pIndex++ )
        {
            Integer prime = primeArray[pIndex];
            if (prime > (int)Math.sqrt(start+add)+1)
            {
                break;
            }

            long multiple = start - (start%prime) + prime;
            if(multiple%2==0)
            {
                multiple += prime;
            }
            for (long composite = multiple; composite< start+add; composite+=2*prime)
            {
                boolAddArray[(int)(composite-start-1)/2] = true;
            }
        }

        long[] primeList = new long[(primeArrayLength)];

        for(Integer oddIndex =0; oddIndex<(add/2); oddIndex++)
        {
            if(!boolAddArray[oddIndex]){
                primeList[intervalCount] = (2*oddIndex)+1+start;
                intervalCount ++;
                totalCount++;
            }
        }
        int numOfZeros = 0;
        for(int removeZeros = primeList.length-1; removeZeros > 0; removeZeros--){
            if(!(primeList[removeZeros]==0)){
                break;
            }
            numOfZeros++;
        }

        long[] newPrimeList = new long[(primeList.length - numOfZeros)];
        for(int newArray = 0; newArray<newPrimeList.length; newArray++){
            newPrimeList[newArray] = primeList[newArray];
        }
//        System.out.println(intervalCount + " is the amount of primes in interval " + start + " to " + (start+add));
        return newPrimeList;
    }
}

