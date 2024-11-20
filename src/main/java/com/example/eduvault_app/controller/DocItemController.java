package com.example.eduvault_app.controller;

import com.example.eduvault_app.model.Document;
import com.example.eduvault_app.model.TypeOfDocument;
import com.example.eduvault_app.model.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class DocItemController {

    @FXML
    private Text AuthorName_TXT;

    @FXML
    private Label TypeTag_icon;

    @FXML
    private FontAwesomeIconView arrange_icon;

    @FXML
    private CheckBox chooseDoc_checkbox;

    @FXML
    private Text createdDate_date;

    @FXML
    private Text docName_TXT;

    @FXML
    private FontAwesomeIconView download_icon;

    @FXML
    private FontAwesomeIconView edit_icon;

    @FXML
    private FontAwesomeIconView linkToDoc_icon;

    /**
     * Gán dữ liệu cho item giao diện tài liệu
     *
     * @param document        Tài liệu cần hiển thị
     * @param user            Người dùng liên quan tới tài liệu
     * @param typeOfDocument  Loại của tài liệu
     */
    public void setData(Document document, User user, TypeOfDocument typeOfDocument) {
        // Gán thông tin tác giả
        if (user != null && user.getFULLNAME() != null) {
            AuthorName_TXT.setText(user.getFULLNAME());
        } else {
            AuthorName_TXT.setText("Unknown Author");
        }

        // Gán thông tin loại tài liệu
        if (typeOfDocument != null && typeOfDocument.getTYPEDOC_NAME() != null) {
            TypeTag_icon.setText(typeOfDocument.getTYPEDOC_NAME());
        } else {
            TypeTag_icon.setText("Unknown Type");
        }

        // Gán thông tin tài liệu
        if (document != null) {
            // Gán tên tài liệu
            docName_TXT.setText(document.getDOC_NAME() != null ? document.getDOC_NAME() : "Untitled Document");

            // Gán ngày tạo (định dạng chuỗi)
            createdDate_date.setText(document.getCREATEDATE() != null
                    ? document.getCREATEDATE().toString()
                    : "No Date");
        }
    }
}
