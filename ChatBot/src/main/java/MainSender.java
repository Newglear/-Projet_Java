import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainSender {

    public static void main(String[] args) throws SocketException, UnknownHostException {

        User usr = new User("Cador",1234, InetAddress.getByName("localhost").getAddress());
        NetworkSender S1 = new NetworkSender(usr);
        NetworkSender S2 = new NetworkSender("GROS CADOR");
        NetworkSender S3 = new NetworkSender();

    }
}
