package com.example.eduvault_app.controller;

import com.example.eduvault_app.MainApp;
import database_conn.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Label totalDocs;
    @FXML
    private Button editButton;

    public void editButtonOnAction(ActionEvent actionEvent) {
        username.getScene().getWindow().hide();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eduvault_app/edituser.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Edit User");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(MainApp.getCurrentUser());
        System.out.println("current" + MainApp.getCurrentUser());
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();

        String query = "SELECT FullName, user_createat FROM user WHERE username = ?";
        try (PreparedStatement stmt = connectDB.prepareStatement(query)) {
            stmt.setString(1, username.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("FullName");
                String date = rs.getDate("user_createat").toString();
                FullName.setText(name);
                username.setText("@"+MainApp.getCurrentUser());
                joinedDate.setText("Joined: " + date);
            } else {
                FullName.setText("User not found.");
            }

            String query2 = "SELECT Count(DOC_ID) FROM DOCUMENT JOIN user ON document.user_id = user.user_id";
            try (PreparedStatement stmt2 = connectDB.prepareStatement(query2)) {
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    totalDocs.setText("Number of files: " + String.valueOf(rs2.getInt(1)));
                    MainApp.setCurrentUserJoinedDate(String.valueOf(rs2.getInt(1)));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
