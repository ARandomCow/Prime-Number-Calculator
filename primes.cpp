// C++ program to print all primes smaller than or equal to
// n using Sieve of Eratosthenes
#include <bits/stdc++.h>
#include <chrono>
#include <iostream>
#include <fstream>
using namespace std;
using namespace std::chrono;

void SieveOfEratosthenes(int n)
{
	// Create a boolean array "prime[0..n]" and initialize
	// all entries it as true. A value in prime[i] will
	// finally be false if i is Not a prime, else true
	bool prime[n + 1];
	memset(prime, true, sizeof(prime));

	for (int p = 2; p * p <= n; p++) {
		// If prime[p] is not changed, then it is a prime
		if (prime[p] == true) {
			// Update all multiples of p greater than or
			// equal to the square of it numbers which are
			// multiple of p and are less than p^2 are
			// already been marked
			for (int i = p * p; i <= n; i += p)
				prime[i] = false;
		}
	}

	// Print all prime numbers
	for (int p = 2; p <= n; p++)
		if (prime[p])
			cout << p << " ";
}

void FermatsTheorem(int n) {

	for (int p = 2; p <= n; p++) {
		int num = (pow(2,p)-1);

		if (num == 1) {
			cout << p << " ";
		}
	}
}

void SieveOfAtkin(int limit)
{
    // Initialise the sieve array
    // with initial false values
    bool sieve[limit];
    for (int i = 0; i <= limit; i++)
        sieve[i] = false;
 
    // 2 and 3 are known to be prime
    if (limit > 2)
        sieve[2] = true;
    if (limit > 3)
        sieve[3] = true;
   
    /* Mark sieve[n] is true if one
       of the following is true:
    a) n = (4*x*x)+(y*y) has odd number of
       solutions, i.e., there exist
       odd number of distinct pairs (x, y)
       that satisfy the equation and
        n % 12 = 1 or n % 12 = 5.
    b) n = (3*x*x)+(y*y) has odd number of
       solutions and n % 12 = 7
    c) n = (3*x*x)-(y*y) has odd number of
       solutions, x > y and n % 12 = 11 */
    for (int x = 1; x * x <= limit; x++) {
        for (int y = 1; y * y <= limit; y++) {
 
            // Condition 1
            int n = (4 * x * x) + (y * y);
            if (n <= limit
                && (n % 12 == 1 || n % 12 == 5))
                sieve[n] ^= true;
 
            // Condition 2
            n = (3 * x * x) + (y * y);
            if (n <= limit && n % 12 == 7)
                sieve[n] ^= true;
 
            // Condition 3
            n = (3 * x * x) - (y * y);
            if (x > y && n <= limit
                && n % 12 == 11)
                sieve[n] ^= true;
        }
    }
 
    // Mark all multiples
    // of squares as non-prime
    for (int r = 5; r * r <= limit; r++) {
        if (sieve[r]) {
            for (int i = r * r; i <= limit; i += r * r)
                sieve[i] = false;
        }
    }
 
    // Print primes using sieve[]
    for (int a = 1; a <= limit; a++)
        if (sieve[a])
            cout << a << " ";
    cout << "\n";
}

bool is_prime(int n) {
    if (n <= 1) {
        return false;
    }
    for (int i = 2; i <= sqrt(n); i++) {
        if (n % i == 0) {
            return false;
        }
    }
    return true;
}


// Driver Code
int main() {
	long long n = 0LL;
    long long limit = 1000000000000LL;

    long long primeCounter = 0LL;

    ofstream myfile("test.txt");

    auto start = high_resolution_clock::now();
    while (n < limit) {
    
        if (is_prime(n)) {
            primeCounter++;
            cout << primeCounter << "\n"; 

             if (myfile.is_open())
            {
                    myfile << n << "\n";
                    //myfile.close();
            }
        }
        //cout << primeCounter 
        n++; 
    }

    auto stop = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(stop - start);
 
    cout << "\nExecution Time: " << duration.count() << " Microseconds\n";
    myfile << duration.count() << " Microseconds"; 
    myfile.close(); 

    cout << "Primes Found: " << primeCounter; 

	return 0;
}
