package com.example.ai_project_10;

public class Budget {
    private int categoryId; // Unique identifier for the expense category
    private double budgetLimit; // The budget limit set by the user for this category

    public Budget(int categoryId, double budgetLimit) {
        this.categoryId = categoryId;
        this.budgetLimit = budgetLimit;
    }

    // Getter and Setter methods for categoryId and budgetLimit
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    // You may add additional methods here as needed, such as toString() for debugging or serialization.

    @Override
    public String toString() {
        return "Budget{" +
                "categoryId=" + categoryId +
                ", budgetLimit=" + budgetLimit +
                '}';
    }


}
