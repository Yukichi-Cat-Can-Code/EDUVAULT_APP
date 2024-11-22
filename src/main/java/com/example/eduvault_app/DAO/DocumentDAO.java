package com.example.eduvault_app.DAO;

import com.example.eduvault_app.model.Document;
import com.example.eduvault_app.util.JDBCUtil;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentDAO implements DAOInterface<Document> {
    private static final Logger LOGGER = Logger.getLogger(DocumentDAO.class.getName());
    static {
        // Set default log level and handler
        LOGGER.setLevel(Level.ALL); // Log all levels
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }

    @Override
    public int add(Document document) {
        int result = 0;
        String maxDocIdSQL = "SELECT COALESCE(MAX(DOC_ID), 0) + 1 AS NEXT_DOC_ID FROM DOCUMENT";
        String sql = "INSERT INTO DOCUMENT (DOC_ID, FOLDER_ID, USER_ID, TYPEDOC_ID, DOC_NAME, SUMMARY, CREATEDATE, DOC_PATH,isDeleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement PsMaxDocId = conn.prepareStatement(maxDocIdSQL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            //Get current time
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.withNano(0);

            //Get base doc_id
            int baseDocId;
            try (ResultSet rs = PsMaxDocId.executeQuery()) {
                rs.next();
                baseDocId = rs.getInt("NEXT_DOC_ID");
            }

            // Step 2: Set parameters for PreparedStatement
            ps.setInt(1, baseDocId);
            ps.setInt(2, document.getFOLDER_ID());
            ps.setInt(3, document.getUSER_ID());
            ps.setInt(4, document.getTYPEDOC_ID());
            ps.setString(5, document.getDOC_NAME());
            ps.setString(6, document.getSUMMARY());
            ps.setTimestamp(7, Timestamp.valueOf(dateTime));
            ps.setString(8, document.getDOC_PATH());
            ps.setInt(9,0);

            // Step 3: Execute SQL
            result =  ps.executeUpdate();

            //close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return result;
    }

    @Override
    public int update(Document document) {
        int result = 0;

        String sql = "UPDATE DOCUMENT SET FOLDER_ID = ?, USER_ID = ?, DOC_NAME = ?, SUMMARY = ?, CREATEDATE = ?, DOC_PATH = ? WHERE DOC_ID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Get current time yyyy/mm/dd

            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.withNano(0);

            // Step 2: Set parameters
            ps.setInt(1, document.getFOLDER_ID());
            ps.setInt(2, document.getUSER_ID());
            ps.setString(4, document.getDOC_NAME());
            ps.setString(5, document.getSUMMARY());
            ps.setTimestamp(6, Timestamp.valueOf(dateTime));
            ps.setString(7, document.getDOC_PATH());

            // Step 3: Execute SQL
            result =  ps.executeUpdate();

            //close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return result;
    }


    public int updateDoc(int FOLDER_ID, String DOC_NAME, String SUMMARY, LocalDateTime CREATEDATE, String DOC_PATH, int DOC_ID) {
        int result = 0;

        String sql = "UPDATE DOCUMENT SET FOLDER_ID = ?, DOC_NAME = ?, SUMMARY = ?, CREATEDATE = ?, DOC_PATH = ? WHERE DOC_ID = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameters
            ps.setInt(1, FOLDER_ID);
            ps.setString(2, DOC_NAME);
            ps.setString(3, SUMMARY);
            ps.setTimestamp(4, Timestamp.valueOf(CREATEDATE));
            ps.setString(5, DOC_PATH);
            ps.setInt(6, DOC_ID);

            // Execute SQL
            result = ps.executeUpdate();

            if (result == 0) {
                Logger.getLogger(DocumentDAO.class.getName()).log(Level.WARNING, "No rows were updated for DOC_ID=" + DOC_ID);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.SEVERE, "Error updating document: DOC_ID=" + DOC_ID, ex);
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.INFO,
                    String.format("Updating document: FOLDER_ID=%d, DOC_NAME=%s, SUMMARY=%s, CREATEDATE=%s, DOC_PATH=%s, DOC_ID=%d",
                            FOLDER_ID, DOC_NAME, SUMMARY, CREATEDATE, DOC_PATH, DOC_ID));

        }

        return result;
    }


    // Xoa file roi danh dau isDeleted = 1
    @Override
    public int delete(int id) {
        int result = 0;
        String sql = "UPDATE DOCUMENT SET isDeleted = 1 WHERE DOC_ID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Step 2: Set parameter
            ps.setInt(1, id);

            // Step 3: Execute SQL
            result =  ps.executeUpdate();

            //close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return result;
    }

    @Override
    public Document get(int id) {
        String sql = "SELECT * FROM DOCUMENT WHERE DOC_ID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Step 2: Set parameter
            ps.setInt(1, id);

            // Step 3: Execute SQL
            ResultSet rs = ps.executeQuery();

            // Step 4: Process the result
            if (rs.next()) {
                return new Document(
                        rs.getInt("DOC_ID"),
                        rs.getInt("FOLDER_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("TYPEDOC_ID"),
                        rs.getString("DOC_NAME"),
                        rs.getString("SUMMARY"),
                        rs.getTimestamp("CREATEDATE").toLocalDateTime(),
                        rs.getString("DOC_PATH"),
                        rs.getShort("isDeleted")
                );
            }

            //close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Document> getAll() {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM DOCUMENT";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Step 4: Process the result set
            while (rs.next()) {
                Document document = new Document(
                        rs.getInt("DOC_ID"),
                        rs.getInt("FOLDER_ID"),
                        rs.getInt("USER_ID"),
                        rs.getInt("TYPEDOC_ID"),
                        rs.getString("DOC_NAME"),
                        rs.getString("SUMMARY"),
                        rs.getTimestamp("CREATEDATE").toLocalDateTime(),
                        rs.getString("DOC_PATH"),
                        rs.getShort("isDeleted")
                );
                documents.add(document);
            }
            //close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return documents;
    }

    public String getDocPath(int id) {
        String sql = "SELECT DOC_PATH FROM DOCUMENT WHERE DOC_ID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Step 2: Set parameter
            ps.setInt(1, id);

            // Step 3: Execute SQL
            ResultSet rs = ps.executeQuery();

            // Step 4: Process the result
            if (rs.next()) {
                return rs.getString("DOC_PATH");
            }

            //close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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


}


