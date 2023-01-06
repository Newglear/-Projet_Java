package org;

import org.database.DatabaseManager;
import org.network.NetworkReceiver;

public class SystemComponents {
    public static DatabaseManager db;

    public static ThreadManager Threads;

    public static NetworkReceiver NetworkServer;
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

    private static String currentNickname;
    private static String currentIp;

    public SystemComponents(){
        db = new DatabaseManager();
        try{
            Threads = new ThreadManager();
            ThreadManager.StartConnection();
            NetworkServer = new NetworkReceiver("Network Server", false, 1234);
        }catch(Exception e){
            System.out.println(e);
        }
    }


}
