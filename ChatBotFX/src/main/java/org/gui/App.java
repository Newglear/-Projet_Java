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
import org.network.NetworkSender;
import org.network.Types;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
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
    public void stop() throws SQLException, SocketException, UnknownHostException {
        //TODO: fermer les threads
        SystemComponents sys = SystemComponents.getInstance();
        NetworkSender send = new NetworkSender(new User(sys.getCurrentNickname(), sys.getPort(), sys.getCurrentNickname()), Types.UDPMode.Disconnect,1234);
        SystemComponents.getInstance().db.DisconnectAll();
        System.out.println("L'application s'est arrêtée");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException, SQLException {
        //System.out.println(System.getProperty("user.dir"));
        SystemComponents sys = SystemComponents.getInstance();
        //sys.db.Flush();
        int port = 1234;
        sys.setPort(port);
        sys.setCurrentIp(SystemComponents.getIPv4().getHostName());

        launch();
    }

}