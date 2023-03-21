import pandas as pd
import numpy as np
import time


chunk_size = 1024  # Define the chunk size in bytes

# Open the binary file in binary mode
with open('0mil_to_1mil.bin', 'rb') as f:
    while True:
        # Read a chunk of data from the file
        chunk = f.read(chunk_size)
        
        # Stop the loop if we have reached the end of the file
        if not chunk:
            break
        
        # Process the chunk of data that we have read
        data = np.frombuffer(chunk, dtype=np.int16)
        
        # Do something with the data here...
        for i in range (2):
            print(data[i])
        
# Close the file
f.close()






