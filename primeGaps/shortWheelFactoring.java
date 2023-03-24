package primeGaps;

import java.util.ArrayList;
import java.util.List;

public class shortWheelFactoring {

	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		//mod 6 p = 3
		short[] wheelMod6 = makeWheel();
		//mod 30 p = 7
		short[] wheelMod30 = biggerWheel(wheelMod6, 6);
//        mod 210, p = 11
		short[] wheelMod210 = biggerWheel(wheelMod30, 30);
//        mod 2310
		short[] wheelMod2310 = biggerWheel(wheelMod210, 210);
//
//		biggerWheel is as far as you can go without hitting a short overflow
		short[] biggerWheel = biggerWheel(wheelMod2310, 2310);

        short[] biggererWheel = biggerWheel(biggerWheel, 2310*13);

        short[] biggestWheel = biggerWheel(biggererWheel, 2310*13*17);

        short[] biggesterWheel = biggerWheel(biggestWheel, 2310*13*17*19);

		for (int i = 0; i<10; i++){
			for(short prime: wheelMod6){
				System.out.print((prime+6*i) + "   ");
			}
			System.out.println();
		}

		int wheelSize = 17*13*11*7*5*3*2*1;
		System.out.println("count = " + biggererWheel.length);
		System.out.println("percent of number not prime = %" + (1.0-((double)biggererWheel.length/wheelSize))*100);

		final long endTime = System.currentTimeMillis();
		System.out.println("Total time elapsed: " + (endTime-startTime) + " milliseconds");

		List<Integer> howToAdd7 = new ArrayList<Integer>();
		for (int i = 0; i<30; i++){
			int numMod6 = (5*i)%6;
			System.out.print(numMod6 + " | ");
			for (short num: wheelMod6){
				if (numMod6 == num){
					howToAdd7.add(i);
				}
			}
		}
		System.out.println();
		for(Integer n: howToAdd7){
			System.out.println(n*5);
		}

		System.out.println("---------------------------------");

//		fuck how do i figure out where counter should start
//		especially for wheel mod 30
//		7mod6=1 so counter is 1 plus where the current mod is?
		int[] mod6GapWheel = new int[]{2, 4};
		int p = 7;
		int notCheckedComposite = 49;
		int counter = 1;
		int distance = 0;
		while (notCheckedComposite < 200){
			int whatToAdd = mod6GapWheel[counter%mod6GapWheel.length];
			distance = whatToAdd;
			notCheckedComposite += distance*p;
			System.out.print(notCheckedComposite + "  ");
			System.out.println((notCheckedComposite-1)/2);
			counter++;
		}

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



	public static int[] biggerIntWheel(short[] wheel, int totalWheelSize){
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
		int[] bigWheel = new int[bigWheelSize];
		for (int num = 0; num < wheelScaling; num++) {
			for (short prime : wheel) {
				if( ((prime + (wheelSize * num))%wheelScaling != 0) ){
					bigWheel[index] = prime + (wheelSize * num);
					index++;
				}
			}
		}


		return bigWheel;
	}



	public static int[] biggerIntWheel(int[] wheel, int totalWheelSize){
		int wheelSize = totalWheelSize;
//        for(boolean isPrime: wheel){
//            System.out.print(isPrime + " ");
//        }
//        System.out.println();
//        shout get    false, true
//        should get   false, true, false, false, false, true

//        find largest num after 1
		int wheelScaling = wheel[1];
//        roll small wheel around big wheel
		int bigWheelSize = (wheel.length*wheelScaling) - wheel.length;

		int index = 0;
		int[] bigWheel = new int[bigWheelSize];
		for (int num = 0; num < wheelScaling; num++) {
			for (int prime : wheel) {
				if( ((prime + (wheelSize * num))%wheelScaling != 0) ){
					bigWheel[index] = prime + (wheelSize * num);
					index++;
				}
			}
		}


		return bigWheel;
	}
}
