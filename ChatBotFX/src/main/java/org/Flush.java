package org;

import org.database.DatabaseManager;

import java.sql.SQLException;

public class Flush {
    public static void main(String[] args) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.Flush();
    }
}
