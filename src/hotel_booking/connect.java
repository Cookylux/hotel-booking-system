/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author rieje
 */
public class connect extends javax.swing.JFrame {
    Connection con;
    Statement stmt;
    ResultSet rs;
    DefaultTableModel SIGNUPACC = new DefaultTableModel();
    String username, new_pass, newusertype, s, n, u, temp_pass, temp_usertype, temp_user;

    public void Doconnect( ){
    try{
        String host= "jdbc:derby://localhost:1527/Hotel";
        String uName= "HOTELUWU";
        String uPass= "12345";
        con = DriverManager.getConnection(host, uName, uPass);
        
        stmt= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_UPDATABLE);
        String sql = "SELECT * FROM HOTELUWU.ACCOUNT";
        rs= stmt.executeQuery(sql);

    }catch(SQLException err) {
        JOptionPane.showMessageDialog(connect.this, err.getMessage());
    }
}
    public void Refresh_RS_STMT(){
        try{
            stmt.close();
            rs.close();
            stmt= con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql= "SELECT * FROM HOTELUWU.ACCOUNT";
            rs= stmt.executeQuery(sql);
        }catch(SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}