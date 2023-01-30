import java.io.*;
import java.net.*;
import java.util.*;

/*
 * This class will eventually handle prime calculations and packet building 
 */
public class calculator {

    // TCP Socket packet sender thing object 
    private static TcpSender sender = new TcpSender(); 

    // Java is special - Byte array to store listener IP
    private static byte IPAddress[] = {0, 0, 0, 0};

    // Listener Port 
    private static int port = 8080; 

    // Fun java weirdness to store IP 
    private static InetAddress listenAddress;

    // Testing socket
    public static void main(String[] args) throws UnknownHostException, IOException{
        // Test array to be sent over socket 
        int[] testArray = {0, 1, 2, 3}; 

        // Convert byte array IP to java weirdness IP 
        listenAddress = InetAddress.getByAddress(IPAddress);
    
        // Send array to listener 
        sender.sendArray(testArray, listenAddress, port);
    }

}