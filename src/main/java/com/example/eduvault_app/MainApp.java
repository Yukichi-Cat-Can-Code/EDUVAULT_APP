package com.example.eduvault_app;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class MainApp extends Application {

    private static String currentUser;

    public static void setCurrentUser(String name) {
        currentUser = name;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    @Override
//    public void start(Stage primaryStage) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.project.view/main.fxml"));
//            Scene scene = new Scene(loader.load());
//            primaryStage.setScene(scene);
//            primaryStage.setTitle("Document Management Application");
//            primaryStage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Log in");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
