package com.example.eduvault_app.testConnection;

import com.example.eduvault_app.util.JDBCUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ACER
 */
public class TestConnection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //Step 1
            Connection con;
            con = (Connection) JDBCUtil.getConnection();
            JDBCUtil.printInfo(con);

            //Step 2: Tao ra doi tuong Statement
            Statement st = con.createStatement();

//            //Step 3: Thuc thi cau lenh SQL
//            String sql = "INSERT INTO Person(ID, FullName, GenderID, DOB)\n"
//                    + "VALUES\n"
//                    + "(3, 'Mai Nhat Minh', 0, '2004-10-19')";
//
//            int check = st.executeUpdate(sql);
//
//            //Step 4: Xu li ket qua
//            System.out.println("So dong thay doi: " + check);
//            if(check>0) {
//                System.out.println("Added successful!");
//            } else {
//                System.out.println("Add failed!");
//            }

            //Step 5
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
