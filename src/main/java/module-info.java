module com.example.eduvault_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.eduvault_app to javafx.fxml;
    exports com.example.eduvault_app;
}