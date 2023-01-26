package org.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.SystemComponents;
import org.database.DatabaseManager;
import org.database.Message;
import org.database.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.show();
        //TODO: add IP address to label
    }

    @Override
    public void stop(){
        System.out.println("L'application s'est arrêtée");
        //TODO: fermer les threads
    }

    public static void main(String[] args) throws IOException, SQLException {
        //System.out.println(System.getProperty("user.dir"));
        SystemComponents sys = SystemComponents.getInstance();
        DatabaseManager.Flush();
        InetAddress addr = InetAddress.getByName("10.1.5.235");
        int port = 1234;
        SystemComponents.setPort(port);
        SystemComponents.setCurrentIp(addr.getHostName());

        List<String> nicknames = Arrays.asList("Gwen","Cador","Evan","Joel","Kiki");
        for(String n:nicknames){
            DatabaseManager.Insert(new User(n,(int)(Math.random()*(2000-1234+1)+1234),"192.168.25."));
            System.out.println(n);
        }
        ArrayList<Message> messageList = new ArrayList<>();
        messageList.add(new Message(nicknames.get(0),true,"Message de test de Gwen"));
        messageList.add(new Message(nicknames.get(1),true,"szerguihrigbs"));
        messageList.add(new Message(nicknames.get(0),true,"vszjbhuzsbvkjhdfgbsrg"));
        messageList.add(new Message(nicknames.get(1),false,"dgfbjhbnsduibsdfuihjsbrgf"));
        for(Message m: messageList){
            DatabaseManager.Insert(m);
        }
        launch();
    }

}