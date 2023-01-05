package org;

import org.conv.ConnectionManager;
import org.conv.SenderThread;
import org.database.DatabaseManager;
import org.database.Message;
import org.database.User;

import java.net.*;
import java.sql.SQLException;

import static java.lang.Thread.sleep;

public class test {

    public static void main(String[] args) throws UnknownHostException, InterruptedException, SQLException {
        SystemComponents sys = new SystemComponents();
        String addr = "localhost";
        InetAddress a = InetAddress.getByName(addr);

        System.out.println(a.getHostAddress());
        System.out.println("========================");
        System.out.println(DatabaseManager.LoadUsers());
        System.out.println("========================");
        ConnectionManager c = new ConnectionManager();
        c.start();
        SenderThread Th = new SenderThread(InetAddress.getByName("localhost"),1234);
        Th.start();
        while(true) {
            sleep(1000);
            Th.Send(new Message("Gwen","Random","Wesh"));
        }

    }

}
