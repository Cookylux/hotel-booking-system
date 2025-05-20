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
import java.util.Arrays;
import java.util.List;
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
        String[] columnNames = {"Check In", "Check Out", "Room Type", "Price", "Status", "Booking Price"};
        DefaultTableModel tbModel = new DefaultTableModel();
        tbModel.setColumnIdentifiers(columnNames);
        srtb.setModel(tbModel); // Link model to table

        String sql = "SELECT b.checkin, b.checkout, r.roomtype, r.price AS room_price, b.status, b.price AS booking_price " +
                    "FROM bookings b " +
                    "JOIN ROOMMANAGEMENT1 r ON b.roomnum = r.roomnum where (b.status = 'Completed' OR b.status = 'Accepted')";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String checkIn = rs.getString("checkin");
                String checkOut = rs.getString("checkout");
                String roomType = rs.getString("roomtype");
                int roomPrice = rs.getInt("room_price");
                String status = rs.getString("status");
                String bookingPrice = rs.getString("booking_price");

                tbModel.addRow(new Object[]{checkIn, checkOut, roomType, roomPrice, status, bookingPrice});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }
    // Call this once in constructor or initComponents() — NOT inside filterByMonth
   private void applySalesFilters() {
    String selectedRoomType = cb_roomtype.getSelectedItem().toString();
    String selectedMonthName = cb_month.getSelectedItem().toString();
    boolean filterByAccepted = accepted.isSelected(); 
    boolean filterByCompleted = completed.isSelected();

    int selectedMonth = Arrays.asList("January", "February", "March", "April", "May", "June",
                                      "July", "August", "September", "October", "November", "December")
                              .indexOf(selectedMonthName) + 1;

    StringBuilder query = new StringBuilder(
        "SELECT b.checkin, b.checkout, r.roomtype, r.price AS room_price, b.status, b.price AS booking_price " +
        "FROM bookings b " +
        "JOIN ROOMMANAGEMENT1 r ON b.roomnum = r.roomnum WHERE 1=1 "
    );

    List<Object> params = new ArrayList<>();

    if (!selectedRoomType.equals("Select Room")) {
        query.append("AND r.roomtype = ? ");
        params.add(selectedRoomType);
    }

    if (!selectedMonthName.equals("Select Month")) {
        query.append("AND MONTH(b.checkin) = ? ");
        params.add(selectedMonth);
    }

    // Handle status filters
    List<String> statusFilters = new ArrayList<>();
    if (filterByAccepted) statusFilters.add("Accepted");
    if (filterByCompleted) statusFilters.add("Completed");

    if (statusFilters.isEmpty()) {
        // Default to show both Accepted and Completed if nothing is selected
        query.append("AND (b.status = ? OR b.status = ?) ");
        params.add("Accepted");
        params.add("Completed");
    } else {
        query.append("AND (");
        for (int i = 0; i < statusFilters.size(); i++) {
            query.append("b.status = ?");
            if (i < statusFilters.size() - 1) {
                query.append(" OR ");
            }
            params.add(statusFilters.get(i));
        }
        query.append(") ");
    }

    String[] columnNames = {"Check In", "Check Out", "Room Type", "Room Price", "Status", "Booking Price"};
    DefaultTableModel tbModel = new DefaultTableModel();
    tbModel.setColumnIdentifiers(columnNames);
    srtb.setModel(tbModel);

    try (PreparedStatement ps = con.prepareStatement(query.toString())) {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
            double total = 0;

            while (rs.next()) {
                String checkIn = rs.getString("checkin");
                String checkOut = rs.getString("checkout");
                String roomType = rs.getString("roomtype");
                int roomPrice = rs.getInt("room_price");
                String status = rs.getString("status");
                double bookingPrice = rs.getDouble("booking_price");

                total += bookingPrice;

                tbModel.addRow(new Object[]{checkIn, checkOut, roomType, roomPrice, status, bookingPrice});
            }

            txt_price.setText(String.valueOf(total));
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
    }
}


   private void resetSalesFilters() {
    cb_roomtype.setSelectedIndex(0);   // Reset room type combo box
    cb_month.setSelectedIndex(0);      // Reset month combo box
    accepted.setSelected(false);    // Unselect radio buttons
    completed.setSelected(false);
    txt_price.setText("");             // Clear total price field

    Select(); // Reload the full unfiltered data
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
        srtb = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        cb_month = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txt_price = new javax.swing.JTextField();
        cb_roomtype = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        accepted = new javax.swing.JRadioButton();
        completed = new javax.swing.JRadioButton();
        reset = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

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

        srtb.setModel(tbModel);
        srtb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                srtbMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(srtb);

        jScrollPane2.setViewportView(jScrollPane1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 81, 785, 338));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Sales Report");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, -1, -1));

        cb_month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Month", "January ", "February ", "March ", "April", "May", "June ", "July ", "August", "September", "October", "November", "December" }));
        cb_month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_monthActionPerformed(evt);
            }
        });
        jPanel1.add(cb_month, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 460, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Filters:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 430, -1, -1));
        jPanel1.add(txt_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 510, 110, -1));

        cb_roomtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Room", "Single Room", "Double Room", "Triple Room", "Quad Room", " " }));
        cb_roomtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_roomtypeActionPerformed(evt);
            }
        });
        jPanel1.add(cb_roomtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 460, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Total Price");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 490, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Room Type:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 460, -1, -1));

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

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(32, 33, 67));
        jButton5.setText("Sales Report");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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

        jButton8.setText("Log out");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
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
                        .addComponent(jButton8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(101, 101, 101))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 560));

        accepted.setText("Accepted");
        accepted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptedActionPerformed(evt);
            }
        });
        jPanel1.add(accepted, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 480, -1, -1));

        completed.setText("Completed");
        completed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedActionPerformed(evt);
            }
        });
        jPanel1.add(completed, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 510, -1, -1));

        reset.setBackground(new java.awt.Color(255, 153, 153));
        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        jPanel1.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 460, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Month:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 460, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Reset Filters:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 430, -1, -1));

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

    private void srtbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_srtbMouseClicked

    }//GEN-LAST:event_srtbMouseClicked

    private void cb_monthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_monthActionPerformed
        applySalesFilters();
    }//GEN-LAST:event_cb_monthActionPerformed

    private void cb_roomtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_roomtypeActionPerformed
        applySalesFilters();
    }//GEN-LAST:event_cb_roomtypeActionPerformed

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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        sales_report n=new sales_report();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        admin_home n=new admin_home();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        JOptionPane.showMessageDialog(this, "Logout Succesfully!");
        home_page n=new home_page();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void acceptedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptedActionPerformed
        applySalesFilters();
    }//GEN-LAST:event_acceptedActionPerformed

    private void completedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedActionPerformed
        applySalesFilters();
    }//GEN-LAST:event_completedActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        resetSalesFilters();
    }//GEN-LAST:event_resetActionPerformed

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
    private javax.swing.JRadioButton accepted;
    private javax.swing.JButton btn_calcu;
    private javax.swing.JComboBox<String> cb_month;
    private javax.swing.JComboBox<String> cb_roomtype;
    private javax.swing.JRadioButton completed;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton reset;
    private javax.swing.JTable srtb;
    private javax.swing.JTextField txt_price;
    // End of variables declaration//GEN-END:variables
}
