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
        db = new DatabaseManager();
        try{
            Threads = new ThreadManager();
            ThreadManager.StartConnection();
            NetworkServer = new NetworkReceiver("Network Server", false, 1234);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static SystemComponents getInstance(){
        if(sys == null){
            sys = new SystemComponents();
        }


        return sys;
    }


}
