import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws UnknownHostException, SocketException {
        NetworkReceiver  R1 = new NetworkReceiver("R1");
        User usr = new User("Cador",1234, InetAddress.getByName("localhost").getAddress());
        NetworkSender S2 = new NetworkSender("S1",usr);
    }
}
