package com.example.eduvault_app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.example.eduvault_app.model.Document;
import com.example.eduvault_app.model.Folder;

public class UpdateDAO {

    private Connection connection;

    public UpdateDAO(Connection connection) {
        this.connection = connection;
    }

    // Phương thức cập nhật tài liệu
    public boolean updateDocument(Document document) {
        String sql = "UPDATE DOCUMENT SET DOC_NAME = ?, SUMMARY = ?, CREATEDATE = ?, DOC_PATH = ?, TYPEDOC_ID = ?, USER_ID = ?, isDeleted = ? WHERE DOC_ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, document.getDOC_NAME());
            stmt.setString(2, document.getSUMMARY());
            stmt.setObject(3, document.getCREATEDATE());
            stmt.setString(4, document.getDOC_PATH());
            stmt.setInt(5, document.getTYPEDOC_ID());
            stmt.setInt(6, document.getUSER_ID());
            stmt.setInt(7, document.getIsDeleted());
            stmt.setInt(8, document.getDOC_ID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức cập nhật thư mục
    public boolean updateFolder(Folder folder) {
        String sql = "UPDATE FOLDER SET FOLDER_NAME = ?, USER_ID = ?, PARENT_ID = ? WHERE FOLDER_ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, folder.getFOLDER_NAME());
            stmt.setInt(2, folder.getUSER_ID());
            stmt.setInt(3, folder.getPARENT_ID());
            stmt.setInt(4, folder.getFOLDER_ID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

