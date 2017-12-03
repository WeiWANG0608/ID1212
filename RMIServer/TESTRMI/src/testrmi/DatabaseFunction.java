/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testrmi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Wei
 */
public class DatabaseFunction {
    public static int dataInsert(UserInfo userInfo) {
    Connection conn = Mysqlconnector.Microsqlconnector();
    int i = 0;
    String sql = "insert into UserInfo (userName,userPassword) values(?,?)";
    PreparedStatement pstmt;
    try {
        
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        pstmt.setString(1, userInfo.getUserName());
        pstmt.setString(2, userInfo.getUserPassword());
        i = pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    public static int dataInsert1(file File) {
    Connection conn = Mysqlconnector.Microsqlconnector();
    int i = 0;
    String sql = "insert into fileInfo (filename,fileowner,fileprivacy) values(?,?,?)";
    PreparedStatement pstmt;
    try {
        
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        pstmt.setString(1, File.getfileName());
        pstmt.setString(2, File.getfileowner());
        pstmt.setString(3, File.getfileprivacy());
        i = pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    
   
    
    public static Integer dataBaseGetAll() {
    Connection conn = Mysqlconnector.Microsqlconnector();
    String sql = "select * from UserInfo";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement)conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        int col = rs.getMetaData().getColumnCount();
        System.out.println("============================");
        while (rs.next()) {
            for (int i = 1; i <= col; i++) {
                System.out.print(rs.getString(i) + "\t");
                if ((i == 2) && (rs.getString(i).length() < 8)) {
                    System.out.print("\t");
                }
             }
            System.out.println("");
        }
            System.out.println("============================");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    public static int dataDelete(String userName) {
    Connection conn = Mysqlconnector.Microsqlconnector();
    int i = 0;
    String sql = "delete from UserInfo where userName='" + userName + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        i = pstmt.executeUpdate();
 //       System.out.println("resutl: " + i);
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    
    
    public static int dataUpdate(file fileInfo) {
    Connection conn = Mysqlconnector.Microsqlconnector();
    int i = 0;
    String sql = "update fileInfo set fileowner='" + fileInfo.getfileowner() + "' where filename='" + fileInfo.getfileName() + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        i = pstmt.executeUpdate();
     //   System.out.println("resutl: " + i);
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
 
    
     public static int fileDelete(String fileName) {
    Connection conn = Mysqlconnector.Microsqlconnector();
    int i = 0;
    String sql = "delete from fileInfo where fileName='" + fileName + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        i = pstmt.executeUpdate();
 //       System.out.println("resutl: " + i);
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    
}
