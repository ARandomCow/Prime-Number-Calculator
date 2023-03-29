package compressor;

import java.io.*;
import org.xerial.snappy.*;

public class Compressor {
    
    public Compressor() {

    }

    public void compressFile(String inputName, String outputName) {

        File inputFile = new File(inputName);
        File outputFile = new File(outputName + ".snappy"); 

        // Read input file contents into a byte array
        byte[] input; 
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            input = inputStream.readAllBytes();
        }
        catch (IOException e) {
            System.err.println("\nError reading input file: " + e.getMessage()); 
            return; 
        }

        // Compress input data 
        byte[] compressed;
        try {
            compressed = Snappy.compress(input);
        }

        catch (IOException e) {
            System.err.println("\nError compressing data: " + e.getMessage());
            return;
        }

        // Write compressed data to output file
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(compressed);
        }
        catch (IOException e) {
            System.err.println("\nError writing output file: " + e.getMessage());
            return; 
        }

            System.out.println("\nCompressed size: " + compressed.length);
            System.out.println("Output file: " + outputFile.getAbsolutePath());
    }

    public void decompressFile(String inputName, String outputName) {
        File inputFile = new File(inputName);
        File outputFile = new File(outputName); 

        // Read input file contents into a byte array
        byte[] input; 
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            input = inputStream.readAllBytes();
        }
        catch (IOException e) {
            System.err.println("\nError reading input file: " + e.getMessage()); 
            return; 
        }

        // Decompress input data
        byte[] decompressed;
        try {
            decompressed = Snappy.uncompress(input);
        }

        catch (IOException e) {
            System.err.println("\nError decompressing data: " + e.getMessage());
            return;
        }

        // Write decompressed data to output file
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(decompressed);
        }
        catch (IOException e) {
            System.err.println("\nError writing output file: " + e.getMessage());
            return; 
        }

            System.out.println("Output file: " + outputFile.getAbsolutePath());


    }
}
