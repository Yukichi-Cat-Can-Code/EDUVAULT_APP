package com.example.eduvault_app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.sql.SQLException;
import com.example.eduvault_app.DAO.TrashDAO;

public class DeleteController {
    @FXML
    private ComboBox<String> itemTypeComboBox;


    @FXML
    private Text resultText;  // Reference to the result text area

    @FXML
    private TextField itemIdField;  // Reference to the TextField for item ID input

    private TrashDAO trashDAO = new TrashDAO();

    // Method to handle the Delete Item button click
    @FXML
    private void handleDeleteItem() {
        String itemIdText = itemIdField.getText();  // Get the text input for item ID
        String itemType = itemTypeComboBox.getValue();

        if (itemType == null || itemIdText.isEmpty()) {
            resultText.setText("Please select an item type and enter an item ID.");
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(itemIdText);  // Convert input to an integer
        } catch (NumberFormatException e) {
            resultText.setText("Invalid item ID. Please enter a valid number.");
            return;
        }

        // Normalize item type
        String normalizedItemType = itemType.trim().toUpperCase();

        // Validate item type
        if (!"DOCUMENT".equals(normalizedItemType) && !"FOLDER".equals(normalizedItemType)) {
            resultText.setText("Invalid item type. Please select either 'Document' or 'Folder'.");
            return;
        }

        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to move this " + normalizedItemType.toLowerCase() + " to the trash?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete Confirmation");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    // Move item to trash
                    trashDAO.moveToTrash(itemId, normalizedItemType);

                    // Update result text on success
                    resultText.setText(normalizedItemType + " with ID " + itemId + " moved to trash successfully.");

                    // Optionally clear input fields
                    itemIdField.clear();
                    itemTypeComboBox.setValue(null);

                } catch (SQLException e) {
                    resultText.setText("Error while moving to trash: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }


    // Method to handle the Restore Item button click
    @FXML
    private void handleRestoreItem() {
        String trashIdText = itemIdField.getText();  // Get the text input for trash ID
        if (trashIdText.isEmpty()) {
            resultText.setText("Please enter a valid trash ID.");
            return;
        }

        int trashId;
        try {
            trashId = Integer.parseInt(trashIdText);  // Convert input to an integer
        } catch (NumberFormatException e) {
            resultText.setText("Invalid trash ID. Please enter a valid number.");
            return;
        }

        try {
            // Call restoreFromTrash with just trashId (no itemType needed)
            trashDAO.restoreFromTrash(trashId);
            resultText.setText("Item with Trash ID " + trashId + " restored successfully.");
        } catch (SQLException e) {
            resultText.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Method to handle the Permanently Delete Item button click
    @FXML
    private void handleDeletePermanently() {
        String itemIdText = itemIdField.getText();  // Get the text input for item ID
        if (itemIdText.isEmpty()) {
            resultText.setText("Please enter a valid item ID.");
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(itemIdText);  // Convert input to an integer
        } catch (NumberFormatException e) {
            resultText.setText("Invalid item ID. Please enter a valid number.");
            return;
        }

        int trashId = itemId;  // Assuming trashId is the same as itemId for simplicity

        try {
            trashDAO.deleteFromTrash(trashId);
            resultText.setText("Item with ID " + itemId + " permanently deleted.");
        } catch (SQLException e) {
            resultText.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
