package com.example.ai_project_10;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String username,email, password,user_id;
    public Map<String, String> items = new HashMap<>();
    public Map<Double, String> budgets = new HashMap<>();
    public Map<Double, String> expenses = new HashMap<>();


    public User() {
    }
    public User(String username, String password, String email, String user_id, Map<String, String> items, Map<Double, String> budgets, Map<Double, String> expenses) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.user_id = user_id;
        this.items = items;
        this.budgets = budgets;
        this.expenses = expenses;


    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public Map<String, String> getItems() {
        return items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }

    public Map<Double, String> getBudgets() {
        return budgets;
    }

    public void setBudgets(Map<Double, String> budgets) {
        this.budgets = budgets;
    }

    public Map<Double, String> getExpenses() {
        return expenses;
    }

    public void setExpenses(Map<Double, String> expenses) {
        this.expenses = expenses;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}





