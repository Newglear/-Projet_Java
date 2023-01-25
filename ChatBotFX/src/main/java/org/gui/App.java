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
    public static Scene scene;
    public static FXMLLoader fxmlloader;
    private ChatController cc = new ChatController();

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("login"), 468, 455);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.show();
        //TODO: add IP address to label
    }

    public static void setWindow(String fxml, Stage stage, String title, int width, int height) throws IOException {
        stage.setTitle(title);
        scene = new Scene(App.loadFXML(fxml), width, height);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        fxmlloader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlloader.load();
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