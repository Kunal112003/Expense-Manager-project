package com.example.ai_project_10.ui.expenses;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ai_project_10.R;
import com.example.ai_project_10.UsernamePersistent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.AlertDialog;



public class ExpensesFragment extends Fragment {

    private Button addExpenseButton;
    private ArrayList<Expenses> expensesList = new ArrayList<>();
    private ExpensesArrayAdapter expensesArrayAdapter;


    private ExpensesViewModel expensesViewModel;

    private UsernamePersistent usernamePersistent;


    public ExpensesFragment() {
        // Required empty public constructor
    }


    public void deleteExpense(String id) {
        // Delete the expense from the database and from the user's expenses list and notify the changes and reduce the



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Expense deleted");
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error deleting expense", e);
                });
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_expenses, container, false);


        addExpenseButton = root.findViewById(R.id.addExpenseButton);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to add expense fragment
                NavHostFragment.findNavController(ExpensesFragment.this)
                        .navigate(R.id.action_nav_expenses_to_add_expense_fragment);
            }
        });

        // Find the ListView by its ID
        ListView listView = root.findViewById(R.id.listView);

        // Create a new adapter with the expenses list
        expensesArrayAdapter = new ExpensesArrayAdapter(requireContext(), expensesList);

        // Set the adapter on the ListView
        listView.setAdapter(expensesArrayAdapter);


        // ...






// ...










        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayList<HashMap> expensesListUser = new ArrayList<>();
        HashMap<String, Double> expenseUser = new HashMap<>();


        //get data from firestore if username is same as current user
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Initialize the ViewModel
        expensesViewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);

        // Observe changes in the expenses list and update the adapter
        expensesViewModel.getExpensesListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                // Update the expensesList in the adapter and notify the changes
                expensesList.clear();
                expensesList.addAll(expenses);
                expensesArrayAdapter.notifyDataSetChanged();
            }
        });


        //get current user
        usernamePersistent = new UsernamePersistent(requireContext());
        String username = usernamePersistent.fetchUsername();

        //get data from firestore collection expenses if username is same as current user if it exists



        db.collection("expenses")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    private static final String TAG = "ExpensesFragment";

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: task is successful");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                //get data from firestore
                                String expenseName = document.getString("expenseName");
                                String expenseCategory = document.getString("categoryId");
                                String expenseDate = document.getString("date");
                                Double expenseAmount = document.getDouble("amount");
                                String expenseNote = document.getString("notes");
                                String expenseUsername = document.getString("username");
                                String expenseId = document.getString("id");

                                // Find the ListView by its ID
                                ListView listView = view.findViewById(R.id.listView);




                                //create new expense object

                                Expenses expense = new Expenses(expenseName,expenseAmount,expenseDate,expenseCategory);

                                //only add expensename,expenseamount,expensedate and category to list
                                expensesList.add(expense);

                                //add expense to expensesListUser
                                expenseUser.put(expenseId,expenseAmount);
                                expensesListUser.add(expenseUser);
                                System.out.println("expensesListUser: " + expensesListUser);

                                //Long click listener to delete expenses

                                listView.setOnItemLongClickListener((parent, view, position, id) -> {
                                    // Get the selected expense



                                    //get expense id from expensesListUser at position and expense id is the key
                                    System.out.println("expense:"+ expensesListUser.get(position));

                                    // Confirm if the user wants to delete the expense
                                    new AlertDialog.Builder(requireContext())
                                            .setTitle("Delete Expense")
                                            .setMessage("Are you sure you want to delete this expense?")
                                            .setPositiveButton("Delete", (dialog, which) -> {
                                                // Call the deleteExpense method to delete the expense
                                                System.out.println("amount: " + expenseAmount);
                                                deleteExpense(expenseId);

//                                                System.out.println("amount: " + expenseAmount);

                                                //reduce the expense amount from the category
                                                db.collection("Budget")
                                                        .document(expenseCategory)
                                                        .collection(username)
                                                        .document(expenseCategory)
                                                        .update("budgetProgress", FieldValue.increment(-expenseAmount));

                                                //remove the expense from the expensesListUser
//                                                expensesListUser.remove(position);

                                                //remove the hashmap of expense from expenses list in Users collection
                                                db.collection("Users")
                                                        .document(username)
                                                        .update("expenses", FieldValue.arrayRemove(expensesListUser.get(position)));




                                                // Remove the expense from the list and update the adapter
                                                expensesList.remove(position);
                                                expensesArrayAdapter.notifyDataSetChanged();
                                            })
                                            .setNegativeButton("Cancel", null)
                                            .show();

                                    return true;
                                });



                            }


                            expensesArrayAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }



                        //notify adapter
                        expensesArrayAdapter.notifyDataSetChanged();
                    }
                });




    }
}
