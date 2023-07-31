package com.example.ai_project_10;

public class Expenses {
    private int id; // Unique identifier for the expense entry
    private double amount; // The expense amount
    private long date; // Timestamp representing the date of the expense
    private int categoryId; // Identifier for the expense category
    private String notes; // Optional notes for the expense

    public Expenses(double amount, long date, int categoryId, String notes) {
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
        this.notes = notes;
    }

    // Getter and Setter methods for id, amount, date, categoryId, and notes
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // You may add additional methods here as needed, such as toString() for debugging or serialization.
}
