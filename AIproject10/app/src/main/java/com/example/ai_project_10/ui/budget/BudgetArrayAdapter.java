package com.example.ai_project_10.ui.budget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ai_project_10.R;
import com.example.ai_project_10.UsernamePersistent;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BudgetArrayAdapter extends ArrayAdapter<Budget> {

    private List<Boolean> editModeList = new ArrayList<>();

    private FirebaseFirestore db;
    private UsernamePersistent usernamePersistent;




    public BudgetArrayAdapter(Context context, int item_budgets) {
        super(context, item_budgets);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Budget budget = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_budgets, parent, false);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        usernamePersistent = new UsernamePersistent(getContext());
        String username = usernamePersistent.fetchUsername();

        final Double[] spendingAmount = {0.0};

        TextView budgetName = convertView.findViewById(R.id.textView_budgetName);
        TextView budgetAmount = convertView.findViewById(R.id.textView_budgetAmountSpent);
        TextView budgetLimit = convertView.findViewById(R.id.TextView_budget);


        budgetName.setText(budget.getCategory());
        budgetLimit.setText(String.valueOf(budget.getBudgetLimit()));



        //get the budget amount spent from the database and set it to budgetAmount
        // Get the spending amount from the Firebase database

            Task<DocumentSnapshot> spendingSnapshot = db.collection("Budget")
                    .document(budget.getCategory())
                    .collection(username)
                    .document(budget.getCategory())
                    .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        spendingAmount[0] = document.getDouble("budgetProgress");
                                        budgetAmount.setText(String.valueOf(spendingAmount[0]));
                                    }
                                }
                            });






//        System.out.println(bu/dget.getCategory()+": " + spendingAmount);

        budgetAmount.setText(String.valueOf(spendingAmount[0]));

        //check if the budget is over the limit
//        if (spendingAmount[0] > budget.getBudgetLimit()) {
//            budgetAmount.setTextColor(getContext().getResources().getColor(R.color.red));
//            budgetName.setTextColor(getContext().getResources().getColor(R.color.red));
//        } else {
//            budgetAmount.setTextColor(getContext().getResources().getColor(R.color.green));
//            budgetName.setTextColor(getContext().getResources().getColor(R.color.green));
//        }

        //if spending amount is over the limit, give warning message
        if (spendingAmount[0] > budget.getBudgetLimit()) {
            budgetAmount.setTextColor(getContext().getResources().getColor(R.color.red));
            budgetName.setTextColor(getContext().getResources().getColor(R.color.red));
            budgetLimit.setTextColor(getContext().getResources().getColor(R.color.red));
            budgetLimit.setText("You are over the limit!");

        } else {
            budgetAmount.setTextColor(getContext().getResources().getColor(R.color.green));
            budgetName.setTextColor(getContext().getResources().getColor(R.color.green));
            budgetLimit.setTextColor(getContext().getResources().getColor(R.color.green));
            budgetLimit.setText("You are under the limit!");
        }
















        return convertView;
    }

}
