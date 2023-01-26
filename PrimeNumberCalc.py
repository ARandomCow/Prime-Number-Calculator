import time
import numpy
import csv
import pandas as pd

#sieve of eratosthenes
#primes = a list of booleans with all odd index values true
#as the method iterates, composite numbers will be labeled as false
#leaving only prime numbers as true

#do this for all prime numbers below sqrt(n) to find all prime numbers below n
#@param an int n for which you will find all primes less than n
#@result a list of booleans where all prime indexes will be True
#and all composite indexes will be False

#2 is skipped as a prime number for efficiency's sake

def sieve_of_eratosthenes(n):
    primes = [False, True] * (int(n/2)+1)
    primes[0] = primes[1] = False

#for i in range of 2 to sqrt(n)+1
#if i is prime
#find all multiples of i from i^2 to n and make them false

    for i in range(3, int(n**(1/2))+1):
        if primes[i]:
            for j in range(i*i, n+1, 2*i):
                primes[j] = False
    return primes

n = 1000000
primes = sieve_of_eratosthenes(n)
primelist = [0, 2]
#2 is there to make up for the sieve method
#0 is there to line up the csv index so that index 1 is the 1st prime

start = time.time()
count = 0

#turns your list of booleans into a list of actual numbers
#takes all indexes of True and puts them into a list
for i in range(3, n, 2):
    if primes[i]:
        primelist.append(i)
        count += 1

#turns list of primes into a csv ready format
primeCSV = pd.DataFrame(primelist)
print('DataFrame:,', primeCSV)
print(count)
#csv_data = primeCSV.to_csv()
#print('\nCSV String:\n', csv_data)

#creates a .csv file test-primes and puts your list into test-primes
with open('test-primes.csv', 'w') as file:
    primeCSV.to_csv('test-primes.csv', sep='|')

end = time.time()
print(end - start)
