import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws UnknownHostException, SocketException, SQLException {
        DatabaseManager db = new DatabaseManager();
        db.LoadUsers();
        db.Disconnect();
    }
}
