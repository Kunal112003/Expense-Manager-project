package com.example.ai_project_10.ui.budget;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


import com.example.ai_project_10.R;
import com.example.ai_project_10.UsernamePersistent;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class BudgetFragment extends Fragment {

    private BudgetViewModel budgetViewModel;

    private TextView budgetName;

    private TextView SpendingAmount;

    private TextView budgetAmount;

    private Button editButton;

    private FirebaseFirestore db;


    private UsernamePersistent usernamePersistent;
    private AtomicMarkableReference<Object> database;

    private List<Boolean> editModeList = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BudgetViewModel searchViewModel =
                new ViewModelProvider(this).get(BudgetViewModel.class);

        View root = inflater.inflate(R.layout.fragment_budget, container, false);



        db = FirebaseFirestore.getInstance();







        ArrayList<String> categoryNames = new ArrayList<String>();

        categoryNames.add("Food");
        categoryNames.add("Transportation");
        categoryNames.add("Entertainment");
        categoryNames.add("Shopping");
        categoryNames.add("Health");
        categoryNames.add("Education");
        categoryNames.add("Gifts");
        categoryNames.add("Investments");
        categoryNames.add("Housing");
        categoryNames.add("Travel");
        categoryNames.add("Personal Care");
        categoryNames.add("Other");



        ListView listView = root.findViewById(R.id.listView_budget);

        BudgetArrayAdapter budgetArrayAdapter = new BudgetArrayAdapter(getContext(), R.layout.item_budgets);

        listView.setAdapter(budgetArrayAdapter);


        System.out.println("size:" + categoryNames.size());




        //get the username from the firebase database

        usernamePersistent = new UsernamePersistent( getContext());

        String username = usernamePersistent.fetchUsername();



        //click edit button to navigate to update budget fragment

        editButton = root.findViewById(R.id.button_addBudget);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(BudgetFragment.this)
                        .navigate(R.id.action_nav_budget_to_update_budget_fragment);
            }
        });



        //get the category names from the firebase database

        //get the budget limit from the firebase database

        //get the spending amount from the firebase database

        //get the budget amount from the firebase database


        for(String categoryName : categoryNames) {

            db.collection("Budget")
                    .document(categoryName)
                    .collection(username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot.isEmpty()) {

                                //get category id by hashing the category name
                                Integer categoryID = (categoryName.hashCode());

                                Budget budget = new Budget(categoryID, 0.0, categoryName);

                                budgetArrayAdapter.add(budget);

                                db.collection("Budget")
                                        .document(categoryName)
                                        .collection(username)
                                        .document(categoryName)
                                        .set(budget);

                                budgetArrayAdapter.notifyDataSetChanged();

                            } else {
                                //get category id by hashing the category name
                                Integer categoryID = (categoryName.hashCode());

                                db.collection("Budget")
                                        .document(categoryName)
                                        .collection(username)
                                        .document(categoryName)
                                        .get()
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                DocumentSnapshot documentSnapshot = task1.getResult();
                                                if (documentSnapshot.exists()) {
                                                    Budget budget = documentSnapshot.toObject(Budget.class);

                                                    budgetArrayAdapter.add(budget);

                                                    budgetArrayAdapter.notifyDataSetChanged();


                                                }
                                            }
                                        });

                            }

                        }
                    });



        }




        return root;
    }


}