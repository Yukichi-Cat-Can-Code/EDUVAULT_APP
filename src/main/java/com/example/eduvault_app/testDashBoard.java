package com.example.eduvault_app;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class testDashBoard extends Application{
    private static String currentUser;

    public static void setCurrentUser(String name) {
        currentUser = name;
    }

    public static String getCurrentUser() {
        return currentUser;
    }


    public void start(Stage primaryStage) throws Exception {
        try {
            // Your code to initialize the application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard.fxml"));
            Parent root = loader.load(); // If loading an FXML file

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("DashBoard");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Log the exception
            System.out.println("Error loading FXML file.");
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
