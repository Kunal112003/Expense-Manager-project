package com.example.ai_project_10.ui.expenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ai_project_10.AddEditExpenseFragment;
import com.example.ai_project_10.Expenses;
import com.example.ai_project_10.R;






public class ExpensesFragment extends Fragment {



    private Button addExpenseButton;


public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExpensesViewModel expensesViewModel =
                new ViewModelProvider(this).get(ExpensesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_expenses, container, false);




        addExpenseButton = root.findViewById(R.id.addExpenseButton);

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //navigate to add expense fragment
                NavHostFragment.findNavController(ExpensesFragment.this)
                        .navigate(R.id.action_nav_expenses_to_add_expense_fragment);
            }
        });


        // get bundle from add expense fragment
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String expenseName = bundle.getString("name");
            double expenseAmount = bundle.getDouble("amount");
            String expenseDate = bundle.getString("date");
            String expenseCategory = bundle.getString("category");
            String categoryID = String.valueOf(Math.abs(expenseCategory.hashCode()));

            String expenseID = String.valueOf(Math.abs(expenseName.hashCode()));

            // create new expense object
            Expenses expense = new Expenses(expenseID, expenseAmount, expenseDate, categoryID, expenseName);

            //set expenses
            expense.setExpenseId(expenseID);
            expense.setAmount(expenseAmount);
            expense.setDate(expenseDate);
            expense.setCategoryId(expenseCategory);
            expense.setNotes(expenseName);


            // Find the ListView by its ID
            ListView listView = root.findViewById(R.id.listView);

            // Create a new adapter that takes an empty list of expenses as input
            ExpensesArrayAdapter adapter = new ExpensesArrayAdapter(requireContext(), expense);

            // Set the adapter on the ListView
            listView.setAdapter(adapter);

            // Notify the adapter to update the list of expenses in the UI
            adapter.notifyDataSetChanged();







        }


    return root;
}
}