package com.example.eduvault_app.controller;

import com.example.eduvault_app.DAO.UpdateDAO;
import com.example.eduvault_app.model.Document;
import com.example.eduvault_app.model.Folder;
import com.example.eduvault_app.util.JDBCUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UpdateController {

    @FXML
    private TextField docNameField;
    @FXML
    private TextField summaryField;
    @FXML
    private TextField folderNameField;

    private UpdateDAO updateDAO;

    // Giả sử bạn đã có kết nối database
    public UpdateController() {
        this.updateDAO = new UpdateDAO(JDBCUtil.getConnection()); // Cần thiết lập kết nối cơ sở dữ liệu
    }

    @FXML
    private void updateDocument() {
        String docName = docNameField.getText();
        String summary = summaryField.getText();

        // Giả sử ID tài liệu cần cập nhật là 1 (cần thay đổi tùy vào yêu cầu)
        Document document = new Document(1, docName, summary, null, null, 1, 0);

        boolean isUpdated = updateDAO.updateDocument(document);
        if (isUpdated) {
            showAlert("Success", "Document updated successfully!", AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update document.", AlertType.ERROR);
        }
    }

    @FXML
    private void updateFolder() {
        String folderName = folderNameField.getText();

        // Giả sử ID thư mục cần cập nhật là 1
        Folder folder = new Folder(1, folderName, 1, 0);

        boolean isUpdated = updateDAO.updateFolder(folder);
        if (isUpdated) {
            showAlert("Success", "Folder updated successfully!", AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update folder.", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

