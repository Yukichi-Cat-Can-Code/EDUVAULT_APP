package com.example.eduvault_app.controller;

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
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private Label message;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File brandingFile = new File("target/classes/Images/avatar_unlogin.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);
    }

    public void loginButtonOnAction(ActionEvent actionEvent) {
        if (usernameTextField.getText().isBlank() || enterPasswordTextField.getText().isBlank()) {
            message.setText("Please enter your username and password!");
        } else {
            //validateLogin();
            message.setText("Please enter your username and password!");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean validateLogin() {

    }

}
