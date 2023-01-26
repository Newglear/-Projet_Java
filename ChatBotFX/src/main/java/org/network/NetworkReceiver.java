package org.network;

import com.google.gson.Gson;
import org.SystemComponents;
import org.database.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLException;

public class NetworkReceiver extends Thread {
    private static int ContactCount;
    Gson gson = new Gson();
    static boolean ThreadMode,NicknameTestMode ;
    int port;
    private DatagramSocket recvSock;

    public NetworkReceiver(String id,boolean mode,int port){
        super(id);
        this.ThreadMode = mode;
        NicknameTestMode = false;
        this.port = port;
        ContactCount = 0;
        System.out.println("====== RECEIVING THREAD INITIATED ======");
        start();
    }
    public void setThreadMode(boolean mode){
        ThreadMode = mode;
    }

    public static void IncrementCount(){
        ContactCount++;
        System.out.println("Count "+ getContactCount());
    }

    public static int getContactCount(){
        return ContactCount;
    }
    public static void resetContactCount(){
        ContactCount = 0;
    }
    @Override
    public void run() {
            Receive();
    }
    private void Receive() {
        try {
            recvSock = new DatagramSocket(port);
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
                NetworkMessage msg = gson.fromJson(var, NetworkMessage.class);
                User usr = gson.fromJson(msg.getObject(), User.class);
                System.out.println(usr);

                if(ThreadMode && usr.getAddr().equals(SystemComponents.getInstance().getCurrentIp())) {
                    continue;
                }
                System.out.println("Petit message de test");
                switch (msg.getMode()){
                    case UserInfos:
                        Receive_Infos(msg.getObject());
                        break;
                    case Nickname:
                        Receive_Nickname(msg.getObject());
                        break;
                    case Answer_Infos:
                        Add_User(msg.getObject());
                    case Answer_Nickname:
                        break;
                    case Error:
                        System.out.println("Error Encountered");
                        SystemComponents.getInstance().setUnicityCheck(true);
                        break;
                    case Disconnect:
                        Receive_Disconnect(msg.getObject());
                        break;
                }
                IncrementCount();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void Receive_Infos(String obj) throws SocketException {
        User usr = gson.fromJson(obj, User.class);
        System.out.println("Nouvel Utilisateur "+usr);
        if(usr.getPseudo().equals(SystemComponents.getInstance().getCurrentNickname()) && (ThreadMode||NicknameTestMode )){
            NetworkSender send  = new NetworkSender(new User(SystemComponents.getInstance().getCurrentNickname(),SystemComponents.getInstance().getPort(),SystemComponents.getInstance().getCurrentIp()),usr.getAddr(),usr.getPort(), Types.UDPMode.Error);
        }else
        {
            try{
                NetworkSender sender = new NetworkSender(new User(SystemComponents.getInstance().getCurrentNickname(),SystemComponents.getInstance().getPort(),SystemComponents.getInstance().getCurrentIp()),usr.getAddr(),usr.getPort(),Types.UDPMode.Answer_Infos);
                SystemComponents.getInstance().db.Insert(usr);
            } catch (SQLException s){
                System.out.println(s);
            }
        }
    }
    public void Receive_Nickname(String obj) throws SocketException {
        User usr = gson.fromJson(obj, User.class);
        System.out.println("Changement de pseudo entrant " + usr.getPseudo() );
        if(usr.getPseudo().equals(SystemComponents.getInstance().getCurrentNickname()) && (ThreadMode||NicknameTestMode)){
            NetworkSender send  = new NetworkSender(new User(SystemComponents.getInstance().getCurrentNickname(),SystemComponents.getInstance().getPort(),SystemComponents.getInstance().getCurrentIp()),usr.getAddr(),usr.getPort(), Types.UDPMode.Error);
        }else {
            try {
                SystemComponents.getInstance().db.Update(usr);
            } catch (SQLException s) {
                System.out.println(s);
            }
        }
    }

    public void Receive_Disconnect(String obj){
        User usr = gson.fromJson(obj, User.class);
        System.out.println("Déconnexion de " + obj);

        try{
            SystemComponents.getInstance().db.Disconnect_User(usr);
        }catch (SQLException s){
            System.out.println(s);
        }
    }

    public void Add_User(String obj){
        System.out.println("Réponse recue ");
        User usr = gson.fromJson(obj, User.class);
        try {
            SystemComponents.getInstance().db.Insert(usr);
        } catch (SQLException s) {
            System.out.println(s);
        }
    }

    public static void Enable_ThreadMode_Mode(boolean enable){
        ThreadMode = enable;
    }
    public static void Enable_Nickname_Mode(boolean enable){
        NicknameTestMode = enable;
    }

}
