package compressor;

import org.xerial.snappy.Snappy;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import compressor.Compressor; 
import compressor.Tools; 

public class App {

    static Compressor compressor = new Compressor(); 

    public static void main(String[] args) {
      
      //byte[] data = "abcdefghijklmnopqrstuvwxyz".getBytes("UTF-8"); 
      byte[] data = ("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + 
        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + 
        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + 
        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + 
        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").getBytes();
      
      //lz4DataTest(data);
      snappyFileTest();
    }

    public static void lz4DataTest(byte[] data) {
        
        // Compress Data
        System.out.println("\nCompressing Data...");
        byte[] compressed = compressor.compressDataLZ4(data, true);

        // Decompress Data
        System.out.println("\nDecompressing Data...");
        byte[] decompressed = compressor.decompressDataLZ4(compressed, true, data.length);

        // Check Decompress Inegrity
        System.out.println("\nCheck Decompress Inegrity...");
        Tools.compareByteArrays(data, decompressed);
    }

    public static void snappyFileTest() {

        String inputFileName = "/home/winston/Documents/GitHub/Prime-Calculator/compression/Java/compressor/app/src/main/java/compressor/test.txt"; 
        String outputFileName = "/home/winston/Documents/GitHub/Prime-Calculator/compression/Java/compressor/app/src/main/java/compressor/compressed.txt";
        String decompressOutputName = "/home/winston/Documents/GitHub/Prime-Calculator/compression/Java/compressor/app/src/main/java/compressor/decompressed.txt";

        // Compress File
        System.out.println("\nCompressing file: " + inputFileName);

        compressor.compressFile(inputFileName, outputFileName);

        // Decompress File
        System.out.println("\nDecompressing file: " + outputFileName);

        String inputName = "Test.txt";

        compressor.decompressFile(outputFileName + ".snappy", decompressOutputName);
    }

}

