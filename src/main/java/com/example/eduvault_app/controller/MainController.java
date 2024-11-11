package com.example.eduvault_app.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane contentPane;

    public void showUsers() {
        loadUI("user");
    }

    public void showNotifications() {
        loadUI("notification");
    }

    private void loadUI(String ui) {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource("/com.example.project.view/" + ui + ".fxml"));
            contentPane.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleExit() {
        System.exit(0);
    }
}
