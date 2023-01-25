package org.conv;

import com.google.gson.Gson;
import org.SystemComponents;
import org.database.Message;
import org.database.DatabaseManager;

import java.io.*;
import java.net.*;
import java.sql.Date;
import java.sql.SQLException;

public class ReceiverThread extends Thread {

    ServerSocket receiverServSocket;
    Gson gson = new Gson();

    public ReceiverThread(ServerSocket socket) {
        super("receive");
        this.receiverServSocket = socket;
    }

    public void run() {
        System.out.println("Receiver running, port : "+this.receiverServSocket.getLocalPort());
        try {
        Socket receiverSocket;
        BufferedReader inBis;
        String message;


            receiverSocket = receiverServSocket.accept();
            //System.out.println("Connection Receiver Sender Accepted");

            while(true){
                inBis = new BufferedReader(new InputStreamReader(receiverSocket.getInputStream()));
                message = inBis.readLine();
                if(message == null){
                    continue;
                }
                System.out.println("=====Receiver=====");
                Message msg = gson.fromJson(message, Message.class);
                msg.setDate(new Date(System.currentTimeMillis()));
                msg.toggleSent();
                System.out.println(msg);
                System.out.println("==================");
                try{
                    SystemComponents.getInstance().db.Insert(msg);
                }catch (SQLException s){
                    System.out.println(s);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
