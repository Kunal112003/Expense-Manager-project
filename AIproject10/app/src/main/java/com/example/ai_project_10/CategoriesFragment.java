package com.example.ai_project_10;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CategoriesFragment extends Fragment {

    private String CategoryName;
    private Double CategoryAmountSpent;



    public CategoriesFragment() {
        // Required empty public constructor
    }

    public CategoriesFragment(String categoryName, Double categoryAmountSpent) {
        CategoryName = categoryName;
        CategoryAmountSpent = categoryAmountSpent;
    }

    public static CategoriesFragment newInstance(String categoryName, Double amountSpent) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString("CategoryName", categoryName);
        args.putDouble("CategoryAmountSpent", amountSpent);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categories, container, false);


        Bundle args = getArguments();
        if (args != null) {
            CategoryName = args.getString("CategoryName");
            CategoryAmountSpent = args.getDouble("CategoryAmountSpent");
        }

        System.out.println(CategoryName);
        System.out.println(CategoryAmountSpent);

        TextView CategoryNameTextView = view.findViewById(R.id.categoryNameTextView);
        TextView CategoryAmountSpentTextView = view.findViewById(R.id.amountSpentTextView);

        CategoryNameTextView.setText(CategoryName);
        CategoryAmountSpentTextView.setText(String.valueOf(CategoryAmountSpent));

        System.out.println(CategoryNameTextView.getText());
        System.out.println(CategoryAmountSpentTextView.getText());



        return view;

    }


}