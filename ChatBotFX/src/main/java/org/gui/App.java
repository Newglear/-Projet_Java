package org.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.SystemComponents;
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

        //scene = new Scene(loadFXML("login"), 468, 455);
        scene = new Scene(loadFXML("login"),980, 630);
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
        SystemComponents sys = SystemComponents.getInstance();
        int port = 1234;
        sys.setPort(port);
        sys.setCurrentIp(SystemComponents.getIPv4().getHostName());
        launch();
    }

}