package org;

import org.conv.ConnectionManager;
import org.database.DatabaseManager;

import java.net.*;

import static java.lang.Thread.sleep;

public class test {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {

        String addr = "localhost";
        InetAddress a = InetAddress.getByName(addr);

        System.out.println(a.getHostAddress());

        ConnectionManager c = new ConnectionManager();
        c.start();
        SendTestThread Th = new SendTestThread("localhost",1234,"????");
        Th.start();
        while(true) {
            Th.Send("wesh");
            sleep(5000);
        }
        //Th.interrupt();
            //ThreadManager.createSenderThread(a, 1234, "");
    }

}
