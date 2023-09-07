package com.example.ai_project_10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapterFragment extends RecyclerView.Adapter<CategoryAdapterFragment.CategoryViewHolder> {

    private ArrayList<Categories> categoriesArrayList;

    // Constructor to initialize the adapter with data
    public CategoryAdapterFragment(ArrayList<Categories> categoriesArrayList) {
        this.categoriesArrayList = categoriesArrayList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_categories, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Categories category = categoriesArrayList.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categoriesArrayList.size();
    }

    // ViewHolder class to hold references to views
    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameTextView;
        TextView amountSpentTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
            amountSpentTextView = itemView.findViewById(R.id.amountSpentTextView);
        }

        // Bind data to the views
        public void bind(Categories category) {
            categoryNameTextView.setText(category.getName());
            amountSpentTextView.setText(String.valueOf(category.getAmountSpent()));
        }
    }
}
