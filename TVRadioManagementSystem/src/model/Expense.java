/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
/**
 *
 * @author admin
 */
public class Expense {

    private int expenseId;
    private String category;
    private String description;
    private double amount;
    private Date expenseDate;
    private int programId; // 0 or -1 if not assigned

    // Constructors
    public Expense() {}

    // For adding new expense
    public Expense(String category, String description, double amount, Date expenseDate, int programId) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.programId = programId;
    }

    // For retrieving/updating
    public Expense(int expenseId, String category, String description, double amount, Date expenseDate, int programId) {
        this.expenseId = expenseId;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.programId = programId;
    }

    // Getters and Setters
    public int getExpenseId() { return expenseId; }
    public void setExpenseId(int expenseId) { this.expenseId = expenseId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Date getExpenseDate() { return expenseDate; }
    public void setExpenseDate(Date expenseDate) { this.expenseDate = expenseDate; }
    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }
}
