package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.User;

public class EmployeeDashboard extends JFrame {
    private User user;

    public EmployeeDashboard(User user) {
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("TV & Radio Management System - Employee Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Main Panel with Background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/images/tv10.PNG"));
                Image img = icon.getImage();
                if (img != null) {
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(255, 255, 255, 200));
        header.setBorder(new EmptyBorder(10, 10, 10, 20));
        JLabel lblUser = new JLabel("Welcome, " + user.getName() + " (" + user.getRole() + ")");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(192, 57, 43));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
        
        header.add(lblUser);
        header.add(btnLogout);
        
        mainPanel.add(header, BorderLayout.NORTH);
        
        JPanel emptyCenter = new JPanel();
        emptyCenter.setOpaque(false);
        mainPanel.add(emptyCenter, BorderLayout.CENTER);
        
        add(mainPanel);
    }
}
