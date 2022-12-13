import java.io.*;
import java.net.*;
import java.sql.Date;
import java.sql.SQLException;

public class ReceiverThread extends Thread {

    ServerSocket receiverServSocket;

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

                System.out.println(message);
                try{
                    DatabaseManager.Insert(new Message("Newg", "Chador", message, new Date(System.currentTimeMillis())));

                }catch (SQLException s){
                    System.out.println(s);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
