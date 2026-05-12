package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Equipment;
import service.TVRadioService;
import util.Config;

public class EquipmentForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtName, txtType;
    private JComboBox<String> cmbStatus;
    private TVRadioService service;

    public EquipmentForm() {
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
        setTitle("Manage Equipment");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formGrid = new JPanel(new GridLayout(3, 2, 10, 10));
        formGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formGrid.setOpaque(true);
        formGrid.setBackground(new java.awt.Color(255, 255, 255, 20));
        
        JLabel lblName = new JLabel("Name:");
        txtName = new JTextField();
        formGrid.add(lblName); formGrid.add(txtName);
        
        JLabel lblType = new JLabel("Type:");
        txtType = new JTextField();
        formGrid.add(lblType); formGrid.add(txtType);
        
        JLabel lblStatus = new JLabel("Status:");
        cmbStatus = new JComboBox<>(new String[]{"Available", "In Use", "Maintenance"});
        formGrid.add(lblStatus); formGrid.add(cmbStatus);
        
        java.awt.Font f = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        java.awt.Color labelColor = new java.awt.Color(255,255,255);
        lblName.setFont(f); lblName.setForeground(labelColor);
        lblType.setFont(f); lblType.setForeground(labelColor);
        lblStatus.setFont(f); lblStatus.setForeground(labelColor);
        txtName.setFont(f); txtType.setFont(f); cmbStatus.setFont(f);
        javax.swing.border.Border pad = BorderFactory.createEmptyBorder(5,5,5,5);
        javax.swing.border.Border line = BorderFactory.createLineBorder(new java.awt.Color(52,152,219));
        javax.swing.border.Border fieldBorder = BorderFactory.createCompoundBorder(line, pad);
        txtName.setBorder(fieldBorder); txtType.setBorder(fieldBorder); cmbStatus.setBorder(fieldBorder);
        
        JPanel controls = new JPanel(new GridLayout(1, 5, 10, 10));
        controls.setOpaque(true);
        controls.setBackground(new java.awt.Color(255, 255, 255, 20));
        
        JButton btnAdd = new JButton("Add Equipment");
        btnAdd.setBackground(new java.awt.Color(46, 204, 113));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addEquipment());
        controls.add(btnAdd);
        
        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteEquipment());
        controls.add(btnDelete);
        
        JButton btnUpdate = new JButton("Update Selected");
        btnUpdate.setBackground(new java.awt.Color(52, 152, 219));
        btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateEquipment());
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

        model = new DefaultTableModel(new Object[]{"ID", "Name", "Type", "Status"}, 0);
        table = new JTable(model);
        javax.swing.JScrollPane tableScroll = new javax.swing.JScrollPane(table);
        JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
        cardPanel.setBackground(new java.awt.Color(26, 32, 44));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(45, 55, 72), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel title = new javax.swing.JLabel("Equipment");
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
            List<Equipment> equipmentList = service.getAllEquipment();
            for (Equipment e : equipmentList) {
                model.addRow(new Object[]{e.getId(), e.getName(), e.getType(), e.getStatus()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addEquipment() {
        try {
            String name = txtName.getText() == null ? "" : txtName.getText().trim();
            String type = txtType.getText() == null ? "" : txtType.getText().trim();
            String status = (String) cmbStatus.getSelectedItem();
            if (!validateEquipment(name, type, status, false, null)) return;
            Equipment eq = new Equipment();
            eq.setName(name);
            eq.setType(type);
            eq.setStatus(status);
            service.addEquipment(eq);
            JOptionPane.showMessageDialog(this, "Equipment Added Successfully");
            loadData();
            txtName.setText("");
            txtType.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding equipment: " + e.getMessage());
        }
    }
    
    private void deleteEquipment() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            try {
                service.deleteEquipment(id);
                JOptionPane.showMessageDialog(this, "Equipment Deleted");
                loadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateEquipment() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            String name = txtName.getText() == null ? "" : txtName.getText().trim();
            String type = txtType.getText() == null ? "" : txtType.getText().trim();
            String status = (String) cmbStatus.getSelectedItem();
            if (!validateEquipment(name, type, status, true, id)) return;
            model.Equipment eq = new model.Equipment();
            eq.setId(id);
            eq.setName(name);
            eq.setType(type);
            eq.setStatus(status);
            try {
                service.updateEquipment(eq);
                JOptionPane.showMessageDialog(this, "Equipment Updated");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating equipment: " + ex.getMessage());
            }
        }
    }
    
    private boolean validateEquipment(String name, String type, String status, boolean isUpdate, Integer id) {
        if (name.isEmpty() || !name.matches("[A-Za-z0-9 &\\-]{2,60}")) {
            JOptionPane.showMessageDialog(this, "Name must be 2–60 valid characters");
            return false;
        }
        if (name.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Name cannot be numbers only");
            return false;
        }
        if (name.contains("  ")) {
            JOptionPane.showMessageDialog(this, "Name must not contain consecutive spaces");
            return false;
        }
        if (type.isEmpty() || !type.matches("[A-Za-z ]{2,40}")) {
            JOptionPane.showMessageDialog(this, "Type must be 2–40 letters");
            return false;
        }
        if (status == null || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Status is required");
            return false;
        }
        if (!(status.equals("Available") || status.equals("In Use") || status.equals("Maintenance"))) {
            JOptionPane.showMessageDialog(this, "Invalid status selected");
            return false;
        }
        try {
            List<Equipment> list = service.getAllEquipment();
            int inUseCount = 0;
            for (Equipment e : list) {
                if (e.getStatus() != null && e.getStatus().equalsIgnoreCase("In Use")) inUseCount++;
                boolean dupName = e.getName() != null && e.getName().trim().equalsIgnoreCase(name);
                if (dupName) {
                    if (!isUpdate || (id == null || e.getId() != id.intValue())) {
                        JOptionPane.showMessageDialog(this, "Duplicate equipment name");
                        return false;
                    }
                }
            }
            if (inUseCount > 5000) {
                JOptionPane.showMessageDialog(this, "Too many equipment items in use");
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
