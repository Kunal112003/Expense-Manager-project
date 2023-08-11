package com.example.ai_project_10.ui.budget;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.navigation.fragment.NavHostFragment;

import com.example.ai_project_10.R;
import com.example.ai_project_10.UsernamePersistent;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateBudgetFragment extends BudgetFragment{


    private Button editButton;

    private Spinner spinner;

    private EditText budgetLimit;

    private ArrayAdapter<CharSequence> spinnerAdapter;

    private FirebaseFirestore db;

    private UsernamePersistent usernamePersistent;

    public UpdateBudgetFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.update_delete_budget, container, false);

        editButton = view.findViewById(R.id.UpdateBudget);

        spinner = view.findViewById(R.id.BudgetCategory);

        budgetLimit = view.findViewById(R.id.BudgetLimit);

// Create an ArrayAdapter using the string array and a default spinner layout
        spinnerAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.categories_array, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(spinnerAdapter);


        // Set an item selected listener to handle spinner selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected category from the spinner
                String selectedCategory = parent.getItemAtPosition(position).toString();

                // Use the selectedCategory as needed (e.g., store in the expense object)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // warn the user that they did not select a category

            }
        });

        usernamePersistent = new UsernamePersistent( getContext());

        String username = usernamePersistent.fetchUsername();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = spinner.getSelectedItem().toString();
                String limit = budgetLimit.getText().toString();

//                Map<String, Object> budget = new HashMap<>();
//                Budget budget = new Budget();
//                budget.setCategory(category);
//                budget.setBudgetLimit(Double.parseDouble(limit));

                System.out.println("Category: " + category);
                System.out.println("Limit: " + limit);

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("Budget").document(category).collection(username).document(category).update("budgetLimit", Double.parseDouble(limit));

                NavHostFragment.findNavController(UpdateBudgetFragment.this)
                        .navigate(R.id.action_update_budget_fragment_to_nav_budget);
            }

        });



        return view;
    }

}
