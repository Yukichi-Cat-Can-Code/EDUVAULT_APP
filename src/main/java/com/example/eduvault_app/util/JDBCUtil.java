package com.example.eduvault_app.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCUtil {
    public static final String strDbUrl = "jdbc:mysql://localhost:906/Eduvault_DB?user=root&password=your_password&useSSL=false&serverTimezone=UTC";

    // Method to establish and return a connection
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(strDbUrl);
            System.out.println("Kết nối thành công");
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // Method to display database information
    public static void printInfo(Connection c) {
        if (c != null) {
            try {
                System.out.println("Connect successfully!");

                DatabaseMetaData data = c.getMetaData();
                // Display SQL information when the connection is successful
                System.out.println("Driver Name: " + data.getDriverName());
                System.out.println("Driver Version: " + data.getDriverVersion());
                System.out.println("Product Name: " + data.getDatabaseProductName());
                System.out.println("Version: " + data.getDatabaseProductVersion());
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Method to close the connection
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Closed Connection!");
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
