package org.network;

import com.google.gson.Gson;
import org.database.User;

import java.io.IOException;
import java.net.*;

public class NetworkSender{
    private final DatagramSocket senderSock;
    Gson gson= new Gson();
    private final byte[] broadcastAddr= {10,32,(byte)47, (byte) 255};
	private String broad = "localhost" ;

    public NetworkSender(User infos, Types.UDPMode mode) throws SocketException {
            senderSock = new DatagramSocket();
            senderSock.setBroadcast(true);
            try{
                switch (mode){
                    case Nickname:
                        Send_Nickname(infos);
                        break;
                    case UserInfos:
                        Send_User_Infos(infos);
                        break;
                    case Disconnect:
                        Disconnect(infos);
                        break;
                }
            }catch (IOException e){
                System.out.println("Message error");
            }
    }


    public void Send_User_Infos(User usr) throws IOException {
        System.out.println("===== Phase d'envoi =====");
        String user = gson.toJson(usr);
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.UserInfos, user));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByName(broad), 1234);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        System.out.println("===== Fin d'envoi =====");
        senderSock.close();
    }
    public void Send_Nickname(User usr) throws IOException {

        System.out.println("===== Phase d'envoi =====");
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Nickname, gson.toJson(usr)));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByName(broad), 1234);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        System.out.println("===== Fin d'envoi =====");
        senderSock.close();
    }
    public void Disconnect(User usr) throws IOException {
        System.out.println("===== Phase d'envoi =====");
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Disconnect, gson.toJson(usr)));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByName(broad), 1234);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        System.out.println("===== Fin d'envoi =====");
        senderSock.close();
    }

}
