package model;
import java.io.Serializable;
import java.sql.Date;
public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    // Plain DTO for client-side usage
    private int expenseId;
    
    private String category;
    private String description;
    private double amount;
    
    private Date expenseDate;
    
    private int programId;

    public Expense() {}

    public Expense(String category, String description, double amount, Date expenseDate, int programId) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.programId = programId;
    }

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
