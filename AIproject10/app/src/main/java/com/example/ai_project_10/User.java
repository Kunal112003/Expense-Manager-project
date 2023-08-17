package com.example.ai_project_10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    public String username,email, password,user_id;
    public ArrayList<HashMap> items;
    public ArrayList<HashMap> budgets;

    public ArrayList<HashMap> expenses;

    //monthly expenses and total expenses

    public double totalExpenses;

public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }



    public User(String username, String email, String password, String hashedUsername, ArrayList<HashMap> items, ArrayList<HashMap> budgets, ArrayList<HashMap> expenses,  double totalExpenses) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.user_id = user_id;
        this.items = items;
        this.budgets =budgets;
        this.expenses = expenses;
        this.totalExpenses = totalExpenses;

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

    public ArrayList<HashMap> getItems() {
        return items;
    }

    public void setItems(ArrayList<HashMap> items) {
        this.items = items;
    }

    public ArrayList<HashMap> getBudgets() {
        return budgets;
    }

    public void setBudgets(ArrayList<HashMap> budgets) {
        this.budgets = budgets;
    }

    public ArrayList<HashMap> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<HashMap> expenses) {
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


    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}





