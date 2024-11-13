package com.example.eduvault_app.controller;

import database_conn.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private Label message;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        File brandingFile = new File("Images/avatar_unlogin.png");
//        Image brandingImage = new Image(brandingFile.toURI().toString());
//        brandingImageView.setImage(brandingImage);

        message.setText("");
    }

    public void loginButtonOnAction(ActionEvent event) {
        if (usernameTextField.getText().isBlank() || enterPasswordField.getText().isBlank()) {
            message.setText("Please enter your username and password!");
        } else {
            if (validateLogin() == true) {
                message.setText("Login Successful!");
            } else {
                message.setText("Login Failed!");
            }
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean validateLogin() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();

        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connectDB.prepareStatement(query)) {
            stmt.setString(1, usernameTextField.getText()); // Set the username parameter
            stmt.setString(2, enterPasswordField.getText());    // Set the password parameter

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } return false;
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return false;
    }

}
