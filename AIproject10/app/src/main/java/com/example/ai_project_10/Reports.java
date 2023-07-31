package com.example.ai_project_10;

public class Reports {
    private String categoryName; // Name of the expense category
    private double totalExpense; // Total amount spent in the category
    private double budgetLimit; // Budget limit set for the category

    public Reports(String categoryName, double totalExpense, double budgetLimit) {
        this.categoryName = categoryName;
        this.totalExpense = totalExpense;
        this.budgetLimit = budgetLimit;
    }

    // Getter and Setter methods for categoryName, totalExpense, and budgetLimit
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    // You may add additional methods here as needed, such as toString() for debugging or serialization.
}
