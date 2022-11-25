import com.google.gson.Gson;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class NetworkReceiver extends Thread {
    Gson gson = new Gson();
    private DatagramSocket recvSock;

    NetworkReceiver(String id){
        super(id);
        System.out.println("====== RECEIVING THREAD INITIATED ======");
        start();
    }

    @Override
    public void run() {
        Receive();

    }
    private void Receive(){
        try {
            recvSock = new DatagramSocket(1234);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        byte[] buffer = new byte[256];
        DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
        while(true){
            try {
                System.out.println("Attente de réception");
                recvSock.receive(inPacket);
                String var = new String(inPacket.getData(), 0, inPacket.getLength());
                User usr = gson.fromJson(var, User.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
