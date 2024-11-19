package com.example.eduvault_app.DAO;

import com.example.eduvault_app.model.Trash;
import com.example.eduvault_app.controller.DeleteController;
import  com.example.eduvault_app.util.JDBCUtil;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrashDAO {
    private static final Logger LOGGER = Logger.getLogger(TrashDAO.class.getName());

    static {
        // Set default log level and handler
        LOGGER.setLevel(Level.ALL); // Log all levels
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }
    public static void moveToTrash(int itemId, String itemType) throws SQLException {
        String getMaxTrashIdSQL = "SELECT COALESCE(MAX(TRASH_ID), 0) + 1 AS NEXT_TRASH_ID FROM TRASH";
        String trashSQL = "INSERT INTO TRASH (TRASH_ID, ITEM_ID, ITEM_TYPE, TRASH_DELETEAT) VALUES (?, ?, ?, ?)";
        String updateDocumentSQL = "UPDATE DOCUMENT SET isDeleted = 1 WHERE DOC_ID = ?";
        String updateFolderSQL = "UPDATE FOLDER SET isDeleted = 1 WHERE FOLDER_ID = ?";
        String insertDocumentsInFolderToTrashSQL = """
        INSERT INTO TRASH (TRASH_ID, ITEM_ID, ITEM_TYPE, TRASH_DELETEAT)
        SELECT (ROW_NUMBER() OVER (ORDER BY DOC_ID) + (SELECT COALESCE(MAX(TRASH_ID), 0) FROM TRASH)) AS TRASH_ID,
               DOC_ID, 'DOCUMENT', ?
        FROM DOCUMENT WHERE FOLDER_ID = ?
    """;
        String updateDocumentsInFolderToTrashSQL = "UPDATE DOCUMENT SET isDeleted = 1 WHERE FOLDER_ID = ?";

        String normalizedItemType = itemType.toUpperCase();

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement psGetMaxId = conn.prepareStatement(getMaxTrashIdSQL);
             PreparedStatement psTrash = conn.prepareStatement(trashSQL);
             PreparedStatement psUpdateDocument = conn.prepareStatement(updateDocumentSQL);
             PreparedStatement psUpdateFolder = conn.prepareStatement(updateFolderSQL);
             PreparedStatement psInsertDocumentsInFolderToTrash = conn.prepareStatement(insertDocumentsInFolderToTrashSQL);
            PreparedStatement psUpdateDocumentsInFolder = conn.prepareStatement(updateDocumentsInFolderToTrashSQL))
        {

            // Start transaction
            conn.setAutoCommit(false);

            // Get the base TRASH_ID
            int baseTrashId;
            try (ResultSet rs = psGetMaxId.executeQuery()) {
                rs.next();
                baseTrashId = rs.getInt("NEXT_TRASH_ID");
            }

            // Insert main item into TRASH
            psTrash.setInt(1, baseTrashId);
            psTrash.setInt(2, itemId);
            psTrash.setString(3, normalizedItemType);
            psTrash.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            psTrash.executeUpdate();

            if ("DOCUMENT".equals(normalizedItemType)) {
                psUpdateDocument.setInt(1, itemId);
                psUpdateDocument.executeUpdate();
            } else if ("FOLDER".equals(normalizedItemType)) {
                psUpdateFolder.setInt(1, itemId);
                psUpdateFolder.executeUpdate();

                psUpdateDocumentsInFolder.setInt(1, itemId);
                psUpdateDocumentsInFolder.executeUpdate();

                // Move documents in the folder to trash
                psInsertDocumentsInFolderToTrash.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now())); // TRASH_DELETEAT
                psInsertDocumentsInFolderToTrash.setInt(2, itemId); // FOLDER_ID
                psInsertDocumentsInFolderToTrash.executeUpdate();
            } else {
                conn.rollback();
                LOGGER.severe("Invalid item type encountered: " + itemType);
                throw new IllegalArgumentException("Invalid item type: " + itemType);
            }

            conn.commit();

        } catch (SQLException e) {
            LOGGER.severe("SQL error in moveToTrash for itemType: " + itemType + ", itemId: " + itemId + ". Error: " + e.getMessage());
            throw e;
        }
    }



    public void restoreFromTrash(int trashId) throws SQLException {
        String deleteTrashSQL = "DELETE FROM TRASH WHERE TRASH_ID = ?";
        String getItemDetailsSQL = "SELECT ITEM_ID, ITEM_TYPE FROM TRASH WHERE TRASH_ID = ?";
        String restoreDocumentSQL = "UPDATE DOCUMENT SET isDeleted = 0 WHERE DOC_ID = ?";
        String restoreFolderSQL = "UPDATE FOLDER SET isDeleted = 0 WHERE FOLDER_ID = ?";
        String restoreDocumentsInFolderSQL = "UPDATE DOCUMENT SET isDeleted = 0 WHERE FOLDER_ID = ?";
        String getDocumentsInFolderSQL = "SELECT DOC_ID FROM DOCUMENT WHERE FOLDER_ID = ?";
        String deleteDocumentsFromTrashSQL = "DELETE FROM TRASH WHERE ITEM_ID = ? AND ITEM_TYPE = 'DOCUMENT'";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement psGetItemDetails = conn.prepareStatement(getItemDetailsSQL);
             PreparedStatement psDeleteTrash = conn.prepareStatement(deleteTrashSQL);
             PreparedStatement psRestoreDocument = conn.prepareStatement(restoreDocumentSQL);
             PreparedStatement psRestoreFolder = conn.prepareStatement(restoreFolderSQL);
             PreparedStatement psRestoreDocumentsInFolder = conn.prepareStatement(restoreDocumentsInFolderSQL);
             PreparedStatement psGetDocumentsInFolder = conn.prepareStatement(getDocumentsInFolderSQL);
             PreparedStatement psDeleteDocumentsFromTrash = conn.prepareStatement(deleteDocumentsFromTrashSQL)) {

            // Start transaction
            conn.setAutoCommit(false);

            // Get ITEM_ID and ITEM_TYPE from TRASH
            psGetItemDetails.setInt(1, trashId);
            ResultSet rs = psGetItemDetails.executeQuery();

            if (rs.next()) {
                int itemId = rs.getInt("ITEM_ID");
                String itemType = rs.getString("ITEM_TYPE");

                // Restore the item in the corresponding table (DOCUMENT or FOLDER)
                if ("DOCUMENT".equalsIgnoreCase(itemType)) {
                    psRestoreDocument.setInt(1, itemId);
                    psRestoreDocument.executeUpdate();
                } else if ("FOLDER".equalsIgnoreCase(itemType)) {
                    // Restore the folder and all documents inside the folder
                    psRestoreFolder.setInt(1, itemId);
                    psRestoreFolder.executeUpdate();

                    // Get all documents in the folder and delete them from TRASH
                    psGetDocumentsInFolder.setInt(1, itemId);
                    ResultSet docRs = psGetDocumentsInFolder.executeQuery();

                    // For each document in the folder, delete the corresponding entry in TRASH
                    while (docRs.next()) {
                        int docId = docRs.getInt("DOC_ID");
                        psDeleteDocumentsFromTrash.setInt(1, docId);
                        psDeleteDocumentsFromTrash.executeUpdate();
                    }

                    // Restore all documents in this folder
                    psRestoreDocumentsInFolder.setInt(1, itemId);
                    psRestoreDocumentsInFolder.executeUpdate();
                } else {
                    LOGGER.severe("Invalid item type encountered in TRASH: " + itemType);
                    throw new IllegalArgumentException("Invalid item type in TRASH: " + itemType);
                }

                // Remove the entry from TRASH
                psDeleteTrash.setInt(1, trashId);
                psDeleteTrash.executeUpdate();

                // Commit transaction
                conn.commit();
            } else {
                LOGGER.warning("No item found in TRASH with TRASH_ID: " + trashId);
            }
        } catch (SQLException e) {
            LOGGER.severe("SQL error in restoreFromTrash: " + e.getMessage());
            throw e;
        }
    }



    //Develop performance for delete
    public void deleteFromTrashUsingProcedure(int trashId) throws SQLException {
        String callProcedureSQL = "{CALL DeleteFromTrashProcedure(?)}";

        try (Connection conn = JDBCUtil.getConnection();
             CallableStatement cs = conn.prepareCall(callProcedureSQL)) {

            cs.setInt(1, trashId);
            cs.execute();

        } catch (SQLException e) {
            LOGGER.severe("SQL error in deleteFromTrashUsingProcedure: " + e.getMessage());
            throw e;
        }
    }



    // Permanently delete an item from the trash
    public void deleteFromTrash(int trashId) throws SQLException {
        String selectTrashSQL = "SELECT ITEM_ID, ITEM_TYPE FROM TRASH WHERE TRASH_ID = ?";
        String deleteDocumentSQL = "DELETE FROM DOCUMENT WHERE DOC_ID = ?";
        String deleteFolderSQL = "DELETE FROM FOLDER WHERE FOLDER_ID = ?";
        String deleteFilesInFolderSQL = "DELETE FROM DOCUMENT WHERE FOLDER_ID = ?";
        String deleteTrashSQL = "DELETE FROM TRASH WHERE TRASH_ID = ?";
        String getDocumentsInFolderSQL = "SELECT DOC_ID FROM DOCUMENT WHERE FOLDER_ID = ?";
        String deleteDocumentsFromTrashSQL = "DELETE FROM TRASH WHERE ITEM_ID = ? AND ITEM_TYPE = 'DOCUMENT'";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement psSelectTrash = conn.prepareStatement(selectTrashSQL);
             PreparedStatement psDeleteDocument = conn.prepareStatement(deleteDocumentSQL);
             PreparedStatement psDeleteFilesInFolder = conn.prepareStatement(deleteFilesInFolderSQL);
             PreparedStatement psDeleteFolder = conn.prepareStatement(deleteFolderSQL);
             PreparedStatement psDeleteTrash = conn.prepareStatement(deleteTrashSQL);
             PreparedStatement psGetDocumentsInFolder = conn.prepareStatement(getDocumentsInFolderSQL);
            PreparedStatement psDeleteDocumentsFromTrash = conn.prepareStatement(deleteDocumentsFromTrashSQL)
        ) {

            // Bắt đầu Transaction
            conn.setAutoCommit(false);

            // Truy vấn thông tin từ TRASH
            psSelectTrash.setInt(1, trashId);
            ResultSet rs = psSelectTrash.executeQuery();

            if (rs.next()) {
                int itemId = rs.getInt("ITEM_ID");
                String itemType = rs.getString("ITEM_TYPE");

                if ("DOCUMENT".equalsIgnoreCase(itemType)) {
                    // Xóa tài liệu trong DOCUMENT
                    psDeleteDocument.setInt(1, itemId);
                    psDeleteDocument.executeUpdate();
                } else if ("FOLDER".equalsIgnoreCase(itemType)) {
                    // Get all documents in the folder and delete them from TRASH
                    psGetDocumentsInFolder.setInt(1, itemId);
                    ResultSet docRs = psGetDocumentsInFolder.executeQuery();

                    // For each document in the folder, delete the corresponding entry in TRASH
                    while (docRs.next()) {
                        int docId = docRs.getInt("DOC_ID");
                        psDeleteDocumentsFromTrash.setInt(1, docId);
                        psDeleteDocumentsFromTrash.executeUpdate();
                    }

                    // Xóa tất cả các tài liệu thuộc FOLDER trong DOCUMENT
                    psDeleteFilesInFolder.setInt(1, itemId);
                    psDeleteFilesInFolder.executeUpdate();

                    // Xóa thư mục trong FOLDER
                    psDeleteFolder.setInt(1, itemId);
                    psDeleteFolder.executeUpdate();
                } else {
                    throw new IllegalArgumentException("Invalid item type: " + itemType);
                }

                // Xóa mục trong TRASH
                psDeleteTrash.setInt(1, trashId);
                psDeleteTrash.executeUpdate();

                // Commit Transaction
                conn.commit();
            } else {
                throw new SQLException("No item found in TRASH with TRASH_ID = " + trashId);
            }
        } catch (SQLException e) {
            // Rollback Transaction khi có lỗi
            LOGGER.severe("SQL error in deleteFromTrashWithTransaction: " + e.getMessage());
            throw e;
        }
    }

    // Helper method to get ITEM_ID from the TRASH table
    private int getItemIdFromTrash(int trashId) throws SQLException {
        String sql = "SELECT ITEM_ID FROM TRASH WHERE TRASH_ID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, trashId);

            //close connection
            JDBCUtil.closeConnection(conn);

            return ps.executeQuery().getInt("ITEM_ID");
        }
    }


}

