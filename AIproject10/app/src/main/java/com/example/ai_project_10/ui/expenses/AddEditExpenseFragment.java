package com.example.ai_project_10.ui.expenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ai_project_10.R;
import com.example.ai_project_10.UsernamePersistent;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddEditExpenseFragment extends Fragment {


    private static final String TAG = "AddExpenseFragment";

    private Button addExpenseButton;

    private UsernamePersistent usernamePersistent;



    //spinner
    private Spinner spinner;

    private ArrayAdapter<CharSequence> spinnerAdapter;

    public AddEditExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pop_up_window_add_expenses, container, false);

        addExpenseButton = view.findViewById(R.id.add_expense_button);

        //spinner
        spinner = view.findViewById(R.id.expenseCategory);

        // Create an ArrayAdapter using the string array and a default spinner layout
        spinnerAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.categories_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
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
                // Do nothing if no category is selected
            }
        });

        usernamePersistent = new UsernamePersistent(requireContext());
        String username = usernamePersistent.fetchUsername();


        // Find the UI elements by their IDs
        EditText editTextAmount = view.findViewById(R.id.expenseAmount);
        DatePicker datePicker = view.findViewById(R.id.expense_date);
        // Find the Spinner for category (if you added it to the layout)
        EditText editTextName = view.findViewById(R.id.expenseName);

        // Set click listener on the "Save Expense" button
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values entered by the user
                double amount = Double.parseDouble(editTextAmount.getText().toString());
                // Retrieve the selected date from the DatePicker
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1; // Month is zero-indexed, so add 1
                int year = datePicker.getYear();
                // Create a formatted date string (you can choose the format you prefer)
                String date = String.format("%02d/%02d/%04d", month, day, year);

                // Get the selected category from the Spinner (if you added it to the layout)
                String name = editTextName.getText().toString();

                //category
                String category = spinner.getSelectedItem().toString();
                String categoryID = String.valueOf(Math.abs(category.hashCode()));


                // Create a new random expense ID
                String expenseID = String.valueOf(Math.abs(name.hashCode()));


                //expensesListUser
                ArrayList<HashMap> expensesListUser = new ArrayList<>();
                HashMap<String, Double> expenseUser = new HashMap<>();


                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Create a new expense object
                Expenses expense = new Expenses(expenseID, amount, date, categoryID, name,username);

                // Add a new document with a generated ID
                db.collection("expenses")
                        .document(expenseID)
                        .set(expense);


                //add to the end expenses array list in firebase under users collection
                expenseUser.put(expenseID, amount);
                expensesListUser.add(expenseUser);
                db.collection("users")
                        .document(username)
                        .update("expenses", FieldValue.arrayUnion(expenseUser));


                //add the expense amount to the budget collection in firebase
                db.collection("Budget").document(category).collection(username).document(category).update("budgetProgress", FieldValue.increment(amount));









                //bundle
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putDouble("amount", amount);
                bundle.putString("date", date);
                bundle.putString("category", categoryID);


                //navigate to add expense fragment
                NavHostFragment.findNavController(AddEditExpenseFragment.this)
                        .navigate(R.id.action_add_expense_fragment_to_nav_expenses, bundle);


            }
        });



        return view;
    }


}
