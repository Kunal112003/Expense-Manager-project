package com.example.ai_project_10;

public class Categories {
    private int id; // Unique identifier for the category
    private String name; // The name of the expense category

    public Categories(String name) {
        this.name = name;
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
