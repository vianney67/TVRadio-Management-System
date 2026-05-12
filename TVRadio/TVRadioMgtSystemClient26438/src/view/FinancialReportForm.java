package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.FinancialReport;
import service.TVRadioService;
import util.Config;

public class FinancialReportForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtProgramName;
    private JTextField txtMonth;
    private JTextField txtIncome;
    private JTextField txtExpenses;
    private TVRadioService service;

    public FinancialReportForm() {
        initComponents();
        initService();
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
        setTitle("Financial Reports");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel input = new JPanel(new GridLayout(0, 2, 10, 10));
        input.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        input.setOpaque(true);
        input.setBackground(new java.awt.Color(255, 255, 255, 20));

        JLabel lblProgram = new JLabel("Program Name");
        txtProgramName = new JTextField();
        input.add(lblProgram);
        input.add(txtProgramName);

        JLabel lblMonth = new JLabel("Month (1-12)");
        txtMonth = new JTextField();
        input.add(lblMonth);
        input.add(txtMonth);

        JLabel lblIncome = new JLabel("Income");
        txtIncome = new JTextField();
        input.add(lblIncome);
        input.add(txtIncome);

        JLabel lblExpenses = new JLabel("Expenses");
        txtExpenses = new JTextField();
        input.add(lblExpenses);
        input.add(txtExpenses);

        java.awt.Font f = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        java.awt.Color labelColor = new java.awt.Color(255, 255, 255);
        for (JLabel lbl : new JLabel[] { lblProgram, lblMonth, lblIncome, lblExpenses }) {
            lbl.setFont(f);
            lbl.setForeground(labelColor);
        }
        txtProgramName.setFont(f);
        txtMonth.setFont(f);
        txtIncome.setFont(f);
        txtExpenses.setFont(f);
        javax.swing.border.Border pad = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        javax.swing.border.Border line = BorderFactory.createLineBorder(new java.awt.Color(52, 152, 219));
        javax.swing.border.Border fieldBorder = BorderFactory.createCompoundBorder(line, pad);
        txtProgramName.setBorder(fieldBorder);
        txtMonth.setBorder(fieldBorder);
        txtIncome.setBorder(fieldBorder);
        txtExpenses.setBorder(fieldBorder);

        // Make calculated fields read-only
        txtIncome.setEditable(false);
        txtExpenses.setEditable(false);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBackground(new java.awt.Color(46, 204, 113));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addReport());
        input.add(btnAdd);

        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteReport());
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

        model = new DefaultTableModel(new Object[] { "ID", "Program", "Month", "Income", "Expenses", "Profit/Loss" },
                0);
        table = new JTable(model);
        javax.swing.JScrollPane tableScroll = new javax.swing.JScrollPane(table);
        JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
        cardPanel.setBackground(new java.awt.Color(26, 32, 44));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(45, 55, 72), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        javax.swing.JLabel title = new javax.swing.JLabel("Financial Reports");
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

    private void loadData() {
        try {
            model.setRowCount(0);
            List<FinancialReport> list = service.getAllFinancialReports();
            for (FinancialReport r : list) {
                model.addRow(new Object[] {
                        r.getId(),
                        r.getProgramName(),
                        r.getMonth(),
                        r.getIncome(),
                        r.getExpenses(),
                        r.getProfitLoss()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void addReport() {
        try {
            String programName = txtProgramName.getText() == null ? "" : txtProgramName.getText().trim();
            String monthText = txtMonth.getText() == null ? "" : txtMonth.getText().trim();
            if (!validateReport(programName, monthText, false, null))
                return;
            int month = Integer.parseInt(monthText);
            double income = 0.0;
            double expenses = 0.0;

            if (programName.equalsIgnoreCase("System")) {
                // Whole System Calculation
                for (model.Advertisement a : service.getAllAdvertisements()) {
                    if (a.getAdDate() != null && a.getAdDate().toLocalDate().getMonthValue() == month) {
                        income += a.getCostPaid();
                    }
                }
                for (model.Expense e : service.getAllExpenses()) {
                    if (e.getExpenseDate() != null && e.getExpenseDate().toLocalDate().getMonthValue() == month) {
                        expenses += e.getAmount();
                    }
                }
            } else {
                // Per Program Calculation
                java.util.Set<Integer> programIds = new java.util.HashSet<>();
                for (model.Program p : service.getAllPrograms()) {
                    if (p.getName() != null && p.getName().trim().equalsIgnoreCase(programName)) {
                        programIds.add(p.getId());
                    }
                }
                for (model.Advertisement a : service.getAllAdvertisements()) {
                    if (programIds.contains(a.getProgramId()) && a.getAdDate() != null
                            && a.getAdDate().toLocalDate().getMonthValue() == month) {
                        income += a.getCostPaid();
                    }
                }
                for (model.Expense e : service.getAllExpenses()) {
                    if (programIds.contains(e.getProgramId()) && e.getExpenseDate() != null
                            && e.getExpenseDate().toLocalDate().getMonthValue() == month) {
                        expenses += e.getAmount();
                    }
                }
            }

            FinancialReport r = new FinancialReport();
            r.setProgramName(programName);
            r.setMonth(month);
            r.setIncome(income);
            r.setExpenses(expenses);
            r.setProfitLoss(income - expenses);
            service.addFinancialReport(r);
            JOptionPane.showMessageDialog(this, "Report added");
            clearInputs();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private boolean validateReport(String programName, String monthText, boolean isUpdate, Integer id) {
        if (programName.isEmpty() || !programName.matches("[A-Za-z0-9 &\\-]{2,80}")) {
            JOptionPane.showMessageDialog(this, "Program name must be 2–80 valid characters");
            return false;
        }
        if (!monthText.matches("^(?:[1-9]|1[0-2])$")) {
            JOptionPane.showMessageDialog(this, "Month must be 1–12");
            return false;
        }
        try {
            // Updated: Allow "System" not to exist in Program table
            if (!programName.equalsIgnoreCase("System")) {
                List<model.Program> programs = service.getAllPrograms();
                boolean exists = false;
                for (model.Program p : programs) {
                    if (p.getName() != null && p.getName().trim().equalsIgnoreCase(programName)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    JOptionPane.showMessageDialog(this, "Program must exist (or use 'System' for total)");
                    return false;
                }
            }

            List<FinancialReport> reports = service.getAllFinancialReports();
            int month = Integer.parseInt(monthText);
            int perYearCount = 0;
            for (FinancialReport r : reports) {
                if (r.getProgramName() != null && r.getProgramName().trim().equalsIgnoreCase(programName)) {
                    if (r.getMonth() == month) {
                        if (!isUpdate || (id == null || r.getId() != id.intValue())) {
                            JOptionPane.showMessageDialog(this, "Duplicate report for program and month");
                            return false;
                        }
                    }
                    perYearCount++;
                }
            }
            if (perYearCount > 12) {
                JOptionPane.showMessageDialog(this, "Too many reports for the program");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Validation error: " + e.getMessage());
            return false;
        }
        return true;
    }

    private void deleteReport() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            try {
                service.deleteFinancialReport(id);
                JOptionPane.showMessageDialog(this, "Deleted");
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void clearInputs() {
        txtProgramName.setText("");
        txtMonth.setText("");
        txtIncome.setText("");
        txtExpenses.setText("");
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
                    if (c < model.getColumnCount() - 1)
                        fw.write(",");
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
                        if (c < model.getColumnCount() - 1)
                            fw.write(",");
                    }
                    fw.write("\n");
                }
                JOptionPane.showMessageDialog(this, "Exported to " + file.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error exporting: " + ex.getMessage());
            }
        }
    }

    // NEW: Export to Excel (as HTML)
    private void exportExcel() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Excel");
        int res = chooser.showSaveDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            java.io.File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".xls")) {
                file = new java.io.File(file.getAbsolutePath() + ".xls");
            }
            try (java.io.PrintWriter pw = new java.io.PrintWriter(file)) {
                pw.println("<html><body><table border='1'>");
                // Header
                pw.println("<tr>");
                for (int c = 0; c < model.getColumnCount(); c++) {
                    pw.println("<th>" + model.getColumnName(c) + "</th>");
                }
                pw.println("</tr>");
                // Data
                for (int r = 0; r < model.getRowCount(); r++) {
                    pw.println("<tr>");
                    for (int c = 0; c < model.getColumnCount(); c++) {
                        Object val = model.getValueAt(r, c);
                        String s = val == null ? "" : String.valueOf(val);
                        pw.println("<td>" + s + "</td>");
                    }
                    pw.println("</tr>");
                }
                pw.println("</table></body></html>");
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
