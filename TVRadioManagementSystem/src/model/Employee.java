/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.Date;
/**
 *
 * @author admin
 */
public class Employee {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;
    private double salaryAmount;
    private String salaryType;
    private Date hireDate;
    private String status;

    // Constructor without ID (Insert)
    public Employee(String firstName, String lastName, String gender, String email,
                    String phone, double salaryAmount, String salaryType, Date hireDate, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.salaryAmount = salaryAmount;
        this.salaryType = salaryType;
        this.hireDate = hireDate;
        this.status = status;
    }

    // Constructor with ID (Retrieve/Update)
    public Employee(int employeeId, String firstName, String lastName, String gender, String email,
                    String phone, double salaryAmount, String salaryType, Date hireDate, String status) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.salaryAmount = salaryAmount;
        this.salaryType = salaryType;
        this.hireDate = hireDate;
        this.status = status;
    }

    // Getters & Setters
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public double getSalaryAmount() { return salaryAmount; }
    public void setSalaryAmount(double salaryAmount) { this.salaryAmount = salaryAmount; }

    public String getSalaryType() { return salaryType; }
    public void setSalaryType(String salaryType) { this.salaryType = salaryType; }

    public Date getHireDate() { return hireDate; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFullName() {
    return this.firstName + " " + this.lastName;
}

}


