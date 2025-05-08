/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author rieje
 */
public class javaconnect extends javax.swing.JFrame{
    public static Connection connectdb() {
        Connection con=null;
        try {
            con= DriverManager.getConnection("jdbc:derby://localhost:1527/HOTELSYSTEM", "Group4", "12345");
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(javaconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
}   