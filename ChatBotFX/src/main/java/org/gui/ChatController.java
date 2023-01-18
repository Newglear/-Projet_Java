package org.gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import org.SystemComponents;
import org.ThreadManager;
import org.conv.SenderThread;
import org.database.DatabaseManager;
import org.database.Message;
import org.network.NetworkSender;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

public class ChatController {


    private String selectedUser = "Connard";
    private String pathIconConnected = "src/main/resources/org/gui/images/iconConnected.png";
    private String pathIconDisconnected = "src/main/resources/org/gui/images/iconDisconnected.png";

    @FXML
    private  VBox vboxUsersConv;

    @FXML
    private VBox vboxUsersMessages;

    @FXML
    private TextField messageInput;

    @FXML
    private ScrollPane scrollMessage;

    @FXML
    private void createUserBorderPane(String nickname, boolean isConnected) throws IOException {
        //create Border Pane
        BorderPane bPaneUsers = new BorderPane();
        bPaneUsers.setPrefWidth(253);
        bPaneUsers.setMinWidth(Control.USE_PREF_SIZE);
        bPaneUsers.setPrefHeight(53);
        bPaneUsers.setMinHeight(Control.USE_PREF_SIZE);
        bPaneUsers.setPadding(new Insets(0,15,0,30));
        bPaneUsers.setStyle("-fx-background-color: #dfdfdf; -fx-background-radius: 10;");

        //create ImageView
        ImageView imViewStatus = new ImageView();
        if(isConnected){
            imViewStatus.setImage(new Image(new FileInputStream(pathIconConnected)));
        }else{
            imViewStatus.setImage(new Image(new FileInputStream(pathIconDisconnected)));
        }
        imViewStatus.setFitHeight(28.0);
        imViewStatus.setFitWidth(34.0);
        imViewStatus.setPickOnBounds(true);
        imViewStatus.setPreserveRatio(true);

        //Create Label
        Label lbName = new Label();
        lbName.setFont(Font.font("Comfortaa", FontWeight.LIGHT, FontPosture.REGULAR, 15));
        lbName.setText(nickname);

        //Add Label and ImageView to BorderPane
        bPaneUsers.setLeft(lbName);
        bPaneUsers.setAlignment(lbName, Pos.CENTER);
        bPaneUsers.setRight(imViewStatus);
        bPaneUsers.setAlignment(imViewStatus, Pos.CENTER);

        //Add BorderPane to Vbox
        vboxUsersConv.getChildren().add(bPaneUsers);
    }

    @FXML
    private void displayMessageSent() throws IOException, SQLException {
        String inputText = messageInput.getText();
        if(inputText.length() > 0){
            createMessageBorderPane(inputText, false);
            messageInput.clear();
            /*SenderThread th= ThreadManager.getThread(user);
            if((th == null ){
                ThreadManager.createSenderThread(InetAddress.getByName(DatabaseManager.LoadUser(selectedUser).getAddr()),DatabaseManager.LoadUser(selectedUser).getPort());
            }
            th.Send(new Message(SystemComponents.getCurrentNickname(),true,inputText));*/
        }
    }

    /*private void displayMessageReceived(String message) throws IOException {
        String inputText = messageInput.getText();
        if(inputText.length() > 0){
            createMessageBorderPane(message, true);
            messageInput.clear();
        }
    }*/

    @FXML
    public void displayContacts() throws SQLException, IOException {
        for(String s: DatabaseManager.LoadUsers()){
            System.out.println(s);
            System.out.println(DatabaseManager.LoadUsers());
            createUserBorderPane(s,true);
        }
    }

    @FXML
    private void createMessageBorderPane(String message, boolean isReceived) throws IOException {
        int stackPaneMaxWidth = 442;
        int stackPaneWidth;
        int stackPaneMinHeight = 39;
        int stackPaneHeight;
        int labelSize;


        if(message.length() <= 50){
            System.out.println(message.length());
            stackPaneWidth = 422*message.length()/50 +20;
            stackPaneHeight = stackPaneMinHeight;
            System.out.println(stackPaneWidth+" "+stackPaneHeight);
            labelSize = 422*message.length()/50;
        }else{
            stackPaneWidth = stackPaneMaxWidth;
            stackPaneHeight = stackPaneMinHeight + (message.length() / 50)*15;
            labelSize = 421;
        }

        //Create Border Pane
        BorderPane bPaneMessage = new BorderPane();
        bPaneMessage.setPrefWidth(684);
        bPaneMessage.setMinWidth(Control.USE_PREF_SIZE);
        bPaneMessage.setPrefHeight(stackPaneHeight);
        bPaneMessage.setMinHeight(Control.USE_PREF_SIZE);
        //bPaneMessage.setStyle("-fx-background-color: pink;");

        //Create Stack Pane
        StackPane sPaneMessage = new StackPane();
        sPaneMessage.setPrefWidth(stackPaneWidth);
        sPaneMessage.setMinWidth(Control.USE_PREF_SIZE);
        sPaneMessage.setPrefHeight(stackPaneHeight);
        sPaneMessage.setMinHeight(Control.USE_PREF_SIZE);

        //Create Label
        Label lbMessage = new Label();
        lbMessage.setPrefWidth(labelSize);
        lbMessage.setMaxWidth(Control.USE_PREF_SIZE);
        lbMessage.setWrapText(true);
        lbMessage.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 13));
        lbMessage.setText(message);

        //Add Label to StackPane and StackPane to BorderPane
        sPaneMessage.getChildren().add(lbMessage);
        sPaneMessage.setAlignment(lbMessage, Pos.CENTER);

        if(isReceived){
            sPaneMessage.setStyle("-fx-background-color: #ededed; -fx-background-radius: 20;");
            bPaneMessage.setLeft(sPaneMessage);
        }else{
            sPaneMessage.setStyle("-fx-background-color: #ff5555; -fx-background-radius: 20;");
            bPaneMessage.setRight(sPaneMessage);
        }

        bPaneMessage.setAlignment(sPaneMessage, Pos.CENTER);

        //Add BorderPane to Vbox
        vboxUsersMessages.getChildren().add(bPaneMessage);
        vboxUsersMessages.heightProperty().addListener(observable -> scrollMessage.setVvalue(1D));
    }
}
