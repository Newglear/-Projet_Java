import java.io.*;
import java.net.*;


public class SenderThread extends Thread{

    InetAddress adressReceiver;
    int publicReceiverPort;
    int receiverPort;
    String message;

    public SenderThread(InetAddress IPaddress, int publicPort, String message)  {
        super("send");
        this.adressReceiver = IPaddress;
        this.publicReceiverPort = publicPort;
        this.message = message;
    }

    public void requestConnectionPort() throws IOException{
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
        try {
            Socket convSocket;
            PrintWriter outBis;
            this.requestConnectionPort();

            convSocket = new Socket(this.adressReceiver, this.receiverPort);


            while(true){
                sleep(5000);
                outBis = new PrintWriter(convSocket.getOutputStream(), true);
                outBis.println(this.message);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}