/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class EmployeeDao {

    public boolean addEmployee(Employee e) {
        String sql = "INSERT INTO employees (first_name, last_name, gender, email, phone, salary_amount, salary_type, hire_date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, e.getFirstName());
            pst.setString(2, e.getLastName());
            pst.setString(3, e.getGender());
            pst.setString(4, e.getEmail());
            pst.setString(5, e.getPhone());
            pst.setDouble(6, e.getSalaryAmount());
            pst.setString(7, e.getSalaryType());
            pst.setDate(8, new java.sql.Date(e.getHireDate().getTime()));
            pst.setString(9, e.getStatus());

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployee(Employee e) {
        String sql = "UPDATE employees SET first_name=?, last_name=?, gender=?, email=?, phone=?, salary_amount=?, salary_type=?, hire_date=?, status=? WHERE employee_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, e.getFirstName());
            pst.setString(2, e.getLastName());
            pst.setString(3, e.getGender());
            pst.setString(4, e.getEmail());
            pst.setString(5, e.getPhone());
            pst.setDouble(6, e.getSalaryAmount());
            pst.setString(7, e.getSalaryType());
            pst.setDate(8, new java.sql.Date(e.getHireDate().getTime()));
            pst.setString(9, e.getStatus());
            pst.setInt(10, e.getEmployeeId());

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM employees WHERE employee_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, employeeId);
            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDouble("salary_amount"),
                        rs.getString("salary_type"),
                        rs.getDate("hire_date"),
                        rs.getString("status")
                );
                list.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE employee_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDouble("salary_amount"),
                        rs.getString("salary_type"),
                        rs.getDate("hire_date"),
                        rs.getString("status")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByEmail(String email) {
        String sql = "SELECT * FROM employees WHERE email=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDouble("salary_amount"),
                        rs.getString("salary_type"),
                        rs.getDate("hire_date"),
                        rs.getString("status")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
