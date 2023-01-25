package org.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.SystemComponents;
import org.database.User;
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

    /*public boolean checkUnicity(String  username){
        boolean isFind = true;
        for (String u : usernames) {
            if(username.equals(u)){
                System.out.println("oj");
                isFind = false;
            }
        }
        return isFind;
    }*/

    @FXML
    private Label warning_lb;

    @FXML
    private TextField username_in;


    @FXML
    private void userLogin(ActionEvent e) throws IOException, InterruptedException, SQLException {
        String nickname = username_in.getText();
        if(nickname.length() > 0) {
            InetAddress Baddr = InetAddress.getByName("255.255.255.255");
            User u = new User(nickname, SystemComponents.getPort(), SystemComponents.getCurrentIp());
            NetworkSender sendInfos = new NetworkSender(u, Types.UDPMode.UserInfos, SystemComponents.getPort());

            //sleep(500);
            if (!SystemComponents.UnicityCheck()) { // TODO : Tester le check unicity
                SystemComponents.setCurrentNickname(nickname);

                App.setWindow("chat", (Stage) username_in.getScene().getWindow(), "Chador", 980, 630);
                ChatController cc = (ChatController) App.fxmlloader.getController();
                cc.updateUsername(nickname);
                cc.displayContacts();
            } else {
                warning_lb.setText("Warning: this username is already used");
                warning_lb.setVisible(true);
            }
        }else{
            warning_lb.setText("Warning: at least one character is needed");
            warning_lb.setVisible(true);
        }
    }

}
