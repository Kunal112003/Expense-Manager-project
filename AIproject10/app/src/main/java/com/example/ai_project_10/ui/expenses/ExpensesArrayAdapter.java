package com.example.ai_project_10.ui.expenses;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import com.example.ai_project_10.Expenses;
import com.example.ai_project_10.R;

public class ExpensesArrayAdapter extends ArrayAdapter<Expenses> {

    private final Context context;
    private final Expenses[] values;

    public ExpensesArrayAdapter(Context context, Expenses[] values) {
        super(context, R.layout.item_expenses, values);
        this.context = context;
        this.values = values;
    }
}
