package org.network;

import com.google.gson.Gson;
import org.database.User;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

public class NetworkSender{
    private final DatagramSocket senderSock;
    int destinationPort;
    Gson gson= new Gson();
	private InetAddress broad ;

    public NetworkSender(User infos, Types.UDPMode mode,int port) throws SocketException {
        senderSock = new DatagramSocket();
        try{
            //broad = getBroadcastAddress().get(0);
            broad = InetAddress.getByName("255.255.255.255");
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
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
        try{
            broad = getBroadcastAddress().get(0);
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
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
            }
        }catch (IOException e){
            System.out.println("Message error");
        }
        System.out.println("===== Fin d'envoi =====");

    }


    private void Send_User_Infos(User usr) throws IOException {
        String user = gson.toJson(usr);
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.UserInfos, user));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),broad, destinationPort);
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
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Answer_Infos, user));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),InetAddress.getByName(addr), port);
        System.out.println("Envoi de réponse");
        senderSock.send(outPacket);
        System.out.println("Réponse Envoyée");
        senderSock.close();
    }
    private void Send_Nickname(User usr) throws IOException {
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Nickname, gson.toJson(usr)));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),broad, destinationPort);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        senderSock.close();
    }
    private void Disconnect(User usr) throws IOException {
        String msg =gson.toJson( new NetworkMessage(Types.UDPMode.Disconnect, gson.toJson(usr)));
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),msg.length(),broad, destinationPort);
        System.out.println("Envoi des infos");
        senderSock.send(outPacket);
        System.out.println("Infos Envoyées");
        senderSock.close();
    }

    public static ArrayList<InetAddress> getBroadcastAddress() throws SocketException, UnknownHostException {
        String[] a = new String[0];
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        ArrayList<InetAddress> addressList = new ArrayList<>();
        ArrayList<InetAddress> list = new ArrayList<>();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback()) {
                continue;
            }
            if (!networkInterface.isUp()) {
                continue;
            }
            for (InterfaceAddress i : networkInterface.getInterfaceAddresses()) {
                InetAddress address = i.getAddress();
                if (!address.equals(InetAddress.getByName("127.0.0.1"))) {
                    addressList.add(address);
                }
            }
        }
        for(InetAddress addr : addressList ){
            if(addr.getHostName().matches("(\\d{1,3}.){3}\\d{1,3}")) {
                a = addr.getHostName().split("[.]");
                a[3] = "255";
                a[0] += ".";
                a[1] += ".";
                a[2] += ".";
                String s = "";
                for (String part : a) {
                    s += part;
                }
                list.add(InetAddress.getByName(s));
            }
        }
        return list;
    }

}
