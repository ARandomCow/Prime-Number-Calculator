
## 🚀 About Me
Hi! I am a computational physics student at the University of Illinois at Urbana Champaign, and this is a small passion project I worked on in highschool. I am not a Github professional, so any advice to make my work nicer/cleaner/better would be greatly appreciated.


# Prime Number Calculator

This project calculates prime numbers very quickly using a variety of methods, and then outputs them into some sort of file.

In this project I have a 2 of working directories. I have PrimeSieve, which sieves regular primes and puts them into a CSV. And I have PrimeGaps, which uses a slightly different algorithm to find and store the gaps between primes for storage optimization purposes. 

In all projects, many lines of code are commented out either because they're for debugging purposes or because sometimes you want the code to run without writing 50GB of prime numbers to your harddrive.

# Prime Gaps (Current Project)

In this directory are two different main methods, multithreadedPrimeGaps.java and primeGapCalc.java. Both files create an initial array of prime numbers in memory and then use a segmented Sieve of Eratosthenes to progressively calculate prime numbers and write them to a CSV. 

multithreadedPrimeGaps uses javas native multithreading feature to allow the user to calculate and write primes in order on a chosen amount of threads.

I tried in both files to write the primes as shorts to a bin file instead of a csv to save mor e storage, but I never was able to correctly read the shorts from the bins

# Prime Sieve (Out of date/uses too much storage to be useful)
The PrimeSieve directory is an IntelliJ file that uses a less optimized but similar algorithm as primeGaps to find primes and write them to a csv. The main method is in primeCalc.java

# Branch: 10TB_Test

This branch is not meant to be touched because multithreadedPrimeGaps.java is 100% ready to go for a test on a large server with a computationally powerful machine. Just run the code, it automatically asks for how many threads you want and how large each segmented sieve should be, and let it rip.

# Branch: wheelFactoring (WIP)

This branch is meant for the inevitable implementation of wheel factoring, an algorithm that when implemented can significantly increase the speed of prime sieving for very large numbers by automatically removing over 50% of numbers as potential prime numbers before starting the sieve of Eratosthenes. However, it doesn't work. 

The WheelFactoring directory contains just method files for the wheel factoring algorithms themselves, while testWheelFactoring in the Testing directory and primeGaps contain actual implementations of wheel factoring in the prime sieve (that don't really work). 
