package org;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, SocketException, UnknownHostException {
        //SystemComponents sys = SystemComponents.getInstance();
        System.out.println("Addresse IP = "+SystemComponents.getIPv4().getHostName());
        System.out.println("Addresse de Broadcast = "+SystemComponents.toBroadcast(SystemComponents.getIPv4()).getHostName());
    }
}
