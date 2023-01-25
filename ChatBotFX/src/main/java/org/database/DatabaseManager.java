package org.database;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseManager {
    private static Connection con;
    public DatabaseManager(){
        try {
            con = DriverManager.getConnection("jdbc:sqlite:ChaDBsqlite");
        }catch (SQLException s) {
            System.out.println(s);
        }
        System.out.println("Database Initialised");
    }

    public static void Initialisation() throws SQLException {
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
                    "Port     integer default 1234 not null" +
                    "State    Bool" +
                ")";
        p = con.prepareStatement(query);
        p.execute();
        System.out.println("===== User Table Initialized =====");
        System.out.println("===== Done =====");
    }
    public static  void Flush() throws SQLException {
        Flush_History();
        Flush_Users();
    }
    public static void Flush_Users() throws SQLException {
        String query = "Delete from Users";
        PreparedStatement p = con.prepareStatement(query);
        p.execute();
    }
    public static void Flush_History() throws SQLException {
        String query = "Delete from History";
        PreparedStatement p = con.prepareStatement(query);
        p.execute();
    }
    public static ArrayList<String> LoadUsers() throws SQLException {
        ArrayList<String> l = new ArrayList<>();
        String query = "Select * from Users";
        PreparedStatement p = con.prepareStatement(query);
        ResultSet rs = p.executeQuery();
        while (rs.next()){
            l.add(rs.getString("Nickname"));
        }
        return l;
    }


    public static void Remove(User user) throws SQLException {
        String query = "DELETE FROM Users where Nickname = ?";
        PreparedStatement p = con.prepareStatement(query);
        p.setString(1,user.getPseudo());
        p.execute();
    }
    public static void Update(User user) throws SQLException {
        String query = "UPDATE Users SET Nickname = ? WHERE Ip = ?";
        PreparedStatement p = con.prepareStatement(query);
        p.setString(1,user.getPseudo());
        p.setString(2,user.getAddr());
        p.execute();
    }
    public static void Insert(User user) throws SQLException {
        String query = "INSERT INTO Users(Nickname, Ip, Port) values (?,?,?)";
        PreparedStatement p = con.prepareStatement(query);
        p.setString(1,user.getPseudo());
        p.setString(2, user.getAddr());
        p.setInt(3,user.getPort());
        p.execute();
    }

    public static Integer LoadUserID(String name) throws SQLException {
        String query = "SELECT ID from Users where Nickname = ? ";
        PreparedStatement p = con.prepareStatement(query);
        p.setString(1, name);
        ResultSet r = p.executeQuery();
        return r.getInt("ID");
    }
    public static void Insert(Message msg) throws SQLException {
        String query = "INSERT INTO History(UserID,Sent, Content,Date) values (?,?,?,?)";
        PreparedStatement p = con.prepareStatement(query);
        p.setString(1, LoadUserID(msg.getSender()).toString());
        p.setString(2, String.valueOf(msg.isSent()));
        p.setString(3,msg.getMsg());
        p.setDate(4, new Date(System.currentTimeMillis()));
        p.execute();
    }
    public static void Disconnect_User(User u) throws  SQLException {
        String query = "UPDATE Users Set State = ? where ID = ?";
        PreparedStatement p  = con.prepareStatement(query);
        p.setBoolean(1,false);
        p.setInt(2,DatabaseManager.LoadUserID(u.getPseudo()));
        p.execute();
    }
    public static ArrayList<Message> LoadHistory(User u) throws SQLException {
        ArrayList<Message> l = new ArrayList<>();
        String query = "Select Nickname,Content,Sent,History.Date from History inner join Users on Users.ID = History.UserID where UserID = ?";
        PreparedStatement p = con.prepareStatement(query);
        p.setInt(1,LoadUserID(u.getPseudo()));
        ResultSet rs = p.executeQuery();
        System.out.println(rs);
        /*System.out.println("........"+ u);
        System.out.println("?????"+u.getPseudo());
        System.out.println("AAAAAAAA: "+LoadUserID(u.getPseudo()));*/
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
    public static User LoadUser(int id) throws SQLException {
        String query = "Select * from Users where ID=?";
        PreparedStatement p = con.prepareStatement(query);
        p.setInt(1,id);
        ResultSet rs = p.executeQuery();
        return new User(rs.getString("Nickname"), rs.getInt("Port"), rs.getString("Ip"));
    }
    public static void Disconnect(){
        try {
            con.close();
        } catch (SQLException s ){
            System.out.println(s);
        }
        System.out.println("Database Disconnected successfully");
    }
}
