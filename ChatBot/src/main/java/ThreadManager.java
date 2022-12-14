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

    public static void createSenderThread(InetAddress IPaddress, int publicPort, String message) {
        senderThreadList.add(new SenderThread(IPaddress,publicPort,message));
        senderThreadList.get(senderThreadList.size()-1).start();
    }
    public static void createNetworkSender(User usr,Types.UDPMode mode) throws SocketException {
        NetworkSender T = new NetworkSender(usr,mode);
    }


    public static void StartConnection() throws Exception {
        if(Connect == null ){
           Connect = new ConnectionManager();
        }else{
            throw new Exception("Already initialized");
        }
    }

}
