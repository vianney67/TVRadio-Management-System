/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.User;
import java.sql.*;

/**
 *
 * @author admin
 */
public class UserDao {

    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/tv_radio_management_system_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       // change if needed
    private static final String PASSWORD = "";       // your MySQL password

    private Connection conn;

    // Constructor: establish connection
    public UserDao() throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Login method
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // login failed
    }

}
