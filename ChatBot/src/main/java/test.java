import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class test {

    public static void main(String[] args) throws UnknownHostException {

        byte[] addr = {10,20, (byte) 255, (byte) 255};
        InetAddress a = InetAddress.getByAddress(addr);

        System.out.println(addr.getHostAddress());
        ConnectionManager c = new ConnectionManager();


    }

}
