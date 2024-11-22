package com.example.eduvault_app.controller;

import com.example.eduvault_app.MainApp;
import com.example.eduvault_app.model.Trash;
import com.example.eduvault_app.util.JDBCUtil;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.example.eduvault_app.DAO.TrashDAO;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DeleteController implements Initializable {
    public Text notiLabel;
    private TrashDAO trashDAO = new TrashDAO();
    public AnchorPane trash_Noti;
    public TextField trash_DocName;
    public TextField trash_Type;
    public TextField trash_DeleteAt;
    @FXML
    private ComboBox<String> itemTypeComboBox;


    @FXML
    private Text resultText;  // Reference to the result text area

    @FXML
    private TextField itemIdField;  // Reference to the TextField for item ID input

    @FXML
    private Button trash_Restore;

    @FXML
    private Button trash_Delete;

    @FXML
    private Button trash_Clear;

    @FXML
    private TextField trashSearch;

    @FXML
    private TableView<Trash> trashTableView;

    @FXML
    private TableColumn<Trash, Integer> trash_col_No;

    @FXML
    private TableColumn<Trash, String> trash_col_Name;

    @FXML
    private TableColumn<Trash, String> trash_col_Type;

    @FXML
    private TableColumn<Trash, LocalDateTime> trash_col_DeleteAt;

    @FXML
    private Text Fullname;

    @FXML
    private Text Email2;

    //khoi tao
    public void initialize(URL location, ResourceBundle resources) {
        Fullname.setText(MainApp.getFullName());
        Email2.setText(MainApp.getEmail());
        showTrashList();
    }


    // Method to handle the Delete Item button click
    @FXML
    private void handleDeleteItem() {
//        String itemIdText = itemIdField.getText();  // Get the text input for item ID
//        String itemType = itemTypeComboBox.getValue();
//
//        if (itemType == null || itemIdText.isEmpty()) {
//            resultText.setText("Please select an item type and enter an item ID.");
//            return;
//        }
//
//        int itemId;
//        try {
//            itemId = Integer.parseInt(itemIdText);  // Convert input to an integer
//        } catch (NumberFormatException e) {
//            resultText.setText("Invalid item ID. Please enter a valid number.");
//            return;
//        }
//
//        // Normalize item type
//        String normalizedItemType = itemType.trim().toUpperCase();
//
//        // Validate item type
//        if (!"DOCUMENT".equals(normalizedItemType) && !"FOLDER".equals(normalizedItemType)) {
//            resultText.setText("Invalid item type. Please select either 'Document' or 'Folder'.");
//            return;
//        }

        // Lấy item được chọn từ TableView
//        Trash selectedTrash = trashTableView.getSelectionModel().getSelectedItem();
//
//        if (selectedTrash == null) {
//            // Hiển thị thông báo nếu không có dòng nào được chọn
//            showNotification("No item selected!");
//            return;
//        }
//
//        // Lấy thông tin từ item được chọn
//        int trashId = selectedTrash.getTRASH_ID(); // ID của Trash
//        int itemId = selectedTrash.getITEM_ID(); // ID của Trash
//        String itemType = selectedTrash.getITEM_TYPE(); // Loại item
//
//        // Show confirmation dialog
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
//                "Are you sure you want to delete this " + itemType.toLowerCase() + " permanently?",
//                ButtonType.YES, ButtonType.NO);
//        alert.setTitle("Restore Confirmation");
//
//
//        alert.showAndWait().ifPresent(response -> {
//            if (response == ButtonType.YES) {
//                try {
//                    // Move item to trash
//                    trashDAO.moveToTrash(itemId, normalizedItemType);
//
//                    // Update result text on success
//                    resultText.setText(normalizedItemType + " with ID " + itemId + " moved to trash successfully.");
//
//                    // Optionally clear input fields
//                    itemIdField.clear();
//                    itemTypeComboBox.setValue(null);
//
//                } catch (SQLException e) {
//                    resultText.setText("Error while moving to trash: " + e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//        });
    }


    // Method to handle the Restore Item button click
    @FXML
    private void handleRestoreItem() {
//        String trashIdText = itemIdField.getText();  // Get the text input for trash ID
//        if (trashIdText.isEmpty()) {
//            showNotification("Please enter a valid trash ID.");
//            return;
//        }
//
//        int trashId;
//        try {
//            trashId = Integer.parseInt(trashIdText);  // Convert input to an integer
//        } catch (NumberFormatException e) {
//            showNotification("Invalid trash ID. Please enter a valid number.");
//            return;
//        }

        // Lấy item được chọn từ TableView
        Trash selectedTrash = trashTableView.getSelectionModel().getSelectedItem();

        if (selectedTrash == null) {
            // Hiển thị thông báo nếu không có dòng nào được chọn
            showNotification("No item selected!");
            return;
        }


        // Lấy thông tin từ item được chọn
        int trashId = selectedTrash.getTRASH_ID(); // ID của Trash
        String itemType = selectedTrash.getITEM_TYPE(); // Loại item


        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to restore this " + itemType.toLowerCase() + " ?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Restore Confirmation");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    // Call restoreFromTrash with just trashId (no itemType needed)
                    trashDAO.restoreFromTrash(trashId);

                    //refresh table view
                    refreshTrashList();

                    showNotification("Restored successfully.");
                } catch (SQLException e) {
                    showNotification("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }


    // Method to handle the Permanently Delete Item button click
    @FXML
    private void handleDeletePermanently() {
//        String itemIdText = itemIdField.getText();  // Get the text input for item ID
//        if (itemIdText.isEmpty()) {
//            resultText.setText("Please enter a valid item ID.");
//            return;
//        }
//
//        int itemId;
//        try {
//            itemId = Integer.parseInt(itemIdText);  // Convert input to an integer
//        } catch (NumberFormatException e) {
//            resultText.setText("Invalid item ID. Please enter a valid number.");
//            return;
//        }

//        int trashId = itemId;  // Assuming trashId is the same as itemId for simplicity
        // Lấy item được chọn từ TableView
        Trash selectedTrash = trashTableView.getSelectionModel().getSelectedItem();

        if (selectedTrash == null) {
            // Hiển thị thông báo nếu không có dòng nào được chọn
            showNotification("No item selected!");
            return;
        }

        // Lấy thông tin từ item được chọn
        int trashId = selectedTrash.getTRASH_ID(); // ID của Trash
        int itemId = selectedTrash.getITEM_ID(); // ID của Trash
        String itemType = selectedTrash.getITEM_TYPE(); // Loại item

        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this " + itemType.toLowerCase() + " permanently?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Restore Confirmation");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    trashDAO.deleteFromTrash(trashId);

                    //refresh table view
                    refreshTrashList();

                    showNotification("Permanently deleted.");
                } catch (SQLException e) {
                    showNotification("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    //show table

    public ObservableList<Trash> trashList() {
        ObservableList<Trash> listData = FXCollections.observableArrayList();

        String sql = """
        SELECT T.TRASH_ID, 
               T.ITEM_ID, 
               T.ITEM_TYPE, 
               T.TRASH_DELETEAT, 
               CASE 
                   WHEN T.ITEM_TYPE = 'DOCUMENT' THEN D.DOC_NAME 
                   WHEN T.ITEM_TYPE = 'FOLDER' THEN F.FOLDER_NAME 
                   ELSE NULL 
               END AS ITEM_NAME
        FROM Trash T
        LEFT JOIN DOCUMENT D ON T.ITEM_ID = D.DOC_ID
        LEFT JOIN FOLDER F ON T.ITEM_ID = F.FOLDER_ID;
    """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql);
             ResultSet resultSet = prepare.executeQuery()) {

            while (resultSet.next()) {
                Trash trash = new Trash(
                        resultSet.getInt("TRASH_ID"),
                        resultSet.getString("ITEM_NAME"), // Thêm ITEM_NAME
                        resultSet.getString("ITEM_TYPE"),
                        resultSet.getDate("TRASH_DELETEAT")

                );

                listData.add(trash);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }


    private ObservableList<Trash> listTrash;
    public void showTrashList() {
        listTrash = trashList();

        trash_col_No.setCellValueFactory(new PropertyValueFactory<>("TRASH_ID"));
        trash_col_Name.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        trash_col_Type.setCellValueFactory(new PropertyValueFactory<>("ITEM_TYPE"));
        trash_col_DeleteAt.setCellValueFactory(new PropertyValueFactory<>("TRASH_DELETEAT"));

        trashTableView.setItems(listTrash);
    }

    public void selectTrashList() {
        Trash trash = trashTableView.getSelectionModel().getSelectedItem();
        int num = trashTableView.getSelectionModel().getSelectedIndex();

        if((num -1) < -1) {
            return;
        }

        // Hiển thị ITEM_NAME
        trash_DocName.setText(trash.getItemName());

        trash_Type.setText(trash.getITEM_TYPE());

        String getDate = String.valueOf(trash.getTRASH_DELETEAT());
        trash_DeleteAt.setText(getDate);

    }

    private void showNotification(String message) {
        // Cập nhật nội dung thông báo
        notiLabel.setText(message);

        // Hiển thị thông báo
        notiLabel.setVisible(true);

        // Tự động ẩn thông báo sau 3 giây
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> notiLabel.setVisible(true));
        pause.play();
    }

    //refresh table view mỗi khi có thay đổi
    public void refreshTrashList() {
        listTrash = trashList(); // Tải lại danh sách từ cơ sở dữ liệu
        trashTableView.setItems(listTrash); // Gán lại danh sách cho TableView
    }


    public void ShowNotification(MouseEvent mouseEvent) {
    }

    public void AskForHelp(MouseEvent mouseEvent) {
    }

    public void ShowComment(MouseEvent mouseEvent) {
    }

    //chuyen trang
    public void showDocumentForm(MouseEvent mouseEvent) {
        try {
            // Lấy Stage hiện tại và đóng nó (nếu cần)
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.close();

            // Mở cửa sổ mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eduvault_app/DashBoard.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Document management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void showTrashForm(MouseEvent mouseEvent) {
        try {
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.close();

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
