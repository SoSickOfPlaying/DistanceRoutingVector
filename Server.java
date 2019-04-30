import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server extends Thread{
    private static int MyPort;

    public Server(int Port) throws SocketException {
        MyPort = Port;
    }
    public void run() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(MyPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte[] receive = new byte[65535];

        DatagramPacket DpReceive = null;
        while (true) {
            DpReceive = new DatagramPacket(receive, receive.length);

            // receive the data in byte buffer
            try {
                ds.receive(DpReceive);
            } catch (IOException e) {
                //e.printStackTrace();
            }
            System.out.println("Client: " + data(receive));

            // Clear the buffer after every message.
            receive = new byte[65535];
        }
    }

    // convert byte array into string to print it out
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}
