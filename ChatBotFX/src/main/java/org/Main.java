package org;
import org.database.User;
import org.network.NetworkSender;
import org.network.Types;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, SocketException, UnknownHostException {
        SystemComponents sys = SystemComponents.getInstance();
        System.out.println("Addresse IP = "+SystemComponents.getIPv4().getHostName());
        System.out.println("Addresse de Broadcast = "+SystemComponents.toBroadcast(SystemComponents.getIPv4()).getHostName());

        NetworkSender s = new NetworkSender(new User("Gwen",1234,SystemComponents.getIPv4().getHostName()),"192.168.1.198",1234 ,Types.UDPMode.Error);
    }
}
