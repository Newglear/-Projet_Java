package org;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        SystemComponents sys = SystemComponents.getInstance();
        sys.db.Initialisation();
        sys.db.Flush();
    }
}
