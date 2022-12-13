import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws UnknownHostException, SocketException, SQLException {
        DatabaseManager db = new DatabaseManager();
        db.Update(new User("pls", 1234,"193.465"));
        db.Disconnect();
    }
}
