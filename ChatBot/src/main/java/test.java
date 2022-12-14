import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class test {

    public static void main(String[] args) throws UnknownHostException, InterruptedException, SQLException {
        DatabaseManager db = new DatabaseManager();
        String addr ="10.1.5.225";

        System.out.println(DatabaseManager.checkUnicity("Wesh"));

        //System.out.println(a.getHostAddress());
        //ConnectionManager c = new ConnectionManager();
        //c.start();
        //ThreadManager.createSenderThread(InetAddress.getByName(addr),1234,"petit suicide entre amis");
    }

}
