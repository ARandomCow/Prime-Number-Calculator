

public class shortWheelFactoring {

	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		//mod 6 p = 3
		short[] firstWheel = makeWheel();
		//mod 30 p = 7
		short[] medWheel = biggerWheel(firstWheel, 6);
//        mod 210, p = 11
		short[] bigmedWheel = biggerWheel(medWheel, 30);
//        mod 2310
		short[] bigWheel = biggerWheel(bigmedWheel, 210);
//
//		biggerWheel is as far as you can go without hitting a short overflow
		short[] biggerWheel = biggerWheel(bigWheel, 2310);

        short[] biggererWheel = biggerWheel(biggerWheel, 2310*13);

        short[] biggestWheel = biggerWheel(biggererWheel, 2310*13*17);

        short[] biggesterWheel = biggerWheel(biggestWheel, 2310*13*17*19);

		for(short prime: biggerWheel){
			System.out.println(prime);
		}

		int wheelSize = 23*19*17*13*11*7*5*3*2*1;
		System.out.println("count = " + biggesterWheel.length);
		System.out.println("percent of number not prime = %" + (1.0-((double)biggesterWheel.length/wheelSize))*100);

		final long endTime = System.currentTimeMillis();
		System.out.println("Total time elapsed: " + (endTime-startTime) + " milliseconds");
	}


	public static short[] makeWheel() {
		int primeCount = 0;

		int wheelSize = 2;

		short[] wheel = new short[]{1};
//        0  1  2  3   4  5
//        6  7  8  9  10 11
//        12 13 14 15 16 17
//        fill wheel with correct booleans


//        shout get    false, true
//        should get   false, true, false, false, false, true

//        find largest num after 1
		int wheelScaling = 3;
//        roll small wheel around big wheel
		int bigWheelSize = ((wheelSize * wheelScaling) - wheelSize) / 2;

		int index = 0;
		short[] bigWheel = new short[bigWheelSize];
		for (int num = 0; num < wheelScaling; num++) {
			for (short prime : wheel) {
				if (prime * wheelScaling != (prime + (wheelSize * num))) {
					bigWheel[index] = (short) (prime + (wheelSize * num));
					index++;
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


	public static short[] biggerWheel(short[] wheel, int totalWheelSize) {
		int wheelSize = totalWheelSize;
//        for(boolean isPrime: wheel){
//            System.out.print(isPrime + " ");
//        }
//        System.out.println();
//        shout get    false, true
//        should get   false, true, false, false, false, true

//        find largest num after 1
		short wheelScaling = wheel[1];
//        roll small wheel around big wheel
		int bigWheelSize = (wheel.length*wheelScaling) - wheel.length;

		int index = 0;
		short[] bigWheel = new short[bigWheelSize];
		for (int num = 0; num < wheelScaling; num++) {
			for (short prime : wheel) {
				if( ((prime + (wheelSize * num))%wheelScaling != 0) ){
					bigWheel[index] = (short) (prime + (wheelSize * num));
					index++;
				}
			}
		}


		return bigWheel;
	}
}

