/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
public class FinancialReport {

    private String programName;
    private int month;
    private double income;
    private double expenses;
    private double profitLoss;

    // --- Getters & Setters ---
    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public double getIncome() { return income; }
    public void setIncome(double income) { this.income = income; }

    public double getExpenses() { return expenses; }
    public void setExpenses(double expenses) { this.expenses = expenses; }

    public double getProfitLoss() { return profitLoss; }
    public void setProfitLoss(double profitLoss) { this.profitLoss = profitLoss; }
}
