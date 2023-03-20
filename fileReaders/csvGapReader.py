import pandas as pd
import numpy as np
import time

chunk_size = 100000
prime = 3
# Create a TextFileReader object to read the CSV file in chunks
tic = float(time.perf_counter())
csv_reader = pd.read_csv('0primeList.csv', chunksize=chunk_size, dtype=int)

# Iterate over each chunk of data
count = 0
for chunk in csv_reader:
    count+=1
    chunkGapList = [int(i) for sublist in chunk.values.tolist() for i in sublist]
    for gap in chunkGapList:
        prime +=gap
        
    if count%100==0:
        print(prime)

    if chunk.empty:
        break
toc = float(time.perf_counter())
print('total time: ')
print(toc - tic)