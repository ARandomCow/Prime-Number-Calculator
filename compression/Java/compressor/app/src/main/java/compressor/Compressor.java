package compressor;

import java.io.*;
import org.xerial.snappy.*;
import net.jpountz.lz4.*; 

public class Compressor {
    
    public Compressor() {

    }

    public void compressDataLZ4(byte[] data, boolean debug) {
        
      // Compressor Object 
      LZ4Factory factory = LZ4Factory.nativeInstance();

      final int decompressedLength = data.length;

      if (debug) {
        System.out.println("\nDecompressed Length: " + decompressedLength);

      }

      // Start Stopwatch  
      long startTime = System.currentTimeMillis();


      // Compress Data
      LZ4Compressor compressor = factory.highCompressor(17); 
      int maxCompressedLength = compressor.maxCompressedLength(decompressedLength);
      byte[] compressed = new byte[maxCompressedLength];
      int compressedLength = compressor.compress(data, 0, decompressedLength, compressed, 0, maxCompressedLength);

      // Stop Stopwatch 
      long endTime = System.currentTimeMillis();

      if (debug) {
        System.out.println("Compressed Length: " + compressedLength);
        
        long duration = endTime - startTime;

        System.out.println("Compression Time: " + duration + " ms");
      }

    }


    public void decompressDataLZ4(byte[] data, boolean debug) {
      
      // Compressor Object 
      LZ4Factory factory = LZ4Factory.fastestInstance();

      final int compressedLength = data.length; 

      if (debug) {
        System.out.println("Compressed Length: " + compressedLength);

      }

      // Start Stopwatch 
      long startTime = System.currentTimeMillis();

      // Decompress Data 
      LZ4FastDecompressor decompressor = factory.fastDecompressor();
      byte[] restored = new byte[compressedLength];
      int compressedLength2 = decompressor.decompress(data, 0, restored, 0, compressedLength);

      // Stop Stopwatch 
      long endTime = System.currentTimeMillis();

      if (debug) {
        System.out.println("Decompressed Length: " + compressedLength2);

        long duration = endTime - startTime;

        System.out.println("Decompression Time: " + duration + " ms");

      } 

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
