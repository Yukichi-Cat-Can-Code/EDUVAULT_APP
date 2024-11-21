package com.example.eduvault_app.controller;

import com.example.eduvault_app.DAO.*;
import com.example.eduvault_app.MainApp;
import com.example.eduvault_app.model.*;
import com.example.eduvault_app.util.JDBCUtil;
import database_conn.DatabaseConnection;
import javafx.animation.PauseTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;


import javafx.util.Duration;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.itextpdf.text.xml.xmp.XmpBasicProperties.CREATEDATE;

public class DashBoardController implements Initializable {


    //ICON
    @FXML
    private FontAwesomeIconView AddDoc_Icon;

    @FXML
    private FontAwesomeIconView AddNewDoc_Icon;

    @FXML
    private FontAwesomeIconView AnalystForm;

    @FXML
    private FontAwesomeIconView CalendarForm;

    @FXML
    private FontAwesomeIconView FeedBack_Icon;

    @FXML
    private FontAwesomeIconView GetHelp_Icon;

    @FXML
    private FontAwesomeIconView Notification_Icon;

    @FXML
    private FontAwesomeIconView SignOut_Icon;

    @FXML
    private FontAwesomeIconView UserForm;

    @FXML
    private FontAwesomeIconView UploadDownload_Icon;

    @FXML
    private FontAwesomeIconView refresh_icon;

    //Pane
    @FXML
    private AnchorPane DashBoardForm;

    @FXML
    private AnchorPane dashBoardTemp;

    @FXML
    private ImageView UserAvatar_Icon;

    //BUTTON
    @FXML
    private Button FilterBTN;

    @FXML
    private Button clearBTN;

    @FXML
    private Button deleteBTN;

    @FXML
    private Button addBTN;

    @FXML
    private Button updateBTN;

    //TABLE
    @FXML
    private TableColumn<DetailDocInfo, LocalDateTime> dateCreate_Col;

    @FXML
    private TableView<DetailDocInfo> docList_TableView;

    @FXML
    private TableColumn<DetailDocInfo, String> docName_Col;

    @FXML
    private TableColumn<DetailDocInfo, String> type_Col;

    @FXML
    private TableColumn<DetailDocInfo, String> author_Col;

    @FXML
    private TableColumn<DetailDocInfo, String> docPath_Col;

    @FXML
    private TableColumn<DetailDocInfo, Integer> docNo_Col;

    //LABEL
    @FXML
    private Label FolderForm;

    @FXML
    private Label TrashForm;

    //TEXTFIELD
    @FXML
    private TextField author_TXT;

    @FXML
    private TextField docName_TXT;

    @FXML
    private TextField FolderName_TXT;

    @FXML
    private TextField docPath_TXT;

    @FXML
    private ComboBox<String> type_TXT;

    @FXML
    private Label Fullname;

    @FXML
    private Label Email;

    @FXML
    private Label username;

    private MouseEvent mouseEvent;

    //RUN TO SHOW DATA
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Word", "Excel", "PDF");
        type_TXT.setItems(list);
        type_TXT.setValue("Word");
        showDocList();

//        username.setText(MainApp.getCurrentUser());
//        System.out.println("current" + MainApp.getCurrentUser());
//        JDBCUtil jdbcUtil = new JDBCUtil();
//        Connection connectDB = jdbcUtil.getConnection();
//
//        String query = "SELECT * FROM user WHERE username = ?";
//        try (PreparedStatement stmt = connectDB.prepareStatement(query)) {
//            stmt.setString(1, username.getText());
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                String name = rs.getString("FULLNAME");
//                Fullname.setText(name);
//                Email.setText(rs.getString("EMAIL"));
//            } else {
//                Fullname.setText("User not found.");
//            }
//        }  catch (SQLException e) {
//        e.printStackTrace();
//        e.getCause();
//        }
    }

    //sign out
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


//ADD NEW DOC
    @FXML
    void HandleAddNewItem(MouseEvent event) {
        try {

            String docName = docName_TXT.getText().trim();
            String type = type_TXT.getValue();  //Combobox
            String author = author_TXT.getText().trim();
            String folder = FolderName_TXT.getText().trim();

            String fileExtension;

            switch (type) {
                case "WORD":
                    fileExtension = ".docx";
                    break;
                case "EXCEL":
                    fileExtension = ".xlsx";
                    break;
                case "PDF":
                    fileExtension = ".pdf";
                    break;
                default:
                    fileExtension = ".allFiles";
                    break;
            }

            String docPath = new String("System/" + folder + "/" + docName + fileExtension);

            //Current Time
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.withNano(0);


            if (docName.isEmpty() || type.isEmpty() || author.isEmpty() || folder.isEmpty()) {
                showErrorAlert("Lỗi nhập liệu", "Vui lòng điền đầy đủ thông tin trước khi thêm.");
                return;
            }

            // Lấy ID của Type và User dựa trên tên hiển thị
            int typeId = new TypeOfDocumentDAO().findByName(type).getTYPEDOC_ID();
//            int userId = new UserDAO().findByName(author).getUSER_ID();

            // Tạo đối tượng Document mới
            Document newDocument = new Document(
                    1, // FOLDER_ID (nếu không sử dụng thư mục)
                    1, //UserId
                    typeId, //typeId
                    docName,
                    null, // Tóm tắt
                    dateTime,
                    docPath, // Đường dẫn tài liệu
                    (short) 0 // isDeleted = 0
            );

            DocumentDAO documentDAO = new DocumentDAO();
            int result = documentDAO.add(newDocument);

            // Kiểm tra kết quả
            if (result > 0) {
                showSuccessAlert("Thành công", "Tài liệu đã được thêm thành công.");
                clearDoc();
                refreshDocList();
            } else {
                showErrorAlert("Thất bại", "Không thể thêm tài liệu. Vui lòng thử lại.");
            }
        } catch (Exception e) {
            showErrorAlert("Lỗi hệ thống", "Đã xảy ra lỗi: " + e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    // CLEAR TEXTFIELD
    @FXML
    void HandleClearCreateItemInfo(MouseEvent event) {
        author_TXT.clear();
        docName_TXT.clear();
        docPath_TXT.clear();
        FolderName_TXT.clear();
        type_TXT.setValue(type_TXT.getItems().get(0));
    }
    //CLEAR DATA FUNCTION
    public void clearDoc(){
        author_TXT.clear();
        docName_TXT.clear();
        docPath_TXT.clear();
        FolderName_TXT.clear();
        type_TXT.setValue(type_TXT.getItems().get(0));
    }

    //FILTERING
    @FXML
    void HandleFilteringItem(MouseEvent event) {

    }

    //LOGOUT
    @FXML
    void HandleLogOut(MouseEvent event) {
        try {
            // Tải giao diện login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eduvault_app/login.fxml"));
            Parent root = loader.load();

            // Lấy controller và khởi tạo dữ liệu hoặc thiết lập giao diện
//            PrimaryController pmrC = loader.getController();

            // Lấy Stage hiện tại và chuyển đổi scene
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            currentStage.setScene(loginScene);

            // Tùy chọn: Thiết lập lại tiêu đề hoặc các thuộc tính khác cho stage
            currentStage.setTitle("Login and Sign Up");
            currentStage.show();

        } catch (IOException ex) {
//            Dong khung tranh xay ra loi
//            Logger.getLogger(PrimaryController.class
//                    .getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    //MOVE DOC TO TRASH
    @FXML
    void HandleMoveItemToTrash(MouseEvent event) {

    }

    //UPDATE DOC INFO
    @FXML
    void HandleUpdateItem(MouseEvent event) {
        DetailDocInfo selectedDoc = docList_TableView.getSelectionModel().getSelectedItem();

        if (selectedDoc == null) {
            // Hiển thị thông báo nếu không có dòng nào được chọn
            showErrorAlert("Error","No item selected!");
            return;
        }

        // Lấy thông tin từ item được chọn
        int docId = selectedDoc.getDOC_ID();

        try {


            refreshDocList();

            showSuccessAlert("Update Document","Update document success!");
        }
        catch (Exception e) {
            showErrorAlert("Error: ",e.getMessage());
            e.printStackTrace();
        }
    }


    //SHOW COMMENT NOT USE NOW
    @FXML
    void ShowComment(MouseEvent event) {

    }

    //SHOW NOTIFICATION ABOUT CRUD
    @FXML
    void ShowNotification(MouseEvent event) {
        this.mouseEvent = mouseEvent;
    }

    // CONTACT TO DEVELOPER
    @FXML
    void AskForHelp(MouseEvent event) {

    }

    //SWITCH FORM
    public void switchForm(ActionEvent event){
        if(event.getSource() == FolderForm || event.getSource() == AddDoc_Icon){
            DashBoardForm.setVisible(true);
        }
        if(event.getSource() == TrashForm){

        }
    }

    @FXML
    void RefreshTable(MouseEvent event) {
        refreshDocList();
    }

    //DISPLAY TABLE DATA

    public ObservableList<DetailDocInfo> docList() {
        ObservableList<DetailDocInfo> listData = FXCollections.observableArrayList();

        String sql = """
        SELECT
            D.DOC_ID,
            D.DOC_NAME,
            D.TYPEDOC_ID,
            D.CREATEDATE,
            D.USER_ID,
            U.FULLNAME,
            T.TYPEDOC_NAME,
            CASE\s
                WHEN D.TYPEDOC_ID = T.TYPEDOC_ID THEN T.TYPEDOC_NAME
                ELSE NULL
            END AS DOCUMENT_TYPE_NAME
        FROM DOCUMENT D
        LEFT JOIN USER U ON D.USER_ID = U.USER_ID
        LEFT JOIN TYPEOFDOCUMENT T ON D.TYPEDOC_ID = T.TYPEDOC_ID
        WHERE D.isDeleted = 0;
        
    """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql);
             ResultSet resultSet = prepare.executeQuery()) {

            while (resultSet.next()) {
                DetailDocInfo detailDocInfo = new DetailDocInfo(
                        resultSet.getInt("DOC_ID"),              // Lấy giá trị DOC_ID
                        resultSet.getString("DOC_NAME"),         // Lấy giá trị DOC_NAME
                        resultSet.getInt("TYPEDOC_ID"),          // Lấy giá trị TYPEDOC_ID
                        resultSet.getTimestamp("CREATEDATE"),    // Lấy giá trị CREATEDATE (nếu có cả ngày và giờ)
                        resultSet.getInt("USER_ID"),             // Lấy giá trị USER_ID
                        resultSet.getString("FULLNAME"),         // Lấy giá trị FULLNAME từ bảng User
                        resultSet.getString("TYPEDOC_NAME")      // Lấy giá trị TYPEDOC_NAME từ bảng TypeofDocument

                );

                listData.add(detailDocInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }


    private ObservableList<DetailDocInfo> listDoc;
    public void showDocList() {
        listDoc = docList();

        docNo_Col.setCellValueFactory(new PropertyValueFactory<>("DOC_ID"));
        docName_Col.setCellValueFactory(new PropertyValueFactory<>("DOC_NAME"));
        type_Col.setCellValueFactory(new PropertyValueFactory<>("TYPEDOC_NAME"));
        dateCreate_Col.setCellValueFactory(new PropertyValueFactory<>("CREATEDATE"));
        author_Col.setCellValueFactory(new PropertyValueFactory<>("FULLNAME"));

        docList_TableView.setItems(listDoc);
    }

    public void selectDocList() {
        DetailDocInfo detailDocInfo = docList_TableView.getSelectionModel().getSelectedItem();
        int num = docList_TableView.getSelectionModel().getSelectedIndex();

        if((num -1) < -1) {
            return;
        }

        // Hiển thị ITEM_NAME
        docName_Col.setText(detailDocInfo.getDOC_NAME());

        type_Col.setText(detailDocInfo.getTYPEDOC_NAME());

        String getDate = String.valueOf(detailDocInfo.getCREATEDATE());
        dateCreate_Col.setText(getDate);
    }

    private void refreshDocList() {
        listDoc = docList(); // Tải lại danh sách từ cơ sở dữ liệu
        docList_TableView.setItems(listDoc); // Gán lại danh sách cho TableView
    }


    private String getTypeName(int typeId, List<TypeOfDocument> types) {
        for (TypeOfDocument type : types) {
            if (type.getTYPEDOC_ID() == typeId) {
                return type.getTYPEDOC_NAME();
            }
        }
        return "Unknown Type";
    }

    private String getAuthorName(int userId, List<User> users) {
        for (User user : users) {
            if (user.getUSER_ID() == userId) {
                return user.getFULLNAME();
            }
        }
        return "Unknown Author";
    }

    // Phương thức phụ để tìm User tương ứng với Document
    private User getUserForDocument(Document document, List<User> users) {
        for (User user : users) {
            if (user.getUSER_ID() == document.getUSER_ID()) {
                return user;
            }
        }
        return null;
    }

    // Phương thức phụ để tìm TypeOfDocument tương ứng với Document
    private TypeOfDocument getTypeForDocument(Document document, List<TypeOfDocument> typeOfDocuments) {
        for (TypeOfDocument typeOfDoc : typeOfDocuments) {
            if (typeOfDoc.getTYPEDOC_ID() == document.getTYPEDOC_ID()) {
                return typeOfDoc;
            }
        }
        return null;
    }


    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //chuyen trang
    public void showDocumentForm(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eduvault_app/DashBoard.fxml"));
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

