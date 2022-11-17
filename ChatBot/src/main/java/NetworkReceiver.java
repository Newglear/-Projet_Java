import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkReceiver {
    public static void main(String[] args) {
        DatagramSocket dgramSocket = null;
        try {
            dgramSocket = new DatagramSocket(1234);
        } catch (Exception e) {
            System.out.println("Connection Denied !");
        }

        byte[] buffer = new byte[256];
        DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
        try {
            dgramSocket.receive(inPacket);
        } catch (Exception e){
            System.out.println("Je sais pas quel pb");
        }
        String recept = new String(inPacket.getData(), 0, inPacket.getLength());
        System.out.println(recept);
        InetAddress clientAddress = inPacket.getAddress();
        int clientPort = inPacket.getPort();
        String response = "Test";
        DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length(),clientAddress, clientPort);

        try {
            dgramSocket.send(outPacket);
        } catch (Exception e){
            System.out.println("Une autre erreur inconnue");
        }

        dgramSocket.close();
    }
}
