module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires java.sql;

    opens org.gui to javafx.fxml;
    opens org.database to com.google.gson;
    opens org.network to com.google.gson;
    exports org.gui;
}
