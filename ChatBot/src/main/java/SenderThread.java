import java.io.*;
import java.net.*;


public class SenderThread extends Thread{

    InetAddress adressReceiver;
    int receiverPort;

    public SenderThread(InetAddress IPaddress, int publicPort)  {
        super();
        this.adressReceiver = IPaddress;
        this.receiverPort = publicPort;
    }

    public void requestConnectionPort() throws IOException{
        Socket socket;
        BufferedReader inBis;
        int convPort;

        socket = new Socket(this.adressReceiver,this.receiverPort);
        inBis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        convPort = Integer.parseInt(inBis.readLine());
        socket.close();
        this.receiverPort = convPort;
    }


    public void run() {
        try {
            Socket convSocket;
            PrintWriter outBis;
            this.requestConnectionPort();

            convSocket = new Socket(this.adressReceiver, this.receiverPort);


            while(true){
                sleep(5000);
                outBis = new PrintWriter(convSocket.getOutputStream(), true);
                outBis.println("Ceci est un message de détresse, achevez-moi");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
