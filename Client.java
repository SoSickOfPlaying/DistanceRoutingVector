import java.io.IOException;
import java.net.*;

public class Client extends Thread {
    public Client(String DestinationIP, int DestinationPort, String message) throws SocketException, UnknownHostException {
        // Step 1:Create the socket object for
        // carrying the data.
        DatagramSocket ds = new DatagramSocket();

        byte buf[] = null;
        // convert the String input into the byte array.
        buf = message.getBytes();

        DatagramPacket DpSend = new DatagramPacket(buf, buf.length, InetAddress.getByName(DestinationIP), DestinationPort);

        // send the data
        try {
            ds.send(DpSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
