import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ConnectionManager extends Thread {

    public static int minNumPort = 1234;
    public static int maxNumPort = 2235;
    public ServerSocket serverSocketManager;
    public ArrayList<ReceiverThread> receiverThreadList = new ArrayList<>();

    public ConnectionManager() {
        super("ConnectionManager");
    }

    public ServerSocket findAvailableSocket() throws Exception {
        ServerSocket serverSocket = null;
        for(int i=minNumPort; i<maxNumPort; i++) {
            try {
                serverSocket = new ServerSocket(i);
                System.out.println(i);
                break;
            } catch (IOException e) {}
        }
        if(serverSocket == null){
            throw new Exception("No port available between "+minNumPort+" and "+maxNumPort);
        }else{
            return serverSocket;
        }
    }

    public void run() {
        try {
            this.serverSocketManager = findAvailableSocket();
            System.out.println(this.serverSocketManager.getLocalPort());

            Socket socketManager;
            ServerSocket receiverSocket;
            PrintWriter outBis;

            while(true){
                socketManager = serverSocketManager.accept();
                receiverSocket = findAvailableSocket();
                System.out.println(receiverSocket.getLocalPort());

                this.receiverThreadList.add(new ReceiverThread(receiverSocket));
                this.receiverThreadList.get(receiverThreadList.size()-1).run();

                outBis = new PrintWriter(socketManager.getOutputStream(), true);
                outBis.println(receiverSocket.getLocalPort());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
