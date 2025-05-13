/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hotel_booking;
import hotel_booking.javaconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author rieje
 */
public class sales_report extends javax.swing.JFrame{
    Connection con= javaconnect.connectdb();
    PreparedStatement ps = null;
    ResultSet rs=null;
    DefaultTableModel tbModel = new DefaultTableModel();
    /**
     * Creates new form status_report_page
     */
    public sales_report() {
        initComponents();
        javaconnect.connectdb();
        Select();
    }
    private void Select(){
        String[] columnNames = {"Check In", "Check Out", "Room Type", "Price", "Total"};
        DefaultTableModel tbModel = new DefaultTableModel();
        tbModel.setColumnIdentifiers(columnNames);
        jTable1.setModel(tbModel); // Link model to table

        String sql = "SELECT b.checkin, b.checkout, r.roomtype, r.price AS room_price, b.price AS booking_price " +
                    "FROM bookings b " +
                    "JOIN roommanagement1 r ON b.roomnum = r.roomnum";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String checkIn = rs.getString("checkin");
                String checkOut = rs.getString("checkout");
                String roomType = rs.getString("roomtype");
                int roomPrice = rs.getInt("room_price");
                String bookingPrice = rs.getString("booking_price");

                tbModel.addRow(new Object[]{checkIn, checkOut, roomType, roomPrice, bookingPrice});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
}
    }
    // Call this once in constructor or initComponents() — NOT inside filterByMonth
    private void initializeMonthComboBox() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                           "July", "August", "September", "October", "November", "December"};
        cb_month.setModel(new DefaultComboBoxModel<>(months));
    }

        private void filterByRoomType() {
    String selectedRoomType = cb_roomtype.getSelectedItem().toString();

    String[] columnNames = {"Check In", "Check Out", "Room Type", "Price", "Total"};
    DefaultTableModel tbModel = new DefaultTableModel();
    tbModel.setColumnIdentifiers(columnNames);
    jTable1.setModel(tbModel); 

    String sql = "SELECT b.checkin, b.checkout, r.roomtype, r.price AS room_price, b.price AS booking_price " +
                 "FROM bookings b " +
                 "JOIN roommanagement1 r ON b.roomnum = r.roomnum " +
                 "WHERE r.roomtype = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, selectedRoomType);  
        try (ResultSet rs = ps.executeQuery()) {
            int total = 0;

            while (rs.next()) {
                String checkIn = rs.getString("checkin");
                String checkOut = rs.getString("checkout");
                String roomType = rs.getString("roomtype");
                int roomPrice = rs.getInt("room_price");
                String bookingPriceStr = rs.getString("booking_price"); // Retrieve as String

                // Convert bookingPrice from String to int
                double bookingPrice = Double.parseDouble(bookingPriceStr); 

                total += bookingPrice; // Now adding an int

                tbModel.addRow(new Object[]{checkIn, checkOut, roomType, roomPrice, bookingPriceStr});
            }

            txt_price.setText(String.valueOf(total));  // Display total booking price

        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error converting booking price: " + e.getMessage());
    }
}

private void filterByMonth() {
    String[] months = {"January", "February", "March", "April", "May", "June",
                       "July", "August", "September", "October", "November", "December"};

    String selectedMonthName = cb_month.getSelectedItem().toString();
    int monthNumber = Arrays.asList(months).indexOf(selectedMonthName) + 1;

    String[] columnNames = {"Check In", "Check Out", "Room Type", "Price", "Total"};
    DefaultTableModel tbModel = new DefaultTableModel();
    tbModel.setColumnIdentifiers(columnNames);
    jTable1.setModel(tbModel);

    String sql = "SELECT b.checkin, b.checkout, r.roomtype, r.price AS room_price, b.price AS booking_price " +
                 "FROM bookings b " +
                 "JOIN roommanagement1 r ON b.roomnum = r.roomnum " +
                 "WHERE MONTH(b.checkin) = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, monthNumber);
        try (ResultSet rs = ps.executeQuery()) {
            int total = 0;

            while (rs.next()) {
                String checkIn = rs.getString("checkin");
                String checkOut = rs.getString("checkout");
                String roomType = rs.getString("roomtype");
                int roomPrice = rs.getInt("room_price");
                String bookingPriceStr = rs.getString("booking_price"); // Retrieve as String

                // Convert bookingPrice from String to int
                double bookingPrice = Double.parseDouble(bookingPriceStr); 

                total += bookingPrice; // Now adding an int

                tbModel.addRow(new Object[]{checkIn, checkOut, roomType, roomPrice, bookingPriceStr});
            }

            txt_price.setText(String.valueOf(total));  // Display total booking price

        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error converting booking price: " + e.getMessage());
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

        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btn_calcu = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        cb_month = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txt_price = new javax.swing.JTextField();
        cb_roomtype = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();

        jButton6.setBackground(new java.awt.Color(204, 255, 204));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setText("Check-out");

        jButton7.setBackground(new java.awt.Color(255, 204, 204));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setText("Clear");

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(32, 33, 67));
        jButton4.setText("Check-out");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btn_calcu.setText("Calculate");
        btn_calcu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calcuActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(tbModel);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setViewportView(jScrollPane1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 81, 785, 338));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Sales Report");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(639, 18, -1, -1));

        cb_month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January ", "February ", "March ", "April", "May", "June ", "July ", "August", "September", "October", "November", "December" }));
        cb_month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_monthActionPerformed(evt);
            }
        });
        jPanel1.add(cb_month, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 440, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Month:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 440, -1, -1));
        jPanel1.add(txt_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 480, 110, -1));

        cb_roomtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Room", "Single Room", "Double Room", "Triple Room", "Quad Room", " " }));
        cb_roomtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_roomtypeActionPerformed(evt);
            }
        });
        jPanel1.add(cb_roomtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 440, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Price:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 480, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Room Type:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 440, -1, -1));

        jPanel10.setBackground(new java.awt.Color(165, 157, 173));

        jButton17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(32, 33, 67));
        jButton17.setText("Booking Details");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(32, 33, 67));
        jButton18.setText("Room Management");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 255, 255, 80));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel_booking/logo 120.png"))); // NOI18N
        jLabel12.setText("jLabel2");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel11.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 110));

        jButton19.setText("Log out");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton20.setForeground(new java.awt.Color(32, 33, 67));
        jButton20.setText("Sales Report");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton21.setForeground(new java.awt.Color(32, 33, 67));
        jButton21.setText("Booking History");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jButton19)))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jButton19)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1092, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btn_calcuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calcuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_calcuActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        booking_details_page n=new booking_details_page();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        room_management_page1 n=new room_management_page1();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        admin_home n=new admin_home();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        user_login n=new user_login();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        sales_report n=new sales_report();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        adminhistory  adhis = new adminhistory();
        adhis.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
       
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void cb_roomtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_roomtypeActionPerformed
        filterByRoomType();

    }//GEN-LAST:event_cb_roomtypeActionPerformed

    private void cb_monthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_monthActionPerformed
        filterByMonth();
    }//GEN-LAST:event_cb_monthActionPerformed

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
            java.util.logging.Logger.getLogger(sales_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sales_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sales_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sales_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sales_report().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_calcu;
    private javax.swing.JComboBox<String> cb_month;
    private javax.swing.JComboBox<String> cb_roomtype;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txt_price;
    // End of variables declaration//GEN-END:variables
}
