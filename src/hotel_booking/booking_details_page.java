/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hotel_booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rieje
 */
public class booking_details_page extends javax.swing.JFrame{
    Connection con= javaconnect.connectdb();
    PreparedStatement ps = null;
    ResultSet rs=null;
    DefaultTableModel tbmodel = new DefaultTableModel();
    /**
     * Creates new form booking_details_page
     */
    public booking_details_page() {
        initComponents();
        javaconnect.connectdb();
        Select();
    }
    private void Select() {
        
    String sql = "SELECT name, contact, email, gender, checkin, checkout, pax, roomnum, roomtype, price,username, status FROM BOOKINGS ";   
    String[] columnNames = {"NAME", "CONTACT", "EMAIL", "GENDER", "CHECKIN", "CHECKOUT", "PAX", "ROOMNUM", "ROOMTYPE", "PRICE", "USERNAME", "STATUS"};
    tbmodel.setColumnIdentifiers(columnNames);
    tbmodel.setRowCount(0);

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            int x = 0;
            while (rs.next()) {
                String name = rs.getString("NAME");
                String contact = rs.getString("CONTACT");
                String email = rs.getString("EMAIL");
                String gender = rs.getString("GENDER");
                String checkin = rs.getString("CHECKIN");
                String checkout = rs.getString("CHECKOUT");
                String pax = rs.getString("PAX");
                String roomNum = rs.getString("ROOMNUM");
                String roomType = rs.getString("ROOMTYPE");
                String price = rs.getString("PRICE");
                String username = rs.getString("USERNAME");
                String status = rs.getString("STATUS");

                tbmodel.addRow(new Object[] {name, contact, email, gender, checkin, checkout, pax, roomNum, roomType, price,username, status});
                x++;
            }

            bookdetailstb.setModel(tbmodel);
            bookdetailstb.setVisible(x > 0);

            if (x == 0) {
                JOptionPane.showMessageDialog(this, "No records found.");
            }

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(this, "Error retrieving data: " + err.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
        }
    }
    private void searchBookingStatus() {
    String roomNumber = roomnum.getText().trim();
    String userName = username.getText().trim();
    String selectedStatus = (String) cb_stat.getSelectedItem();

   
    StringBuilder sql = new StringBuilder("SELECT name, contact, email, gender, checkin, checkout, pax, roomnum, roomtype, price, username, status FROM BOOKINGS WHERE 1=1");
    List<String> parameters = new ArrayList<>();

    if (!userName.isEmpty()) {
        sql.append(" AND username = ?");
        parameters.add(userName);
    }
    if (!roomNumber.isEmpty()) {
        sql.append(" AND roomnum = ?");
        parameters.add(roomNumber);
    }
    if (selectedStatus != null && !selectedStatus.trim().isEmpty() && !selectedStatus.equals("Select Status")) {
        sql.append(" AND status = ?");
        parameters.add(selectedStatus);
    }

    if (parameters.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please provide at least one filter (Username, Room Number, or Status).");
        return;
    }

    String[] columnNames = {"NAME", "CONTACT", "EMAIL", "GENDER", "CHECKIN", "CHECKOUT", "PAX", "ROOMNUM", "ROOMTYPE", "PRICE", "USERNAME", "STATUS"};
    tbmodel.setColumnIdentifiers(columnNames);
    tbmodel.setRowCount(0);

    try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
        for (int i = 0; i < parameters.size(); i++) {
            ps.setString(i + 1, parameters.get(i));
        }

        ResultSet rs = ps.executeQuery();
        int count = 0;

        while (rs.next()) {
            tbmodel.addRow(new Object[] {
                rs.getString("NAME"),
                rs.getString("CONTACT"),
                rs.getString("EMAIL"),
                rs.getString("GENDER"),
                rs.getString("CHECKIN"),
                rs.getString("CHECKOUT"),
                rs.getString("PAX"),
                rs.getString("ROOMNUM"),
                rs.getString("ROOMTYPE"),
                rs.getString("PRICE"),
                rs.getString("USERNAME"),
                rs.getString("STATUS")
            });
            count++;
        }

        bookdetailstb.setModel(tbmodel);
        bookdetailstb.setVisible(count > 0);

        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No matching records found for the provided filters.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error retrieving data: " + e.getMessage());
        e.printStackTrace();
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        btn_decline1 = new javax.swing.JButton();
        btn_decline = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        roomnum = new javax.swing.JTextField();
        username = new javax.swing.JTextField();
        cb_stat = new javax.swing.JComboBox<>();
        btn_search = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookdetailstb = new javax.swing.JTable();
        btn_update = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        clear1 = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_decline1.setBackground(new java.awt.Color(255, 204, 153));
        btn_decline1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_decline1.setText("Decline");
        btn_decline1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_decline1ActionPerformed(evt);
            }
        });

        btn_decline.setBackground(new java.awt.Color(255, 204, 204));
        btn_decline.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_decline.setText("Delete");
        btn_decline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_declineActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1120, 600));
        setMinimumSize(new java.awt.Dimension(1120, 600));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(165, 157, 173));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(32, 33, 67));
        jButton1.setText("Booking Details");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(32, 33, 67));
        jButton2.setText("Room Management");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(32, 33, 67));
        jButton4.setText("Sales Report");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255, 80));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel_booking/logo 120.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 110));

        jButton5.setText("Log out");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jButton5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(101, 101, 101))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 593));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Booking Details");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Status:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Room Number:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, -1, -1));

        roomnum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomnumActionPerformed(evt);
            }
        });
        jPanel1.add(roomnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 140, 31));
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 140, 31));

        cb_stat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Status", "Pending", "Accepted", "Completed", "Declined" }));
        jPanel1.add(cb_stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, -1, -1));

        btn_search.setBackground(new java.awt.Color(153, 204, 255));
        btn_search.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanel1.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 60, -1, -1));

        bookdetailstb.setModel(tbmodel);
        bookdetailstb.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        bookdetailstb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookdetailstbMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(bookdetailstb);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 780, 390));

        btn_update.setBackground(new java.awt.Color(204, 255, 204));
        btn_update.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel1.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 100, -1, -1));

        clear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        jPanel1.add(clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Username:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, -1, -1));

        clear1.setBackground(new java.awt.Color(255, 153, 153));
        clear1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clear1.setText("Delete");
        clear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear1ActionPerformed(evt);
            }
        });
        jPanel1.add(clear1, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 140, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_declineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_declineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_declineActionPerformed

    private void btn_decline1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_decline1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_decline1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        booking_details_page n=new booking_details_page();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        room_management_page1 n=new room_management_page1();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        sales_report n=new sales_report();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        admin_home n=new admin_home();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JOptionPane.showMessageDialog(this, "Logout Succesfully!");
        home_page n=new home_page();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void roomnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roomnumActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        searchBookingStatus();

    }//GEN-LAST:event_btn_searchActionPerformed

    private void bookdetailstbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookdetailstbMouseClicked
        int row = bookdetailstb.getSelectedRow();
        if (row >= 0) {
            DefaultTableModel model = (DefaultTableModel) bookdetailstb.getModel();

            if (model.getColumnCount() > 11) {
                String roomNumber = model.getValueAt(row, 7).toString();
                String userName = model.getValueAt(row, 10).toString();
                String status = model.getValueAt(row, 11).toString();

                cb_stat.setSelectedItem(status);
                username.setText(userName);

                if (roomNumber.matches("R(0[1-9]|1[0-9]|20)")) {
                    roomnum.setText(roomNumber);
                }
            } else {
                System.out.println("Error: Table does not have enough columns. Found only " + model.getColumnCount());
            }
        }
    }//GEN-LAST:event_bookdetailstbMouseClicked

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        String[] columnNames = {"NAME", "CONTACT", "EMAIL", "GENDER", "CHECKIN", "CHECKOUT", "PAX", "ROOMNUM", "ROOMTYPE", "PRICE", "USERNAME", "STATUS"};
        tbmodel.setColumnIdentifiers(columnNames);
        tbmodel.setRowCount(0);

        String roomNumValue = roomnum.getText().trim();
        String usernameValue = username.getText().trim();
        String selectedStatus = cb_stat.getSelectedItem().toString();

        try {
            // Step 1: Check current booking status
            String checkQuery = "SELECT STATUS FROM BOOKINGS WHERE ROOMNUM = ? AND USERNAME = ?";
            ps = con.prepareStatement(checkQuery);
            ps.setString(1, roomNumValue);
            ps.setString(2, usernameValue);
            rs = ps.executeQuery();

            if (rs.next()) {
                String currentStatus = rs.getString("STATUS");

                if (currentStatus.equalsIgnoreCase("Declined") || currentStatus.equalsIgnoreCase("Completed")) {
                    JOptionPane.showMessageDialog(this, "Cannot update. Booking is already " + currentStatus + ".");
                } else {
                    // Step 2: Perform the update
                    String updateQuery = "UPDATE BOOKINGS SET STATUS = ? WHERE ROOMNUM = ? AND USERNAME = ?";
                    try (PreparedStatement updatePs = con.prepareStatement(updateQuery)) {
                        updatePs.setString(1, selectedStatus);
                        updatePs.setString(2, roomNumValue);
                        updatePs.setString(3, usernameValue);
                        updatePs.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(this, "Record Updated");
                }

                Select(); // Refresh the table view
            } else {
                JOptionPane.showMessageDialog(this, "Booking not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error while updating booking.", "Error", JOptionPane.ERROR_MESSAGE);
        }
       

    }//GEN-LAST:event_btn_updateActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        username.setText("");  // Clear Username field
        roomnum.setText("");  // Clear Room Number field
        cb_stat.setSelectedIndex(0);
        Select();
    }//GEN-LAST:event_clearActionPerformed

    private void clear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear1ActionPerformed
        try {
            int confirm = JOptionPane.showConfirmDialog(null, "Do you want to delete this record?", "Warning", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String roomNumValue = roomnum.getText().trim();
                String usernameValue = username.getText().trim();

                if (roomNumValue.isEmpty() || usernameValue.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Room number and username are required to delete a booking.");
                    return;
                }

                // Step 1: Check booking status
                String checkQuery = "SELECT STATUS FROM BOOKINGS WHERE ROOMNUM = ? AND USERNAME = ?";
                try (PreparedStatement ps = con.prepareStatement(checkQuery)) {
                    ps.setString(1, roomNumValue);
                    ps.setString(2, usernameValue);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String deleteQuery = "DELETE FROM BOOKINGS WHERE ROOMNUM = ? AND USERNAME = ?";
                            try (PreparedStatement deletePs = con.prepareStatement(deleteQuery)) {
                                deletePs.setString(1, roomNumValue);
                                deletePs.setString(2, usernameValue);
                                int rowsAffected = deletePs.executeUpdate();

                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(this, "Booking deleted successfully.");
                                    Select(); 
                                } else {
                                    JOptionPane.showMessageDialog(this, "Booking not found.");
                                }
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "Booking not found.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_clear1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(booking_details_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(booking_details_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(booking_details_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(booking_details_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new booking_details_page().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bookdetailstb;
    private javax.swing.JButton btn_decline;
    private javax.swing.JButton btn_decline1;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> cb_stat;
    private javax.swing.JButton clear;
    private javax.swing.JButton clear1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField roomnum;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
