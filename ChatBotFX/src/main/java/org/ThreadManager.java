package org;

import org.conv.ConnectionManager;
import org.conv.ReceiverThread;
import org.conv.SenderThread;

import java.net.*;
import java.util.ArrayList;

public class ThreadManager {
    public static ArrayList<ReceiverThread> receiverThreadList = new ArrayList<>();
    public static ArrayList<SenderThread> senderThreadList = new ArrayList<>();

    private static ConnectionManager Connect;
    public static void createReceiverThread(ServerSocket socket) {
        receiverThreadList.add(new ReceiverThread(socket));
        receiverThreadList.get(receiverThreadList.size()-1).start();
    }

    public static void createSenderThread(InetAddress IPaddress, int publicPort) {
        senderThreadList.add(new SenderThread(IPaddress,publicPort));
        senderThreadList.get(senderThreadList.size()-1).start();
    }

    public static void StartConnection() throws Exception {
        if(Connect == null ){
           Connect = new ConnectionManager();
        }else{
            throw new Exception("Already initialized");
        }
    }

}
