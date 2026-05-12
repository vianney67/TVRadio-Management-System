/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import model.Channel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class ChannelDao {
    private static final String URL = "jdbc:mysql://localhost:3306/tv_radio_management_system_db";
    private static final String USER = "root";   // change as needed
    private static final String PASSWORD = "";

    private Connection connection;

    // Constructor - establish DB connection
    public ChannelDao() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // CREATE - Add a new channel
    public boolean addChannel(Channel channel) {
        String sql = "INSERT INTO channels (name, frequency, type, language) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, channel.getName());
            stmt.setString(2, channel.getFrequency());
            stmt.setString(3, channel.getType());
            stmt.setString(4, channel.getLanguage());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding channel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get all channels
    public List<Channel> getAllChannels() {
        List<Channel> channels = new ArrayList<>();
       String sql = "SELECT * FROM channels";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Channel channel = new Channel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("frequency"),
                        rs.getString("type"),
                        rs.getString("language")
                );
                channels.add(channel);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching channels: " + e.getMessage());
            e.printStackTrace();
        }
        return channels;
    }

    // UPDATE - Update channel details
    public boolean updateChannel(Channel channel) {
        String sql = "UPDATE channels SET name=?, frequency=?, type=?, language=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, channel.getName());
            stmt.setString(2, channel.getFrequency());
            stmt.setString(3, channel.getType());
            stmt.setString(4, channel.getLanguage());
            stmt.setInt(5, channel.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating channel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Remove channel by ID
    public boolean deleteChannel(int id) {
        String sql = "DELETE FROM channels WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting channel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // READ - Get channel by ID
    public Channel getChannelById(int id) {
        String sql = "SELECT * FROM channels WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Channel(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("frequency"),
                            rs.getString("type"),
                            rs.getString("language")
                    );
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching channel: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
  
    // READ - Get channel by name
public Channel getChannelByName(String name) {
    String sql = "SELECT * FROM channels WHERE name=?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, name);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Channel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("frequency"),
                        rs.getString("type"),
                        rs.getString("language")
                );
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error fetching channel by name: " + e.getMessage());
        e.printStackTrace();
    }
    return null; // if not found
}

}
