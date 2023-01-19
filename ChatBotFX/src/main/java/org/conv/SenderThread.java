package org.conv;

import com.google.gson.Gson;
import org.database.Message;
import org.database.DatabaseManager;

import java.io.*;
import java.net.*;
import java.sql.Date;
import java.sql.SQLException;

public class SenderThread extends Thread {
    static int number = 0;
    Gson gson = new Gson();
    Socket convSocket;
    PrintWriter outChannel;
    InetAddress adressReceiver;
    int publicReceiverPort;
    int receiverPort;
    public SenderThread(InetAddress IPaddress, int publicPort) {
        super(IPaddress.getHostName());
        this.adressReceiver = IPaddress;
        this.publicReceiverPort = publicPort;
    }

    public void requestConnectionPort() throws IOException {
        Socket socket;
        BufferedReader inBis;
        socket = new Socket(this.adressReceiver, this.publicReceiverPort);
        inBis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Conversation port received : " + this.receiverPort);
        this.receiverPort = Integer.parseInt(inBis.readLine());
    }

    public void run() {
        System.out.println("Sender running, public port : " + this.publicReceiverPort);
        try {
            this.requestConnectionPort();
            convSocket = new Socket(this.adressReceiver, this.receiverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Send(Message msg){
        try {
            msg.setSent(true);
            outChannel = new PrintWriter(convSocket.getOutputStream(), true);
            outChannel.println(gson.toJson(msg));
            DatabaseManager.Insert(msg);
        } catch (SQLException | IOException s) {
            s.printStackTrace();
        }
    }

}



