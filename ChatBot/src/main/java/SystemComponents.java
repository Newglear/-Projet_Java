public class SystemComponents {
    public DatabaseManager db;

    public static ThreadManager ThreadManager;

    public static String currentNickname;
    public static String currentIp;

    public SystemComponents(){
        db = new DatabaseManager();

    }
}
