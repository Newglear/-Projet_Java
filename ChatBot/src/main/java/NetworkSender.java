import com.google.gson.Gson;

import java.io.IOException;
import java.net.*;

public class NetworkSender{
    private final DatagramSocket senderSock;
    Gson gson= new Gson();
    private final byte[] broadcastAddr= {10,32,(byte)47, (byte) 255};
    NetworkSender(User infos) throws SocketException {
            senderSock = new DatagramSocket();
            senderSock.setBroadcast(true);
            try{
                Send_User_Infos(infos);
            }catch (IOException e){
                System.out.println("Message error");
            }
    }
    NetworkSender(String pseudo) throws SocketException {
        senderSock = new DatagramSocket();
        senderSock.setBroadcast(true);
        try {
            Send_Nickname(pseudo);
        }
        catch(IOException e){
            System.out.println("Message error");
        }
    }

    NetworkSender() throws SocketException {
        senderSock = new DatagramSocket();
        senderSock.setBroadcast(true);
        try{
        Disconnect();}
        catch(IOException e){
            System.out.println("Message error");
        }
    }

    public void Send_User_Infos(User usr) throws IOException {
        System.out.println("===== Phase d'envoi =====");
        String user = gson.toJson(usr);
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.UserInfos, user));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByAddress(broadcastAddr), 1234);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        System.out.println("===== Fin d'envoi =====");
        senderSock.close();
    }
    public void Send_Nickname(String nick) throws IOException {

        System.out.println("===== Phase d'envoi =====");
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Nickname, nick));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByAddress(broadcastAddr), 1234);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        System.out.println("===== Fin d'envoi =====");
        senderSock.close();
    }
    public void Disconnect() throws IOException {
        System.out.println("===== Phase d'envoi =====");
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Disconnect, "Connection ended"));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByAddress(broadcastAddr), 1234);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        System.out.println("===== Fin d'envoi =====");
        senderSock.close();
    }

}
