package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.User;

public class AdminDashboard extends JFrame {
    private User user;
    private JPanel sidebar;
    private JPanel contentPanel;
    private JPanel statsPanel;

    public AdminDashboard(User user) {
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("TV & Radio Management System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sidebar = new JPanel();
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setLayout(new GridLayout(12, 1, 0, 10));
        sidebar.setBorder(new EmptyBorder(20, 20, 20, 20));
        sidebar.setPreferredSize(new Dimension(250, 800));
        addSidebarButton("Dashboard");
        addSidebarButton("Channels");
        addSidebarButton("Programs");
        addSidebarButton("Schedules");
        addSidebarButton("Employees");
        addSidebarButton("Advertisements");
        addSidebarButton("Expenses");
        addSidebarButton("Financial Reports");
        addSidebarButton("Program Assignments");
        addSidebarButton("Equipment");
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(192, 57, 43));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
        sidebar.add(btnLogout);
        add(sidebar, BorderLayout.WEST);

        contentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                java.net.URL url = getClass().getResource("/images/tv10.PNG");
                if (url != null) {
                    ImageIcon icon = new ImageIcon(url);
                    Image img = icon.getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(255, 255, 255, 200));
        header.setBorder(new EmptyBorder(10, 10, 10, 20));
        JLabel lblUser = new JLabel("Welcome, " + user.getName() + " (" + user.getRole() + ")");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.add(lblUser);
        contentPanel.add(header, BorderLayout.NORTH);

        JPanel emptyCenter = new JPanel();
        emptyCenter.setOpaque(false);
        contentPanel.add(emptyCenter, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void addSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(52, 73, 94));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNavigation(text);
            }
        });
        sidebar.add(btn);
    }
    
    private JButton createModuleButton(String text, Runnable onClick) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(220, 120));
        btn.addActionListener(e -> onClick.run());
        return btn;
    }

    private void handleNavigation(String menu) {
        if (menu.equals("Dashboard")) {
            JOptionPane.showMessageDialog(this, "Dashboard");
        } else if (menu.equals("Channels")) {
            new ChannelFrame().setVisible(true);
        } else if (menu.equals("Programs")) {
            new ProgramForm().setVisible(true);
        } else if (menu.equals("Schedules")) {
            new ScheduleForm().setVisible(true);
        } else if (menu.equals("Employees")) {
            new EmployeeForm().setVisible(true);
        } else if (menu.equals("Advertisements")) {
            new AdvertisementForm().setVisible(true);
        } else if (menu.equals("Expenses")) {
            new ExpenseForm().setVisible(true);
        } else if (menu.equals("Financial Reports")) {
            new FinancialReportForm().setVisible(true);
        } else if (menu.equals("Program Assignments")) {
            new ProgramAssignmentForm().setVisible(true);
        } else if (menu.equals("Equipment")) {
            new EquipmentForm().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, menu + " module coming soon!");
        }
    }
}
