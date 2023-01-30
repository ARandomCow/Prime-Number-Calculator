import java.io.*;
import java.net.*;

public class TcpSender {

    // Constructor - I know crazy complicated don't freak out 
    public TcpSender() {

    }

    // Sends array over socket 
    // Need to add an Exception handler - If IOException gets thrown data loss is a very likely which is a major sad face :(
    public void sendArray(int[] arr, InetAddress host, int port) throws IOException {
        // Create a socket object to handle connection to listener 
        Socket socket = new Socket(host, port);
        // Data stream objects to stream array over socket 
        OutputStream out = socket.getOutputStream();
        DataOutputStream dout = new DataOutputStream(out);
        
        // Build packet starting with length of array then array 
        dout.writeInt(arr.length);
        for (int i : arr) {
            dout.writeInt(i);
        }
        
        // Flush and Close connection 
        dout.flush();
        dout.close();
        socket.close();
    }
}
