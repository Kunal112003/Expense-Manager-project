package com.example.ai_project_10.ui.expenses;

public class Expenses {
    private String id; // Unique identifier for the expense entry
    private double amount; // The expense amount
    private String date; // Timestamp representing the date of the expense
    private String categoryId; // Identifier for the expense category
    private String expenseName; // Optional notes for the expense

    private String username;

    //make constructor for expense name, amount, date, category
    public Expenses(String expenseName, double amount, String date, String categoryId) {
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
        this.expenseName = expenseName;
    }

    public Expenses(String id,double amount, String date, String categoryId, String expenseName, String username) {
        this.amount = amount;
        this.date = date;
        this.id = id;
        this.categoryId = categoryId;
        this.expenseName = expenseName;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter methods for id, amount, date, categoryId, and notes
    public String getExpenseId() {
        return id;
    }

    public void setExpenseId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
        this.expenseName = expenseName;
    }

    // You may add additional methods here as needed, such as toString() for debugging or serialization.
}
