package org;

import org.conv.ConnectionManager;
import org.database.DatabaseManager;
import org.database.User;
import org.network.NetworkReceiver;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class SystemComponents {



    private String state;
    private static SystemComponents sys = null;
    public  DatabaseManager db;
    public  ThreadManager Threads;
    public NetworkReceiver NetworkServer;
    public ConnectionManager ConnectionServer;
    private String currentNickname;
    private String currentIp;
    private int port;
    private boolean unicityCheck;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPort() {
        return port;
    }

    public  void setPort(int port) {
        this.port = port;
    }
    public  String getCurrentNickname() {
        return this.currentNickname;
    }

    public void setCurrentNickname(String currentNickname) {
        this.currentNickname = currentNickname;
    }

    public  String getCurrentIp() {
        return this.currentIp;
    }

    public  void setCurrentIp(String currentIp) {
        this.currentIp = currentIp;
    }


    private SystemComponents(){
        unicityCheck = false;
        try{
            db = new DatabaseManager();
            System.out.println("INITIALISATION DES COMPOSANTS SYSTEME");
            Threads = new ThreadManager();
            ThreadManager.StartConnection();
            System.out.println(("Thread Manager OK"));
            NetworkServer = new NetworkReceiver("Network Server", true, 1234);
            System.out.println(("Network Server OK"));
            ConnectionServer = new ConnectionManager();
            ConnectionServer.start();
            System.out.println(("Connection Manager OK"));

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public boolean UnicityCheck() {
        return unicityCheck;
    }

    public  void setUnicityCheck(boolean unicityCheck) {
        this.unicityCheck = unicityCheck;
    }

    public static SystemComponents getInstance(){
        if(sys == null){
            sys = new SystemComponents();
        }
        return sys;
    }

    public static ArrayList<InetAddress> getSelfAddresses() throws SocketException, UnknownHostException {
        // several interfaces with several broadcasts
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        ArrayList<InetAddress> addressList = new ArrayList<InetAddress>();
        // iterates over all
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            // A valid address
            // Is not the loopback
            if (networkInterface.isLoopback()) {
                continue;
            }
            // Has its interface up
            if (!networkInterface.isUp()) {
                continue;
            }
            for (InterfaceAddress i : networkInterface.getInterfaceAddresses()) {
                InetAddress address = i.getAddress();
                // Is not the localhost address
                if (address.equals(InetAddress.getByName("127.0.0.1"))) {
                    continue;
                }
                addressList.add(address);
            }
        }
        return addressList;
    }
    public static InetAddress getIPv4() throws SocketException, UnknownHostException {
        ArrayList<InetAddress> addresses = getSelfAddresses();
        for(InetAddress addr : addresses){
            if(addr.getHostName().matches("(\\d{1,3}\\.){3}\\d{1,3}")){
                return addr;
            }
        }
        return null;
    }

    public static InetAddress toBroadcast(InetAddress address) throws UnknownHostException {
        String addr = address.getHostName();
        String[] split = addr.split("\\.");
        split[0] += ".";
        split[1] += ".";
        split[2] += ".";
        split[3] = "255";
        return InetAddress.getByName(split[0]+split[1]+split[2]+split[3]);
    }


}
