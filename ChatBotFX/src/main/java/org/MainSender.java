package org;

import org.database.DatabaseManager;
import org.database.User;
import org.network.NetworkReceiver;
import org.network.NetworkSender;
import org.network.Types;

import java.net.SocketException;
import java.net.UnknownHostException;

public class MainSender {

    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatabaseManager db = new DatabaseManager();
        User usr = new User("Cador",1234, "localhost");
        NetworkReceiver net = new NetworkReceiver("R1");
        NetworkSender S1 = new NetworkSender(usr, Types.UDPMode.UserInfos);
        usr= new User("Wesh",1234,"localhost");
        NetworkSender S2 = new NetworkSender(usr, Types.UDPMode.Nickname);
        //NetworkSender S3 = new NetworkSender(usr, Types.UDPMode.Disconnect);

    }
}
