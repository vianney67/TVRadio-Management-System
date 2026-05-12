package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Employee;
import service.TVRadioService;
import util.Config;

public class EmployeeForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtFirstName, txtLastName, txtEmail, txtPhone, txtRole;
    private TVRadioService service;

    public EmployeeForm() {
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
        setTitle("Manage Employees");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formGrid = new JPanel(new GridLayout(5, 2, 10, 10));
        formGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formGrid.setOpaque(true);
        formGrid.setBackground(new java.awt.Color(255, 255, 255, 20));
        
        formGrid.add(new JLabel("First Name:"));
        txtFirstName = new JTextField();
        formGrid.add(txtFirstName);
        
        formGrid.add(new JLabel("Last Name:"));
        txtLastName = new JTextField();
        formGrid.add(txtLastName);
        
        formGrid.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formGrid.add(txtEmail);
        
        formGrid.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        formGrid.add(txtPhone);
        
        formGrid.add(new JLabel("Role:"));
        txtRole = new JTextField();
        formGrid.add(txtRole);
        
        JPanel controls = new JPanel(new GridLayout(1, 5, 10, 10));
        controls.setOpaque(true);
        controls.setBackground(new java.awt.Color(255, 255, 255, 20));
        
        JButton btnAdd = new JButton("Add Employee");
        btnAdd.setBackground(new java.awt.Color(46, 204, 113));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addEmployee());
        controls.add(btnAdd);
        
        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteEmployee());
        controls.add(btnDelete);
        
        JButton btnUpdate = new JButton("Update Selected");
        btnUpdate.setBackground(new java.awt.Color(52, 152, 219));
        btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateEmployee());
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

        model = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Email", "Phone", "Role"}, 0);
        table = new JTable(model);
        javax.swing.JScrollPane tableScroll = new javax.swing.JScrollPane(table);
        JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
        cardPanel.setBackground(new java.awt.Color(26, 32, 44));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(45, 55, 72), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel title = new javax.swing.JLabel("Employees");
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
            List<Employee> employees = service.getAllEmployees();
            for (Employee e : employees) {
                model.addRow(new Object[]{e.getId(), e.getFirstName(), e.getLastName(), e.getEmail(), e.getPhone(), e.getRole()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addEmployee() {
        try {
            String first = txtFirstName.getText() == null ? "" : txtFirstName.getText().trim();
            String last = txtLastName.getText() == null ? "" : txtLastName.getText().trim();
            String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
            String phone = txtPhone.getText() == null ? "" : txtPhone.getText().trim();
            String role = txtRole.getText() == null ? "" : txtRole.getText().trim();
            if (!validateEmployee(first, last, email, phone, role, false, null)) return;
            Employee emp = new Employee();
            emp.setFirstName(first);
            emp.setLastName(last);
            emp.setEmail(email);
            emp.setPhone(phone);
            emp.setRole(role);
            
            service.addEmployee(emp);
            JOptionPane.showMessageDialog(this, "Employee Added Successfully");
            loadData();
            txtFirstName.setText("");
            txtLastName.setText("");
            txtEmail.setText("");
            txtPhone.setText("");
            txtRole.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding employee");
        }
    }
    
    private boolean validateEmployee(String first, String last, String email, String phone, String role, boolean isUpdate, Integer id) {
        if (!first.matches("[A-Za-z\\-]{2,40}")) {
            JOptionPane.showMessageDialog(this, "First name must be 2–40 letters");
            return false;
        }
        if (!last.matches("[A-Za-z\\-]{2,40}")) {
            JOptionPane.showMessageDialog(this, "Last name must be 2–40 letters");
            return false;
        }
        if (!email.matches("^[A-Za-z0-9._%+-]{3,64}@[A-Za-z0-9.-]{3,64}\\.[A-Za-z]{2,10}$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format");
            return false;
        }
        if (!phone.matches("^\\+?[0-9]{7,15}$")) {
            JOptionPane.showMessageDialog(this, "Phone must be 7–15 digits");
            return false;
        }
        if (!role.matches("[A-Za-z ]{2,30}")) {
            JOptionPane.showMessageDialog(this, "Role must be 2–30 letters");
            return false;
        }
        try {
            List<Employee> employees = service.getAllEmployees();
            int adminCount = 0;
            int perRoleCount = 0;
            for (Employee e : employees) {
                if (e.getRole() != null && e.getRole().equalsIgnoreCase("Admin")) adminCount++;
                if (e.getRole() != null && role != null && e.getRole().equalsIgnoreCase(role)) perRoleCount++;
                boolean dupEmail = e.getEmail() != null && e.getEmail().equalsIgnoreCase(email);
                boolean dupPhone = e.getPhone() != null && e.getPhone().equalsIgnoreCase(phone);
                if (dupEmail || dupPhone) {
                    if (!isUpdate || (id == null || e.getId() != id)) {
                        JOptionPane.showMessageDialog(this, "Duplicate email or phone");
                        return false;
                    }
                }
                boolean dupName = e.getFirstName() != null && e.getLastName() != null &&
                                  e.getFirstName().trim().equalsIgnoreCase(first) &&
                                  e.getLastName().trim().equalsIgnoreCase(last);
                if (dupName) {
                    if (!isUpdate || (id == null || e.getId() != id)) {
                        JOptionPane.showMessageDialog(this, "Duplicate employee name");
                        return false;
                    }
                }
            }
            if (role.equalsIgnoreCase("Admin") && adminCount >= 5) {
                JOptionPane.showMessageDialog(this, "Admin limit reached");
                return false;
            }
            if (perRoleCount > 1000) {
                JOptionPane.showMessageDialog(this, "Too many employees for the selected role");
                return false;
            }
            if (role.equalsIgnoreCase("Admin") && email.toLowerCase().endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(this, "Admin must not use personal email domains");
                return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Validation error: " + ex.getMessage());
            return false;
        }
        return true;
    }
    private void deleteEmployee() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            try {
                service.deleteEmployee(id);
                JOptionPane.showMessageDialog(this, "Employee Deleted");
                loadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateEmployee() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            String first = txtFirstName.getText() == null ? "" : txtFirstName.getText().trim();
            String last = txtLastName.getText() == null ? "" : txtLastName.getText().trim();
            String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
            String phone = txtPhone.getText() == null ? "" : txtPhone.getText().trim();
            String role = txtRole.getText() == null ? "" : txtRole.getText().trim();
            if (!validateEmployee(first, last, email, phone, role, true, id)) return;
            Employee emp = new Employee();
            emp.setId(id);
            emp.setFirstName(first);
            emp.setLastName(last);
            emp.setEmail(email);
            emp.setPhone(phone);
            emp.setRole(role);
            try {
                service.updateEmployee(emp);
                JOptionPane.showMessageDialog(this, "Employee Updated");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating employee: " + ex.getMessage());
            }
        }
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
