/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.FinancialReport;

/**
 *
 * @author admin
 */
public class FinancialReportDao {



    // Get total income from advertisements
    public double getTotalIncome() {
        double totalIncome = 0;
        String sql = "SELECT SUM(cost_paid) AS total_income FROM advertisements";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalIncome = rs.getDouble("total_income");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalIncome;
    }

    // Get total expenses from expenses table
    public double getTotalExpenses() {
        double totalExpenses = 0;
        String sql = "SELECT SUM(amount) AS total_expenses FROM expenses";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalExpenses = rs.getDouble("total_expenses");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalExpenses;
    }

    // Per program analysis
    public List<FinancialReport> getPerProgramAnalysis() {
        List<FinancialReport> list = new ArrayList<>();
        String sql = "SELECT p.program_name, " +
                     "COALESCE(SUM(a.cost_paid),0) AS income, " +
                     "COALESCE(SUM(e.amount),0) AS expenses, " +
                     "COALESCE(SUM(a.cost_paid),0) - COALESCE(SUM(e.amount),0) AS profit_loss " +
                     "FROM programs p " +
                     "LEFT JOIN advertisements a ON p.program_id = a.program_id " +
                     "LEFT JOIN expenses e ON p.program_id = e.program_id " +
                     "GROUP BY p.program_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FinancialReport fr = new FinancialReport();
                fr.setProgramName(rs.getString("program_name"));
                fr.setIncome(rs.getDouble("income"));
                fr.setExpenses(rs.getDouble("expenses"));
                fr.setProfitLoss(rs.getDouble("profit_loss"));
                list.add(fr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Monthly analysis
    public List<FinancialReport> getMonthlyReport() {
        List<FinancialReport> list = new ArrayList<>();
        String sql = "SELECT MONTH(a.ad_date) AS month, " +
                     "SUM(a.cost_paid) AS income, " +
                     "(SELECT SUM(amount) FROM expenses WHERE MONTH(expense_date) = MONTH(a.ad_date)) AS expenses, " +
                     "SUM(a.cost_paid) - (SELECT SUM(amount) FROM expenses WHERE MONTH(expense_date) = MONTH(a.ad_date)) AS profit_loss " +
                     "FROM advertisements a " +
                     "GROUP BY MONTH(a.ad_date)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FinancialReport fr = new FinancialReport();
                fr.setMonth(rs.getInt("month"));
                fr.setIncome(rs.getDouble("income"));
                fr.setExpenses(rs.getDouble("expenses"));
                fr.setProfitLoss(rs.getDouble("profit_loss"));
                list.add(fr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
