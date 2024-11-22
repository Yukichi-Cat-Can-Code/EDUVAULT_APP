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


    public ComboBox type_filter;
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
    private FontAwesomeIconView refresh_icon;

    @FXML
    private FontAwesomeIconView refreshFolder_icon;

    @FXML
    private FontAwesomeIconView downloadBTN;

    @FXML
    private FontAwesomeIconView deleteBTN;

    //Pane
    @FXML
    private AnchorPane DashBoardForm;

    @FXML
    private AnchorPane dashBoardTemp;

    @FXML
    private AnchorPane DocumentPane;

    @FXML
    private AnchorPane FolderPane;

    @FXML
    private ImageView UserAvatar_Icon;

    //BUTTON
    @FXML
    private Button FilterBTN;

    @FXML
    private Button clearBTN;

    @FXML
    private Button addBTN;

    @FXML
    private Button updateBTN;

    @FXML
    private Button addFolderBTN;

    @FXML
    private Button clearFolderBTN;

    @FXML
    private Button deleteFolderBTN;

    @FXML
    private Button updateFolderBTN;


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

    @FXML
    private TableColumn<DetailDocInfo, String> DocStoreInFolder_Col;

    @FXML
    private TableView<DetailFolderInfo> folderList_TableView;

    @FXML
    private TableColumn<DetailFolderInfo, String> authorFolder_Col;

    @FXML
    private TableColumn<DetailFolderInfo, LocalDateTime> dateFolderCreate_Col;

    @FXML
    private TableColumn<DetailFolderInfo, String> folderName_Col;

    @FXML
    private TableColumn<DetailFolderInfo, Integer> folderNo_Col;

    @FXML
    private TableColumn<DetailFolderInfo, String> folderPath_Col;

    //LABEL
    @FXML
    private Label FolderForm;

    @FXML
    private Label TrashForm;

    //TEXTFIELD
//    @FXML
//    private TextField author_TXT;

    @FXML
    private TextField docName_TXT;

    @FXML
    private TextField FolderName_TXT;

    @FXML
    private TextField docPath_TXT;

    @FXML
    private ComboBox<String> type_TXT;

    @FXML
    private TextField nameFolder_TXT;

    @FXML
    private TextField authorFolder_TXT;

    @FXML
    private TextField folderPath_TXT;

    @FXML
    private TextField parentFolder_TXT;

    @FXML
    private TextField Summary_TXT;

    private String username;

    @FXML
    private Label Fullname;
    @FXML
    private Label Email;

    private MouseEvent mouseEvent;

    //RUN TO SHOW DATA
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Word", "Excel", "PDF");
        type_TXT.setItems(list);
        type_TXT.setValue("Word");

        // Thiết lập giá trị cho ComboBox
        type_filter.setItems(FXCollections.observableArrayList("ALL", "WORD", "PDF", "EXCEL"));
        type_filter.setValue("ALL"); // Mặc định là ALL (hiển thị tất cả)


        // Lắng nghe sự kiện thay đổi của ComboBox
        type_filter.setOnAction(event -> {
            String selectedType = (String) type_filter.getValue();
            refreshDocList(selectedType); // Tải lại danh sách với filter
        });

        Fullname.setText(MainApp.getFullName());
        Email.setText(MainApp.getEmail());
        showDocList();
        showFolderList();


    }


    //sign out
    public void signOutLabelOnMouseClicked(MouseEvent mouseEvent) {
        MainApp.setCurrentUser("");
        MainApp.setCurrentUserJoinedDate("");
//        username.getScene().getWindow().hide();
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


//CUA DOCUMENT

//ADD NEW DOC
    @FXML
    void HandleAddNewItem(MouseEvent event) {
        try {

            String docName = docName_TXT.getText().trim();
            String type = type_TXT.getValue();  //Combobox
//            String author = author_TXT.getText().trim();
            String folder = FolderName_TXT.getText().trim();
            String Summary = Summary_TXT.getText().trim();

            String fileExtension;

            switch (type) {
                case "Word":
                    fileExtension = ".docx";
                    break;
                case "Excel":
                    fileExtension = ".xlsx";
                    break;
                case "PDF":
                    fileExtension = ".pdf";
                    break;
                default:
                    fileExtension = ".allFiles";
                    break;
            }
            String Formatfolder = "";
            if(!folder.isEmpty()){
               Formatfolder = folder + "/" ;
            }

            String docPath = new String("System/" + Formatfolder + docName + fileExtension);

            //Current Time
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.withNano(0);


            if (docName.isEmpty() || type.isEmpty()) {
                showErrorAlert("ADD ERROR", "Please fill all the fields before adding!");
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
                    Summary, // Tóm tắt
                    dateTime,
                    docPath, // Đường dẫn tài liệu
                    (short) 0 // isDeleted = 0
            );

            DocumentDAO documentDAO = new DocumentDAO();
            int result = documentDAO.add(newDocument);

            // Kiểm tra kết quả
            if (result > 0) {
                showSuccessAlert("Success", "Document added successfully!");
                clearDoc();
                refreshDocList("ALL");
            } else {
                showErrorAlert("Failed", "Can not add the document. Please try again!");
            }
        } catch (Exception e) {
            showErrorAlert("System error", "ERROR OCCUR: " + e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    // CLEAR TEXTFIELD
    @FXML
    void HandleClearCreateItemInfo(MouseEvent event) {
        clearDoc();
    }
    //CLEAR DATA FUNCTION
    public void clearDoc(){
        docName_TXT.clear();
        docPath_TXT.clear();
        FolderName_TXT.clear();
        type_TXT.setValue(type_TXT.getItems().get(0));
        type_TXT.setDisable(false);
        docPath_TXT.setDisable(false);
        Summary_TXT.clear();
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
        String docName = selectedDoc.getDOC_NAME();
        String folderName = selectedDoc.getFOLDER_NAME();

        FolderDAO folderDAO = new FolderDAO();
        int folderId = folderDAO.getFolderId(folderName);

        String docPath = selectedDoc.getDOC_PATH();
        String summary = selectedDoc.getSUMMARY();
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.withNano(0);

        int updateResult = 0;


        try {


            refreshDocList("ALL");

            showSuccessAlert("Update Document","Update document success!");
        if(docName.isEmpty())
        {
            showErrorAlert("Failed", "Can not update the document. Please fill all the fields before updating!");
            return ;
        }
        try {
            DocumentDAO documentDAO = new DocumentDAO();
            updateResult = documentDAO.updateDoc(folderId,docName,summary,dateTime,docPath,docId);

            // Kiểm tra kết quả
            if (updateResult > 0) {
                showSuccessAlert("Update Document", "Document updated successfully!");
                clearDoc();
                refreshDocList();
            } else {
                showErrorAlert("Failed", "Can not update the document. Please try again!");
            }
        } catch (Exception e) {
            showErrorAlert("System error", "ERROR OCCUR: " + e.getMessage());
            System.out.println(e.getMessage());
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
        refreshDocList("ALL");
    }

    //DISPLAY TABLE DATA

    public ObservableList<DetailDocInfo> docList(String typeFilter) {
        ObservableList<DetailDocInfo> listData = FXCollections.observableArrayList();

        // SQL cơ bản
        String sql = """
        SELECT
            D.DOC_ID,
            D.DOC_NAME,
            D.TYPEDOC_ID,
            D.CREATEDATE,
            D.USER_ID,
            D.DOC_PATH,
            D.FOLDER_ID,
            F.FOLDER_NAME,
            U.FULLNAME,
            T.TYPEDOC_NAME
        FROM DOCUMENT D
        LEFT JOIN USER U ON D.USER_ID = U.USER_ID
        LEFT JOIN TYPEOFDOCUMENT T ON D.TYPEDOC_ID = T.TYPEDOC_ID
        LEFT JOIN FOLDER F ON D.FOLDER_ID = F.FOLDER_ID
        WHERE D.isDeleted = 0
    """;

        // Thêm điều kiện filter (nếu có)
        if (typeFilter != null && !typeFilter.equalsIgnoreCase("ALL")) {
            sql += " AND T.TYPEDOC_NAME = ?";
        }

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql)) {

            // Set tham số filter nếu có
            if (typeFilter != null && !typeFilter.equalsIgnoreCase("ALL")) {
                prepare.setString(1, typeFilter);
            }

            try (ResultSet resultSet = prepare.executeQuery()) {
                while (resultSet.next()) {
                    DetailDocInfo detailDocInfo = new DetailDocInfo(
                            resultSet.getInt("DOC_ID"),
                            resultSet.getString("DOC_NAME"),
                            resultSet.getInt("TYPEDOC_ID"),
                            resultSet.getTimestamp("CREATEDATE"),
                            resultSet.getInt("USER_ID"),
                            resultSet.getString("FULLNAME"),
                            resultSet.getString("TYPEDOC_NAME"),
                            resultSet.getString("DOC_PATH"),
                            resultSet.getString("FOLDER_NAME")
                    );
                    listData.add(detailDocInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }



    private ObservableList<DetailDocInfo> listDoc;
    public void showDocList() {

//        docNo_Col.setCellValueFactory(new PropertyValueFactory<>("DOC_ID"));
//        docName_Col.setCellValueFactory(new PropertyValueFactory<>("DOC_NAME"));
//        type_Col.setCellValueFactory(new PropertyValueFactory<>("TYPEDOC_NAME"));
//        dateCreate_Col.setCellValueFactory(new PropertyValueFactory<>("CREATEDATE"));
//        author_Col.setCellValueFactory(new PropertyValueFactory<>("FULLNAME"));
//        docPath_Col.setCellValueFactory(new PropertyValueFactory<>("DOC_PATH"));
//        DocStoreInFolder_Col.setCellValueFactory(new PropertyValueFactory<>("FOLDER_NAME"));
//
//        docList_TableView.setItems(listDoc);
        refreshDocList("ALL"); // Hiển thị tất cả tài liệu
    }

    public void selectDocList() {
        DetailDocInfo detailDocInfo = docList_TableView.getSelectionModel().getSelectedItem();
        int num = docList_TableView.getSelectionModel().getSelectedIndex();

        DocumentDAO docDao = new DocumentDAO();
        Document doc = docDao.get(detailDocInfo.getDOC_ID());


        if((num -1) < -1) {
            clearDoc();
            return;
        }

        // Hiển thị ITEM_NAME
        docName_TXT.setText(detailDocInfo.getDOC_NAME());
        type_TXT.setValue(detailDocInfo.getTYPEDOC_NAME());
        docPath_TXT.setText(detailDocInfo.getDOC_PATH());
        Summary_TXT.setText(doc.getSUMMARY());
        FolderName_TXT.setText(detailDocInfo.getFOLDER_NAME());
        type_TXT.setDisable(true);
        docPath_TXT.setDisable(true);

    }

    private void refreshDocList(String typeFilter) {
        listDoc = docList(typeFilter); // Gọi hàm docList() với filter
        docList_TableView.setItems(listDoc); // Cập nhật TableView
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
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.close();

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

    //Download Document
    @FXML
    void HandleDownloadItem(MouseEvent event) {

    }


    //CUA FOLDER
    //TAO FOLDER MOI
    @FXML
    void HandleAddNewFolder(MouseEvent event) {
        try {
            String folderName = nameFolder_TXT.getText().trim();
            String author = authorFolder_TXT.getText().trim();
            String parentFolder = parentFolder_TXT.getText().trim();

            String FolderInherit = "";
            if (!folderName.isEmpty()) {
                FolderInherit = parentFolder + "/" + folderName;
            }

            String folderPath = new String("System/" + FolderInherit);

            //Current Time
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.withNano(0);

            if (folderName.isEmpty() || author.isEmpty()) {
                showErrorAlert("Lỗi nhập liệu", "Vui lòng điền đầy đủ thông tin trước khi thêm.");
                return;
            }

            int userId = new UserDAO().getUserIdForFolder(author);
            int parentId = new UserDAO().getUserIdForFolder(parentFolder);


            Folder folder = new Folder();
            folder.setUSER_ID(userId);
            folder.setFOLDER_NAME(folderName);
            folder.setPARENT_ID(parentId);
            folder.setFOLDER_CREATEAT(dateTime);
            folder.setIsDeleted((short) 0);

            FolderDAO folderDAO = new FolderDAO();
            int result = folderDAO.add(folder);

            if (result > 0) {
                showSuccessAlert("Success", "Folder added successfully!");
                clearDoc();
                refreshDocList("ALL");
            } else {
                showErrorAlert("Failed", "Can not create the folder. Please try again!");
            }
        }
        catch (Exception e) {
            showErrorAlert("System error", "ERROR OCCUR: " + e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //XOA THONG TIN FOLDER
    @FXML
    void HandleClearCreateFolderInfo(MouseEvent event) {
       clearFolder();
    }

    public void clearFolder (){
        folderPath_TXT.clear();
        nameFolder_TXT.clear();
        authorFolder_TXT.clear();
        parentFolder_TXT.clear();
    }

    //LOC FOLDER
    @FXML
    void HandleFilteringFolder(MouseEvent event) {

    }

    //DUA FOLDER VAO THUNG RAC
    @FXML
    void HandleMoveFolderToTrash(MouseEvent event) {

    }

    //CAP NHAT FOLDER
    @FXML
    void HandleUpdateFolder(MouseEvent event) {

    }

    //DISPLAY FOLDER DATA

    public ObservableList<DetailFolderInfo> folderList() {
        ObservableList<DetailFolderInfo> listData = FXCollections.observableArrayList();
        String sql = """
         
                SELECT
             F.FOLDER_ID,
             F.FOLDER_NAME,
             F.PARENT_ID,
             F.USER_ID,
             U.FULLNAME AS USER_FULLNAME,
             F.FOLDER_CREATEAT,
             F2.FOLDER_NAME AS PARENT_FOLDER_NAME,
             CASE
                 WHEN F.USER_ID = U.USER_ID THEN U.FULLNAME
                 WHEN F.PARENT_ID = F2.FOLDER_ID THEN F2.FOLDER_NAME
                 ELSE NULL
             END AS FOLDER_USER_NAME
         FROM FOLDER F
         LEFT JOIN USER U ON F.USER_ID = U.USER_ID
         LEFT JOIN FOLDER F2 ON F.PARENT_ID = F2.FOLDER_ID
         WHERE F.isDeleted = 0;
         """;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement prepare = conn.prepareStatement(sql);
             ResultSet resultSet = prepare.executeQuery()) {

            while (resultSet.next()) {
                DetailFolderInfo detailFolderInfo = new DetailFolderInfo(
                                resultSet.getInt("FOLDER_ID"),
                                resultSet.getString("FOLDER_NAME"),
                                resultSet.getInt("PARENT_ID"),
                                resultSet.getInt("USER_ID"),
                                resultSet.getString("USER_FULLNAME"),
                                resultSet.getTimestamp("FOLDER_CREATEAT").toLocalDateTime()
                );
                listData.add(detailFolderInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<DetailFolderInfo> listFolder;
    public void showFolderList() {
        listFolder = folderList();

        folderNo_Col.setCellValueFactory(new PropertyValueFactory<>("FOLDER_ID"));
        folderName_Col.setCellValueFactory(new PropertyValueFactory<>("FOLDER_NAME"));
        authorFolder_Col.setCellValueFactory(new PropertyValueFactory<>("FULLNAME"));
        dateFolderCreate_Col.setCellValueFactory(new PropertyValueFactory<>("FOLDER_CREATEAT"));

        folderList_TableView.setItems(listFolder);
    }

    public void selectFolderList() {
        DetailFolderInfo detailFolderInfo = folderList_TableView.getSelectionModel().getSelectedItem();
        int num = folderList_TableView.getSelectionModel().getSelectedIndex();

        if((num -1) < -1) {
            return;
        }

        // Hiển thị ITEM_NAME
        folderName_Col.setText(detailFolderInfo.getFOLDER_NAME());

        authorFolder_Col.setText(detailFolderInfo.getFULL_NAME());

        String getDate = String.valueOf(detailFolderInfo.getFOLDER_CREATEAT());
        dateFolderCreate_Col.setText(getDate);
    }

    //CAP NHAT BANG DU LIEU FOLDER
    @FXML
    void RefreshFolderTable(MouseEvent event) {
        refreshFolderList();
    }

    void refreshFolderList() {
        listFolder = folderList();
        folderList_TableView.setItems(listFolder);
    }

    //MOVE DOC TO TRASH
    @FXML
    private void handleDeleteItem() {
        // Lấy item được chọn từ bảng
        DetailDocInfo selectedDocument = docList_TableView.getSelectionModel().getSelectedItem();

        if (selectedDocument == null) {
            // Hiển thị thông báo nếu không có dòng nào được chọn
            showNotification("No document selected!");
            return;
        }

        // Lấy thông tin từ document được chọn
        int documentId = selectedDocument.getDOC_ID();
        int folderId = selectedDocument.getFOLDER_ID(); // Nếu cần xóa theo thư mục
        String itemType = (folderId == 0) ? "DOCUMENT" : "FOLDER";

        // Hiển thị hộp thoại xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to move this " + itemType.toLowerCase() + " to trash?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete Confirmation");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    // Gọi phương thức moveToTrash
                    if ("DOCUMENT".equalsIgnoreCase(itemType)) {
                        DocumentDAO.moveToTrash(documentId, itemType);
                    } else if ("FOLDER".equalsIgnoreCase(itemType)) {
                        DocumentDAO.moveToTrash(folderId, itemType);
                    }

                    // Làm mới danh sách tài liệu
                    refreshDocList("ALL");
                    clearDoc();
                    // Hiển thị thông báo thành công
                    showNotification(itemType + " moved to trash successfully.");
                } catch (SQLException e) {
                    // Hiển thị thông báo lỗi
                    showNotification("Error while moving to trash: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    // Phương thức hiển thị thông báo
    private void showNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

