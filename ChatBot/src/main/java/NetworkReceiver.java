import com.google.gson.Gson;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLException;
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
                NetworkMessage msg = gson.fromJson(var, NetworkMessage.class);
                switch (msg.getMode()){
                    case UserInfos:
                        Receive_Infos(msg.getObject());
                        break;
                    case Nickname:
                        Receive_Nickname(msg.getObject());
                        break;
                    case Disconnect:
                        Receive_Disconnect(msg.getObject());
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void Receive_Infos(String obj){
        User usr = gson.fromJson(obj, User.class);
        System.out.println("Nouvel Utilisateur "+usr);
        try{
            DatabaseManager.Insert(usr);
        } catch (SQLException s){
            System.out.println(s);
        }
    }
    public void Receive_Nickname(String obj){
        User usr = gson.fromJson(obj, User.class);
        System.out.println("Changement de pseudo entrant " + usr.getPseudo() );
        try {
            DatabaseManager.Update(usr);
        } catch ( SQLException s){
            System.out.println(s);
        }
    }
    public void Receive_Disconnect(String obj){
        User usr = gson.fromJson(obj, User.class);
        System.out.println("Déconnexion de " + obj);

        try{
            DatabaseManager.Remove(usr);
        }catch (SQLException s){
            System.out.println(s);
        }
    }

}
