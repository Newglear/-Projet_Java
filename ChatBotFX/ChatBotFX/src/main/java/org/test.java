package org;

import org.conv.ConnectionManager;
import org.database.DatabaseManager;

import java.net.*;

public class test {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        DatabaseManager db = new DatabaseManager();
        String addr ="localhost";


        //System.out.println(a.getHostAddress());

        ConnectionManager c = new ConnectionManager();
        c.start();
        ThreadManager.createSenderThread(InetAddress.getByName(addr),1234,"petit suicide entre amis");
    }

}
