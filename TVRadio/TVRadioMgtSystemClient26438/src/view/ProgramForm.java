package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Channel;
import model.Program;
import service.TVRadioService;
import util.Config;

public class ProgramForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtName, txtDuration;
    private JComboBox<Channel> cmbChannel;
    private TVRadioService service;

    public ProgramForm() {
        initComponents();
        initService();
        loadData();
    }

    private void initService() {
        try {
            Registry registry = LocateRegistry.getRegistry(Config.HOST, Config.PORT);
            service = (TVRadioService) registry.lookup("TVRadioService");
            loadChannels();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage());
        }
    }
    
    private void loadChannels() {
        try {
            List<Channel> channels = service.getAllChannels();
            cmbChannel.removeAllItems();
            for (Channel c : channels) {
                cmbChannel.addItem(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setTitle("Manage Programs");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formGrid = new JPanel(new GridLayout(3, 2, 10, 10));
        formGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formGrid.setOpaque(true);
        formGrid.setBackground(new java.awt.Color(255, 255, 255, 20));
        
        JLabel lblName = new JLabel("Program Name:");
        txtName = new JTextField();
        formGrid.add(lblName); formGrid.add(txtName);
        
        JLabel lblDur = new JLabel("Duration (e.g. 30 mins):");
        txtDuration = new JTextField();
        formGrid.add(lblDur); formGrid.add(txtDuration);
        
        JLabel lblCh = new JLabel("Channel:");
        cmbChannel = new JComboBox<>();
        formGrid.add(lblCh); formGrid.add(cmbChannel);
        
        java.awt.Font f = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        java.awt.Color labelColor = new java.awt.Color(255,255,255);
        lblName.setFont(f); lblName.setForeground(labelColor);
        lblDur.setFont(f); lblDur.setForeground(labelColor);
        lblCh.setFont(f); lblCh.setForeground(labelColor);
        txtName.setFont(f); txtDuration.setFont(f); cmbChannel.setFont(f);
        javax.swing.border.Border pad = BorderFactory.createEmptyBorder(5,5,5,5);
        javax.swing.border.Border line = BorderFactory.createLineBorder(new java.awt.Color(52,152,219));
        javax.swing.border.Border fieldBorder = BorderFactory.createCompoundBorder(line, pad);
        txtName.setBorder(fieldBorder); txtDuration.setBorder(fieldBorder); cmbChannel.setBorder(fieldBorder);
        
        JPanel controls = new JPanel(new GridLayout(1, 5, 10, 10));
        controls.setOpaque(true);
        controls.setBackground(new java.awt.Color(255, 255, 255, 20));
        
        JButton btnAdd = new JButton("Add Program");
        btnAdd.setBackground(new java.awt.Color(46, 204, 113));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addProgram());
        controls.add(btnAdd);
        
        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteProgram());
        controls.add(btnDelete);
        
        JButton btnUpdate = new JButton("Update Selected");
        btnUpdate.setBackground(new java.awt.Color(52, 152, 219));
        btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateProgram());
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

        model = new DefaultTableModel(new Object[]{"ID", "Name", "Duration", "Channel"}, 0);
        table = new JTable(model);
        javax.swing.JScrollPane tableScroll = new javax.swing.JScrollPane(table);
        JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
        cardPanel.setBackground(new java.awt.Color(26, 32, 44));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(45, 55, 72), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel title = new javax.swing.JLabel("Programs");
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
            List<Program> programs = service.getAllPrograms();
            for (Program p : programs) {
                String channelName = (p.getChannel() != null) ? p.getChannel().getName() : "N/A";
                model.addRow(new Object[]{p.getId(), p.getName(), p.getDuration(), channelName});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addProgram() {
        try {
            String name = txtName.getText() == null ? "" : txtName.getText().trim();
            String duration = txtDuration.getText() == null ? "" : txtDuration.getText().trim();
            Channel channel = (Channel) cmbChannel.getSelectedItem();
            if (!validateProgram(name, duration, channel, false, null)) return;
            Program p = new Program();
            p.setName(name);
            p.setDuration(duration);
            p.setChannel(channel);
            if (channel != null) p.setChannelId(channel.getId());
            
            service.addProgram(p);
            JOptionPane.showMessageDialog(this, "Program Added Successfully");
            loadData();
            txtName.setText("");
            txtDuration.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding program");
        }
    }
    
    private void deleteProgram() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            try {
                service.deleteProgram(id);
                JOptionPane.showMessageDialog(this, "Program Deleted");
                loadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateProgram() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            String name = txtName.getText() == null ? "" : txtName.getText().trim();
            String duration = txtDuration.getText() == null ? "" : txtDuration.getText().trim();
            Channel channel = (Channel) cmbChannel.getSelectedItem();
            if (!validateProgram(name, duration, channel, true, id)) return;
            Program p = new Program();
            p.setId(id);
            p.setName(name);
            p.setDuration(duration);
            p.setChannel(channel);
            if (channel != null) p.setChannelId(channel.getId());
            try {
                service.updateProgram(p);
                JOptionPane.showMessageDialog(this, "Program Updated");
                loadData();
                txtName.setText("");
                txtDuration.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating program: " + ex.getMessage());
            }
        }
    }
    
    private boolean validateProgram(String name, String duration, Channel channel, boolean isUpdate, Integer id) {
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Program name is required");
            return false;
        }
        if (!name.matches("[A-Za-z0-9 &\\-]{2,100}")) {
            JOptionPane.showMessageDialog(this, "Program name must be 2-100 chars and contain letters, numbers, spaces, '&' or '-'");
            return false;
        }
        if (name.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Program name cannot be numbers only");
            return false;
        }
        if (duration.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Duration is required");
            return false;
        }
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d{1,3})").matcher(duration);
        int mins = -1;
        if (m.find()) {
            try { mins = Integer.parseInt(m.group(1)); } catch (Exception ignored) {}
        }
        if (mins < 5 || mins > 300) {
            JOptionPane.showMessageDialog(this, "Duration must be between 5 and 300 minutes");
            return false;
        }
        if (mins % 5 != 0) {
            JOptionPane.showMessageDialog(this, "Duration must be a multiple of 5 minutes");
            return false;
        }
        if (channel != null && channel.getType() != null && channel.getType().equalsIgnoreCase("Radio") && mins > 60) {
            JOptionPane.showMessageDialog(this, "Radio programs cannot exceed 60 minutes");
            return false;
        }
        if (channel == null) {
            JOptionPane.showMessageDialog(this, "Select a channel");
            return false;
        }
        try {
            List<Program> existing = service.getAllPrograms();
            int perChannel = 0;
            for (Program ep : existing) {
                if (ep.getChannel() != null && ep.getChannel().getId() == channel.getId()) perChannel++;
                if (ep.getName() != null && ep.getName().trim().equalsIgnoreCase(name)) {
                    if (!isUpdate || (id == null || ep.getId() != id)) {
                        JOptionPane.showMessageDialog(this, "Program name already exists");
                        return false;
                    }
                }
                if (ep.getChannel() != null && ep.getChannel().getId() == channel.getId() &&
                    ep.getName() != null && ep.getName().trim().equalsIgnoreCase(name)) {
                    if (!isUpdate || (id == null || ep.getId() != id)) {
                        JOptionPane.showMessageDialog(this, "Program name already exists for the selected channel");
                        return false;
                    }
                }
            }
            if (perChannel > 200) {
                JOptionPane.showMessageDialog(this, "Too many programs under the selected channel");
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
