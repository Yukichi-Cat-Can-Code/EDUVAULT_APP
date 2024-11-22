package com.example.eduvault_app.controller;

import com.example.eduvault_app.MainApp;
import com.example.eduvault_app.util.JDBCUtil;
import com.sun.tools.javac.Main;
import database_conn.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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

        //message.setText(" ");
    }

    public void loginButtonOnAction(ActionEvent event) throws IOException {
        if (usernameTextField.getText().isBlank() || enterPasswordField.getText().isBlank()) {
            message.setText("Please enter your username and password!");
        } else {
            if (validateLogin() == true) {
                MainApp.setCurrentUser(usernameTextField.getText());
                message.getScene().getWindow().hide();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eduvault_app/testMergeUser.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("User Details");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    e.getCause();
                }
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
