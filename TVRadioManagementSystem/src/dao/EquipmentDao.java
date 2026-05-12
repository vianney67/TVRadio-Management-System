/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Equipment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author admin
 */
public class EquipmentDao {

    private static final String URL = "jdbc:mysql://localhost:3306/tv_radio_management_system_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ADD EQUIPMENT
    public boolean addEquipment(Equipment e) {
        String sql = "INSERT INTO equipment (name, type, status, condition_note) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, e.getName());
            pst.setString(2, e.getType());
            pst.setString(3, e.getStatus());
            pst.setString(4, e.getConditionNote());

            return pst.executeUpdate() > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error adding equipment: " + ex.getMessage());
            return false;
        }
    }

    // UPDATE
    public boolean updateEquipment(Equipment e) {
        String sql = "UPDATE equipment SET name=?, type=?, status=?, condition_note=? WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, e.getName());
            pst.setString(2, e.getType());
            pst.setString(3, e.getStatus());
            pst.setString(4, e.getConditionNote());
            pst.setInt(5, e.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating equipment: " + ex.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deleteEquipment(int id) {
        String sql = "DELETE FROM equipment WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting equipment: " + ex.getMessage());
            return false;
        }
    }

    // GET ALL
    public List<Equipment> getAllEquipment() {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT * FROM equipment";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Equipment e = new Equipment(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getString("condition_note")
                );
                list.add(e);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading equipment: " + ex.getMessage());
        }

        return list;
    }

    // GET BY ID
    public Equipment getEquipmentById(int id) {
        String sql = "SELECT * FROM equipment WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Equipment(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("status"),
                        rs.getString("condition_note")
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error finding equipment: " + ex.getMessage());
        }

        return null;
    }   
}
