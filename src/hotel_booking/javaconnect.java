/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rieje
 */
public class javaconnect extends javax.swing.JFrame{
    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    public static Connection connectdb() {

        try {
            con= DriverManager.getConnection("jdbc:derby://localhost:1527/HOTELSYSTEM", "Group4", "12345");
            stmt = con.createStatement();
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(javaconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    public static Statement getStatement() {
        return stmt;
    }

    public static ResultSet getResultSet() {
        return rs;
    }

    public static ResultSet executeQuery(String query) {
        try {
            if (stmt == null) {
                connectdb();  // ensure connection and statement are initialized
            }
            rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(javaconnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}