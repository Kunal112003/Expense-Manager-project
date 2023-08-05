package com.example.ai_project_10.ui.expenses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ai_project_10.R;

import java.util.List;

public class ExpensesArrayAdapter extends ArrayAdapter<Expenses> {

    public ExpensesArrayAdapter(Context context, List<Expenses> expensesList) {
        super(context, R.layout.item_expenses, expensesList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expenses expenses = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expenses, parent, false);
        }

        TextView expenseName = convertView.findViewById(R.id.expenseName);
        TextView expenseAmount = convertView.findViewById(R.id.expenseAmount);
        TextView expenseDate = convertView.findViewById(R.id.expenseDate);
        TextView expenseCategory = convertView.findViewById(R.id.expenseCategory);

        expenseName.setText(expenses.getNotes());
        expenseAmount.setText(String.valueOf(expenses.getAmount()));
        expenseDate.setText(expenses.getDate());
        expenseCategory.setText(expenses.getCategoryId());

        return convertView;
    }
}
