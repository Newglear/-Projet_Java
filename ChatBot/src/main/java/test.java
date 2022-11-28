import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class test {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {

        byte[] addr = {10,1,5,77};
        InetAddress a = InetAddress.getByAddress(addr);

        System.out.println(a.getHostAddress());

        ConnectionManager c = new ConnectionManager();
        c.start();

        SenderThread s = new SenderThread(a, 1234, "OKOKmdmdmdmdm");
        s.start();
    }

}
