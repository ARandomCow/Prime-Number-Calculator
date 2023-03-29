import lz4.frame
import time
import os

# Define input and output file names
input_filename = '0bil_to_1bil.bin'
output_filename = 'output.bin.lz4'

# Get size of input file in bytes and print in gigabytes
input_size = os.path.getsize(input_filename)
print(f"\nBin Size: {input_size / (1024**3)} gigabytes")

# Read input file and measure time
print("\nReading!")
start_read_time = time.perf_counter() 
with open(input_filename, 'rb') as input_file:
    input_data = input_file.read()
end_read_time = time.perf_counter()
read_time = (end_read_time - start_read_time) * 1000
print(f"Read time: {read_time:.3f} ms")

# Compress input data and measure time
print("\nCompressing!")
start_compress_time = time.perf_counter() 
compressed_data = lz4.frame.compress(input_data)
end_compress_time = time.perf_counter()
compress_time = (end_compress_time - start_compress_time) * 1000
print(f"Compress time: {compress_time:.3f} ms")

# Write compressed data to output file and measure time
print("\nWriting!")
start_write_time = time.perf_counter()
with open(output_filename, 'wb') as output_file:
    output_file.write(compressed_data)
end_write_time = time.perf_counter()
write_time = (end_write_time - start_write_time) * 1000
print(f"Write time: {write_time:.3f} ms")

# Get size of output file in bytes and print in gigabytes
output_size = os.path.getsize(output_filename)
print(f"\nCompressed Bin Size: {output_size / (1024**3)} gigabytes")
