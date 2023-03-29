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
		short[] wheelMod30030 = biggerWheel(wheelMod2310, 2310);

        short[] wheelMod510510 = biggerWheel(wheelMod30030, 2310*13);

        short[] wheelMod9699690 = biggerWheel(wheelMod510510, 2310*13*17);

        short[] wheelMod223092870 = biggerWheel(wheelMod9699690, 2310*13*17*19);

		short[] indexWheelMod6 = new short[]{0,2};

		short[] indexWheelMod30 = biggerIndexWheel(indexWheelMod6, 6);

		short[] indexGapWheelMod30 = biggerIndexGapWheel(indexWheelMod6, 6);

		for (int i = 0; i<1; i++){
			for(short prime: wheelMod30){
				System.out.print(((prime-1)/2) + "   ");
			}
			System.out.println();
		}

		for (int i = 0; i<1; i++){
			for(short prime: indexGapWheelMod30){
				System.out.print((prime) + "   ");
			}
			System.out.println();
		}

		int wheelSize = 29*23*17*13*11*7*5*3*2*1;
		System.out.println("count = " + wheelMod223092870.length);
		System.out.println("percent of number not prime = %" + (1.0-((double)wheelMod223092870.length/wheelSize))*100);

		final long endTime = System.currentTimeMillis();
		System.out.println("Total time elapsed: " + (endTime-startTime) + " milliseconds");

//		List<Integer> howToAdd7 = new ArrayList<Integer>();
//		for (int i = 0; i<30; i++){
//			int numMod6 = (5*i)%6;
//			System.out.print(numMod6 + " | ");
//			for (short num: wheelMod6){
//				if (numMod6 == num){
//					howToAdd7.add(i);
//				}
//			}
//		}
//		System.out.println();
//		for(Integer n: howToAdd7){
//			System.out.println(n*5);
//		}
//
//		System.out.println("---------------------------------");
//
////		fuck how do i figure out where counter should start
////		especially for wheel mod 30
////		7mod6=1 so counter is 1 plus where the current mod is?
//		int[] mod6GapWheel = new int[]{2, 4};
//		int p = 7;
//		int notCheckedComposite = 49;
//		int counter = 1;
//		int distance = 0;
//		while (notCheckedComposite < 200){
//			int whatToAdd = mod6GapWheel[counter%mod6GapWheel.length];
//			distance = whatToAdd;
//			notCheckedComposite += distance*p;
//			System.out.print(notCheckedComposite + "  ");
//			System.out.println((notCheckedComposite-1)/2);
//			counter++;
//		}

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

	public static short[] biggerIndexWheel(short[] smallIndexWheel, int totalWheelSize){
		int wheelSize = totalWheelSize/2;
//        for(boolean isPrime: wheel){
//            System.out.print(isPrime + " ");
//        }
//        System.out.println();
//        shout get    false, true
//        should get   false, true, false, false, false, true

//        find largest num after 1
		int wheelScaling = (smallIndexWheel[1]*2)+1;
//        roll small wheel around big wheel
		int bigWheelSize = (smallIndexWheel.length*wheelScaling) - smallIndexWheel.length;

//		rolling the wheel
		int index = 0;
		short[] bigWheel = new short[bigWheelSize];
		for (int num = 0; num < wheelScaling; num++) {
			for (short prime : smallIndexWheel) {
				int actualPrime = ((prime + (wheelSize * num))*2)+1;
				if( (actualPrime%wheelScaling) != 0 ){
					bigWheel[index] = (short) (prime + (wheelSize * num));
					index++;
				}
			}
		}


		return bigWheel;
	}


	public static short[] biggerIndexGapWheel(short[] wheel, int totalWheelSize){
		int wheelSize = totalWheelSize/2;
		int wheelScaling = (wheel[1]*2)+1;
		int bigWheelSize = (wheel.length*wheelScaling) - wheel.length;
		int gapSetter = 0;

//		rolling the wheel
		int index = 0;
		int num = 0;
		short[] bigWheel = new short[bigWheelSize];
		while(num<wheelScaling) {
			for (short prime : wheel) {
				if( ((((prime + (wheelSize * num))*2)+1)%wheelScaling != 0) ){
					bigWheel[index] = (short) (prime + (wheelSize * num) - gapSetter);
					gapSetter = prime + (wheelSize * num);
					index++;
				}
			}
			num++;
		}
			num--;
		bigWheel[0] = (short)((wheelSize*wheelScaling) - (wheel[wheel.length-1] + (wheelSize * num)));


		return bigWheel;
	}



	public static int[] biggerIndexGapWheelInt(int[] wheel, int totalWheelSize){
		int wheelSize = totalWheelSize/2;
		int wheelScaling = (wheel[1]*2)+1;
		int bigWheelSize = (wheel.length*wheelScaling) - wheel.length;
		int gapSetter = 0;

//		rolling the wheel
		int index = 0;
		int num = 0;
		int[] bigWheel = new int[bigWheelSize];
		while(num<wheelScaling) {
			for (int prime : wheel) {
				if( ((((prime + (wheelSize * num))*2)+1)%wheelScaling != 0) ){
					bigWheel[index] = (prime + (wheelSize * num) - gapSetter);
					gapSetter = prime + (wheelSize * num);
					index++;
				}
			}
			num++;
		}
		num--;
		bigWheel[0] = ((wheelSize*wheelScaling) - (wheel[wheel.length-1] + (wheelSize * num)));


		return bigWheel;
	}


	public static int[] biggerIndexGapWheelInt(short[] wheel, int totalWheelSize){
		int wheelSize = totalWheelSize/2;
		int wheelScaling = (wheel[1]*2)+1;
		int bigWheelSize = (wheel.length*wheelScaling) - wheel.length;
		int gapSetter = 0;

//		rolling the wheel
		int index = 0;
		int num = 0;
		int[] bigWheel = new int[bigWheelSize];
		while(num<wheelScaling) {
			for (short prime : wheel) {
				if( ((((prime + (wheelSize * num))*2)+1)%wheelScaling != 0) ){
					bigWheel[index] = (prime + (wheelSize * num) - gapSetter);
					gapSetter = prime + (wheelSize * num);
					index++;
				}
			}
			num++;
		}
		num--;
		bigWheel[0] = ((wheelSize*wheelScaling) - (wheel[wheel.length-1] + (wheelSize * num)));


		return bigWheel;
	}


	public static int[] biggerIndexWheelInt(short[] wheel, int totalWheelSize){
		int wheelSize = totalWheelSize/2;
//        for(boolean isPrime: wheel){
//            System.out.print(isPrime + " ");
//        }
//        System.out.println();
//        shout get    false, true
//        should get   false, true, false, false, false, true

//        find largest num after 1
		int wheelScaling = (wheel[1]*2)+1;
//        roll small wheel around big wheel
		int bigWheelSize = (wheel.length*wheelScaling) - wheel.length;

//		rolling the wheel
		int index = 0;
		int[] bigWheel = new int[bigWheelSize];
		for (int num = 0; num < wheelScaling; num++) {
			for (short prime : wheel) {
				if( ((((prime + (wheelSize * num))*2)+1)%wheelScaling != 0) ){
					bigWheel[index] = (prime + (wheelSize * num));
					index++;
				}
			}
		}


		return bigWheel;
	}



	public static int[] biggerIndexWheelInt(int[] wheel, int totalWheelSize){
		int wheelSize = totalWheelSize/2;
//        for(boolean isPrime: wheel){
//            System.out.print(isPrime + " ");
//        }
//        System.out.println();
//        shout get    false, true
//        should get   false, true, false, false, false, true

//        find largest num after 1
		int wheelScaling = (wheel[1]*2)+1;
//        roll small wheel around big wheel
		int bigWheelSize = (wheel.length*wheelScaling) - wheel.length;

//		rolling the wheel
		int index = 0;
		int[] bigWheel = new int[bigWheelSize];
		for (int num = 0; num < wheelScaling; num++) {
			for (int prime : wheel) {
				if( ((((prime + (wheelSize * num))*2)+1)%wheelScaling != 0) ){
					bigWheel[index] = (prime + (wheelSize * num));
					index++;
				}
			}
		}


		return bigWheel;
	}
}

