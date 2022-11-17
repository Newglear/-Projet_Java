import java.net.*;

public class NetworkSender {

    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket dgramSocket = null;
        try {
            dgramSocket = new DatagramSocket();
        } catch (Exception e) {
            System.out.println("Connection Denied !");
        }
        dgramSocket.setBroadcast(true);

        String message = new String("Sending some shit");
        byte[] addr= {10,1,(byte)255, (byte) 255};
        //byte[] addr = {(byte) 255, (byte) 255, (byte) 255, (byte) 255};
        DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.length(),InetAddress.getByAddress(addr), 1234);
        try {
            dgramSocket.send(outPacket);
        } catch (Exception e){
            System.out.println("Je sais pas quel pb");
        }
        byte[] buffer = new byte[256];
        DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);

        try {
            dgramSocket.receive(inPacket);
        } catch (Exception e){
            System.out.println("Une autre erreur inconnue");
        }
        String response = new String(inPacket.getData(), 0, inPacket.getLength());
        System.out.println(response);
        dgramSocket.close();
    }
}
