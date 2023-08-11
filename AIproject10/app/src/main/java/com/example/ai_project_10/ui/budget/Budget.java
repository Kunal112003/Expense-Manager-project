package com.example.ai_project_10.ui.budget;

public class Budget {
    private int categoryId; // Unique identifier for the expense category
    private double budgetLimit; // The budget limit set by the user for this category

    private String category; // The name of the category

    public Budget() {
        // No-argument constructor required for Firestore deserialization
    }



    public Budget(int categoryId, double budgetLimit, String category) {

        this.categoryId = categoryId;
        this.budgetLimit = budgetLimit;
        this.category = category;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBudgetProgress() {
        // Calculate the budget progress based on expenses for the category
        // (You'll need access to the expenses associated with this category)
        // Return a value between 0 and 1 representing the progress.
        // For example, 0.5 means 50% of the budget is spent.


        return 0;
    }

    public boolean isBudgetExceeded(Double budgetLimit, Double budgetProgress) {
        // Use the budget progress method to check if the budget is exceeded or not
        // Return true if the budget is exceeded, false otherwise.

        if (budgetProgress > budgetLimit) {
            return true;
        }

        else {
            return false;
        }


    }



    @Override
    public String toString() {
        return "Budget{" +
                "categoryId=" + categoryId +
                ", budgetLimit=" + budgetLimit +
                '}';
    }


}
