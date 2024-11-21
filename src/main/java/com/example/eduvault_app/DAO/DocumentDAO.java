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
}


