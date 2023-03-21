#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <bzlib.h>
#include <time.h>
#include "bzipCompress.h"

int Main() {

    char* input_filename = "/home/winston/Documents/GitHub/Prime-Calculator/compression/0bil_to_1bil.csv";
    char* output_filename = "/home/winston/Documents/GitHub/Prime-Calculator/compression/output.bin";

    // Compress Using same shit -- Is the first argument supposed to be a 1? No Clue 
    compressFile(input_filename); 

    // Decompress Using same shit 
    decompressFile(output_filename); 
    // Compare input and output 

    return 0; 

}