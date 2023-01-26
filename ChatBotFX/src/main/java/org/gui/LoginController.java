package org.gui;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.SystemComponents;
import org.database.DatabaseManager;
import org.database.Message;
import org.database.User;
import org.network.NetworkReceiver;
import org.network.NetworkSender;
import org.network.Types;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Thread.sleep;

public class LoginController {

    public ArrayList<String> usernames = new ArrayList<>(Arrays.asList("Paul","Jean"));
    public String userNickname;
    public String IpAddress;

    public boolean checkUnicity(String  username){
        boolean isFind = true;
        for (String u : usernames) {
            if(username.equals(u)){
                System.out.println("oj");
                isFind = false;
            }
        }
        return isFind;
    }

    @FXML
    private Button bt_login;

    @FXML
    private Label warning_lb;

    @FXML
    private TextField username_in;


    @FXML
    private void userLogin(ActionEvent e) throws IOException, InterruptedException, SQLException {
        String nickname = username_in.getText();
        if(nickname.length() > 0) {
            InetAddress Baddr = SystemComponents.toBroadcast(SystemComponents.getIPv4());
            User u = new User(nickname, SystemComponents.getInstance().getPort(), SystemComponents.getInstance().getCurrentIp());
            NetworkSender sendInfos = new NetworkSender(u, Types.UDPMode.UserInfos, SystemComponents.getInstance().getPort());

            sleep(1500);
            if (!SystemComponents.getInstance().UnicityCheck()) {
                SystemComponents.getInstance().setCurrentNickname(nickname);
                switchLoginScene(e, nickname);

            } else {
                warning_lb.setText("Warning: this username is already used");
                warning_lb.setVisible(true);
                SystemComponents.getInstance().setUnicityCheck(false);
            }
        }else{
            warning_lb.setText("Warning: at least one character is needed");
            warning_lb.setVisible(true);
        }
    }

    private void switchLoginScene(ActionEvent e, String nickname) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Chador");
        stage.centerOnScreen();

        ChatController cc = (ChatController) loader.getController();
        //Note: si tu veux un chatcontroller dans une autre classe tu écris la ligne en  dessous pour le récupérer
        //ChatController cc = (ChatController) (new FXMLLoader(getClass().getResource("chat.fxml"))).getController();
        System.out.println(cc);
        if(cc.getClass() == ChatController.class){
            SystemComponents.getInstance().db.subscribe(cc);
        }
        SystemComponents.getInstance().setState("chat");
        cc.updateUsername(nickname);
        cc.displayContacts();
    }
    public void setIpAddress(String addr){
        IpAddress = addr;
    }
}
