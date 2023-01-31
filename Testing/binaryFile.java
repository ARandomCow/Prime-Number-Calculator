import java.io.*;

public class binaryFile {

    public static void writeToBinaryFile(int[] numbers, String fileName) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName))) {
            for (int number : numbers) {
                out.writeInt(number);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        writeToBinaryFile(numbers, "numbers.bin");
    }
}
