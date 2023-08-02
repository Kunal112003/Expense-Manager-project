package com.example.ai_project_10;

public class Expenses {
    private int id; // Unique identifier for the expense entry
    private double amount; // The expense amount
    private long date; // Timestamp representing the date of the expense
    private String categoryId; // Identifier for the expense category
    private String expenseName; // Optional notes for the expense

    public Expenses(double amount, long date, String categoryId, String notes) {
        this.amount = amount;
        this.date = date;

        this.categoryId = categoryId;
        this.expenseName = expenseName;
    }

    // Getter and Setter methods for id, amount, date, categoryId, and notes
    public int getExpenseId() {
        return id;
    }

    public void setExpenseId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getNotes() {
        return expenseName;
    }

    public void setNotes(String notes) {
        this.expenseName = notes;
    }

    // You may add additional methods here as needed, such as toString() for debugging or serialization.
}
