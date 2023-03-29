#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "lz4.h"

int main() {
    const char* input_filename = "0bil_to_1bil.bin";
    const char* output_filename = "output.bin.lz4";
    
    // Get size of input file in bytes and print in gigabytes
    FILE* input_file = fopen(input_filename, "rb");
    fseek(input_file, 0L, SEEK_END);
    long int input_size = ftell(input_file);
    fclose(input_file);
    printf("\nBin Size: %.3f gigabytes\n", (double) input_size / (1024*1024*1024));
    
    // Read input file and measure time
    printf("\nReading!\n");
    clock_t start_read_time = clock();
    input_file = fopen(input_filename, "rb");
    char* input_data = (char*) malloc(input_size);
    fread(input_data, sizeof(char), input_size, input_file);
    fclose(input_file);
    clock_t end_read_time = clock();
    double read_time = ((double) (end_read_time - start_read_time)) / CLOCKS_PER_SEC * 1000.0;
    printf("Read time: %.3f ms\n", read_time);
    
    // Compress input data and measure time
    printf("\nCompressing!\n");
    clock_t start_compress_time = clock();
    int max_compressed_size = LZ4_compressBound(input_size);
    char* compressed_data = (char*) malloc(max_compressed_size);
    int compressed_size = LZ4_compress_default(input_data, compressed_data, input_size, max_compressed_size);
    free(input_data);
    clock_t end_compress_time = clock();
    double compress_time = ((double) (end_compress_time - start_compress_time)) / CLOCKS_PER_SEC * 1000.0;
    printf("Compress time: %.3f ms\n", compress_time);
    
    // Write compressed data to output file and measure time
    printf("\nWriting!\n");
    clock_t start_write_time = clock();
    FILE* output_file = fopen(output_filename, "wb");
    fwrite(compressed_data, sizeof(char), compressed_size, output_file);
    fclose(output_file);
    free(compressed_data);
    clock_t end_write_time = clock();
    double write_time = ((double) (end_write_time - start_write_time)) / CLOCKS_PER_SEC * 1000.0;
    printf("Write time: %.3f ms\n", write_time);
    
    // Get size of output file in bytes and print in gigabytes
    input_file = fopen(output_filename, "rb");
    fseek(input_file, 0L, SEEK_END);
    long int output_size = ftell(input_file);
    fclose(input_file);
    printf("\nCompressed Bin Size: %.3f gigabytes\n", (double) output_size / (1024*1024*1024));
    
    return 0;
}
