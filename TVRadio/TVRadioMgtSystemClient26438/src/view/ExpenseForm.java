package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Expense;
import model.Program;
import service.TVRadioService;
import util.Config;

public class ExpenseForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<Program> cmbProgram;
    private JTextField txtCategory;
    private JTextField txtDescription;
    private JTextField txtAmount;
    private JTextField txtDate;
    private TVRadioService service;

    public ExpenseForm() {
        initComponents();
        initService();
        loadPrograms();
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
        setTitle("Manage Expenses");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel input = new JPanel(new GridLayout(0, 2, 10, 10));
        input.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        input.setOpaque(true);
        input.setBackground(new java.awt.Color(255, 255, 255, 20));

        JLabel lblProgram = new JLabel("Program");
        cmbProgram = new JComboBox<>();
        input.add(lblProgram); input.add(cmbProgram);

        JLabel lblCategory = new JLabel("Category");
        txtCategory = new JTextField();
        input.add(lblCategory); input.add(txtCategory);

        JLabel lblDesc = new JLabel("Description");
        txtDescription = new JTextField();
        input.add(lblDesc); input.add(txtDescription);

        JLabel lblAmount = new JLabel("Amount");
        txtAmount = new JTextField();
        input.add(lblAmount); input.add(txtAmount);

        JLabel lblDate = new JLabel("Date (YYYY-MM-DD)");
        txtDate = new JTextField();
        JPanel datePanel = new JPanel(new BorderLayout(5,0));
        datePanel.add(txtDate, BorderLayout.CENTER);
        JButton btnPickDate = new JButton("Pick Date");
        btnPickDate.addActionListener(e -> pickDateInto(txtDate));
        datePanel.add(btnPickDate, BorderLayout.EAST);
        input.add(lblDate); input.add(datePanel);
        
        java.awt.Font f = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        java.awt.Color labelColor = new java.awt.Color(255,255,255);
        for (JLabel lbl : new JLabel[]{lblProgram,lblCategory,lblDesc,lblAmount,lblDate}) { lbl.setFont(f); lbl.setForeground(labelColor); }
        txtCategory.setFont(f); txtDescription.setFont(f); txtAmount.setFont(f); txtDate.setFont(f); cmbProgram.setFont(f);
        javax.swing.border.Border pad = BorderFactory.createEmptyBorder(5,5,5,5);
        javax.swing.border.Border line = BorderFactory.createLineBorder(new java.awt.Color(52,152,219));
        javax.swing.border.Border fieldBorder = BorderFactory.createCompoundBorder(line, pad);
        txtCategory.setBorder(fieldBorder); txtDescription.setBorder(fieldBorder); txtAmount.setBorder(fieldBorder); txtDate.setBorder(fieldBorder); cmbProgram.setBorder(fieldBorder);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBackground(new java.awt.Color(46, 204, 113));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addExpense());
        input.add(btnAdd);

        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteExpense());
        input.add(btnDelete);
        
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
        
        JButton btnUpdate = new JButton("Update Selected");
        btnUpdate.setBackground(new java.awt.Color(52, 152, 219));
        btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateExpense());
        input.add(btnUpdate);

        

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

        model = new DefaultTableModel(new Object[]{"ID", "Program", "Category", "Description", "Amount", "Date"}, 0);
        table = new JTable(model);
        javax.swing.JScrollPane tableScroll = new javax.swing.JScrollPane(table);
        JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
        cardPanel.setBackground(new java.awt.Color(26, 32, 44));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(45, 55, 72), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        javax.swing.JLabel title = new javax.swing.JLabel("Expenses");
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

    private void loadPrograms() {
        try {
            cmbProgram.removeAllItems();
            List<Program> programs = service.getAllPrograms();
            for (Program p : programs) cmbProgram.addItem(p);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading programs: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            model.setRowCount(0);
            List<Expense> list = service.getAllExpenses();
            for (Expense ex : list) {
                String programName = "";
                for (int i = 0; i < cmbProgram.getItemCount(); i++) {
                    Program p = cmbProgram.getItemAt(i);
                    if (p.getId() == ex.getProgramId()) { programName = p.getName(); break; }
                }
                model.addRow(new Object[]{
                    ex.getExpenseId(),
                    programName,
                    ex.getCategory(),
                    ex.getDescription(),
                    ex.getAmount(),
                    ex.getExpenseDate()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void addExpense() {
        try {
            Program p = (Program) cmbProgram.getSelectedItem();
            String category = txtCategory.getText() == null ? "" : txtCategory.getText().trim();
            String description = txtDescription.getText() == null ? "" : txtDescription.getText().trim();
            String amountText = txtAmount.getText() == null ? "" : txtAmount.getText().trim();
            String dateText = txtDate.getText() == null ? "" : txtDate.getText().trim();
            if (!validateExpense(p, category, description, amountText, dateText, false, null)) return;
            double amount = Double.parseDouble(amountText);
            Date date = Date.valueOf(dateText);
            Expense e = new Expense();
            e.setProgramId(p.getId());
            e.setCategory(category);
            e.setDescription(description);
            e.setAmount(amount);
            e.setExpenseDate(date);
            service.addExpense(e);
            JOptionPane.showMessageDialog(this, "Expense added");
            clearInputs();
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteExpense() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            try {
                service.deleteExpense(id);
                JOptionPane.showMessageDialog(this, "Deleted");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void clearInputs() {
        txtCategory.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
        txtDate.setText("");
    }
    
    private void updateExpense() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            Program p = (Program) cmbProgram.getSelectedItem();
            String category = txtCategory.getText() == null ? "" : txtCategory.getText().trim();
            String description = txtDescription.getText() == null ? "" : txtDescription.getText().trim();
            String amountText = txtAmount.getText() == null ? "" : txtAmount.getText().trim();
            String dateText = txtDate.getText() == null ? "" : txtDate.getText().trim();
            if (!validateExpense(p, category, description, amountText, dateText, true, id)) return;
            Expense e = new Expense();
            e.setExpenseId(id);
            e.setProgramId(p.getId());
            e.setCategory(category);
            e.setDescription(description);
            e.setAmount(Double.parseDouble(amountText));
            e.setExpenseDate(Date.valueOf(dateText));
            try {
                service.updateExpense(e);
                JOptionPane.showMessageDialog(this, "Expense Updated");
                loadData();
                clearInputs();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating: " + ex.getMessage());
            }
        }
    }
    
    private boolean validateExpense(Program program, String category, String description, String amountText, String dateText, boolean isUpdate, Integer id) {
        if (program == null) {
            JOptionPane.showMessageDialog(this, "Select program");
            return false;
        }
        if (category.isEmpty() || !category.matches("[A-Za-z &\\-]{3,40}")) {
            JOptionPane.showMessageDialog(this, "Category must be 3–40 letters");
            return false;
        }
        if (category.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Category cannot be numbers only");
            return false;
        }
        if (description.isEmpty() || description.length() > 200) {
            JOptionPane.showMessageDialog(this, "Description must be 1–200 characters");
            return false;
        }
        if (!amountText.matches("^\\d+(\\.\\d{1,2})?$")) {
            JOptionPane.showMessageDialog(this, "Amount must be a number with up to 2 decimals");
            return false;
        }
        double amount;
        try { amount = Double.parseDouble(amountText); } catch (Exception ex) { amount = -1; }
        if (amount <= 0 || amount > 10000000) {
            JOptionPane.showMessageDialog(this, "Amount must be between 1 and 10,000,000");
            return false;
        }
        if (!dateText.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            JOptionPane.showMessageDialog(this, "Date must be YYYY-MM-DD");
            return false;
        }
        java.sql.Date dt;
        try { dt = java.sql.Date.valueOf(dateText); } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format");
            return false;
        }
        java.time.LocalDate today = java.time.LocalDate.now();
        if (dt.toLocalDate().isAfter(today)) {
            JOptionPane.showMessageDialog(this, "Expense date cannot be in the future");
            return false;
        }
        try {
            List<Expense> existing = service.getAllExpenses();
            int perDayCount = 0;
            double monthTotal = 0.0;
            for (Expense e : existing) {
                if (e.getProgramId() == program.getId()) {
                    if (e.getExpenseDate() != null && e.getExpenseDate().toLocalDate().getYear() == dt.toLocalDate().getYear() &&
                        e.getExpenseDate().toLocalDate().getMonthValue() == dt.toLocalDate().getMonthValue()) {
                        monthTotal += e.getAmount();
                    }
                    if (e.getExpenseDate() != null && e.getExpenseDate().equals(dt)) {
                        perDayCount++;
                        boolean sameId = isUpdate && id != null && e.getExpenseId() == id.intValue();
                        boolean dupDesc = e.getDescription() != null && e.getDescription().trim().equalsIgnoreCase(description);
                        boolean dupCat = e.getCategory() != null && e.getCategory().trim().equalsIgnoreCase(category);
                        if (!sameId && dupDesc && dupCat) {
                            JOptionPane.showMessageDialog(this, "Duplicate expense description and category for the program and date");
                            return false;
                        }
                    }
                }
            }
            if (perDayCount > 100) {
                JOptionPane.showMessageDialog(this, "Too many expenses for the program on the selected date");
                return false;
            }
            if (monthTotal + amount > 10000000) {
                JOptionPane.showMessageDialog(this, "Monthly expenses threshold exceeded for the program");
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
    
    private void pickDateInto(JTextField target) {
        JSpinner spinner = new JSpinner(new SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        int res = JOptionPane.showConfirmDialog(this, spinner, "Select Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd");
            target.setText(fmt.format((java.util.Date) spinner.getValue()));
        }
    }
}
