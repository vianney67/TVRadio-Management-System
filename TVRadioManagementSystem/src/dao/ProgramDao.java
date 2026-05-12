/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Program;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author admin
 */
public class ProgramDao {

    // Insert program
    public boolean insertProgram(Program program) {
        String sql = "INSERT INTO programs (program_name, channel_id, description, program_type, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, program.getProgramName());
            pst.setInt(2, program.getChannelId());
            pst.setString(3, program.getDescription());
            pst.setString(4, program.getProgramType());
            pst.setString(5, program.getStatus());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all programs
    public List<Program> getAllPrograms() {
        List<Program> list = new ArrayList<>();
        String sql = "SELECT * FROM programs";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Program(
                        rs.getInt("program_id"),
                        rs.getString("program_name"),
                        rs.getInt("channel_id"),
                        rs.getString("description"),
                        rs.getString("program_type"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Delete program
    public boolean deleteProgram(int id) {
        String sql = "DELETE FROM programs WHERE program_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 // Update Program
public boolean updateProgram(Program program) {
    String sql = "UPDATE programs SET program_name=?, channel_id=?, description=?, program_type=?, status=? WHERE program_id=?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, program.getProgramName());
        pst.setInt(2, program.getChannelId());
        pst.setString(3, program.getDescription());
        pst.setString(4, program.getProgramType());
        pst.setString(5, program.getStatus());
        pst.setInt(6, program.getProgramId());

        return pst.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
// Get Program by ID
public Program getProgramById(int programId) {
    String sql = "SELECT * FROM programs WHERE program_id = ?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, programId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            Program p = new Program();
            p.setProgramId(rs.getInt("program_id"));
            p.setProgramName(rs.getString("program_name"));
            p.setChannelId(rs.getInt("channel_id"));
            p.setDescription(rs.getString("description"));
            p.setProgramType(rs.getString("program_type"));
            p.setStatus(rs.getString("status"));
            return p;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null; // if not found
}
// Get Program by name
public Program getProgramByName(String programName) {
    String sql = "SELECT * FROM programs WHERE program_name = ?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, programName);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            Program p = new Program();
            p.setProgramId(rs.getInt("program_id"));
            p.setProgramName(rs.getString("program_name"));
            p.setChannelId(rs.getInt("channel_id"));
            p.setDescription(rs.getString("description"));
            p.setProgramType(rs.getString("program_type"));
            p.setStatus(rs.getString("status"));
            return p;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null; // if not found
}

public String getChannelNameById(int channelId) {
    String channelName = "";
    String sql = "SELECT name FROM channels WHERE id = ?"; // use 'id' instead of 'channel_id'

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, channelId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            channelName = rs.getString("name"); // use 'name' column
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return channelName;
}

 // Get all programs of a specific channel
    public List<Program> getProgramsByChannel(int channelId) {
        List<Program> programs = new ArrayList<>();
        String sql = "SELECT * FROM programs WHERE channel_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, channelId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Program program = new Program(
                        rs.getInt("program_id"),
                        rs.getString("program_name"),
                        rs.getInt("channel_id"),
                        rs.getString("description"),
                        rs.getString("program_type"),
                        rs.getString("status")
                );
                programs.add(program);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return programs;
    }



}
