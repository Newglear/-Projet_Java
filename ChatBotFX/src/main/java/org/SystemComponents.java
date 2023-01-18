package org;

import org.database.DatabaseManager;
import org.database.User;
import org.network.NetworkReceiver;

public class SystemComponents {

    private static SystemComponents sys = null;
    public static DatabaseManager db;
    public static ThreadManager Threads;
    public static NetworkReceiver NetworkServer;
    private static String currentNickname;
    private static String currentIp;
    private  static  int port;
    private static boolean unicityCheck;


    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        SystemComponents.port = port;
    }
    public static String getCurrentNickname() {
        return currentNickname;
    }

    public static void setCurrentNickname(String currentNickname) {
        SystemComponents.currentNickname = currentNickname;
    }

    public static String getCurrentIp() {
        return currentIp;
    }

    public static void setCurrentIp(String currentIp) {
        SystemComponents.currentIp = currentIp;
    }


    private SystemComponents(){
        unicityCheck = false;
        db = new DatabaseManager();
        try{
            Threads = new ThreadManager();
            ThreadManager.StartConnection();
            NetworkServer = new NetworkReceiver("Network Server", true, 1234);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static boolean UnicityCheck() {
        return unicityCheck;
    }

    public static void setUnicityCheck(boolean unicityCheck) {
        SystemComponents.unicityCheck = unicityCheck;
    }

    public static SystemComponents getInstance(){
        if(sys == null){
            sys = new SystemComponents();
        }

        return sys;
    }


}
