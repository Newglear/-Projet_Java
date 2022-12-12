module com.example.pour_evan {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.pour_evan to javafx.fxml;
    exports com.example.pour_evan;
}