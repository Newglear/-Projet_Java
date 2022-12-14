import java.io.IOException;

public class SystemComponents {
    public static DatabaseManager db;

    public static ThreadManager Threads;

    public static String currentNickname;
    public static String currentIp;

    public SystemComponents(){
        db = new DatabaseManager();
        try{
            Threads = new ThreadManager();
            ThreadManager.StartConnection();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
