import pandas as pd
import numpy as np
import time

chunk_size = 1000
prime = 2
# Create a TextFileReader object to read the CSV file in chunks
tic = float(time.perf_counter())
csv_reader = pd.read_csv('00bil_to_10bil.csv', chunksize=chunk_size, dtype=int, header =None)
chunk = csv_reader  

# Iterate over each chunk of data
count = 0
for chunk in csv_reader:
    # print(chunk)
    count+=1
    chunkGapList = [int(i) for sublist in chunk.values.tolist() for i in sublist]
    for gap in chunkGapList:
        prime +=gap
        # print(prime)
        
    if count%1000==0:
        print(prime)

        # if chunk.empty:
        #     break
print("biggest prime = " + str(prime))
toc = float(time.perf_counter())
print('total time: ')
print(toc - tic)