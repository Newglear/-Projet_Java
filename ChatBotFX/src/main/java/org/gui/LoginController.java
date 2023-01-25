package org.gui;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.SystemComponents;
import org.database.DatabaseManager;
import org.database.Message;
import org.database.User;
import org.network.NetworkSender;
import org.network.Types;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Thread.sleep;

public class LoginController {

    public ArrayList<String> usernames = new ArrayList<>(Arrays.asList("Paul","Jean"));
    public String userNickname;

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
    private void userLogin() throws IOException, InterruptedException {
        String nickname = username_in.getText();
        InetAddress Baddr = InetAddress.getByName("255.255.255.255");
        User u = new User(nickname,SystemComponents.getInstance().getPort(),SystemComponents.getInstance().getCurrentIp());
        NetworkSender sendInfos = new NetworkSender(u, Types.UDPMode.UserInfos,SystemComponents.getInstance().getPort());

        sleep(1000);
        if(!SystemComponents.getInstance().UnicityCheck()) { // TODO : Tester le check unicity
            SystemComponents.getInstance().setUnicityCheck(false);
            SystemComponents.getInstance().setCurrentNickname(nickname);
            Stage stage = (Stage) bt_login.getScene().getWindow();
            stage.setHeight(660);
            stage.setWidth(980);
            App.setRoot("chat");
            stage.setTitle("Chador");
            if(App.fxmlLoader.getController().getClass() == ChatController.class){
                System.out.println("JAI CHANGE DE CLASSE");
                SystemComponents.getInstance().db.subscribe(App.fxmlLoader.getController());
            }
            stage.centerOnScreen();
            SystemComponents.getInstance().setState("chat");
        }else{
            SystemComponents.getInstance().setUnicityCheck(false);
            warning_lb.setVisible(true);
        }
    }

    public void handleDatabaseHandler(Types.DataEvent event,String data) throws IOException {
        return;
    }


}
