import java.io.*;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.net.InetAddress;


public class ReadingTopology {

    private static int PORT_NUMBER, numberOfServers, numberOfNeighbors;
    private static String MY_IP;
    private static String TOPOLOGY;
    private static int INTERVAL;
    private static ArrayList<String> topoFile = new ArrayList<>();

    public ReadingTopology(String args[]) throws IOException {
        //IP Address of the user
        try {
            MY_IP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(MY_IP);

        //check if the port number is given by searching for their ip in the file
        //if their ip is not in the file then let them enter another file, hopefully updated and check again
        checkArgs(args);
        retrieveFile(TOPOLOGY);
        printTopo(topoFile);
        System.out.println(INTERVAL+" second intervals.");
        PORT_NUMBER = findPort(MY_IP);

        if(PORT_NUMBER == -1) {
            //your ip was not found in the topology file
            System.out.println("Your ip address was not found on the topology file." +
                    " Your ip is: "+MY_IP+"\nPlease update your file with your IP Address and try again.");
            System.exit(1);
        }
        System.out.println("Port number is: "+PORT_NUMBER+"\nNumber of Servers: "+numberOfServers+
                "\nNumber of Neighbors: "+numberOfNeighbors);
    }

    public static void checkArgs (String []args) {
        if(args.length >= 4) {
            //topology is after it
            if(args[0].equalsIgnoreCase("-t")) {
                TOPOLOGY = args[1];
                try {
                    INTERVAL = Integer.parseInt(args[3]);
                } catch(NumberFormatException e) {
                    System.out.println("args 3 is not a number");
                    specifyFormat();
                }
            }
            else if (args[0].equalsIgnoreCase("-i")) {
                TOPOLOGY = args[3];
                try {
                    INTERVAL = Integer.parseInt(args[1]);
                } catch(NumberFormatException e) {
                    System.out.println("args 1 is not a number");
                    specifyFormat();
                }
            }
            else {
                System.out.println("didnt start with -t or -i");
                specifyFormat();
            }
        }
        else {
            System.out.println("Not args 4");
            specifyFormat();
        }
    }

    public static void specifyFormat () {
        Scanner scanner = new Scanner(System.in);
        boolean correctFormat = false;
        while(!correctFormat) {
            System.out.println("Please specify -t <topology-file-name> -i <routing-update-interval> in seconds \n" +
                    "For example: \"-t topology.txt -i 60\"");
            System.out.print(">");
            String temp = scanner.nextLine();
            String[] tokenAns = temp.split(" ");

            TOPOLOGY = tokenAns[1];
            try {
                INTERVAL = Integer.parseInt(tokenAns[3]);
            } catch(NumberFormatException e) {
                System.out.println("args 3 is not a number " +tokenAns[3]);
            }
            correctFormat = true;
        }
    }

    public static ArrayList<String> retrieveFile (String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        int i=0;
        while(scanner.hasNextLine()){
            topoFile.add(scanner.nextLine());
            i++;
        }
        return topoFile;
    }

    public static void printTopo (ArrayList<String> topo) {
        if(!topo.isEmpty()) {
            for(int i=0; i < topo.size(); i++) {
                System.out.println(topo.get(i));
            }
        }
        else {
            System.out.println("Topology file is empty.");
        }
    }

    //stores number of servers and neighbors
    //look for the ip of this server which will then tell you the port number
    //should be the second string following the 2nd row and onward
    //if it doesnt find it then return -1
    public static int findPort(String ip) {
        //boolean found = false;
        numberOfServers = Integer.parseInt(topoFile.get(0));
        numberOfNeighbors = Integer.parseInt(topoFile.get(1));
        String temp;
        String[] hodor;

        //numberOfServers+2 since the first 2 lines will not contain port numbers
        //and numberOfServers+2 is where the last one containing a port number should be
        for(int i=2; i < (numberOfServers+2); i++) {
            temp = topoFile.get(i);
            hodor = temp.split(" ");
            if(hodor[1].equals(ip)) {
                return Integer.parseInt(hodor[2]);
            }
        }
        return -1;
    }

    public static int returnPort() {
        return PORT_NUMBER;
    }

    public static String returnIP() {
        return MY_IP;
    }
}

