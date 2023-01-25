package chatsystem.database;

import org.SystemComponents;
import org.database.User;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContactTests {
    static User usr;
    static ArrayList<User> contactList;
    static SystemComponents sys;
    @BeforeClass
    public static void initSystem() throws SQLException {
        int num = 1;
        sys = SystemComponents.getInstance();
        if(contactList != null)
            contactList.clear();
        contactList = new ArrayList<>();
        usr = new User("PublicTester", 1234,"localhost");
        List<String> nicknames = Arrays.asList("Gwen","Cador","Evan","Joel","Kiki","G\nen","Wesh","???.?????????????????????    ???????"); // Caractères spéciaux et chaines trop longues rejetés
        for(String n:nicknames){
            contactList.add(new User(n,(int)(Math.random()*(2000-1234+1)+1234),"192.168.25."+(num++)));
        }
        sys.db.Flush();
    }
    @Before
    public void Clean() throws SQLException {
        sys.db.Flush();
    }
    @Test
    public void Adding_Users() throws SQLException {
        for(User u: contactList){
            sys.db.Insert(u);
        }
        System.out.println(sys.db.LoadUsers());
        assertEquals(contactList.size(),sys.db.LoadUsers().toArray().length);
    }
    @Test
    public void Removing_Users() throws SQLException {
        for(User u: contactList){
            sys.db.Insert(u);
        }
        sys.db.Remove(contactList.get(0));
        sys.db.Remove(contactList.get(contactList.size()-1));
        System.out.println(sys.db.LoadUsers());
        assertEquals(contactList.size()-2,sys.db.LoadUsers().toArray().length);
    }

    @Test
    public void Updating_Users() throws SQLException {
        for(User u: contactList){
            sys.db.Insert(u);
        }
        sys.db.Update(new User("Test",contactList.get(0).getPort(),"192.168.25.1"));
        System.out.println(sys.db.LoadUsers());
        assertTrue(sys.db.LoadUsers().contains("Test"));
    }

    @Test
    public void Loading_Single_User() throws SQLException {
        for(User u: contactList){
            sys.db.Insert(u);
        }
        assertEquals(sys.db.LoadUser(SystemComponents.getInstance().db.LoadUserID("Gwen")).getPseudo(), contactList.get(0).getPseudo());
        assertEquals(sys.db.LoadUser(SystemComponents.getInstance().db.LoadUserID("Gwen")).getAddr(), contactList.get(0).getAddr());
        assertEquals(sys.db.LoadUser(SystemComponents.getInstance().db.LoadUserID("Gwen")).getPort(), contactList.get(0).getPort());
    }
    @Test
    public void Loading_Multiple_Users() throws SQLException {
        for(User u: contactList){
            sys.db.Insert(u);
        }
        assertEquals(sys.db.LoadUsers().size(),contactList.size());
    }
    @AfterClass
    public static void Clear() throws SQLException {
        sys.db.Flush();
    }

}
