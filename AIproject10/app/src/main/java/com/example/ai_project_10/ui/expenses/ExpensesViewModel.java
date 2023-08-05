package com.example.ai_project_10.ui.expenses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExpensesViewModel extends ViewModel {
    private MutableLiveData<List<Expenses>> expensesListLiveData = new MutableLiveData<>();
    private List<Expenses> expensesList = new ArrayList<>();

    public void addExpense(Expenses expense) {
        expensesList.add(expense);
        expensesListLiveData.setValue(expensesList);
    }

    public LiveData<List<Expenses>> getExpensesListLiveData() {
        return expensesListLiveData;
    }
}

