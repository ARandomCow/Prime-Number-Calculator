#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <bzlib.h>
#include <time.h>
#include "bzipCompress.h"

int main() {

    char input_filename[] = "/home/winston/Documents/GitHub/Prime-Calculator/compression/0bil_to_1bil.csv";

    // Compress Using same shit
    //compressFile("0bil_to_1bil.csv"); 

    // Decompress Using same shit 
    decompressFile("output.bz2", "decompressed_output.csv"); 
    // Compare input and output 

    return 0; 

}