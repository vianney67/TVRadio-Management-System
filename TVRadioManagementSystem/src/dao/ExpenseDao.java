/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Expense;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ExpenseDao {

 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


    // ✅ ADD EXPENSE
    public boolean insertExpense(Expense expense) {
        String sql = "INSERT INTO expenses (category, description, amount, expense_date, program_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, expense.getCategory());
            ps.setString(2, expense.getDescription());
            ps.setDouble(3, expense.getAmount());
            ps.setDate(4, expense.getExpenseDate());
            ps.setInt(5, expense.getProgramId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ UPDATE EXPENSE
    public boolean updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET category = ?, description = ?, amount = ?, expense_date = ?, program_id = ? WHERE expense_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, expense.getCategory());
            ps.setString(2, expense.getDescription());
            ps.setDouble(3, expense.getAmount());
            ps.setDate(4, expense.getExpenseDate());
            ps.setInt(5, expense.getProgramId());
            ps.setInt(6, expense.getExpenseId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ DELETE EXPENSE
    public boolean deleteExpense(int expenseId) {
        String sql = "DELETE FROM expenses WHERE expense_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, expenseId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ GET ALL EXPENSES (MODEL ONLY – NO PROGRAM NAME)
    public List<Expense> getAllExpenses() {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expenses";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Expense e = new Expense(
                    rs.getInt("expense_id"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getDouble("amount"),
                    rs.getDate("expense_date"),
                    rs.getInt("program_id")
                );
                list.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ ✅ ✅ GET ALL EXPENSES WITH PROGRAM NAME (FOR TABLE DISPLAY ONLY)
    // ⚠️ DOES NOT TOUCH YOUR MODEL
    public List<Object[]> getAllExpensesWithProgramName() {
        List<Object[]> list = new ArrayList<>();

        String sql = "SELECT e.expense_id, e.category, e.description, e.amount, " +
                     "e.expense_date, p.program_name " +
                     "FROM expenses e " +
                     "LEFT JOIN programs p ON e.program_id = p.program_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[]{
                    rs.getInt("expense_id"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getDouble("amount"),
                    rs.getDate("expense_date"),
                    rs.getString("program_name") // ✅ ONLY FOR JTable
                };
                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ OPTIONAL: GET EXPENSE BY ID (FOR FUTURE USE)
    public Expense getExpenseById(int id) {
        String sql = "SELECT * FROM expenses WHERE expense_id = ?";
        Expense expense = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                expense = new Expense(
                    rs.getInt("expense_id"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getDouble("amount"),
                    rs.getDate("expense_date"),
                    rs.getInt("program_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return expense;
    }

}
