package chatsystem.database;

import org.SystemComponents;
import org.database.Message;
import org.database.User;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HistoryTests {
    static User usr;
    static ArrayList<User> contactList;
    static ArrayList<Message> messageList;
    static SystemComponents sys;
    @BeforeClass
    public static void initSystem() throws SQLException {
        int num = 1;
        sys = SystemComponents.getInstance();
        if(contactList != null)
            contactList.clear();
        contactList = new ArrayList<>();
        messageList = new ArrayList<>();
        usr = new User("PublicTester", 1234,"localhost");
        List<String> nicknames = Arrays.asList("Gwen","Cador","Evan","Joel","Kiki","G\nen","Wesh","???.?????????????????????    ???????"); // Caractères spéciaux et chaines trop longues rejetés
        for(String n:nicknames){
            contactList.add(new User(n,(int)(Math.random()*(2000-1234+1)+1234),"192.168.25."+(num++)));
        }
        messageList.add(new Message(contactList.get(0).getPseudo(),true,"Message de test de Gwen"));
        messageList.add(new Message(contactList.get(1).getPseudo(),true,"szerguihrigbs"));
        messageList.add(new Message(contactList.get(0).getPseudo(),false,"vszjbhuzsbvkjhdfgbsrg"));
        messageList.add(new Message(contactList.get(1).getPseudo(),false,"dgfbjhbnsduibsdfuihjsbrgf"));
        SystemComponents.getInstance().db.Flush();
    }
    @Before
    public void Clean() throws SQLException {
        SystemComponents.getInstance().db.Flush();
        for(User u: contactList){
            SystemComponents.getInstance().db.Insert(u);
        }
    }
    @Test
   public void Add_Single_Message() throws SQLException {
        SystemComponents.getInstance().db.Insert(new Message(contactList.get(0).getPseudo(),false,"Petit message de test n°1",new Date(System.currentTimeMillis())));
        assertEquals(new Message(contactList.get(0).getPseudo(),false,"Petit message de test n°1",new Date(System.currentTimeMillis())).getMsg() ,SystemComponents.getInstance().db.LoadHistory(contactList.get(0)).get(0).getMsg());
    }
    @Test
    public void Add_Multiple_Messages() throws SQLException {
        for(Message m: messageList){
            SystemComponents.getInstance().db.Insert(m);
        }
        assertEquals(2, SystemComponents.getInstance().db.LoadHistory(contactList.get(0)).size());
    }
    @AfterClass
    public static void Clear() throws SQLException {
        SystemComponents.getInstance().db.Flush();
    }

}
