package com.example.ai_project_10;

public class Categories {
    private int id; // Unique identifier for the category
    private String name; // The name of the expense category

    private Double amountSpent; // The amount spent on the category

    public Categories(String name, Double amountSpent) {
        this.name = name;
        this.amountSpent = amountSpent;
    }

    public Double getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(Double amountSpent) {
        this.amountSpent = amountSpent;
    }

    // Getter and Setter methods for id and name
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // You may add additional methods here as needed, such as toString() for debugging or serialization.
}
