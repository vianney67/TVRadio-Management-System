/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ProgramDao;
import dao.ProgramScheduleDAO;
import java.sql.Time;
import model.Program;
import model.ProgramSchedule;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Calendar;



/**
 *
 * @author admin
 */
public class ProgramSchedulePanel extends javax.swing.JPanel {

     private ProgramScheduleDAO scheduleDAO;
    private ProgramDao programDAO;
    private SimpleDateFormat timeFormat;
    /**
     * Creates new form ProgramSchedulePanel
     */
    public ProgramSchedulePanel() {
        initComponents();
         // Initialize DAOs
    // Initialize DAOs
    scheduleDAO = new ProgramScheduleDAO();
    programDAO = new ProgramDao();

    // Time formatter
    timeFormat = new SimpleDateFormat("HH:mm");

    // Load programs and days using your helper methods
    loadPrograms();
    loadDays();

    // Initialize spinners with valid times (to avoid IllegalArgumentException)
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    Date midnight = cal.getTime();

    startTimeSpinner.setModel(new SpinnerDateModel(midnight, null, null, Calendar.MINUTE));
    startTimeSpinner.setEditor(new JSpinner.DateEditor(startTimeSpinner, "HH:mm"));

    endTimeSpinner.setModel(new SpinnerDateModel(midnight, null, null, Calendar.MINUTE));
    endTimeSpinner.setEditor(new JSpinner.DateEditor(endTimeSpinner, "HH:mm"));

    // Load table data
    loadTable();

    // Clear all fields safely
    clearFields();
    
    programCombo.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        programComboActionPerformed(evt);
    }
});

    }

    
    private void loadPrograms() {
    programCombo.removeAllItems();
    List<Program> programs = programDAO.getAllPrograms();
    for (Program p : programs) {
        programCombo.addItem(p); // store Program object itself
    }
}


private void programComboActionPerformed(java.awt.event.ActionEvent evt) {
   Program selectedProgram = (Program) programCombo.getSelectedItem();
if (selectedProgram != null) {
    channelNameTxt.setText(programDAO.getChannelNameById(selectedProgram.getChannelId()));
} else {
    channelNameTxt.setText("");
}

}
 
    // Helper: Load days into combo
    private void loadDays() {
        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        dayCombo.removeAllItems();
        for(String d: days){
            dayCombo.addItem(d);
        }
    }

    // Helper: Load schedule table
    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        List<ProgramSchedule> schedules = scheduleDAO.getAllSchedules();
        for(ProgramSchedule ps : schedules) {
            Program p = programDAO.getProgramById(ps.getProgramId());
          model.addRow(new Object[]{
    ps.getScheduleId(),
    p.getProgramName(),
    ps.getChannelName(), // <- channel name
    ps.getDay(),
    ps.getStartTime(),
    ps.getEndTime()
});

      
        }
    }

// Helper: Clear all input fields
private void clearFields() {
    refreshAll(); 
}
 private boolean validateSchedule(Program program, String day, Time start, Time end) {
        if (program == null) {
            JOptionPane.showMessageDialog(this, "Please select a program.");
            return false;
        }
        if (day == null || day.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a day.");
            return false;
        }
        if (!start.before(end)) {
            JOptionPane.showMessageDialog(this, "Start time must be before end time.");
            return false;
        }
        // Check for overlap
        if (scheduleDAO.hasOverlap(program.getProgramId(), day, start, end)) {
            JOptionPane.showMessageDialog(this, "This schedule overlaps with an existing schedule.");
            return false;
        }
        return true;
    }
 private void refreshAll() {
    // Reload data from database
    loadPrograms();
    loadDays();

    // Clear selections properly
    if (programCombo.getItemCount() > 0) {
        programCombo.setSelectedIndex(0);
    }

    if (dayCombo.getItemCount() > 0) {
        dayCombo.setSelectedIndex(0);
    }

    channelNameTxt.setText("");

    // Reset time spinners to midnight
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    Date midnight = cal.getTime();

    startTimeSpinner.setValue(midnight);
    endTimeSpinner.setValue(midnight);

    // Reload table
    loadTable();
}


    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        refreshBtn = new javax.swing.JButton();
        startTimeSpinner = new javax.swing.JSpinner();
        endTimeSpinner = new javax.swing.JSpinner();
        dayCombo = new javax.swing.JComboBox<>();
        programCombo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        channelNameTxt = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(51, 0, 51));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Program Schedule Page");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Program Schedule information");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("Program");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setText("Day");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel5.setText("Start Time");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setText("End Time");

        addBtn.setBackground(new java.awt.Color(255, 0, 204));
        addBtn.setForeground(new java.awt.Color(255, 255, 255));
        addBtn.setText("Add ");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        updateBtn.setBackground(new java.awt.Color(102, 102, 255));
        updateBtn.setForeground(new java.awt.Color(255, 255, 255));
        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setBackground(new java.awt.Color(255, 0, 51));
        deleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        refreshBtn.setBackground(new java.awt.Color(0, 153, 153));
        refreshBtn.setForeground(new java.awt.Color(255, 255, 255));
        refreshBtn.setText("refresh");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        dayCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        programCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setText("Channel name");

        channelNameTxt.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(85, 85, 85)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endTimeSpinner)
                            .addComponent(programCombo, 0, 129, Short.MAX_VALUE)
                            .addComponent(channelNameTxt)
                            .addComponent(dayCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startTimeSpinner))
                        .addGap(93, 93, 93))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(addBtn)
                        .addGap(18, 18, 18)
                        .addComponent(updateBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(refreshBtn)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(programCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(channelNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dayCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(startTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(endTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshBtn)
                    .addComponent(deleteBtn)
                    .addComponent(updateBtn)
                    .addComponent(addBtn))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Schedule ID", "Program name", "Channel name", "Day", "Start time", "End time"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed



    try {
        // 1️⃣ Program selection validation
        Program selectedProgram = (Program) programCombo.getSelectedItem();
        if (selectedProgram == null) {
            JOptionPane.showMessageDialog(this, "Please select a program!");
            return;
        }

        // 2️⃣ Channel validation
        String channelName = channelNameTxt.getText();
        if (channelName == null || channelName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Channel name is missing!");
            return;
        }

        // 3️⃣ Program must not be registered to another channel
        if (scheduleDAO.isProgramAssignedToAnotherChannel(selectedProgram.getProgramId(), channelName)) {
            JOptionPane.showMessageDialog(this,
                    "This program is already assigned to another channel!\nA program can belong to only one channel.");
            return;
        }

        // 4️⃣ Day validation
        String day = (String) dayCombo.getSelectedItem();
        if (day == null || day.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a day!");
            return;
        }

        // 5️⃣ Time validation
        Date start = (Date) startTimeSpinner.getValue();
        Date end = (Date) endTimeSpinner.getValue();

        Time startTime = new Time(start.getTime());
        Time endTime = new Time(end.getTime());

        if (!startTime.before(endTime)) {
            JOptionPane.showMessageDialog(this,
                    "Invalid time range!\nStart time MUST be earlier than End time.");
            return;
        }

        // 6️⃣ Overlap validation: same program, same day, same time not allowed
        if (scheduleDAO.isProgramAlreadyScheduled(
                selectedProgram.getProgramId(), day, startTime, endTime)) {

            JOptionPane.showMessageDialog(this,
                    "This program is already scheduled on the same day at the same time!");
            return;
        }

        // ✅ All validations passed → Create object
        ProgramSchedule ps = new ProgramSchedule(
                selectedProgram.getProgramId(),
                channelName,
                day,
                startTime,
                endTime
        );

        boolean success = scheduleDAO.insertSchedule(ps);

        if (success) {
            JOptionPane.showMessageDialog(this, "✅ Schedule added successfully!");
            loadTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Failed to add schedule!");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "System Error: " + ex.getMessage());
    }

    }//GEN-LAST:event_addBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed


    try {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a schedule to update!");
            return;
        }

        int scheduleId = (int) jTable1.getValueAt(selectedRow, 0);

        Program selectedProgram = (Program) programCombo.getSelectedItem();
        if (selectedProgram == null) {
            JOptionPane.showMessageDialog(this, "Please select a program!");
            return;
        }

        String channelName = channelNameTxt.getText();
        if (channelName == null || channelName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Channel name is missing!");
            return;
        }

        String day = (String) dayCombo.getSelectedItem();

        Date start = (Date) startTimeSpinner.getValue();
        Date end = (Date) endTimeSpinner.getValue();

        Time startTime = new Time(start.getTime());
        Time endTime = new Time(end.getTime());

        // ✅ Start < End check
        if (!startTime.before(endTime)) {
            JOptionPane.showMessageDialog(this, "Start time must be before End time!");
            return;
        }

        // ✅ Duplicate schedule check
        if (scheduleDAO.isProgramAlreadyScheduledExceptThis(
                scheduleId, selectedProgram.getProgramId(), day, startTime, endTime)) {

            JOptionPane.showMessageDialog(this,
                    "Another schedule already exists with the same program, day and time!");
            return;
        }

        // ✅ Create updated object
        ProgramSchedule ps = new ProgramSchedule(
                scheduleId,
                selectedProgram.getProgramId(),
                channelName,
                day,
                startTime,
                endTime
        );

        boolean success = scheduleDAO.updateSchedule(ps);

        if (success) {
            JOptionPane.showMessageDialog(this, "✅ Schedule updated successfully!");
            loadTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Failed to update schedule!");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "System Error: " + ex.getMessage());
    }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed

        try {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Select a schedule to delete.");
                return;
            }

            // Get schedule ID from the selected row
            int scheduleId = (int) jTable1.getValueAt(selectedRow, 0);

            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this schedule?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = scheduleDAO.deleteSchedule(scheduleId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Schedule deleted successfully!");
                    loadTable();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete schedule.");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed

       refreshAll();

    JOptionPane.showMessageDialog(this,
        "Data refreshed successfully",
        "Refresh",
        JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_refreshBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField channelNameTxt;
    private javax.swing.JComboBox<String> dayCombo;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JSpinner endTimeSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<Object> programCombo;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JSpinner startTimeSpinner;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
