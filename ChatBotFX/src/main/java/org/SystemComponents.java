package org;

import org.conv.ConnectionManager;
import org.database.DatabaseManager;
import org.database.User;
import org.network.NetworkReceiver;

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


}
