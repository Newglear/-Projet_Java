package chatsystem.network;

import org.SystemComponents;
import org.database.DatabaseManager;
import org.database.User;
import org.junit.*;
import org.network.NetworkReceiver;
import org.network.NetworkSender;
import org.network.Types;

import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UDPBroadcastTest {

    User usr;
    ArrayList<User> contactList;
    static SystemComponents sys;
    @BeforeClass
    public static void initSystem() {
        sys = new SystemComponents();
    }
    @Before
    public void initUdp() throws SQLException {
        DatabaseManager.Flush();
        NetworkReceiver.Enable_ThreadMode_Mode(true);
        NetworkReceiver.resetContactCount();
        if(contactList != null)
            contactList.clear();
        contactList = new ArrayList<>();
        usr = new User("PublicTester", 1234,"localhost");
        List<String> nicknames = Arrays.asList("Gwen","Cador","Evan","Joel","Kiki","G\nen","Wesh","???.?????????????????????    ???????"); // Caractères spéciaux et chaines trop longues rejetés
        for(String n:nicknames){
            contactList.add(new User(n,(int)(Math.random()*(2000-1234+1)+1234),"localhost"));
        }
    }
    @Test
    public void SendReceiveContacts() throws SocketException, InterruptedException, SQLException {
        for(User u: contactList){
            NetworkSender n = new NetworkSender(u, Types.UDPMode.UserInfos,1234);
            sleep(100);
        }
        assertEquals(contactList.toArray().length,DatabaseManager.LoadUsers().toArray().length);
    }

    @Test
    public void Simple_Interaction() throws SocketException{
        NetworkSender sender = new NetworkSender(usr, Types.UDPMode.UserInfos,1234);
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(NetworkReceiver.getContactCount());
        assertEquals( 2,NetworkReceiver.getContactCount());

    }

    @Test
    public void Nickname_Update() throws SocketException, SQLException {
        DatabaseManager.Insert(new User("Tester",1234,"localhost"));
        NetworkSender sender = new NetworkSender(new User("Modif",1234,"localhost"), Types.UDPMode.Nickname,1234);
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(DatabaseManager.LoadUsers().contains("Modif"));
    }
    /*
    @Test
    public void Unicity_Check() throws SocketException, SQLException {
        NetworkReceiver.Enable_ThreadMode_Mode(true);
        DatabaseManager.Insert(new User("Tester",1234,"localhost"));
        NetworkSender sender = new NetworkSender(new User("Tester",1234,"localhost"), Types.UDPMode.Nickname,1234);
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/
    @AfterClass
    public static void Clear() throws SQLException {
        DatabaseManager.Flush();
     }

}
