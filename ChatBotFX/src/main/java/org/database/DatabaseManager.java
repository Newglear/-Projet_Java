package org.database;

import com.google.gson.Gson;
import javafx.application.Platform;
import org.gui.App;
import org.gui.ChatController;
import org.gui.LoginController;
import org.network.Types;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Observable;


public class DatabaseManager  {
    private Connection con;

    private ArrayList<ChatController> observers;

    public DatabaseManager() throws SQLException {
        observers = new ArrayList<>();
        try {
            con = DriverManager.getConnection("jdbc:sqlite:ChaDBsqlite");
            System.out.println("CON == "+con);
        }catch (SQLException s) {
            System.out.println(s);
        }
        System.out.println("Database Initialised");
        Initialisation();
    }

    public void subscribe (ChatController c){
        observers.add(c);
        System.out.println("==== ADDED NEW SUBSCRIBER ====");
    }
    public void unsubscribe(ChatController c){
        observers.remove(c);
    }
    public void invoke(Types.DataEvent ev, String data) throws IOException {
        for(ChatController c: observers){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        c.handleDatabaseHandler(ev,data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }


    public synchronized  void Initialisation() throws SQLException {
        System.out.println("===== Initialisation =====");
        String query = "create table if not exists main.History " +
                "( " +
                    "Id       integer not null " +
                    "primary key autoincrement, " +
                    "UserID  VARCHAR not null, "+
                    "Sent    Bool    not null,"+
                    "Content  VARCHAR, " +
                    "Date     Date" +
                ")";
        PreparedStatement p = con.prepareStatement(query);
        p.execute();
        System.out.println("===== History Table Initialized =====");
        query = "create table if not exists main.Users " +
                "( " +
                    "ID       integer not null " +
                    "primary key autoincrement," +
                    "Nickname VARCHAR              not null, " +
                    "Ip       VARCHAR(120)         not null, " +
                    "Port     integer default 1234 not null," +
                    "State    Bool" +
                ")";
        p = con.prepareStatement(query);
        p.execute();
        System.out.println("===== User Table Initialized =====");
        System.out.println("===== Done =====");
    }
    public synchronized void Flush() throws SQLException {
        Flush_History();
        Flush_Users();
    }
    public synchronized void Flush_Users() throws SQLException {
        String query = "Delete from Users";
        PreparedStatement p = con.prepareStatement(query);
        p.execute();
    }
    public synchronized  void Flush_History() throws SQLException {
        String query = "Delete from History";
        PreparedStatement p = con.prepareStatement(query);
        p.execute();
    }
    public ArrayList<String> LoadUsers() throws SQLException {
        ArrayList<String> l = new ArrayList<>();
        String query = "Select * from Users where State = 1 ORDER BY Nickname ASC";
        PreparedStatement p = con.prepareStatement(query);
        ResultSet rs = p.executeQuery();
        while (rs.next()){
            l.add(rs.getString("Nickname"));
        }
        return l;
    }

    public ArrayList<String> LoadDisconnectedUsers() throws SQLException {
        ArrayList<String> l = new ArrayList<>();
        String query = "Select * from Users where State = 0 ORDER BY Nickname ASC";
        PreparedStatement p = con.prepareStatement(query);
        ResultSet rs = p.executeQuery();
        while (rs.next()){
            l.add(rs.getString("Nickname"));
        }
        return l;
    }


    public synchronized void Remove(User user) throws SQLException {
        String query = "DELETE FROM Users where Nickname = ?";
        PreparedStatement p = con.prepareStatement(query);
        p.setString(1,user.getPseudo());
        p.execute();
    }
    public synchronized void Update(User user) throws SQLException {
        String query = "UPDATE Users SET Nickname = ? WHERE Ip = ?";
        PreparedStatement p = con.prepareStatement(query);
        p.setString(1,user.getPseudo());
        p.setString(2,user.getAddr());
        p.execute();
        try{
            invoke(Types.DataEvent.UpdateUser,user.getPseudo());
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public synchronized void Insert(User user) throws SQLException {
        int Uid = LoadUserID(user.getPseudo());
        if(Uid == 0){
            String query = "INSERT INTO Users(Nickname, Ip, Port,State) values (?,?,?,?)";
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1,user.getPseudo());
            p.setString(2, user.getAddr());
            p.setInt(3,user.getPort());
            p.setBoolean(4,true);
            p.execute();
        }else{
            Connect_User(user);
        }
        try{
            invoke(Types.DataEvent.NewUser,user.getPseudo());

        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public synchronized Integer LoadUserID(String name) throws SQLException {
        String query = "SELECT ID from Users where Nickname = ? ";
        PreparedStatement p = con.prepareStatement(query);
        p.setString(1, name);
        ResultSet r = p.executeQuery();
        return r.getInt("ID");
    }

    public synchronized void Insert(Message msg) throws SQLException {
        System.out.println("Insertion de message entrant");
        String query = "INSERT INTO History(UserID,Sent, Content,Date) values (?,?,?,?)";
        PreparedStatement p = con.prepareStatement(query);
        p.setString(1, LoadUserID(msg.getSender()).toString());
        p.setString(2, String.valueOf(msg.isSent()));
        p.setString(3,msg.getMsg());
        p.setDate(4, new Date(System.currentTimeMillis()));
        p.execute();
        try{
            invoke(Types.DataEvent.NewMessage,new Gson().toJson(msg));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void Connect_User(User u) throws  SQLException {
        System.out.println("==== User Disconnected ====");
        String query = "UPDATE Users Set State = ? where ID = ?";
        PreparedStatement p  = con.prepareStatement(query);
        p.setBoolean(1,true);
        p.setInt(2,LoadUserID(u.getPseudo()));
        p.execute();
        try{
            invoke(Types.DataEvent.RemUser,LoadUserID(u.getPseudo()).toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public synchronized void Disconnect_User(User u) throws  SQLException {
        System.out.println("==== User Disconnected ====");
        String query = "UPDATE Users Set State = ? where ID = ?";
        PreparedStatement p  = con.prepareStatement(query);
        p.setBoolean(1,false);
        p.setInt(2,LoadUserID(u.getPseudo()));
        p.execute();
        try{
                invoke(Types.DataEvent.RemUser,LoadUserID(u.getPseudo()).toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public synchronized ArrayList<Message> LoadHistory(User u) throws SQLException {
        ArrayList<Message> l = new ArrayList<>();
        String query = "Select Nickname,Content,Sent,History.Date from History inner join Users on Users.ID = History.UserID where UserID = ? ";
        PreparedStatement p = con.prepareStatement(query);
        p.setInt(1,LoadUserID(u.getPseudo()));
        ResultSet rs = p.executeQuery();
        while (rs.next()){
            System.out.println("Bool : "+ rs.getString("Sent"));
            Message m = new Message(rs.getString("Nickname"),rs.getString("Sent").equals("true"),rs.getString("Content"),rs.getDate("Date"));
            //System.out.println(rs.getString("Nickname"));
            l.add(m);

            //System.out.println(m);
            //System.out.println("--------------------");
        }
        return l;
    }
    public synchronized User LoadUser(int id) throws SQLException {
        String query = "Select * from Users where ID=?";
        PreparedStatement p = con.prepareStatement(query);
        p.setInt(1,id);
        ResultSet rs = p.executeQuery();
        return new User(rs.getString("Nickname"), rs.getInt("Port"), rs.getString("Ip"));
    }
    public synchronized void DisconnectAll() throws SQLException {
        System.out.println("==== Users Discconnected ====");
        String query = "UPDATE Users Set State = false";
        PreparedStatement p  = con.prepareStatement(query);
        p.execute();
    }
    public synchronized void Disconnect(){
        try {
            con.close();
        } catch (SQLException s ){
            System.out.println(s);
        }
        System.out.println("Database Disconnected successfully");
    }
}
