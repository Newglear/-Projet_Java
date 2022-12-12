import java.sql.*;

/*
    A implémenter :

    Insérer User
    Remove User
    Update User
    Load Users
    Load User Informations

    Insert Message
    Load History



    Insertion de messages
    (Insertion de users ?)
    Récupération de la liste de message d'un user

*/

public class DatabaseManager {
    Connection con;
    public DatabaseManager(){

        try {
            con = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        }catch (SQLException s) {
            System.out.println(s);
        }
       /* String query = "SELECT * FROM main.History where ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1,"History");
        ResultSet rs = pstmt.executeQuery();

        System.out.println(rs.getString("user"));
*/
        System.out.println("Database Initialised");


    }

    public void LoadUsers() throws SQLException {
        String query = "Select * from Users";
        PreparedStatement p = con.prepareStatement(query);
        ResultSet rs = p.executeQuery();

/*
        do{
            System.out.println(rs.getString(2));
        }while (rs.next());*/
        System.out.println(rs.getString(2));
        rs.next();
        System.out.println(rs.getString(2));

    }
    public void Disconnect(){
        try {
            con.close();
        } catch (SQLException s ){
            System.out.println(s);
        }
        System.out.println("Database Disconnected successfully");
    }

}
