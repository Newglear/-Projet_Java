import java.io.*;
import java.net.*;

public class ReceiverThread extends Thread {

    ServerSocket receiverServSocket;

    public ReceiverThread(ServerSocket socket) {
        this.receiverServSocket = socket;
    }

    public void run() {
        try {
        Socket receiverSocket;
        BufferedReader inBis;
        String message;


            receiverSocket = receiverServSocket.accept();

            while(true){
                inBis = new BufferedReader(new InputStreamReader(receiverSocket.getInputStream()));;
                message = inBis.readLine();
                System.out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
