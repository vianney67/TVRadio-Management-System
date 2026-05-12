package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import util.Config;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Channel;
import service.TVRadioService;

public class ChannelFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtName, txtFreq, txtType, txtLang;
    private TVRadioService service;

    public ChannelFrame() {
        initComponents();
        initService();
        loadData();
    }

    private void initService() {
        try {
            Registry registry = LocateRegistry.getRegistry(Config.HOST, Config.PORT);
            service = (TVRadioService) registry.lookup("TVRadioService");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage());
        }
    }

    private void initComponents() {
        setTitle("Manage Channels");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formGrid = new JPanel(new GridLayout(4, 2, 10, 10));
        formGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formGrid.setOpaque(true);
        formGrid.setBackground(new java.awt.Color(255, 255, 255, 20));
        
        JLabel lblName = new JLabel("Channel Name:");
        txtName = new JTextField();
        formGrid.add(lblName); formGrid.add(txtName);
        
        JLabel lblType = new JLabel("Type (TV/Radio):");
        txtType = new JTextField();
        formGrid.add(lblType); formGrid.add(txtType);
        
        JLabel lblFreq = new JLabel("Frequency:");
        txtFreq = new JTextField();
        formGrid.add(lblFreq); formGrid.add(txtFreq);
        
        JLabel lblLang = new JLabel("Language:");
        txtLang = new JTextField();
        formGrid.add(lblLang); formGrid.add(txtLang);
        
        java.awt.Font f = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        java.awt.Color labelColor = new java.awt.Color(255,255,255);
        lblName.setFont(f); lblName.setForeground(labelColor);
        lblType.setFont(f); lblType.setForeground(labelColor);
        lblFreq.setFont(f); lblFreq.setForeground(labelColor);
        lblLang.setFont(f); lblLang.setForeground(labelColor);
        txtName.setFont(f); txtType.setFont(f); txtFreq.setFont(f); txtLang.setFont(f);
        javax.swing.border.Border pad = BorderFactory.createEmptyBorder(5,5,5,5);
        javax.swing.border.Border line = BorderFactory.createLineBorder(new java.awt.Color(52,152,219));
        javax.swing.border.Border fieldBorder = BorderFactory.createCompoundBorder(line, pad);
        txtName.setBorder(fieldBorder); txtType.setBorder(fieldBorder); txtFreq.setBorder(fieldBorder); txtLang.setBorder(fieldBorder);
        
        JPanel controls = new JPanel(new GridLayout(1, 5, 10, 10));
        controls.setOpaque(true);
        controls.setBackground(new java.awt.Color(255, 255, 255, 20));
        
        JButton btnAdd = new JButton("Add Channel");
        btnAdd.setBackground(new java.awt.Color(46, 204, 113));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addChannel());
        controls.add(btnAdd);
        
        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteChannel());
        controls.add(btnDelete);
        
        JButton btnUpdate = new JButton("Update Selected");
        btnUpdate.setBackground(new java.awt.Color(52, 152, 219));
        btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateChannel());
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

        model = new DefaultTableModel(new Object[]{"ID", "Name", "Frequency", "Type", "Language"}, 0);
        table = new JTable(model);
        javax.swing.JScrollPane tableScroll = new javax.swing.JScrollPane(table);
        JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
        cardPanel.setBackground(new java.awt.Color(26, 32, 44));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(45, 55, 72), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel title = new javax.swing.JLabel("Channels");
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
            List<Channel> channels = service.getAllChannels();
            for (Channel c : channels) {
                model.addRow(new Object[]{c.getId(), c.getName(), c.getFrequency(), c.getType(), c.getLanguage()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addChannel() {
        try {
            String name = txtName.getText() == null ? "" : txtName.getText().trim();
            String type = txtType.getText() == null ? "" : txtType.getText().trim();
            String freq = txtFreq.getText() == null ? "" : txtFreq.getText().trim();
            String lang = txtLang.getText() == null ? "" : txtLang.getText().trim();
            if (!validateChannel(name, type, freq, lang, false, null)) return;
            Channel c = new Channel();
            c.setName(name);
            c.setFrequency(freq);
            c.setType(type);
            c.setLanguage(lang);
            
            service.addChannel(c);
            JOptionPane.showMessageDialog(this, "Channel Added Successfully");
            loadData();
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding channel");
        }
    }
    
    private void deleteChannel() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            try {
                service.deleteChannel(id);
                JOptionPane.showMessageDialog(this, "Channel Deleted");
                loadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtFreq.setText("");
        txtType.setText("");
        txtLang.setText("");
    }
    
    private void updateChannel() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            String name = txtName.getText() == null ? "" : txtName.getText().trim();
            String type = txtType.getText() == null ? "" : txtType.getText().trim();
            String freq = txtFreq.getText() == null ? "" : txtFreq.getText().trim();
            String lang = txtLang.getText() == null ? "" : txtLang.getText().trim();
            if (!validateChannel(name, type, freq, lang, true, id)) return;
            Channel c = new Channel();
            c.setId(id);
            c.setName(name);
            c.setFrequency(freq);
            c.setType(type);
            c.setLanguage(lang);
            try {
                service.updateChannel(c);
                JOptionPane.showMessageDialog(this, "Channel Updated");
                loadData();
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating channel: " + ex.getMessage());
            }
        }
    }
    
    private boolean validateChannel(String name, String type, String freq, String lang, boolean isUpdate, Integer id) {
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Channel name is required");
            return false;
        }
        if (!name.matches("[A-Za-z0-9 &\\-]{2,80}")) {
            JOptionPane.showMessageDialog(this, "Channel name must be 2-80 valid characters");
            return false;
        }
        if (type == null || !(type.equalsIgnoreCase("TV") || type.equalsIgnoreCase("Radio"))) {
            JOptionPane.showMessageDialog(this, "Type must be 'TV' or 'Radio'");
            return false;
        }
        if (freq.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Frequency is required");
            return false;
        }
        double f;
        try { f = Double.parseDouble(freq.replaceAll("[^0-9.]", "")); } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Frequency must be numeric");
            return false;
        }
        if (type.equalsIgnoreCase("Radio")) {
            if (f < 87.5 || f > 108.0) {
                JOptionPane.showMessageDialog(this, "Radio frequency must be 87.5–108.0");
                return false;
            }
        } else {
            if (f < 40.0 || f > 860.0) {
                JOptionPane.showMessageDialog(this, "TV frequency must be 40–860");
                return false;
            }
        }
        if (lang.isEmpty() || !lang.matches("[A-Za-z]{2,30}")) {
            JOptionPane.showMessageDialog(this, "Language must be 2–30 letters");
            return false;
        }
        try {
            List<Channel> channels = service.getAllChannels();
            int countType = 0;
            int countLang = 0;
            for (Channel c : channels) {
                if (c.getType() != null && c.getType().equalsIgnoreCase(type)) countType++;
                if (c.getLanguage() != null && c.getLanguage().trim().equalsIgnoreCase(lang)) countLang++;
                boolean sameName = c.getName() != null && c.getName().trim().equalsIgnoreCase(name);
                boolean sameFreq = c.getFrequency() != null && c.getFrequency().trim().equalsIgnoreCase(freq);
                if (sameName || sameFreq) {
                    if (!isUpdate || (id == null || c.getId() != id)) {
                        JOptionPane.showMessageDialog(this, "Duplicate channel name or frequency");
                        return false;
                    }
                }
            }
            if (countType > 1000) {
                JOptionPane.showMessageDialog(this, "Too many channels of the selected type");
                return false;
            }
            if (countLang > 5000) {
                JOptionPane.showMessageDialog(this, "Too many channels for the selected language");
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
