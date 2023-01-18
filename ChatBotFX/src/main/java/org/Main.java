package org;

import org.database.DatabaseManager;
import org.database.User;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        SystemComponents sys = SystemComponents.getInstance();
        DatabaseManager.Initialisation();
        DatabaseManager.Flush();
    }
}
