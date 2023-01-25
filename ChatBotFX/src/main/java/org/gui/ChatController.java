package org.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
import org.database.User;
import org.network.NetworkSender;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatController {

    private BorderPane activeBorderPane = null;

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
    private Label labelUsername;

    @FXML
    private Label labelWarningPseudo;

    @FXML
    private TextField inputChangePseudo;

    public void updateUsername(String username){
        labelUsername.setText(username);
    }

    public void loadConversation(int id) throws SQLException, IOException {
        clearConversation();
        User u =DatabaseManager.LoadUser(id);
        //System.out.println(u);
        ArrayList<Message> conversation = DatabaseManager.LoadHistory(u);
        //System.out.println(DatabaseManager.LoadHistory(u));
        System.out.println("Conv "+conversation);
        for(Message m : conversation){
            System.out.println("message "+m.getMsg()+" isSent"+m.isSent());
            createMessage(m.getMsg(),m.isSent());
        }
    }

    public void clearConversation(){
        vboxUsersMessages.getChildren().clear();
    }

    private void highLightUser(MouseEvent e){
        if(activeBorderPane != null){
            activeBorderPane.setStyle("-fx-background-color:#dfdfdf; -fx-background-radius:10");
        }
        activeBorderPane = (BorderPane) e.getTarget();
        activeBorderPane.setStyle("-fx-border-color:#ff5555; -fx-border-radius:10");
    }

/////////////////////////////////

    @FXML
    private void createUser(String nickname, boolean isConnected) throws IOException, SQLException {
        //create Border Pane
        BorderPane bPaneUsers = createUserBorderPane(nickname);

        //create ImageView
        ImageView imViewStatus = createUserImageView(isConnected);

        //Create Label
        Label lbName = createUserLabel(nickname);

        //Add Label and ImageView to BorderPane
        bPaneUsers.setLeft(lbName);
        bPaneUsers.setAlignment(lbName, Pos.CENTER);
        bPaneUsers.setRight(imViewStatus);
        bPaneUsers.setAlignment(imViewStatus, Pos.CENTER);

        //Add BorderPane to Vbox
        vboxUsersConv.getChildren().add(bPaneUsers);
    }

    private BorderPane createUserBorderPane(String nickname) throws SQLException {
        BorderPane bp = new BorderPane();
        bp.setPrefWidth(253);
        bp.setMinWidth(Control.USE_PREF_SIZE);
        bp.setPrefHeight(53);
        bp.setMinHeight(Control.USE_PREF_SIZE);
        bp.setPadding(new Insets(0,15,0,30));
        bp.setStyle("-fx-background-color:#dfdfdf; -fx-background-radius:10");
        bp.setId(DatabaseManager.LoadUserID(nickname).toString());
        bp.setOnMouseClicked(event -> {
            try {
                System.out.println(bp.getId());
                loadConversation(Integer.parseInt(bp.getId()));
                highLightUser(event);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return bp;
    }

    private ImageView createUserImageView(boolean isConnected) throws FileNotFoundException {
        ImageView iv = new ImageView();
        if(isConnected){
            iv.setImage(new Image(new FileInputStream(pathIconConnected)));
        }else{
            iv.setImage(new Image(new FileInputStream(pathIconDisconnected)));
        }
        iv.setFitHeight(28.0);
        iv.setFitWidth(34.0);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        return iv;
    }

    private Label createUserLabel(String nickname){
        Label lb = new Label();
        lb.setFont(Font.font("Comfortaa", FontWeight.LIGHT, FontPosture.REGULAR, 15));
        lb.setText(nickname);
        return lb;
    }

/////////////////////////////////

    @FXML
    private void createMessage(String message, boolean isSent) throws IOException {
        double stackPaneMaxWidth = 422;
        double messageWidth = findMessageWidth(message,stackPaneMaxWidth);

        //Create Label
        Label lbMessage = createMessageLabel(message,messageWidth);

        //Create Stack Pane
        StackPane sPaneMessage = createMessageStackPane(messageWidth);

        //Create Border Pane
        BorderPane bPaneMessage = createMessageBorderPane();

        //Add Label to StackPane
        sPaneMessage.getChildren().add(lbMessage);
        sPaneMessage.setAlignment(lbMessage, Pos.CENTER);

        //Add StackPane to BorderPane depending of sending or receiving
        if(isSent){
            sPaneMessage.setStyle("-fx-background-color: #ff5555; -fx-background-radius: 20;");
            bPaneMessage.setRight(sPaneMessage);
        }else{
            sPaneMessage.setStyle("-fx-background-color: #ededed; -fx-background-radius: 20;");
            bPaneMessage.setLeft(sPaneMessage);
        }
        bPaneMessage.setAlignment(sPaneMessage, Pos.CENTER);

        //Add BorderPane to Vbox
        vboxUsersMessages.getChildren().add(bPaneMessage);
        vboxUsersMessages.heightProperty().addListener(observable -> scrollMessage.setVvalue(1D));
    }

    private double findMessageWidth(String message, double maxWidth){
        Label lb = new Label();
        lb.setWrapText(true);
        lb.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 13));
        lb.setText(message);
        HBox h = new HBox();
        h.getChildren().add(lb);
        Scene s = new Scene(h);
        lb.applyCss();
        if(lb.prefWidth(-1) > maxWidth){
            return maxWidth;
        }else{
            return(lb.prefWidth(-1));
        }
    }

    private Label createMessageLabel(String message, double messageWidth){
        Label lb = new Label();
        lb.setWrapText(true);
        lb.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 13));
        lb.setText(message);
        lb.setPrefWidth(messageWidth);
        lb.setMaxWidth(Control.USE_PREF_SIZE);
        //System.out.println(lb.maxWidth(-1));
        lb.setPrefHeight(Control.USE_COMPUTED_SIZE);
        lb.setMinHeight(Control.USE_PREF_SIZE);
        return lb;
    }

    private BorderPane createMessageBorderPane(){
        BorderPane bp = new BorderPane();
        bp.setPrefWidth(684);
        bp.setMinWidth(Control.USE_PREF_SIZE);
        bp.setPrefHeight(Control.USE_COMPUTED_SIZE);
        bp.setMinHeight(Control.USE_PREF_SIZE);
        //bp.setStyle("-fx-background-color: pink;");
        return bp;
    }

    private StackPane createMessageStackPane(double width){
        StackPane sp = new StackPane();
        sp.setPrefWidth(width+20);
        sp.setPrefHeight(Control.USE_COMPUTED_SIZE);
        sp.setMinWidth(Control.USE_PREF_SIZE);
        sp.setMinHeight(Control.USE_PREF_SIZE);
        sp.setPadding(new Insets(7,0,7,0));
        return sp;
    }

////////////////////////////////

    @FXML
    private void sendMessage() throws IOException, SQLException {
        if(activeBorderPane != null) {
            String inputText = messageInput.getText();
            if (inputText.length() > 0) {
                //TODO: Add message to conversation history in database
                //TODO: Send the message with TCP
                createMessage(inputText, true);
                messageInput.clear();
            }
        }
    }

    public void receiveMessage(String message) throws IOException {
        //TODO: check if the conversation is selected before displaying the message
        createMessage(message, false);
    }

    @FXML
    public void displayContacts() throws SQLException, IOException {
        for(String s: DatabaseManager.LoadUsers()){
            System.out.println(s);
            System.out.println(DatabaseManager.LoadUsers());
            createUser(s,true);
        }
    }

    @FXML
    public void changePseudo(){
        String pseudo = inputChangePseudo.getText();
        if(true){ //TODO: methodGwenChangerPseudo(pseudo)
            updateUsername(pseudo);
            labelWarningPseudo.setVisible(false);
        }else{
            labelWarningPseudo.setVisible(true);
        }
    }


}
