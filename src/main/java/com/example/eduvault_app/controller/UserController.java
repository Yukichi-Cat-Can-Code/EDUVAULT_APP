//package com.example.eduvault_app.controller;
//
//import com.example.eduvault_app.model.User;
//import com.example.eduvault_app.service.UserService;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TableColumn;
//
//public class UserController {
//    @FXML
//    private TableView<User> userTable;
//    @FXML
//    private TableColumn<User, Integer> userIdColumn;
//    @FXML
//    private TableColumn<User, String> usernameColumn;
//
//    private final UserService userService = new UserService();
//    private ObservableList<User> userData;
//
//    @FXML
//    public void initialize() {
//        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
//        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
//
//        userData = FXCollections.observableArrayList(userService.getAllUsers());
//        userTable.setItems(userData);
//    }
//}
//
