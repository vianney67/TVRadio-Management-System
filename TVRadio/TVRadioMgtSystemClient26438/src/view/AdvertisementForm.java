package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Advertisement;
import model.Channel;
import model.Program;
import service.TVRadioService;
import util.Config;

public class AdvertisementForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtCompany;
    private JComboBox<Channel> cmbChannel;
    private JComboBox<Program> cmbProgram;
    private JTextField txtCostPaid;
    private JTextField txtDuration;
    private JTextField txtDate;
    private JComboBox<String> cmbPaymentStatus;
    private TVRadioService service;

    public AdvertisementForm() {
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
        setTitle("Manage Advertisements");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel input = new JPanel(new GridLayout(0, 2, 10, 10));
        input.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        input.setOpaque(true);
        input.setBackground(new java.awt.Color(255, 255, 255, 20));

        JLabel lblCompany = new JLabel("Company");
        txtCompany = new JTextField();
        input.add(lblCompany);
        input.add(txtCompany);

        JLabel lblChannel = new JLabel("Channel");
        cmbChannel = new JComboBox<>();
        input.add(lblChannel);
        input.add(cmbChannel);

        JLabel lblProgram = new JLabel("Program");
        cmbProgram = new JComboBox<>();
        input.add(lblProgram);
        input.add(cmbProgram);

        JLabel lblCost = new JLabel("Cost Paid");
        txtCostPaid = new JTextField();
        input.add(lblCost);
        input.add(txtCostPaid);

        JLabel lblDuration = new JLabel("Duration (hours)");
        txtDuration = new JTextField();
        input.add(lblDuration);
        input.add(txtDuration);

        JLabel lblDate = new JLabel("Date (YYYY-MM-DD)");
        txtDate = new JTextField();
        JPanel datePanel = new JPanel(new BorderLayout(5, 0));
        datePanel.add(txtDate, BorderLayout.CENTER);
        JButton btnPickDate = new JButton("Pick Date");
        btnPickDate.addActionListener(e -> pickDateInto(txtDate));
        datePanel.add(btnPickDate, BorderLayout.EAST);
        input.add(lblDate);
        input.add(datePanel);

        JLabel lblStatus = new JLabel("Payment Status");
        cmbPaymentStatus = new JComboBox<>(new String[] { "Pending", "Paid", "Overdue" });
        input.add(lblStatus);
        input.add(cmbPaymentStatus);

        java.awt.Font f = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        java.awt.Color labelColor = new java.awt.Color(255, 255, 255);
        for (JLabel lbl : new JLabel[] { lblCompany, lblChannel, lblProgram, lblCost, lblDuration, lblDate,
                lblStatus }) {
            lbl.setFont(f);
            lbl.setForeground(labelColor);
        }
        txtCompany.setFont(f);
        txtCostPaid.setFont(f);
        txtDuration.setFont(f);
        txtDate.setFont(f);
        cmbChannel.setFont(f);
        cmbProgram.setFont(f);
        cmbPaymentStatus.setFont(f);
        javax.swing.border.Border pad = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        javax.swing.border.Border line = BorderFactory.createLineBorder(new java.awt.Color(52, 152, 219));
        javax.swing.border.Border fieldBorder = BorderFactory.createCompoundBorder(line, pad);
        txtCompany.setBorder(fieldBorder);
        txtCostPaid.setBorder(fieldBorder);
        txtDuration.setBorder(fieldBorder);
        txtDate.setBorder(fieldBorder);
        cmbChannel.setBorder(fieldBorder);
        cmbProgram.setBorder(fieldBorder);
        cmbPaymentStatus.setBorder(fieldBorder);

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

        JButton btnAdd = new JButton("Add");
        btnAdd.setBackground(new java.awt.Color(46, 204, 113));
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addAdvert());
        input.add(btnAdd);

        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteAdvert());
        input.add(btnDelete);

        JButton btnUpdate = new JButton("Update Selected");
        btnUpdate.setBackground(new java.awt.Color(52, 152, 219));
        btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateAdvert());
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

        model = new DefaultTableModel(
                new Object[] { "ID", "Company", "Channel", "Program", "Cost", "Duration", "Date", "Status" }, 0);
        table = new JTable(model);

        javax.swing.JScrollPane tableScroll = new javax.swing.JScrollPane(table);
        JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
        cardPanel.setBackground(new java.awt.Color(26, 32, 44));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(45, 55, 72), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        javax.swing.JLabel title = new javax.swing.JLabel("Advertisements");
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
            List<Channel> channels = service.getAllChannels();
            for (Channel c : channels)
                cmbChannel.addItem(c);

            cmbProgram.removeAllItems();
            List<Program> programs = service.getAllPrograms();
            for (Program p : programs)
                cmbProgram.addItem(p);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading references: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            model.setRowCount(0);
            List<Advertisement> ads = service.getAllAdvertisements();
            for (Advertisement a : ads) {
                String channelName = "";
                Channel ch = null;
                for (int i = 0; i < cmbChannel.getItemCount(); i++) {
                    ch = cmbChannel.getItemAt(i);
                    if (ch.getId() == a.getChannelId()) {
                        channelName = ch.getName();
                        break;
                    }
                }
                String programName = "";
                Program pr = null;
                for (int i = 0; i < cmbProgram.getItemCount(); i++) {
                    pr = cmbProgram.getItemAt(i);
                    if (pr.getId() == a.getProgramId()) {
                        programName = pr.getName();
                        break;
                    }
                }
                model.addRow(new Object[] {
                        a.getAdId(),
                        a.getCompanyName(),
                        channelName,
                        programName,
                        a.getCostPaid(),
                        a.getAdDuration(),
                        a.getAdDate(),
                        a.getPaymentStatus()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void addAdvert() {
        try {
            Channel c = (Channel) cmbChannel.getSelectedItem();
            Program p = (Program) cmbProgram.getSelectedItem();
            String company = txtCompany.getText() == null ? "" : txtCompany.getText().trim();
            String costText = txtCostPaid.getText() == null ? "" : txtCostPaid.getText().trim();
            String durationText = txtDuration.getText() == null ? "" : txtDuration.getText().trim();
            String dateText = txtDate.getText() == null ? "" : txtDate.getText().trim();
            String status = (String) cmbPaymentStatus.getSelectedItem();
            if (!validateAdvertisement(company, c, p, costText, durationText, dateText, status, false, null))
                return;
            double cost = Double.parseDouble(costText);
            int hours = Integer.parseInt(durationText);
            Time duration = Time.valueOf(String.format("%02d:00:00", hours));
            Date date = Date.valueOf(dateText);
            Advertisement a = new Advertisement();
            a.setCompanyName(company);
            a.setChannelId(c.getId());
            a.setProgramId(p.getId());
            a.setCostPaid(cost);
            a.setAdDuration(duration);
            a.setAdDate(date);
            a.setPaymentStatus(status);
            service.addAdvertisement(a);
            JOptionPane.showMessageDialog(this, "Advertisement added");
            clearInputs();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteAdvert() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            try {
                service.deleteAdvertisement(id);
                JOptionPane.showMessageDialog(this, "Deleted");
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void clearInputs() {
        txtCompany.setText("");
        txtCostPaid.setText("");
        txtDuration.setText("");
        txtDate.setText("");
        cmbPaymentStatus.setSelectedIndex(0);
    }

    private void updateAdvert() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            Channel c = (Channel) cmbChannel.getSelectedItem();
            Program p = (Program) cmbProgram.getSelectedItem();
            String company = txtCompany.getText() == null ? "" : txtCompany.getText().trim();
            String costText = txtCostPaid.getText() == null ? "" : txtCostPaid.getText().trim();
            String durationText = txtDuration.getText() == null ? "" : txtDuration.getText().trim();
            String dateText = txtDate.getText() == null ? "" : txtDate.getText().trim();
            String status = (String) cmbPaymentStatus.getSelectedItem();
            if (!validateAdvertisement(company, c, p, costText, durationText, dateText, status, true, id))
                return;
            Advertisement a = new Advertisement();
            a.setAdId(id);
            a.setCompanyName(company);
            a.setChannelId(c.getId());
            a.setProgramId(p.getId());
            a.setCostPaid(Double.parseDouble(costText));
            int hours = Integer.parseInt(durationText);
            a.setAdDuration(Time.valueOf(String.format("%02d:00:00", hours)));
            a.setAdDate(Date.valueOf(dateText));
            a.setPaymentStatus(status);
            try {
                service.updateAdvertisement(a);
                JOptionPane.showMessageDialog(this, "Advertisement Updated");
                loadData();
                clearInputs();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating: " + ex.getMessage());
            }
        }
    }

    private boolean validateAdvertisement(String company, Channel channel, Program program, String costText,
            String durationText, String dateText, String status, boolean isUpdate, Integer id) {
        if (channel == null || program == null) {
            JOptionPane.showMessageDialog(this, "Select channel and program");
            return false;
        }
        if (program.getChannel() != null && program.getChannel().getId() != 0) {
            if (channel.getId() != program.getChannel().getId()) {
                JOptionPane.showMessageDialog(this, "Selected channel must match the program's channel");
                return false;
            }
        }
        if (company.isEmpty() || !company.matches("[A-Za-z0-9 &\\-']{2,80}")) {
            JOptionPane.showMessageDialog(this, "Company must be 2–80 valid characters");
            return false;
        }
        if (!costText.matches("^\\d+(\\.\\d{1,2})?$")) {
            JOptionPane.showMessageDialog(this, "Cost must be a number with up to 2 decimals");
            return false;
        }
        double cost;
        try {
            cost = Double.parseDouble(costText);
        } catch (Exception ex) {
            cost = -1;
        }
        if (cost <= 0 || cost > 1000000) {
            JOptionPane.showMessageDialog(this, "Cost must be between 1 and 1,000,000");
            return false;
        }
        if (!durationText.matches("^\\d{1,2}$")) {
            JOptionPane.showMessageDialog(this, "Duration must be whole hours (e.g., 1, 2, 3)");
            return false;
        }
        int hours;
        try {
            hours = Integer.parseInt(durationText);
        } catch (Exception ex) {
            hours = -1;
        }
        /*
         * Removed validation as requested
         * if (hours < 1 || hours > 12) {
         * JOptionPane.showMessageDialog(this,
         * "Ad duration must be between 1 and 12 hours");
         * return false;
         * }
         */

        if (!dateText.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            JOptionPane.showMessageDialog(this, "Date must be YYYY-MM-DD");
            return false;
        }
        java.sql.Date dt;
        try {
            dt = java.sql.Date.valueOf(dateText);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid time/date format");
            return false;
        }
        int programMinutes = 0;
        if (program.getDuration() != null) {
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d{1,3})").matcher(program.getDuration());
            if (m.find()) {
                try {
                    programMinutes = Integer.parseInt(m.group(1));
                } catch (Exception ignored) {
                }
            }
        }
        /*
         * Removed validation as requested
         * if (programMinutes > 0) {
         * if ((hours * 60.0) > programMinutes) {
         * JOptionPane.showMessageDialog(this,
         * "Ad duration cannot exceed program duration");
         * return false;
         * }
         * }
         */
        try {
            List<Advertisement> existing = service.getAllAdvertisements();
            int perDayCount = 0;
            for (Advertisement a : existing) {
                if (a.getProgramId() == program.getId() && a.getAdDate() != null && a.getAdDate().equals(dt)) {
                    perDayCount++;
                    boolean sameId = isUpdate && id != null && a.getAdId() == id.intValue();
                    boolean dupCompany = a.getCompanyName() != null
                            && a.getCompanyName().trim().equalsIgnoreCase(company);
                    if (!sameId && dupCompany) {
                        JOptionPane.showMessageDialog(this,
                                "Duplicate company advertisement for this program and date");
                        return false;
                    }
                }
            }
            if (perDayCount > 200) {
                JOptionPane.showMessageDialog(this, "Too many advertisements for the selected program and date");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Validation error: " + e.getMessage());
            return false;
        }
        if (status == null || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Payment status is required");
            return false;
        }
        return true;
    }

    private void pickDateInto(JTextField target) {
        JSpinner spinner = new JSpinner(
                new SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        int res = JOptionPane.showConfirmDialog(this, spinner, "Select Date", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd");
            target.setText(fmt.format((java.util.Date) spinner.getValue()));
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

    private void printTable() {
        try {
            table.print(JTable.PrintMode.FIT_WIDTH);
        } catch (java.awt.print.PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Print error: " + ex.getMessage());
        }
    }
}
