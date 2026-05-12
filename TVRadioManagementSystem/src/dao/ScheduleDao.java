/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Schedule;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author admin
 */
public class ScheduleDao {
    private static final String URL = "jdbc:mysql://localhost:3306/tv_radio_management_system_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ADD
    public boolean addSchedule(Schedule s) {
        String sql = "INSERT INTO schedules (channel_id, program_id, employee_id, start_time, end_time) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, s.getChannelId());
            pst.setInt(2, s.getProgramId());

            if (s.getEmployeeId() == null) {
                pst.setNull(3, Types.INTEGER);
            } else {
                pst.setInt(3, s.getEmployeeId());
            }

            pst.setTimestamp(4, new Timestamp(s.getStartTime().getTime()));
            pst.setTimestamp(5, new Timestamp(s.getEndTime().getTime()));

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding schedule: " + e.getMessage());
            return false;
        }
    }

    // UPDATE
    public boolean updateSchedule(Schedule s) {
        String sql = "UPDATE schedules SET channel_id=?, program_id=?, employee_id=?, "
                   + "start_time=?, end_time=? WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, s.getChannelId());
            pst.setInt(2, s.getProgramId());

            if (s.getEmployeeId() == null) {
                pst.setNull(3, Types.INTEGER);
            } else {
                pst.setInt(3, s.getEmployeeId());
            }

            pst.setTimestamp(4, new Timestamp(s.getStartTime().getTime()));
            pst.setTimestamp(5, new Timestamp(s.getEndTime().getTime()));
            pst.setInt(6, s.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating schedule: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deleteSchedule(int id) {
        String sql = "DELETE FROM schedules WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting schedule: " + e.getMessage());
            return false;
        }
    }

    // GET ALL
    public List<Schedule> getAllSchedules() {
        List<Schedule> list = new ArrayList<>();

        String sql =
            "SELECT s.id, s.channel_id, s.program_id, s.employee_id, "
          + "s.start_time, s.end_time, "
          + "c.name AS channel_name, p.name AS program_name, "
          + "CONCAT(e.first_name, ' ', e.last_name) AS employee_name "
          + "FROM schedules s "
          + "JOIN channels c ON s.channel_id = c.id "
          + "JOIN programs p ON s.program_id = p.id "
          + "LEFT JOIN employees e ON s.employee_id = e.id";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Schedule s = new Schedule(
                        rs.getInt("id"),
                        rs.getInt("channel_id"),
                        rs.getInt("program_id"),
                        rs.getObject("employee_id") == null ? null : rs.getInt("employee_id"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        rs.getString("channel_name"),
                        rs.getString("program_name"),
                        rs.getString("employee_name")
                );

                list.add(s);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading schedules: " + e.getMessage());
        }

        return list;
    }

    // GET BY ID
    public Schedule getScheduleById(int id) {
        String sql =
            "SELECT s.id, s.channel_id, s.program_id, s.employee_id, "
          + "s.start_time, s.end_time, "
          + "c.name AS channel_name, p.name AS program_name, "
          + "CONCAT(e.first_name, ' ', e.last_name) AS employee_name "
          + "FROM schedules s "
          + "JOIN channels c ON s.channel_id = c.id "
          + "JOIN programs p ON s.program_id = p.id "
          + "LEFT JOIN employees e ON s.employee_id = e.id "
          + "WHERE s.id=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Schedule(
                        rs.getInt("id"),
                        rs.getInt("channel_id"),
                        rs.getInt("program_id"),
                        rs.getObject("employee_id") == null ? null : rs.getInt("employee_id"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        rs.getString("channel_name"),
                        rs.getString("program_name"),
                        rs.getString("employee_name")
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error finding schedule: " + e.getMessage());
        }

        return null;
    } 


}
