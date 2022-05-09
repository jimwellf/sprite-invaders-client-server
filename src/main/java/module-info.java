module com.example.spriteinvadersclientserver {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;

    opens com.example.spriteinvadersclientserver to javafx.fxml;
    exports com.example.spriteinvadersclientserver;
}