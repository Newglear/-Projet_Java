package org.network;

import com.google.gson.Gson;
import org.SystemComponents;
import org.database.Message;
import org.database.User;

import java.io.IOException;
import java.net.*;

public class NetworkSender{
    private final DatagramSocket senderSock;
    int destinationPort;
    Gson gson= new Gson();
	private String broad ;

    public NetworkSender(User infos, Types.UDPMode mode,int port) throws SocketException, UnknownHostException {
        senderSock = new DatagramSocket();
        broad = SystemComponents.toBroadcast(SystemComponents.getIPv4()).getHostName();
        this.destinationPort = port;
        senderSock.setBroadcast(true);
        System.out.println("===== Phase d'envoi =====");
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
        System.out.println("===== Fin d'envoi =====");
    }
    public NetworkSender(User infos, String addr,int port,Types.UDPMode mode) throws SocketException {
        senderSock = new DatagramSocket();
        System.out.println("===== Phase d'envoi =====");
        try{
            senderSock.setBroadcast(false);
            switch (mode){
                case Answer_Infos:
                    Answer_Infos(infos,addr,port);
                    break;
                case Answer_Nickname:
                    Answer_Nickname(infos,addr,port);
                    break;
                case Error:
                    Answer_Error(infos,addr,port);
                    break;
            }
        }catch (IOException e){
            System.out.println("Message error");
        }
        System.out.println("===== Fin d'envoi =====");

    }


    private void Send_User_Infos(User usr) throws IOException {
        String user = gson.toJson(usr);
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.UserInfos, user));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByName(broad), destinationPort);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        senderSock.close();
    }
    private void Answer_Infos(User usr,String addr,int port) throws IOException {
        String user = gson.toJson(usr);
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Answer_Infos, user));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByName(addr), port);
        System.out.println("Envoi de réponse");
        senderSock.send(outPacket);
        System.out.println("Réponse Envoyée");
        senderSock.close();
    }
    private void Answer_Nickname(User usr,String addr,int port) throws IOException {
        String user = gson.toJson(usr);
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Answer_Nickname, user));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByName(addr), port);
        System.out.println("Envoi de réponse");
        senderSock.send(outPacket);
        System.out.println("Réponse Envoyée");
        senderSock.close();
    }
    private void Answer_Error(User usr,String addr,int port) throws IOException {
        String user = gson.toJson(usr);
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Error, user));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByName(addr), port);
        System.out.println("Envoi d'erreur Pseudo");
        senderSock.send(outPacket);
        System.out.println("Réponse Envoyée");
        senderSock.close();
    }
    private void Send_Nickname(User usr) throws IOException {
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Nickname, gson.toJson(usr)));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByName(broad), destinationPort);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        senderSock.close();
    }

    private void Disconnect(User usr) throws IOException {
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Disconnect, gson.toJson(usr)));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByName(broad), destinationPort);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        senderSock.close();
    }

}
