import com.google.gson.Gson;

import java.io.IOException;
import java.net.*;

public class NetworkSender extends Thread{
    private DatagramSocket senderSock;
    Gson gson= new Gson();
    private byte[] broadcastAddr= {10,32,(byte)47, (byte) 255};
    NetworkSender(String id,User infos) throws SocketException {
            super(id);
            senderSock = new DatagramSocket();
            senderSock.setBroadcast(true);
            try{
                Send_User_Infos(infos);
            }catch (IOException e){
                System.out.println("Erreur d'envoi");
            }
    }
    NetworkSender(String id, String pseudo) throws SocketException {
        super(id);
        senderSock = new DatagramSocket();
        senderSock.setBroadcast(true);
        Send_Nickname(pseudo);
    }

    NetworkSender(String id) throws SocketException {
        super(id);
        senderSock = new DatagramSocket();
        senderSock.setBroadcast(true);
        Disconnect();
    }

    @Override
    public void run() {
            return;
    }

    public void Send_User_Infos(User usr) throws IOException {
        System.out.println("===== Phase d'envoi =====");
        String user = gson.toJson(usr);
        DatagramPacket outPacket = new DatagramPacket(user.getBytes(),user.length(),InetAddress.getByAddress(broadcastAddr), 1234);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        System.out.println("===== Fin d'envoi =====");
        senderSock.close();
    }
    public void Send_Nickname(String nick){
        return;
    }
    public void Disconnect(){
        return;
    }

}
