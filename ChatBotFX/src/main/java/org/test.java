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
        //SystemComponents sys = SystemComponents.getInstance();
        //DatabaseManager.Flush();
        InetAddress a = InetAddress.getByName("localhost");
        System.out.println("========================");
        //System.out.println(DatabaseManager.LoadUsers());
        System.out.println("========================");
        //ConnectionManager c = new ConnectionManager();
        //c.start();
        ThreadManager.createSenderThread(InetAddress.getByName("localhost"),1235);
        SenderThread th =ThreadManager.getThread("localhost");
        System.out.println(th);
        sleep(1000);
        th.Send(new Message("Cador","ALEDDD"));
    }

}
