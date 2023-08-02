package com.example.ai_project_10.ui.expenses;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ai_project_10.Expenses;
import com.example.ai_project_10.R;

public class ExpensesArrayAdapter extends ArrayAdapter<Expenses> {


    private final Expenses expenses;

    public ExpensesArrayAdapter(Context context, Expenses expenses) {
        super(context, R.layout.item_expenses);

        this.expenses = expenses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expenses expenses = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expenses, parent, false);
        }
        ListView listView = convertView.findViewById(R.id.listView);

        TextView expenseName = convertView.findViewById(R.id.expenseName);
        TextView expenseAmount = convertView.findViewById(R.id.expenseAmount);
        TextView expenseDate = convertView.findViewById(R.id.expenseDate);
        TextView expenseCategory = convertView.findViewById(R.id.expenseCategory);

        expenseName.setText(expenses.getNotes());
        expenseAmount.setText((int) expenses.getAmount());
        expenseDate.setText(expenses.getDate());
        expenseCategory.setText(expenses.getCategoryId());





        return convertView;
    }



}
