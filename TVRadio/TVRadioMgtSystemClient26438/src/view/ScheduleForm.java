package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Program;
import model.ProgramSchedule;
import service.TVRadioService;
import util.Config;

public class ScheduleForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtStartTime, txtEndTime, txtChannel;
    private JComboBox<String> cmbDay;
    private JComboBox<Program> cmbProgram;
    private TVRadioService service;
    private Map<Integer, String> programMap = new HashMap<>();

    public ScheduleForm() {
        initComponents();
        initService();
        loadData();
    }

    private void initService() {
        try {
            Registry registry = LocateRegistry.getRegistry(Config.HOST, Config.PORT);
            service = (TVRadioService) registry.lookup("TVRadioService");
            loadPrograms();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage());
        }
    }
    
    private void loadPrograms() {
        try {
            List<Program> programs = service.getAllPrograms();
            cmbProgram.removeAllItems();
            programMap.clear();
            for (Program p : programs) {
                cmbProgram.addItem(p);
                programMap.put(p.getId(), p.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setTitle("Manage Program Schedules");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formGrid = new JPanel(new GridLayout(5, 2, 10, 10));
        formGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formGrid.setOpaque(true);
        formGrid.setBackground(new java.awt.Color(255, 255, 255, 20));
        
        JLabel lblProgram = new JLabel("Program:");
        cmbProgram = new JComboBox<>();
        formGrid.add(lblProgram); formGrid.add(cmbProgram);
        
        JLabel lblChannel = new JLabel("Channel Name:");
        txtChannel = new JTextField();
        txtChannel.setEditable(false);
        formGrid.add(lblChannel); formGrid.add(txtChannel);
        
        JLabel lblDay = new JLabel("Day:");
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        cmbDay = new JComboBox<>(days);
        formGrid.add(lblDay); formGrid.add(cmbDay);
        
        JLabel lblStart = new JLabel("Start Hour (0-23):");
        txtStartTime = new JTextField();
        formGrid.add(lblStart); formGrid.add(txtStartTime);
        
        JLabel lblEnd = new JLabel("End Hour (0-23):");
        txtEndTime = new JTextField();
        formGrid.add(lblEnd); formGrid.add(txtEndTime);
        
        java.awt.Font f = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        java.awt.Color labelColor = new java.awt.Color(255,255,255);
        for (JLabel lbl : new JLabel[]{lblProgram,lblChannel,lblDay,lblStart,lblEnd}) { lbl.setFont(f); lbl.setForeground(labelColor); }
        cmbProgram.setFont(f); txtChannel.setFont(f); cmbDay.setFont(f); txtStartTime.setFont(f); txtEndTime.setFont(f);
        javax.swing.border.Border pad = BorderFactory.createEmptyBorder(5,5,5,5);
        javax.swing.border.Border line = BorderFactory.createLineBorder(new java.awt.Color(52,152,219));
        javax.swing.border.Border fieldBorder = BorderFactory.createCompoundBorder(line, pad);
        cmbProgram.setBorder(fieldBorder); txtChannel.setBorder(fieldBorder); cmbDay.setBorder(fieldBorder); txtStartTime.setBorder(fieldBorder); txtEndTime.setBorder(fieldBorder);
        
        cmbProgram.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                Object o = e.getItem();
                if (o instanceof Program) {
                    Program p = (Program) o;
                    String ch = "";
                    if (p.getChannel() != null && p.getChannel().getName() != null) {
                        ch = p.getChannel().getName();
                    }
                    txtChannel.setText(ch);
                }
            }
        });
        
        JPanel controls = new JPanel(new GridLayout(1, 6, 10, 10));
        controls.setOpaque(true);
        controls.setBackground(new java.awt.Color(255, 255, 255, 20));
        
        JButton btnAdd = new JButton("Add Schedule");
        btnAdd.setBackground(new java.awt.Color(46, 204, 113));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addSchedule());
        controls.add(btnAdd);
        
        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteSchedule());
        controls.add(btnDelete);
        
        JButton btnUpdate = new JButton("Update Selected");
        btnUpdate.setBackground(new java.awt.Color(52, 152, 219));
        btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateSchedule());
        controls.add(btnUpdate);
        
        JButton btnExport = new JButton("Export CSV");
        btnExport.setBackground(new java.awt.Color(26, 188, 156));
        btnExport.setForeground(java.awt.Color.WHITE);
        btnExport.setFocusPainted(false);
        btnExport.addActionListener(e -> exportCSV());
        controls.add(btnExport);
        
        JButton btnPrint = new JButton("Print Table");
        btnPrint.setBackground(new java.awt.Color(127, 140, 141));
        btnPrint.setForeground(java.awt.Color.WHITE);
        btnPrint.setFocusPainted(false);
        btnPrint.addActionListener(e -> printTable());
        controls.add(btnPrint);
        
        

        JPanel bg = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.net.URL url = getClass().getResource("/images/tv10.PNG");
                if (url != null) {
                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(url);
                    java.awt.Image img = icon.getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        bg.setLayout(new BorderLayout(10, 10));
        add(bg, BorderLayout.CENTER);

        model = new DefaultTableModel(new Object[]{"ID", "Program", "Channel", "Day", "Start Time", "End Time"}, 0);
        table = new JTable(model);
        javax.swing.JScrollPane tableScroll = new javax.swing.JScrollPane(table);
        JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
        cardPanel.setBackground(new java.awt.Color(26, 32, 44));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(45, 55, 72), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel title = new javax.swing.JLabel("Program Schedules");
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setForeground(java.awt.Color.WHITE);
        title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        cardPanel.add(title, BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setOpaque(false);
        contentPanel.add(formGrid, BorderLayout.NORTH);
        contentPanel.add(tableScroll, BorderLayout.CENTER);
        cardPanel.add(contentPanel, BorderLayout.CENTER);
        cardPanel.add(controls, BorderLayout.SOUTH);
        bg.add(cardPanel, BorderLayout.CENTER);
    }

    private void loadData() {
        try {
            model.setRowCount(0);
            List<ProgramSchedule> schedules = service.getAllProgramSchedules();
            
            // Refresh program map if needed (optional, but good practice if programs changed)
            if (programMap.isEmpty()) loadPrograms();

            for (ProgramSchedule s : schedules) {
                String programName = programMap.getOrDefault(s.getProgramId(), "Unknown ID: " + s.getProgramId());
                model.addRow(new Object[]{
                    s.getScheduleId(), 
                    programName, 
                    s.getChannelName(),
                    s.getDay(),
                    s.getStartTime(), 
                    s.getEndTime()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSchedule() {
        try {
            Program selectedProgram = (Program) cmbProgram.getSelectedItem();
            String channel = txtChannel.getText() == null ? "" : txtChannel.getText().trim();
            String day = (String) cmbDay.getSelectedItem();
            String startText = txtStartTime.getText() == null ? "" : txtStartTime.getText().trim();
            String endText = txtEndTime.getText() == null ? "" : txtEndTime.getText().trim();
            if (!validateSchedule(selectedProgram, channel, day, startText, endText, false, null)) return;
            ProgramSchedule s = new ProgramSchedule();
            s.setProgramId(selectedProgram.getId());
            s.setChannelName(channel);
            s.setDay(day);
            int sh = Integer.parseInt(startText);
            int eh = Integer.parseInt(endText);
            s.setStartTime(Time.valueOf(String.format("%02d:00:00", sh)));
            s.setEndTime(Time.valueOf(String.format("%02d:00:00", eh)));
            service.addProgramSchedule(s);
            JOptionPane.showMessageDialog(this, "Schedule Added Successfully");
            loadData();
            txtChannel.setText("");
            txtStartTime.setText("");
            txtEndTime.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding schedule: " + e.getMessage());
        }
    }
    
    private void deleteSchedule() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            try {
                service.deleteProgramSchedule(id);
                JOptionPane.showMessageDialog(this, "Schedule Deleted");
                loadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateSchedule() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            Program selectedProgram = (Program) cmbProgram.getSelectedItem();
            String channel = txtChannel.getText() == null ? "" : txtChannel.getText().trim();
            String day = (String) cmbDay.getSelectedItem();
            String startText = txtStartTime.getText() == null ? "" : txtStartTime.getText().trim();
            String endText = txtEndTime.getText() == null ? "" : txtEndTime.getText().trim();
            if (!validateSchedule(selectedProgram, channel, day, startText, endText, true, id)) return;
            ProgramSchedule s = new ProgramSchedule();
            s.setScheduleId(id);
            s.setProgramId(selectedProgram.getId());
            s.setChannelName(channel);
            s.setDay(day);
            int sh = Integer.parseInt(startText);
            int eh = Integer.parseInt(endText);
            s.setStartTime(java.sql.Time.valueOf(String.format("%02d:00:00", sh)));
            s.setEndTime(java.sql.Time.valueOf(String.format("%02d:00:00", eh)));
            try {
                service.updateProgramSchedule(s);
                JOptionPane.showMessageDialog(this, "Schedule Updated");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating schedule: " + ex.getMessage());
            }
        }
    }
    
    private boolean validateSchedule(Program program, String channel, String day, String startText, String endText, boolean isUpdate, Integer id) {
        if (program == null) {
            JOptionPane.showMessageDialog(this, "Please select a program");
            return false;
        }
        if (channel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Channel is required");
            return false;
        }
        if (program.getChannel() != null && program.getChannel().getName() != null) {
            if (!channel.equalsIgnoreCase(program.getChannel().getName())) {
                JOptionPane.showMessageDialog(this, "Channel must match the program's channel");
                return false;
            }
        }
        if (day == null || day.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Day is required");
            return false;
        }
        int sh, eh;
        try { sh = Integer.parseInt(startText); eh = Integer.parseInt(endText); } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hours must be numeric");
            return false;
        }
        if (sh < 0 || sh > 23 || eh < 0 || eh > 23) {
            JOptionPane.showMessageDialog(this, "Hours must be between 0 and 23");
            return false;
        }
        java.sql.Time start = java.sql.Time.valueOf(String.format("%02d:00:00", sh));
        java.sql.Time end = java.sql.Time.valueOf(String.format("%02d:00:00", eh));
        if (!start.before(end)) {
            JOptionPane.showMessageDialog(this, "Start time must be before end time");
            return false;
        }
        long durationMs = end.getTime() - start.getTime();
        if (durationMs > 4L * 60L * 60L * 1000L) {
            JOptionPane.showMessageDialog(this, "Schedule duration cannot exceed 4 hours");
            return false;
        }
        if (sh < 5 || eh > 23) {
            JOptionPane.showMessageDialog(this, "Schedules must be between hours 5 and 23");
            return false;
        }
        try {
            List<ProgramSchedule> existing = service.getAllProgramSchedules();
            int perDayCount = 0;
            for (ProgramSchedule s : existing) {
                if (s.getDay() != null && s.getDay().equalsIgnoreCase(day) && s.getChannelName() != null && s.getChannelName().equalsIgnoreCase(channel)) {
                    perDayCount++;
                    boolean sameId = isUpdate && id != null && s.getScheduleId() == id.intValue();
                    boolean overlap = start.before(s.getEndTime()) && end.after(s.getStartTime());
                    if (!sameId && overlap) {
                        JOptionPane.showMessageDialog(this, "Schedule overlaps with an existing entry on this channel and day");
                        return false;
                    }
                }
                if (s.getProgramId() == program.getId() && s.getDay() != null && s.getDay().equalsIgnoreCase(day) &&
                    s.getStartTime().equals(start) && s.getEndTime().equals(end)) {
                    if (!isUpdate || (id == null || s.getScheduleId() != id.intValue())) {
                        JOptionPane.showMessageDialog(this, "Duplicate schedule for the program on the same day and time");
                        return false;
                    }
                }
            }
            if (perDayCount > 500) {
                JOptionPane.showMessageDialog(this, "Too many schedules for the selected day and channel");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Validation error: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    private void exportCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save CSV");
        int res = chooser.showSaveDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            java.io.File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".csv")) {
                file = new java.io.File(file.getAbsolutePath() + ".csv");
            }
            try (java.io.FileWriter fw = new java.io.FileWriter(file)) {
                for (int c = 0; c < model.getColumnCount(); c++) {
                    fw.write(model.getColumnName(c));
                    if (c < model.getColumnCount() - 1) fw.write(",");
                }
                fw.write("\n");
                for (int r = 0; r < model.getRowCount(); r++) {
                    for (int c = 0; c < model.getColumnCount(); c++) {
                        Object val = model.getValueAt(r, c);
                        String s = val == null ? "" : String.valueOf(val).replace("\"", "\"\"");
                        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
                            s = "\"" + s + "\"";
                        }
                        fw.write(s);
                        if (c < model.getColumnCount() - 1) fw.write(",");
                    }
                    fw.write("\n");
                }
                JOptionPane.showMessageDialog(this, "Exported to " + file.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error exporting: " + ex.getMessage());
            }
        }
    }
    
    private void printTable() {
        try {
            table.print(JTable.PrintMode.FIT_WIDTH);
        } catch (java.awt.print.PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Print error: " + ex.getMessage());
        }
    }
}
