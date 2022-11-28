import java.net.*;
import java.util.ArrayList;

public class ThreadManager {
    public static ArrayList<ReceiverThread> receiverThreadList = new ArrayList<>();

    public static void createReceiverThread(ServerSocket socket) {
        receiverThreadList.add(new ReceiverThread(socket));
        receiverThreadList.get(receiverThreadList.size()-1).start();
    }

}
