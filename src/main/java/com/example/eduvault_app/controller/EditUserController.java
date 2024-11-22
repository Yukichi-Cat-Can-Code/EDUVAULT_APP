package com.example.eduvault_app.controller;

import com.example.eduvault_app.MainApp;
import database_conn.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {
    @FXML
    private TextField fullNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label joinedDate;
    @FXML
    private Label username;
    @FXML
    private TextField emailField;
    @FXML
    private Label Fullname;
    @FXML
    private Label Email2;

    public void saveButtonOnAction(ActionEvent actionEvent) throws IOException {
        String fullName = fullNameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();

        if (!fullName.isEmpty()) {
            String query = "UPDATE user SET fullname = ? WHERE username = ?;";
            try (PreparedStatement stmt = connectDB.prepareStatement(query)) {
                stmt.setString(1, fullNameField.getText());
                stmt.setString(2, MainApp.getCurrentUser());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Update successful!");
                } else {
                    System.out.println("User not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        if (!password.isEmpty()) {
            String query = "UPDATE user SET password = ? WHERE username = ?;";
            try (PreparedStatement stmt = connectDB.prepareStatement(query)) {
                stmt.setString(1, passwordField.getText());
                stmt.setString(2, MainApp.getCurrentUser());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Update successful!");
                } else {
                    System.out.println("User not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        if (!email.isEmpty()) {
            String query = "UPDATE user SET email = ? WHERE username = ?;";
            try (PreparedStatement stmt = connectDB.prepareStatement(query)) {
                stmt.setString(1, emailField.getText());
                stmt.setString(2, MainApp.getCurrentUser());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Update successful!");
                } else {
                    System.out.println("User not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        username.getScene().getWindow().hide();
        switchScene();
    }

    public void signOutLabelOnMouseClicked(MouseEvent mouseEvent) {
        MainApp.setCurrentUser("");
        MainApp.setCurrentUserJoinedDate("");
        username.getScene().getWindow().hide();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eduvault_app/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void switchScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eduvault_app/testMergeUser.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User Details");
        stage.show();
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        switchScene();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText("@" + MainApp.getCurrentUser());
        joinedDate.setText(MainApp.getCurrentUserJoinedDate());
        Email2.setText(MainApp.getEmail());
        Fullname.setText(MainApp.getFullName());
    }

    public void ShowNotification(MouseEvent mouseEvent) {
    }

    public void AskForHelp(MouseEvent mouseEvent) {
    }

    public void ShowComment(MouseEvent mouseEvent) {
    }

    public void showDocumentForm(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eduvault_app/TienDash.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Document management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void showTrashForm(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eduvault_app/trash.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Junk document management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void showUserProfile(MouseEvent mouseEvent) {
        try {
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.close();

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
    }
}