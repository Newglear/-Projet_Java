package org.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.SystemComponents;
import org.database.User;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {
    //login : height 455 / width 468 630 980
    private static App app = null;
    private static Scene scene;
    public static FXMLLoader fxmlLoader;
    private int height = 455;
    private int width = 468;
    private ChatController cc = new ChatController();


    @Override
    public void start(Stage stage) throws IOException {

        app = this;
        scene = new Scene(loadFXML("login"), width, height);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Login");
        SystemComponents.getInstance().setState("Login");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    public static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException, SQLException {
        //System.out.println(System.getProperty("user.dir"));
        SystemComponents sys = SystemComponents.getInstance();

        SystemComponents.getInstance().db.Flush();
        InetAddress addr = InetAddress.getByName("10.1.5.235");
        int port = 1234;
        SystemComponents.getInstance().setPort(port);
        SystemComponents.getInstance().setCurrentIp(addr.getHostName());

        List<String> nicknames = Arrays.asList("Gwen","Cador","Evan","Joel","Kiki","G\nen","Wesh","???.?????????????????????    ???????");
        for(String n:nicknames){
            SystemComponents.getInstance().db.Insert(new User(n,(int)(Math.random()*(2000-1234+1)+1234),"192.168.25."));
            System.out.println(n);
        }
        launch();

    }

}