module com.example.eduvault_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires itextpdf;
    requires org.apache.poi.ooxml;

    opens com.example.eduvault_app to javafx.fxml;
    exports com.example.eduvault_app;

    opens com.example.eduvault_app.controller to javafx.fxml;
    exports com.example.eduvault_app.controller;
}