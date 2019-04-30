import java.io.IOException;
import java.util.Scanner;

public class DistanceRoutingVectorUDP {
    private static int PORT_NUMBER;
    private static String MY_IP;

    public static void main (String []args) throws IOException {
        ReadingTopology getFile = new ReadingTopology(args);

        PORT_NUMBER = getFile.returnPort();
        MY_IP = getFile.returnIP();

        Server MyServer = new Server(PORT_NUMBER);
        MyServer.start();
        String[] tokenAns;
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Type 'help' for a list of commands.");

        while(true) {
            System.out.print(">");
            String temp = scanner.nextLine();
            tokenAns = temp.split(" ");

            switch (tokenAns[0].toLowerCase()) {
                //send is for testing purposes, made two topology files same ip (same computer) with different ports
                case "send":
                    Client clientMessage = new Client(MY_IP, 4090, tokenAns[1]);
                    break;
                case "update":
                    break;
                case "step":
                    break;
                case "packets":
                    break;
                case "display":
                    break;
                case "disable":
                    break;
                case "crash":
                    break;
            }
        }
    }
}
