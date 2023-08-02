package com.example.ai_project_10.ui.expenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ai_project_10.AddEditExpenseFragment;
import com.example.ai_project_10.R;






public class ExpensesFragment extends Fragment {



    private Button addExpenseButton;


public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExpensesViewModel expensesViewModel =
                new ViewModelProvider(this).get(ExpensesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_expenses, container, false);

//        final TextView textView = root.findViewById(R.id.text_expenses);
//        expensesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        addExpenseButton = root.findViewById(R.id.addExpenseButton);

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //navigate to add expense fragment
                NavHostFragment.findNavController(ExpensesFragment.this)
                        .navigate(R.id.action_nav_expenses_to_add_expense_fragment);








            }
        });
    return root;
}
}