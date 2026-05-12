/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.ProgramAssignment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ProgramAssignmentDao {

    // ✅ Insert new assignment
    public boolean insertAssignment(ProgramAssignment assignment) {
        String sql = "INSERT INTO program_assignments (channel_id, program_id, employee_id, assigned_role) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, assignment.getChannelId());
            pst.setInt(2, assignment.getProgramId());
            pst.setInt(3, assignment.getEmployeeId());
            pst.setString(4, assignment.getAssignedRole());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Retrieve all assignments
    public List<ProgramAssignment> getAllAssignments() {
        List<ProgramAssignment> list = new ArrayList<>();
        String sql = "SELECT * FROM program_assignments";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ProgramAssignment pa = new ProgramAssignment(
                        rs.getInt("assignment_id"),
                        rs.getInt("channel_id"),
                        rs.getInt("program_id"),
                        rs.getInt("employee_id"),
                        rs.getString("assigned_role")
                );
                list.add(pa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ Update an existing assignment
    public boolean updateAssignment(ProgramAssignment assignment) {
        String sql = "UPDATE program_assignments SET channel_id=?, program_id=?, employee_id=?, assigned_role=? WHERE assignment_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, assignment.getChannelId());
            pst.setInt(2, assignment.getProgramId());
            pst.setInt(3, assignment.getEmployeeId());
            pst.setString(4, assignment.getAssignedRole());
            pst.setInt(5, assignment.getAssignmentId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Delete an assignment
    public boolean deleteAssignment(int assignmentId) {
        String sql = "DELETE FROM program_assignments WHERE assignment_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, assignmentId);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Optional: Get assignments for a specific program
    public List<ProgramAssignment> getAssignmentsByProgram(int programId) {
        List<ProgramAssignment> list = new ArrayList<>();
        String sql = "SELECT * FROM program_assignments WHERE program_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, programId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ProgramAssignment pa = new ProgramAssignment(
                            rs.getInt("assignment_id"),
                            rs.getInt("channel_id"),
                            rs.getInt("program_id"),
                            rs.getInt("employee_id"),
                            rs.getString("assigned_role")
                    );
                    list.add(pa);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
