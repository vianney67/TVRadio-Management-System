package model;
import java.io.Serializable;
public class FinancialReport implements Serializable {
    private static final long serialVersionUID = 1L;
    // Plain DTO for client-side usage
    private int id;

    private String programName;
    
    private int month;
    private double income;
    private double expenses;
    
    private double profitLoss;

    public FinancialReport() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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
