package compressor; 

public class Tools {

    public static void compareByteArrays(byte[] array1, byte[] array2) {
      int minLength = Math.min(array1.length, array2.length);
      int maxLength = Math.max(array1.length, array2.length);
    
      for (int i = 0; i < minLength; i++) {
        if (array1[i] != array2[i]) {
          System.out.printf("Byte at index %d is different: %d (array1) vs. %d (array2)\n", i, array1[i], array2[i]);
        }
      }
    
      if (maxLength > minLength) {
        System.out.printf("Arrays are of different lengths: %d (array1) vs. %d (array2)\n", array1.length, array2.length);
      } 
      else {
        System.out.println("Arrays are the same.");
      }
    }
} 
