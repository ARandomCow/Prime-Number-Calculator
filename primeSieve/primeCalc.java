
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
//vague instructions
//primeMax, add, and repeat are the two important variables to mess around with
//primeMax determines to what number you find the first prime numbers (0, MAX)
//numOfPrimes will be the size of your starting array thatll be used for the rest of the sieves
//add determines the interval that you'll be finding more prime numbers in (start, start+add)
//primeArrayLength determines the size of your prime carrying array. Make sure it's big enough.
//repeat is the simplest one, it just determines how many times to repeat the add function
//aka how many times you increase your prime number interval by 'add'
import java.io.PrintWriter;

public class primeCalc extends sieveMethods
{
    public static void main( String[] args ) throws FileNotFoundException
    {

        final long startTime = System.currentTimeMillis();

        //--------------------------------------------------------------------------------------------------
                Integer primeMax = 10000;
                int numOfPrimes = 1228;
        //its the number of primes - 1 (because this algorithm doesnt worry about 2 or multiples of it)
        //primeMax = 100, numOfPrimes = 24
        //if primeMax = 1,000 then numOfPrimes = 167
        //if primeMax = 10,000 then numOfPrimes = 1228
        //if primeMax = 1,000,000, then numOfPrimes = 78,497
        // if 100,000,000, then 5761454
        //if primeMax = 1,000,000,000 then numOfPrimes = 50847533
        //--------------------------------------------------------------------------------------------------
        
                sieveMethods erat = new sieveMethods(primeMax);
        
                int[] primeArray = erat.sieveOfEratosthenes(numOfPrimes);
        
        //        for(int i =0; i<primeArray.length; i++){
        //            System.out.println(primeArray[i]);
        //        }
        
                long startCount = erat.getStartCount();
                System.out.println(startCount + " is the amount of primes below " + primeMax);
        
                final long firstEndTime = System.currentTimeMillis();
                System.out.println("Initial execution time: " + (firstEndTime - startTime) + " milliseconds");
        
                long start = primeMax;
                int add = 500000;
                int primeArrayLength = 50000;
                int repeat = 1;
                int intervalCount = 0;
                long totalCount = erat.getTotalCount();
                int checkInterval = 2;
                long bufferstart = start;
                File csvFile = new File("primeList.csv");
                PrintWriter out = new PrintWriter(csvFile);
                long intervalStartTime = System.currentTimeMillis();
               


                FileOutputStream fileOs = new FileOutputStream("primes.bin");
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(fileOs);
                    for(int i = 0; i<primeArray.length; i++){
                        oos.writeInt(primeArray[i]);
                    }
                    } 
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


/**/
                out.println("1 | 2");
                for(int i=0; i<primeArray.length; i++){
                    out.println((i+2) + " | " + primeArray[i]);
                }
/**/                
                //repeat should be initialized as 1 + (primeMax/add)
                while (totalCount < 3000000)
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
        
/**/
                    for(int i=0; i < primeList.length; i++){
                        out.println((totalCount+i+2) + " | " + primeList[i]);
                    }
/**/                    
                    try {
                            ObjectOutputStream loos = new ObjectOutputStream(fileOs);
                            for(int i=0; i < primeList.length; i++){
                                loos.writeLong(primeList[i]);
                                }
                        }
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

        
                    repeat++;
                    totalCount = erat.getTotalCount();
                    intervalCount = primeList.length+5000;
                    primeArrayLength = intervalCount;
        
                }

                out.close();

                final long finalEndTime = System.currentTimeMillis();
                System.out.println("Final execution time: " + (finalEndTime - startTime) + " milliseconds");
        
                totalCount = erat.getTotalCount();
                System.out.println("Total primes calculated: " + totalCount);
        
    }
}
