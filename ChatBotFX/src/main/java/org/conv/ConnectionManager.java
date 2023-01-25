package org.conv;

import org.ThreadManager;

import java.io.*;
import java.net.*;

public class ConnectionManager extends Thread {

    public static int minNumPort = 1234;
    public static int maxNumPort = 2235;
    public ServerSocket serverSocketManager;

    public ConnectionManager() {
        super("ConnectionManager");
    }

    public ServerSocket findAvailableSocket() throws Exception {
        ServerSocket serverSocket = null;
        for(int i=minNumPort; i<maxNumPort; i++) {
            try {
                serverSocket = new ServerSocket(i);
                //System.out.println(i);
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
            this.serverSocketManager = new ServerSocket(1235);
            System.out.println("public port : "+this.serverSocketManager.getLocalPort());

            Socket socketManager;
            ServerSocket receiverSocket;
            PrintWriter outBis;

            while(true){
                socketManager = serverSocketManager.accept();
                receiverSocket = findAvailableSocket();
                //System.out.println("port found : "+receiverSocket.getLocalPort());

                ThreadManager.createReceiverThread(receiverSocket);
                //System.out.println("receiver OK");

                outBis = new PrintWriter(socketManager.getOutputStream(), true);
                outBis.println(receiverSocket.getLocalPort());
                //System.out.println("CODE CONTINUE");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
