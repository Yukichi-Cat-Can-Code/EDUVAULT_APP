package com.example.eduvault_app.DAO;

import com.example.eduvault_app.model.Folder;

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

public class FolderDAO implements DAOInterface<Folder> {

    private static final Logger LOGGER = Logger.getLogger(FolderDAO.class.getName());
    static {
        // Set default log level and handler
        LOGGER.setLevel(Level.ALL); // Log all levels
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }



    // Thêm folder
    @Override
    public int add(Folder folder) {
        int result = 0;
        String maxFolderIdSQL = "SELECT COALESCE(MAX(FOLDER_ID), 0) + 1 AS NEXT_FOLDER_ID FROM FOLDER";
        String sql = "INSERT INTO FOLDER (FOLDER_ID,USER_ID, PARENT_ID, FOLDER_NAME, FOLDER_CREATEAT, isDeleted) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement PsMaxFolderId = conn.prepareStatement(maxFolderIdSQL);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            //Get current time
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.withNano(0);

            int baseFolderId;
            try (ResultSet rs = PsMaxFolderId.executeQuery()) {
                rs.next();
                baseFolderId = rs.getInt("NEXT_FOLDER_ID");
            }

            preparedStatement.setInt(1,baseFolderId);
            preparedStatement.setInt(2, folder.getUSER_ID());
            preparedStatement.setInt(3, folder.getPARENT_ID());
            preparedStatement.setString(4, folder.getFOLDER_NAME());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(dateTime));
            preparedStatement.setInt(6, 0);

            result = preparedStatement.executeUpdate();

            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(FolderDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return result; // Trả về -1 nếu thêm thất bại
    }

    // Cập nhật folder
    @Override
    public int update(Folder folder) {
        int result = 0;

        String sql = "UPDATE FOLDER SET USER_ID = ?, PARENT_ID = ?, FOLDER_NAME = ?, FOLDER_CREATEAT = ?, isDeleted = ? WHERE FOLDER_ID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.withNano(0);

            preparedStatement.setInt(1, folder.getUSER_ID());
            preparedStatement.setInt(2, folder.getPARENT_ID());
            preparedStatement.setString(3, folder.getFOLDER_NAME());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(dateTime));
            preparedStatement.setShort(5, folder.getIsDeleted());

            result = preparedStatement.executeUpdate();
            // Trả về số dòng bị ảnh hưởng

            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(FolderDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return result;
    }

    // Xóa folder (đánh dấu isDeleted = 1)
    @Override
    public int delete(int id) {
        String sql = "UPDATE FOLDER SET isDeleted = 1 WHERE FOLDER_ID = ?";
        int result = 0;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

           result =  preparedStatement.executeUpdate(); // Trả về số dòng bị ảnh hưởng
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(FolderDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return result;
    }

    // Lấy folder theo ID
    @Override
    public Folder get(int id) {
        String sql = "SELECT * FROM FOLDER WHERE FOLDER_ID = ? AND isDeleted = 0";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Folder folder = new Folder();
                    folder.setFOLDER_ID(resultSet.getInt("FOLDER_ID"));
                    folder.setUSER_ID(resultSet.getInt("USER_ID"));
                    folder.setPARENT_ID(resultSet.getInt("PARENT_ID"));
                    folder.setFOLDER_NAME(resultSet.getString("FOLDER_NAME"));
                    folder.setFOLDER_CREATEAT(resultSet.getTimestamp("CREATEDATE").toLocalDateTime());
                    folder.setIsDeleted(resultSet.getShort("isDeleted"));

                    return folder;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
    }

    // Lấy tất cả các folder
    @Override
    public List<Folder> getAll() {
        List<Folder> folders = new ArrayList<>();
        String sql = "SELECT * FROM FOLDER WHERE isDeleted = 0";

        try (Connection conn = JDBCUtil.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Folder folder = new Folder();
                folder.setFOLDER_ID(resultSet.getInt("FOLDER_ID"));
                folder.setUSER_ID(resultSet.getInt("USER_ID"));
                folder.setPARENT_ID(resultSet.getInt("PARENT_ID"));
                folder.setFOLDER_NAME(resultSet.getString("FOLDER_NAME"));
                folder.setFOLDER_CREATEAT(resultSet.getTimestamp("CREATEDATE").toLocalDateTime());
                folder.setIsDeleted(resultSet.getShort("isDeleted"));

                folders.add(folder);

                JDBCUtil.closeConnection(conn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DocumentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return folders;
    }

    public int getParentId(String parentName, String folderName) {
        if ( parentName.equals(folderName) ) {
            return 0;
        }
        int parentId = 0;

        String sql = "SELECT FOLDER_ID FROM FOLDER WHERE FOLDER_NAME = ?";

        try(Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement((sql))){
            ps.setString(1, parentName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                parentId = rs.getInt("FOLDER_ID");
            }

            JDBCUtil.closeConnection(conn);
            return parentId;
        } catch (SQLException ex){
            Logger.getLogger(FolderDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    public int getFolderId(String folderName){
        if(folderName.isEmpty()){
            return 0;
        }

        int folderId = 0;

        String sql = "SELECT FOLDER_ID FROM FOLDER WHERE FOLDER_NAME = ?";

        try(Connection conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement((sql))){
            ps.setString(1, folderName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                folderId = rs.getInt("FOLDER_ID");
            }

            JDBCUtil.closeConnection(conn);
            return folderId;
        } catch (SQLException ex){
            Logger.getLogger(FolderDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

//    public String getFolderPath(int id) {
//        String sql = "SELECT FOLDER_PATH FROM FOLDER WHERE FOLDER_ID = ?";
//        try (Connection conn = JDBCUtil.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            // Step 2: Set parameter
//            ps.setInt(1, id);
//
//            // Step 3: Execute SQL
//            ResultSet rs = ps.executeQuery();
//
//            // Step 4: Process the result
//            if (rs.next()) {
//                return rs.getString("DOC_PATH");
//            }
//
//            //close connection
//            JDBCUtil.closeConnection(conn);
//        } catch (SQLException ex) {
//            Logger.getLogger(FolderDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
}
