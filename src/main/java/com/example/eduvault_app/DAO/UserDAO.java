package com.example.eduvault_app.DAO;

import com.example.eduvault_app.model.Folder;
import com.example.eduvault_app.model.User;
import com.example.eduvault_app.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO implements DAOInterface<User> {

    @Override
    public int add(User user) {
        int result = 0;
        String sql = "INSERT INTO USER (USER_ID, USERNAME, PASSWORD, EMAIL, FULLNAME, AVATAR, USER_CREATEAT) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set parameters
            ps.setInt(1, user.getUSER_ID());
            ps.setString(2, user.getUSERNAME());
            ps.setString(3, user.getPASSWORD());
            ps.setString(4, user.getEMAIL());
            ps.setString(5, user.getFULLNAME());
            ps.setString(6, user.getAVATAR());
            ps.setTimestamp(7, new Timestamp(user.getUSER_CREATEAT().getTime()));

            // Execute update
            result = ps.executeUpdate();

            // Close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(User user) {
        int result = 0;
        String sql = "UPDATE USER SET USERNAME = ?, PASSWORD = ?, EMAIL = ?, FULLNAME = ?, AVATAR = ?, USER_CREATEAT = ? WHERE USER_ID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set parameters
            ps.setString(1, user.getUSERNAME());
            ps.setString(2, user.getPASSWORD());
            ps.setString(3, user.getEMAIL());
            ps.setString(4, user.getFULLNAME());
            ps.setString(5, user.getAVATAR());
            ps.setTimestamp(6, new Timestamp(user.getUSER_CREATEAT().getTime()));
            ps.setInt(7, user.getUSER_ID());

            // Execute update
            result = ps.executeUpdate();

            // Close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(int id) {
        int result = 0;
        String sql = "DELETE FROM USER WHERE USER_ID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set parameter
            ps.setInt(1, id);

            // Execute delete
            result = ps.executeUpdate();

            // Close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public User get(int id) {
        String sql = "SELECT * FROM USER WHERE USER_ID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set parameter
            ps.setInt(1, id);

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Process result set
            if (rs.next()) {
                // Convert Timestamp to Date
                Date createAtDate = rs.getTimestamp("USER_CREATEAT");

                // Create User object
                return new User(
                        rs.getInt("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("EMAIL"),
                        rs.getString("FULLNAME"),
                        rs.getString("AVATAR"),
                        createAtDate
                );
            }

            // Close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User findByName(String fullName) {
        String sql = "SELECT * FROM USER WHERE FULLNAME = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set parameter
            ps.setString(1, fullName);

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Process result set
            if (rs.next()) {
                // Convert Timestamp to Date
                Date createAtDate = rs.getTimestamp("USER_CREATEAT");

                // Create User object
                return new User(
                        rs.getInt("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("EMAIL"),
                        rs.getString("FULLNAME"),
                        rs.getString("AVATAR"),
                        createAtDate
                );
            }
            // Close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USER";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Process result set
            while (rs.next()) {
                // Convert Timestamp to Date
                Date createAtDate = rs.getTimestamp("USER_CREATEAT");

                // Create User object
                User user = new User(
                        rs.getInt("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("EMAIL"),
                        rs.getString("FULLNAME"),
                        rs.getString("AVATAR"),
                        createAtDate
                );
                users.add(user);
            }
            // Close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public int getUserIdForFolder(String author) {
        int userId = -1;
        String sql = "SELECT * FROM USER WHERE FULLNAME = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ) {

            ps.setString(1, author);
            // Execute query
            ResultSet rs = ps.executeQuery();
            // Process result set
            if (rs.next()) {
                userId = rs.getInt("USER_ID");
            }

            JDBCUtil.closeConnection(conn);
            return userId;
        }
        catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }
}