package com.example.ai_project_10.ui.expenses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ai_project_10.R;

import java.util.Calendar;
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

        //change expense adapter to empty start of every month
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        //get expense date
        String expense_date = expenses.getDate();

        //get expense month
        String expense_month = expense_date.substring(0,2);

        //get expense year
        String expense_year = expense_date.substring(6,10);




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
