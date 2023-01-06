package org;

import org.database.DatabaseManager;
import org.network.NetworkReceiver;

public class MainReceive {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        NetworkReceiver r = new NetworkReceiver("R1",false,1234);
    }
}
