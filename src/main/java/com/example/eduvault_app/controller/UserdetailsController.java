package com.example.eduvault_app.controller;

import com.example.eduvault_app.MainApp;
import database_conn.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserdetailsController implements Initializable {
    @FXML
    private Label username;
    @FXML
    private Label FullName;
    @FXML
    private Label joinedDate;
    @FXML
    private Label numOfDocs;

    public UserdetailsController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(MainApp.getCurrentUser());

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();

        String query = "SELECT FullName FROM user WHERE username = ?";
        try (PreparedStatement stmt = connectDB.prepareStatement(query)) {
            stmt.setString(1, username.getText());
            ResultSet rs = stmt.executeQuery();
            FullName.setText(rs.toString());
        }catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
