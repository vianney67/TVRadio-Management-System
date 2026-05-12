/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.ProgramSchedule;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author admin
 */
public class ProgramScheduleDAO {

public boolean insertSchedule(ProgramSchedule schedule) {
    String sql = "INSERT INTO program_schedule (program_id, channel_name, day, start_time, end_time) VALUES (?, ?, ?, ?, ?)";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, schedule.getProgramId());    // must exist in programs table
        pst.setString(2, schedule.getChannelName());
        pst.setString(3, schedule.getDay());
        pst.setTime(4, schedule.getStartTime());
        pst.setTime(5, schedule.getEndTime());

        return pst.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


    // ✅ GET ALL Schedules (WITH channel_name)
    public List<ProgramSchedule> getAllSchedules() {
        List<ProgramSchedule> list = new ArrayList<>();
        String sql = "SELECT * FROM program_schedule";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new ProgramSchedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("program_id"),
                        rs.getString("channel_name"),
                        rs.getString("day"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ DELETE Schedule
    public boolean deleteSchedule(int id) {
        String sql = "DELETE FROM program_schedule WHERE schedule_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ UPDATE Schedule (WITH channel_name)
    public boolean updateSchedule(ProgramSchedule ps) {
        String sql = "UPDATE program_schedule SET program_id = ?, channel_name = ?, day = ?, start_time = ?, end_time = ? WHERE schedule_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, ps.getProgramId());
            pst.setString(2, ps.getChannelName());
            pst.setString(3, ps.getDay());
            pst.setTime(4, ps.getStartTime());
            pst.setTime(5, ps.getEndTime());
            pst.setInt(6, ps.getScheduleId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasOverlap(int programId, String day, Time start, Time end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean isProgramAlreadyScheduled(int programId, String day, Time start, Time end) {
    String sql = "SELECT COUNT(*) FROM program_schedule WHERE program_id=? AND day=? AND start_time=? AND end_time=?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, programId);
        pst.setString(2, day);
        pst.setTime(3, start);
        pst.setTime(4, end);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

    public boolean isProgramAssignedToAnotherChannel(int programId, String channelName) {
    String sql = "SELECT COUNT(*) FROM program_schedule WHERE program_id=? AND channel_name<>?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, programId);
        pst.setString(2, channelName);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

    public boolean isProgramAlreadyScheduledExceptThis(
        int scheduleId, int programId, String day, Time start, Time end) {

    String sql = "SELECT COUNT(*) FROM program_schedule WHERE program_id=? AND day=? AND start_time=? AND end_time=? AND schedule_id<>?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, programId);
        pst.setString(2, day);
        pst.setTime(3, start);
        pst.setTime(4, end);
        pst.setInt(5, scheduleId);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

}
