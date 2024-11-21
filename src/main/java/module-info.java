module com.example.eduvault_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires itextpdf;
    requires org.apache.poi.ooxml;
    requires jdk.compiler;

    opens com.example.eduvault_app to javafx.fxml;
    exports com.example.eduvault_app;

    opens com.example.eduvault_app.controller to javafx.fxml;
    exports com.example.eduvault_app.controller;

    opens com.example.eduvault_app.model to javafx.fxml;
    exports com.example.eduvault_app.model;

    exports com.example.eduvault_app.testConnection to javafx.graphics;

}