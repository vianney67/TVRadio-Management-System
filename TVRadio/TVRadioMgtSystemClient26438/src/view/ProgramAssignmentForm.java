package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Channel;
import model.Employee;
import model.Program;
import model.ProgramAssignment;
import service.TVRadioService;
import util.Config;

public class ProgramAssignmentForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<Channel> cmbChannel;
    private JComboBox<Program> cmbProgram;
    private JComboBox<Employee> cmbEmployee;
    private JTextField txtRole;
    private TVRadioService service;

    public ProgramAssignmentForm() {
        initComponents();
        initService();
        loadRefs();
        loadData();
    }

    private void initService() {
        try {
            Registry registry = LocateRegistry.getRegistry(Config.HOST, Config.PORT);
            service = (TVRadioService) registry.lookup("TVRadioService");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage());
        }
    }

    private void initComponents() {
        setTitle("Program Assignments");
        setSize(850, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel input = new JPanel(new GridLayout(0, 2, 10, 10));
        input.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        input.setOpaque(true);
        input.setBackground(new java.awt.Color(44, 62, 80));

        JLabel lblChannel = new JLabel("Channel");
        cmbChannel = new JComboBox<>();
        input.add(lblChannel); input.add(cmbChannel);

        JLabel lblProgram = new JLabel("Program");
        cmbProgram = new JComboBox<>();
        input.add(lblProgram); input.add(cmbProgram);

        JLabel lblEmployee = new JLabel("Employee");
        cmbEmployee = new JComboBox<>();
        input.add(lblEmployee); input.add(cmbEmployee);

        JLabel lblRole = new JLabel("Assigned Role");
        txtRole = new JTextField();
        input.add(lblRole); input.add(txtRole);
        
        cmbProgram.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                Object o = e.getItem();
                if (o instanceof Program) {
                    Program p = (Program) o;
                    if (p.getChannel() != null) {
                        int chId = p.getChannel().getId();
                        for (int i = 0; i < cmbChannel.getItemCount(); i++) {
                            Channel c = cmbChannel.getItemAt(i);
                            if (c.getId() == chId) {
                                cmbChannel.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                }
            }
        });
        
        java.awt.Font f = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        java.awt.Color labelColor = new java.awt.Color(255,255,255);
        for (JLabel lbl : new JLabel[]{lblChannel,lblProgram,lblEmployee,lblRole}) { lbl.setFont(f); lbl.setForeground(labelColor); }
        cmbChannel.setFont(f); cmbProgram.setFont(f); cmbEmployee.setFont(f); txtRole.setFont(f);
        javax.swing.border.Border pad = BorderFactory.createEmptyBorder(5,5,5,5);
        javax.swing.border.Border line = BorderFactory.createLineBorder(new java.awt.Color(52,152,219));
        javax.swing.border.Border fieldBorder = BorderFactory.createCompoundBorder(line, pad);
        cmbChannel.setBorder(fieldBorder); cmbProgram.setBorder(fieldBorder); cmbEmployee.setBorder(fieldBorder); txtRole.setBorder(fieldBorder);

        JButton btnAdd = new JButton("Assign");
        btnAdd.setBackground(new java.awt.Color(46, 204, 113));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addAssignment());
        input.add(btnAdd);

        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteAssignment());
        input.add(btnDelete);
        
        JButton btnUpdate = new JButton("Update Selected");
        btnUpdate.setBackground(new java.awt.Color(52, 152, 219));
        btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateAssignment());
        input.add(btnUpdate);

        JButton btnExport = new JButton("Export CSV");
        btnExport.setBackground(new java.awt.Color(26, 188, 156));
        btnExport.setForeground(java.awt.Color.WHITE);
        btnExport.setFocusPainted(false);
        btnExport.addActionListener(e -> exportCSV());
        input.add(btnExport);
        
        JButton btnPrint = new JButton("Print Table");
        btnPrint.setBackground(new java.awt.Color(127, 140, 141));
        btnPrint.setForeground(java.awt.Color.WHITE);
        btnPrint.setFocusPainted(false);
        btnPrint.addActionListener(e -> printTable());
        input.add(btnPrint);
        
        

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

        model = new DefaultTableModel(new Object[]{"ID", "Channel", "Program", "Employee", "Role"}, 0);
        table = new JTable(model);
        javax.swing.JScrollPane tableScroll = new javax.swing.JScrollPane(table);
        JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
        cardPanel.setBackground(new java.awt.Color(26, 32, 44));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(45, 55, 72), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel title = new javax.swing.JLabel("Program Assignments");
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setForeground(java.awt.Color.WHITE);
        title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        cardPanel.add(title, BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setOpaque(false);
        contentPanel.add(input, BorderLayout.NORTH);
        contentPanel.add(tableScroll, BorderLayout.CENTER);
        cardPanel.add(contentPanel, BorderLayout.CENTER);
        bg.add(cardPanel, BorderLayout.CENTER);
    }

    private void loadRefs() {
        try {
            cmbChannel.removeAllItems();
            for (Channel c : service.getAllChannels()) cmbChannel.addItem(c);
            cmbProgram.removeAllItems();
            for (Program p : service.getAllPrograms()) cmbProgram.addItem(p);
            cmbEmployee.removeAllItems();
            for (Employee e : service.getAllEmployees()) cmbEmployee.addItem(e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading references: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            model.setRowCount(0);
            List<ProgramAssignment> list = service.getAllProgramAssignments();
            for (ProgramAssignment pa : list) {
                String channelName = "";
                for (int i = 0; i < cmbChannel.getItemCount(); i++) {
                    Channel c = cmbChannel.getItemAt(i);
                    if (c.getId() == pa.getChannelId()) { channelName = c.getName(); break; }
                }
                String programName = "";
                for (int i = 0; i < cmbProgram.getItemCount(); i++) {
                    Program p = cmbProgram.getItemAt(i);
                    if (p.getId() == pa.getProgramId()) { programName = p.getName(); break; }
                }
                String employeeName = "";
                for (int i = 0; i < cmbEmployee.getItemCount(); i++) {
                    Employee e = cmbEmployee.getItemAt(i);
                    String full = e.getFirstName() + " " + e.getLastName();
                    if (e.getId() == pa.getEmployeeId()) { employeeName = full; break; }
                }
                model.addRow(new Object[]{
                    pa.getAssignmentId(),
                    channelName,
                    programName,
                    employeeName,
                    pa.getAssignedRole()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void addAssignment() {
        try {
            Channel c = (Channel) cmbChannel.getSelectedItem();
            Program p = (Program) cmbProgram.getSelectedItem();
            Employee e = (Employee) cmbEmployee.getSelectedItem();
            String role = txtRole.getText() == null ? "" : txtRole.getText().trim();
            if (!validateAssignment(c, p, e, role, false, null)) return;
            ProgramAssignment pa = new ProgramAssignment();
            pa.setChannelId(c.getId());
            pa.setProgramId(p.getId());
            pa.setEmployeeId(e.getId());
            pa.setAssignedRole(role);
            service.addProgramAssignment(pa);
            JOptionPane.showMessageDialog(this, "Assignment added");
            txtRole.setText("");
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteAssignment() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            try {
                service.deleteProgramAssignment(id);
                JOptionPane.showMessageDialog(this, "Deleted");
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    
    private void updateAssignment() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            Channel c = (Channel) cmbChannel.getSelectedItem();
            Program p = (Program) cmbProgram.getSelectedItem();
            Employee e = (Employee) cmbEmployee.getSelectedItem();
            String role = txtRole.getText() == null ? "" : txtRole.getText().trim();
            if (!validateAssignment(c, p, e, role, true, id)) return;
            ProgramAssignment pa = new ProgramAssignment();
            pa.setAssignmentId(id);
            pa.setChannelId(c.getId());
            pa.setProgramId(p.getId());
            pa.setEmployeeId(e.getId());
            pa.setAssignedRole(role);
            try {
                service.updateProgramAssignment(pa);
                JOptionPane.showMessageDialog(this, "Assignment Updated");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating: " + ex.getMessage());
            }
        }
    }
    
    private boolean validateAssignment(Channel channel, Program program, Employee employee, String role, boolean isUpdate, Integer id) {
        if (channel == null) {
            JOptionPane.showMessageDialog(this, "Select a channel");
            return false;
        }
        if (program == null) {
            JOptionPane.showMessageDialog(this, "Select a program");
            return false;
        }
        if (employee == null) {
            JOptionPane.showMessageDialog(this, "Select an employee");
            return false;
        }
        if (program.getChannel() != null && program.getChannel().getId() != 0) {
            if (channel.getId() != program.getChannel().getId()) {
                JOptionPane.showMessageDialog(this, "Selected channel must match the program's channel");
                return false;
            }
        }
        if (role.isEmpty() || !role.matches("[A-Za-z ]{2,40}")) {
            JOptionPane.showMessageDialog(this, "Role must be 2–40 letters");
            return false;
        }
        if (!role.equals(role.trim())) {
            JOptionPane.showMessageDialog(this, "Role must not start or end with spaces");
            return false;
        }
        if (role.contains("  ")) {
            JOptionPane.showMessageDialog(this, "Role must not contain consecutive spaces");
            return false;
        }
        try {
            List<ProgramAssignment> existing = service.getAllProgramAssignments();
            int perProgram = 0;
            int perEmployee = 0;
            for (ProgramAssignment pa : existing) {
                if (pa.getProgramId() == program.getId()) perProgram++;
                if (pa.getEmployeeId() == employee.getId()) perEmployee++;
                boolean dup = pa.getProgramId() == program.getId() && pa.getEmployeeId() == employee.getId();
                if (dup) {
                    if (!isUpdate || (id == null || pa.getAssignmentId() != id.intValue())) {
                        JOptionPane.showMessageDialog(this, "Employee already assigned to this program");
                        return false;
                    }
                }
            }
            if (perProgram > 500) {
                JOptionPane.showMessageDialog(this, "Too many assignments for the selected program");
                return false;
            }
            if (perEmployee > 20) {
                JOptionPane.showMessageDialog(this, "Employee has too many assignments");
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
