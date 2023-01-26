import time
import numpy as np
import csv
import pandas as pd

#primes = a list of booleans that initially start as all true
#as the method iterates, composite numbers will be labeled as false
#leaving only prime numbers as true

#for i in range of 2 to sqrt(n)+1
#if i is prime
#find all multiples of i from i^2 to n and make them false

#do this for all prime numbers and find all other prime numbers
def sieve_of_eratosthenes(n):
    primes = [False, True] * (int(n/2)+1)
    primes[0] = primes[1] = False
    for i in range(3, int(n**(1/2))+1):
        if primes[i]:
            for j in range(i*i, n+1, 2*i):
                primes[j] = False
    return primes

#@param primeList needs to be the list of prime numbers less than m
def sieve_add_n(start, n, primeList):
    primes = [False, True] * (start+n)
    for i in range(0, len(primeList)):
        if(primeList[i]<=int((start+n)**(1/2))+1):
            prime = primeList[i]
            multiple = start - (start%prime) + prime
            if multiple%2==0:
                multiple += prime
            for j in range(multiple, start+n, prime):
                primes[j] = False
    return primes




n = 100

#start = time.time()

primes = sieve_of_eratosthenes(n)
primelist = []

count = 0
for i in range(1, n, 2):
    if primes[i]:
        primelist.append(i)
        count += 1
        if count == n:
            break

#print(count)
#print(primelist)

#end = time.time()
#print(end - start)

#print('Primes from 0 to ' + str(n) + ' now collected')

morePrimes = sieve_add_n(n, n, primelist)
for i in range(n+1, n+n, 2):
    if morePrimes[i]:
        primelist.append(i)
        count +=1
#print(count)
print(primelist)
