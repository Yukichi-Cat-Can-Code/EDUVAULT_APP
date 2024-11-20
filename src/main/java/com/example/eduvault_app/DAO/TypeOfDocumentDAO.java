package com.example.eduvault_app.DAO;

import com.example.eduvault_app.model.TypeOfDocument;
import com.example.eduvault_app.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TypeOfDocumentDAO implements DAOInterface<TypeOfDocument> {

    @Override
    public int add(TypeOfDocument typeOfDocument) {
        int result = 0;
        String sql = "INSERT INTO TYPEOFDOCUMENT (TYPEDOC_ID, TYPEDOC_NAME, TYPEDOC_DESCRIPTION) VALUES (?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, typeOfDocument.getTYPEDOC_ID());
            ps.setString(2, typeOfDocument.getTYPEDOC_NAME());
            ps.setString(3, typeOfDocument.getTYPEDOC_DESCRIPTION());

            // Step 3: Execute SQL
            result =  ps.executeUpdate();

            //close connection
            JDBCUtil.closeConnection(conn);

        } catch (SQLException e) {
            Logger.getLogger(TypeOfDocumentDAO.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }

        return result;
    }

    @Override
    public int update(TypeOfDocument typeOfDocument) {
        int result = 0;
        String sql = "UPDATE TYPEOFDOCUMENT SET TYPEDOC_NAME = ?, TYPEDOC_DESCRIPTION = ? WHERE TYPEDOC_ID = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, typeOfDocument.getTYPEDOC_NAME());
            ps.setString(2, typeOfDocument.getTYPEDOC_DESCRIPTION());
            ps.setInt(3, typeOfDocument.getTYPEDOC_ID());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(TypeOfDocumentDAO.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }

        return result;
    }

    @Override
    public int delete(int id) {
        int result = 0;
        String sql = "DELETE FROM TYPEOFDOCUMENT WHERE TYPEDOC_ID = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(TypeOfDocumentDAO.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }

        return result;
    }

    @Override
    public TypeOfDocument get(int id) {
        String sql = "SELECT * FROM TYPEOFDOCUMENT WHERE TYPEDOC_ID = ?";
        TypeOfDocument typeOfDocument = null;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    typeOfDocument = new TypeOfDocument(
                            rs.getInt("TYPEDOC_ID"),
                            rs.getString("TYPEDOC_NAME"),
                            rs.getString("TYPEDOC_DESCRIPTION")
                    );
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(TypeOfDocumentDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return typeOfDocument;
    }

    @Override
    public List<TypeOfDocument> getAll() {
        List<TypeOfDocument> typeOfDocuments = new ArrayList<>();
        String sql = "SELECT * FROM TYPEOFDOCUMENT";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TypeOfDocument typeOfDocument = new TypeOfDocument(
                        rs.getInt("TYPEDOC_ID"),
                        rs.getString("TYPEDOC_NAME"),
                        rs.getString("TYPEDOC_DESCRIPTION")
                );
                typeOfDocuments.add(typeOfDocument);
            }
        } catch (SQLException e) {
            Logger.getLogger(TypeOfDocumentDAO.class.getName()).log(Level.SEVERE, null, e);

        }

        return typeOfDocuments;
    }

    public TypeOfDocument findByName(String typeName) {
        String sql = "SELECT * FROM TYPEOFDOCUMENT WHERE TYPEDOC_NAME = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Set parameter
            ps.setString(1, typeName);

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Process result set
            if (rs.next()) {
                return new TypeOfDocument(
                        rs.getInt("TYPEDOC_ID"),
                        rs.getString("TYPEDOC_NAME"),
                        rs.getString("TYPEDOC_DESCRIPTION")
                );
            }
            // Close connection
            JDBCUtil.closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(TypeOfDocumentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

