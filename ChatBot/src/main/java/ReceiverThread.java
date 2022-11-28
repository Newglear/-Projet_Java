import java.io.*;
import java.net.*;

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
            inBis = new BufferedReader(new InputStreamReader(receiverSocket.getInputStream()));

            while(true){
                message = inBis.readLine();
                System.out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
