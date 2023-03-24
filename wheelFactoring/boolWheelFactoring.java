

public class boolWheelFactoring {

    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        //mod 6 p = 3
        boolean[] firstWheel = makeWheel();
        //mod 30 p = 7
        boolean[] medWheel = biggerWheel(firstWheel);
//        mod 210, p = 11
        boolean[] bigmedWheel = biggerWheel(medWheel);
//        mod 2310
        boolean[] bigWheel = biggerWheel(bigmedWheel);

        boolean[] biggerWheel = biggerWheel(bigWheel);

        boolean[] biggererWheel = biggerWheel(biggerWheel);

        boolean[] biggestWheel = biggerWheel(biggererWheel);

        boolean[] biggesterWheel = biggerWheel(biggestWheel);




        int count = 0;
//        for (int i = 0; i<biggerWheel.length; i++){
//            if(biggerWheel[i]){
//                System.out.println(i);
//                count++;
//            }
//        }
        System.out.println("count = " + count);
        System.out.println("percent of number not prime = %" + (1.0-((double)count/biggestWheel.length))*100);

        final long endTime = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (endTime-startTime) + " milliseconds");
    }


    public static boolean[] makeWheel(){
        int primeCount = 0;

        int wheelSize = 2;

        boolean[] wheel = new boolean[]{false, true};
//        0  1  2  3   4  5
//        6  7  8  9  10 11
//        12 13 14 15 16 17
//        fill wheel with correct booleans


//        shout get    false, true
//        should get   false, true, false, false, false, true

//        find largest num after 1
        int wheelScaling = 3;
//        roll small wheel around big wheel
        int bigWheelSize = wheelSize*wheelScaling;

        boolean[] bigWheel = new boolean[bigWheelSize];
        for (int num = 0; num<bigWheelSize; num++){
            if (wheel[num%wheelSize]){
                bigWheel[num] = true;
                primeCount++;
            }
        }

//        scale small wheel up and get rid of things that add
        for (int smallIndex = 0; smallIndex<wheelSize; smallIndex++){
            if (wheel[smallIndex]){
                if (bigWheel[smallIndex*wheelScaling]){
                    bigWheel[smallIndex*wheelScaling] = false;
                    primeCount--;
                }
            }
        }

        return bigWheel;

//        put primes from wheel into array
//        int[] primes = new int[primeCount];
//        int indexCount = 0;
//        for(int index = 0; index<bigWheelSize; index++) {
//            if (bigWheel[index]) {
//                primes[indexCount] = index;
//                indexCount++;
//            }
//        }
//        return primes;
    }



    public static boolean[] biggerWheel(boolean[] wheel){
        int wheelSize = wheel.length;
//        for(boolean isPrime: wheel){
//            System.out.print(isPrime + " ");
//        }
//        System.out.println();
//        shout get    false, true
//        should get   false, true, false, false, false, true

//        find largest num after 1
        int wheelScaling = 2;
        while (!wheel[wheelScaling%wheelSize]){
            wheelScaling++;
        }
        System.out.println("wheelScaling = " + wheelScaling);
//        roll small wheel around big wheel
        int bigWheelSize = wheelSize*wheelScaling;
        System.out.println("bigWheelSize = " + bigWheelSize);
        boolean[] bigWheel = new boolean[bigWheelSize];
        for (int num = 0; num<bigWheelSize; num++){
            if (wheel[num%wheelSize]){
                bigWheel[num] = true;
            }
        }

//        scale small wheel up and get rid of things that add
        for (int smallIndex = 0; smallIndex<wheelSize; smallIndex++){
            if (wheel[smallIndex]){
                if (bigWheel[smallIndex*wheelScaling]){
                    bigWheel[smallIndex*wheelScaling] = false;
                }
            }
        }

        return bigWheel;
    }
}