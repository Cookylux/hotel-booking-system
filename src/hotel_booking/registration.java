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
public class registration extends javax.swing.JFrame {
    Connection con;
    Statement stmt;
    ResultSet rs;
    DefaultTableModel LoginModel = new DefaultTableModel();
    
    int contact, adult, children;
    String name, email, gender, stats, checkin, checkout, roomtype, rooms;
            

    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(() -> {
            new registration().setVisible(false);
        });
        
    }
    public void Doregistration(){
        try{
            String host = "jdbc:derby://localhost:1527/Hotel_booking";
            String uName = "hotel";
            String uPass = "group4";

            con = DriverManager.getConnection(host, uName, uPass);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM ROOM_MANAGEMENT";
            rs = stmt.executeQuery(sql);
        }catch (SQLException err){
            JOptionPane.showMessageDialog(registration.this, err.getMessage());
        }
    }
    public void Refresh_RS_STMT(){
        try {
            stmt.close();
            rs.close();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM ROOM_MANAGEMENT";
            rs = stmt.executeQuery(sql);
        }catch (SQLException ex){
            Logger.getLogger(registration.class.getName()).log(Level.SEVERE, null, ex);   
        }
    }
}
    
