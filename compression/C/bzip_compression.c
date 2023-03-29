#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <bzlib.h>
#include <time.h>

int main(int argc, char* argv[]) {

    if (argc < 2) {
        printf("Please provide the name of the file to compress.\n");
        return 1;
    }

    char* input_filename = argv[1];
    char* output_filename = "output.bin.bz2";

    FILE* input_file = fopen(input_filename, "rb");
    if (input_file == NULL) {
        printf("Error: Cannot open input file.\n");
        return 1;
    }

    fseek(input_file, 0, SEEK_END);
    int input_size = ftell(input_file);
    fseek(input_file, 0, SEEK_SET);

    printf("\nBin Size: %f gigabytes\n", (double) input_size / (1024 * 1024 * 1024));

    char* input_data = (char*) malloc(sizeof(char) * input_size);
    fread(input_data, sizeof(char), input_size, input_file);
    fclose(input_file);

    printf("\nCompressing!\n");
    clock_t start_compress_time = clock();


    char* compressed_data = (char*) malloc(sizeof(char) * input_size);

    unsigned int compressed_size;
    BZ2_bzBuffToBuffCompress(compressed_data, &compressed_size, input_data, input_size, 9, 0, 0);

    clock_t end_compress_time = clock();
    double compress_time = (double) (end_compress_time - start_compress_time) / CLOCKS_PER_SEC * 1000;
    printf("Compress time: %f ms\n", compress_time);

    FILE* output_file = fopen(output_filename, "wb");
    if (output_file == NULL) {
        printf("Error: Cannot open output file.\n");
        return 1;
    }

    printf("\nWriting!\n");
    clock_t start_write_time = clock();

    fwrite(compressed_data, sizeof(char), compressed_size, output_file);
    fclose(output_file);

    clock_t end_write_time = clock();
    double write_time = (double) (end_write_time - start_write_time) / CLOCKS_PER_SEC * 1000;
    printf("Write time: %f ms\n", write_time);

    free(input_data);
    free(compressed_data);

    FILE* output_file_size = fopen(output_filename, "rb");
    if (output_file_size == NULL) {
        printf("Error: Cannot open output file.\n");
        return 1;
    }

    fseek(output_file_size, 0, SEEK_END);
    int output_size = ftell(output_file_size);
    fseek(output_file_size, 0, SEEK_SET);
    fclose(output_file_size);

    printf("\nCompressed Bin Size: %f gigabytes\n", (double) output_size / (1024 * 1024 * 1024));

    return 0;
}
