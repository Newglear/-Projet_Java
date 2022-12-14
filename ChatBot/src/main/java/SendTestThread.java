import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;

public class SendTestThread extends Thread{
    String adressReceiver;
    int publicReceiverPort;
    int receiverPort;
    String message;

    public SendTestThread(String IPaddress, int publicPort, String message)  {
        this.adressReceiver = IPaddress;
        this.publicReceiverPort = publicPort;
        this.message = message;
    }


    public void requestConnectionPort() throws IOException {
        Socket socket;
        BufferedReader inBis;
        int convPort;

        socket = new Socket(this.adressReceiver,this.publicReceiverPort);
        //System.out.println("Connected to Manager");
        inBis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        convPort = Integer.parseInt(inBis.readLine());
        System.out.println("Convesation port received : "+convPort);
        this.receiverPort = convPort;
    }


    public void run() {
        System.out.println("Sender running, public port : "+this.publicReceiverPort);
    }

    public void Send(String msg){
        System.out.println("Envoi du message" + msg);
        try {
            Socket convSocket;
            PrintWriter outBis;
            this.requestConnectionPort();

            convSocket = new Socket(this.adressReceiver, this.receiverPort);
            outBis = new PrintWriter(convSocket.getOutputStream(), true);
            outBis.println(this.message);
            }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String GetAddress(){
        return this.adressReceiver;
    }

}
