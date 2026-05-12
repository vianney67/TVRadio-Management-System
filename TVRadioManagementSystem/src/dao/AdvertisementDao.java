/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Advertisement;
import model.Channel;
import model.Program;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class AdvertisementDao {


    // Add new advertisement
    public boolean addAdvertisement(Advertisement ad) {
        String sql = "INSERT INTO Advertisements (company_name, channel_id, program_id, cost_paid, ad_duration, ad_date, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, ad.getCompanyName());
            pst.setInt(2, ad.getChannelId());
            pst.setInt(3, ad.getProgramId());
            pst.setDouble(4, ad.getCostPaid());
            pst.setTime(5, ad.getAdDuration());
            pst.setDate(6, ad.getAdDate());
            pst.setString(7, ad.getPaymentStatus());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update existing advertisement
    public boolean updateAdvertisement(Advertisement ad) {
        String sql = "UPDATE Advertisements SET company_name=?, channel_id=?, program_id=?, cost_paid=?, ad_duration=?, ad_date=?, payment_status=? WHERE ad_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, ad.getCompanyName());
            pst.setInt(2, ad.getChannelId());
            pst.setInt(3, ad.getProgramId());
            pst.setDouble(4, ad.getCostPaid());
            pst.setTime(5, ad.getAdDuration());
            pst.setDate(6, ad.getAdDate());
            pst.setString(7, ad.getPaymentStatus());
            pst.setInt(8, ad.getAdId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete advertisement
    public boolean deleteAdvertisement(int adId) {
        String sql = "DELETE FROM Advertisements WHERE ad_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, adId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all advertisements
    public List<Advertisement> getAllAdvertisements() {
        List<Advertisement> ads = new ArrayList<>();
        String sql = "SELECT * FROM Advertisements";
        try (Connection conn = DBConnection.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Advertisement ad = new Advertisement(
                        rs.getInt("ad_id"),
                        rs.getString("company_name"),
                        rs.getInt("channel_id"),
                        rs.getInt("program_id"),
                        rs.getDouble("cost_paid"),
                        rs.getTime("ad_duration"),
                        rs.getDate("ad_date"),
                        rs.getString("payment_status")
                );
                ads.add(ad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ads;
    }

    // Get programs by channel (for filtering programCombo)
    public List<Program> getProgramsByChannel(int channelId) {
        List<Program> programs = new ArrayList<>();
        String sql = "SELECT * FROM Programs WHERE channel_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }

    // Get channel by ID (helper)
    public Channel getChannelById(int channelId) {
        String sql = "SELECT * FROM Channels WHERE id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, channelId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Channel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("frequency"),
                        rs.getString("type"),
                        rs.getString("language")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
