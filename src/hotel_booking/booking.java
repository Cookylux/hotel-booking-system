/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hotel_booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import javax.swing.SpinnerNumberModel;
import java.time.temporal.ChronoUnit;
/**
 *
 * @author rieje
 */
public class booking extends javax.swing.JFrame {
        private String username;
        Connection con= javaconnect.connectdb();
        PreparedStatement ps=null;
        ResultSet rs=null;
    /**
     * Creates new form user_booking_details_page
     */
    public booking() {
        
        initComponents();
        con = javaconnect.connectdb(); 

        loadcustomerdata(username);

        cb_gender.setSelectedItem(BookingData.gender);
        check_in.setText(BookingData.checkIn);
        check_out.setText(BookingData.checkOut);
        cb_roomnum.setSelectedItem(BookingData.roomType);
        price.setText(BookingData.price);

        SimpleDateFormat reg = new SimpleDateFormat("MM-dd-yyyy");
        try {
            check_in.setText(reg.format(TransferBookSpinner.InDate));
            check_out.setText(reg.format(TransferBookSpinner.OutDate));
            spin_pax.setValue(TransferBookSpinner.adults + TransferBookSpinner.children);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadcustomerdata(String username) {
        try {
            String sql = "SELECT firstname, lastname, email, contactnum FROM SIGNUP WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(sql); 
            ps.setString(1, Current.loggedInUsername); 
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String emailValue = rs.getString("email");

                if (firstName == null) firstName = "";
                if (lastName == null) lastName = "";

                name.setText((firstName + " " + lastName).trim());

                email.setText(emailValue != null ? emailValue : "No email");
                contact.setText(rs.getString("contactnum"));
            } else {
                System.out.println("No user found with username: " + Current.loggedInUsername);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePrice() {
        try {
        int pricePerRoom = switch ((String) cb_roomtype.getSelectedItem()) {
            case "Single Room" -> 2999;
            case "Double Room" -> 9599;
            case "Triple Room" -> 15600;
            case "Quad Room" -> 20000;
            default -> 0;
        };

        NumberFormat php = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        String formatted = php.format(pricePerRoom).replaceAll("\\.00$", "");
        price.setText(formatted);

    } catch (Exception e) {
        price.setText("₱0");
    }  
    }
    private void loadAvailableRoomsByType(String roomType) {
    try {
            String checkInText = check_in.getText().trim();
            String checkOutText = check_out.getText().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            long nights = 1;
            LocalDate checkInDate = null;
            LocalDate checkOutDate = null;

            if (!checkInText.isEmpty() && !checkOutText.isEmpty()) {
                try {
                    checkInDate = LocalDate.parse(checkInText, formatter);
                    checkOutDate = LocalDate.parse(checkOutText, formatter);

                    if (!checkOutDate.isBefore(checkInDate)) {
                        nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                    }
                } catch (DateTimeParseException e) {
                    nights = 1;
                }
            }

            cb_roomnum.removeAllItems();
            price.setText(null);

            String query = """
                SELECT ROOMNUM, PRICE FROM ROOMMANAGEMENT1
                WHERE ROOMTYPE = ? AND STATUS = 'Available'
                  AND ROOMNUM NOT IN (
                      SELECT ROOMNUM FROM UNAVAILROOMS
                      WHERE UNAVAILDATES BETWEEN ? AND ?
                  )
            """;

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, roomType);
            stmt.setDate(2, checkInDate != null ? java.sql.Date.valueOf(checkInDate) : java.sql.Date.valueOf(LocalDate.now()));
            stmt.setDate(3, checkOutDate != null ? java.sql.Date.valueOf(checkOutDate.minusDays(1)) : java.sql.Date.valueOf(LocalDate.now()));

            ResultSet rs = stmt.executeQuery();

            NumberFormat php = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
            boolean first = true;

            while (rs.next()) {
                cb_roomnum.addItem(rs.getString("ROOMNUM"));

                if (first) {
                    try {
                        double pricePerNight = rs.getDouble("PRICE");
                        double totalPrice = pricePerNight * nights;

                        String priceInfo = "₱" + (int) pricePerNight + " x " + nights + " night(s) = " +
                                php.format(totalPrice).replaceAll("\\.00$", "");
                        price.setText(priceInfo);
                    } catch (NumberFormatException e) {
                        price.setText("₱0");
                    }
                    first = false;
                }
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void processBooking() {
        java.sql.Date checkin = null;
        java.sql.Date checkout = null;
        String status = "Pending";
        if (name.getText().trim().isEmpty() || contact.getText().trim().isEmpty() || email.getText().trim().isEmpty() ||
        cb_gender.getSelectedItem() == null || cb_roomtype.getSelectedItem() == null || cb_roomnum.getSelectedItem() == null ||
        spin_pax.getValue() == null) {

        JOptionPane.showMessageDialog(this, "Please fill out all fields before proceeding.", "Missing Info", JOptionPane.WARNING_MESSAGE);
        return;
    }
        try {
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate checkInDate = LocalDate.parse(check_in.getText().trim(), formatter);
            LocalDate checkOutDate = LocalDate.parse(check_out.getText().trim(), formatter);

            if (checkOutDate.isBefore(checkInDate)) {
                JOptionPane.showMessageDialog(this, "Check-out date cannot be before Check-in date.", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (name.getText().trim().isEmpty() || contact.getText().trim().isEmpty() || email.getText().trim().isEmpty() ||
                cb_gender.getSelectedItem() == null || cb_roomtype.getSelectedItem() == null || cb_roomnum.getSelectedItem() == null ||
                spin_pax.getValue() == null) {

                JOptionPane.showMessageDialog(this, "Please fill out all fields before proceeding.", "Missing Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String nameRegex = "^[A-Za-z ]+$";
            if (!name.getText().trim().matches(nameRegex)) {
                JOptionPane.showMessageDialog(this, "Name contain only letters.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String iemail = email.getText().trim();
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (!iemail.matches(emailRegex)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
             try {
                String contactStr = contact.getText().trim();
                    if (!contactStr.matches("09\\d{9}")) {
                        JOptionPane.showMessageDialog(null, "Contact number must start with '09' and be exactly 11 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Contact number must be digits only.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
            String selectedGender = cb_gender.getSelectedItem().toString();
            if (selectedGender.equals("Select Gender")) {
                JOptionPane.showMessageDialog(this, "Please select a valid gender.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int pax = Integer.parseInt(spin_pax.getValue().toString());
            if (pax == 0){
                JOptionPane.showMessageDialog(this, "Please input number of people.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String roomType = cb_roomtype.getSelectedItem().toString();
            String roomNum = cb_roomnum.getSelectedItem().toString();

            int capacity = switch (roomType) {
                case "Single Room" -> 2;
                case "Double Room", "Triple Room" -> 4;
                case "Quad Room" -> 6;
                default -> 1;
            };

            if (pax > capacity) {
                JOptionPane.showMessageDialog(this,
                    "Selected room type cannot accommodate this many guests.\n" +
                    "Room type: " + roomType + "\n" +
                    "Capacity: " + capacity + "\n" +
                    "Guests: " + pax,
                    "Capacity Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String availabilitySQL = "SELECT UNAVAILDATES FROM UNAVAILROOMS WHERE ROOMNUM = ?";
            PreparedStatement ps = con.prepareStatement(availabilitySQL);
            ps.setString(1, roomNum);
            ResultSet rs = ps.executeQuery();

            boolean conflict = false;
            while (rs.next()) {
                LocalDate unavailDate = rs.getDate("UNAVAILDATES").toLocalDate();
                if (!unavailDate.isBefore(checkInDate) && unavailDate.isBefore(checkOutDate)) {
                    conflict = true;
                    break;
                }
            }
            rs.close();
            ps.close();

            if (conflict) {
                JOptionPane.showMessageDialog(this, "Selected room is not available during chosen dates.", "Room Unavailable", JOptionPane.WARNING_MESSAGE);
                return;
            }

            checkin = java.sql.Date.valueOf(checkInDate);
            checkout = java.sql.Date.valueOf(checkOutDate);
            String priceText = price.getText().trim();
            double totalPrice = 0.0;
            try {
                String[] parts = priceText.split("=");
                if (parts.length == 2) {
                    String numericPart = parts[1].replaceAll("[^\\d.]", "");
                    totalPrice = Double.parseDouble(numericPart);
                }
            } catch (Exception e) {
                totalPrice = 0.0; // fallback if parsing fails
            }
            String insertSQL = "INSERT INTO BOOKINGS (name, contact, email, gender, checkin, checkout, pax, roomnum, roomtype, price, username, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertPs = con.prepareStatement(insertSQL);
            insertPs.setString(1, name.getText().trim());
            insertPs.setString(2, contact.getText().trim());
            insertPs.setString(3, email.getText().trim());
            insertPs.setString(4, cb_gender.getSelectedItem().toString());
            insertPs.setDate(5, checkin);
            insertPs.setDate(6, checkout);
            insertPs.setString(7, String.valueOf(pax));
            insertPs.setString(8, roomNum);
            insertPs.setString(9, roomType);
            insertPs.setString(10, String.valueOf(totalPrice)); 
            insertPs.setString(11, Current.loggedInUsername);
            insertPs.setString(12, status);
            insertPs.executeUpdate();
            insertPs.close();

            String unavailableSQL = "INSERT INTO UNAVAILROOMS (ROOMNUM, UNAVAILDATES) VALUES (?, ?)";
            PreparedStatement unavailablePs = con.prepareStatement(unavailableSQL);

            for (LocalDate date = checkInDate; date.isBefore(checkOutDate); date = date.plusDays(1)) {
                unavailablePs.setString(1, roomNum);
                unavailablePs.setDate(2, java.sql.Date.valueOf(date));
                unavailablePs.addBatch();
            }

            unavailablePs.executeBatch();
            unavailablePs.close();
            con.close();

            JOptionPane.showMessageDialog(this, "Booking Successful!");
            new loggedin_home_page().setVisible(true);
            this.setVisible(false);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use MM-DD-YYYY", "Date Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Something went wrong while processing the booking.", "Error", JOptionPane.ERROR_MESSAGE);
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

        spin_child = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        spin_roomnum = new javax.swing.JSpinner();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        price = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        proceed = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        contact = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cb_roomnum = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        check_in = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        check_out = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        spin_pax = new javax.swing.JSpinner();
        cb_gender = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cb_roomtype = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        bt_home = new javax.swing.JButton();

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel7.setText("Children:");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1060, 597));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel3.setBackground(new java.awt.Color(75, 59, 91));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel2.setText("Booking");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel3.setText("Name:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel11.setText("Room Type:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel13.setText("Price:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, -1, -1));

        price.setEditable(false);
        jPanel2.add(price, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 360, 290, 30));

        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });
        jPanel2.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 290, 30));

        proceed.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        proceed.setText("Proceed");
        proceed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proceedActionPerformed(evt);
            }
        });
        jPanel2.add(proceed, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 130, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel15.setText("Contact Number:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, -1));

        contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactActionPerformed(evt);
            }
        });
        jPanel2.add(contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 290, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel16.setText("Email:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, -1));

        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        jPanel2.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 290, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel17.setText("Gender:");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, -1, -1));

        cb_roomnum.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cb_roomnum.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", " " }));
        cb_roomnum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_roomnumActionPerformed(evt);
            }
        });
        jPanel2.add(cb_roomnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 170, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel4.setText("Check-in:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, -1, -1));

        check_in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_inActionPerformed(evt);
            }
        });
        jPanel2.add(check_in, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 290, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel5.setText("Check-out:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, -1, -1));

        check_out.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_outActionPerformed(evt);
            }
        });
        jPanel2.add(check_out, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 290, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel6.setText("Pax");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 160, -1, -1));
        jPanel2.add(spin_pax, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 180, 100, 30));

        cb_gender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cb_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", "" }));
        jPanel2.add(cb_gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 290, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel8.setText("Available Rooms:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 280, -1, -1));

        cb_roomtype.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cb_roomtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Room Type", "Single Room", "Double Room", "Triple Room", "Quad Room", " " }));
        cb_roomtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_roomtypeActionPerformed(evt);
            }
        });
        jPanel2.add(cb_roomtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 240, 290, 30));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 880, 480));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hotel_booking/logo 120.png"))); // NOI18N
        jLabel14.setText("jLabel14");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, -1));

        bt_home.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        bt_home.setText("Home");
        bt_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_homeActionPerformed(evt);
            }
        });
        jPanel3.add(bt_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 100, 40));

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 1060, 590);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        loggedin_home_page n=new loggedin_home_page();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel14MouseClicked

    private void proceedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proceedActionPerformed
        processBooking();
    }//GEN-LAST:event_proceedActionPerformed

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameActionPerformed

    private void contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void check_inActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_inActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check_inActionPerformed

    private void check_outActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_outActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check_outActionPerformed

    private void cb_roomnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_roomnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_roomnumActionPerformed

    private void cb_roomtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_roomtypeActionPerformed
        
    String selectedType = (String) cb_roomtype.getSelectedItem();

    if (selectedType != null && !selectedType.equals("Select")) {
        loadAvailableRoomsByType(selectedType);
    }


    }//GEN-LAST:event_cb_roomtypeActionPerformed

    private void bt_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_homeActionPerformed
        loggedin_home_page n=new loggedin_home_page();
        n.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bt_homeActionPerformed
    
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
            java.util.logging.Logger.getLogger(booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new booking().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_home;
    private javax.swing.JComboBox<String> cb_gender;
    private javax.swing.JComboBox<String> cb_roomnum;
    private javax.swing.JComboBox<String> cb_roomtype;
    private javax.swing.JTextField check_in;
    private javax.swing.JTextField check_out;
    private javax.swing.JTextField contact;
    private javax.swing.JTextField email;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField name;
    private javax.swing.JTextField price;
    private javax.swing.JButton proceed;
    private javax.swing.JSpinner spin_child;
    private javax.swing.JSpinner spin_pax;
    private javax.swing.JSpinner spin_roomnum;
    // End of variables declaration//GEN-END:variables

    
    
}
