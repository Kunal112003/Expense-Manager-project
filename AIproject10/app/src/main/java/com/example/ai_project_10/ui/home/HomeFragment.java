package com.example.ai_project_10.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ai_project_10.Categories;
import com.example.ai_project_10.CategoryAdapterFragment;
import com.example.ai_project_10.R;
import com.example.ai_project_10.UsernamePersistent;
import com.example.ai_project_10.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


    public class HomeFragment extends Fragment {

        private ArrayList<Categories> categories = new ArrayList<>(); // List of category data

        private ViewPager2 viewPager2;

        private FirebaseFirestore db = FirebaseFirestore.getInstance();

        private UsernamePersistent usernamePersistent;

        private CategoryAdapterFragment categoryAdapter;

        private RecyclerView recyclerView;

        public HomeFragment() {
            // Required empty public constructor
        }

        public static HomeFragment newInstance() {
            return new HomeFragment();
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_home, container, false);

            usernamePersistent = new UsernamePersistent(requireContext());
            String username = usernamePersistent.fetchUsername();

            viewPager2 = root.findViewById(R.id.viewPager);

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



            //--------------------------------------------------------------------------------------------------------//
            // Get the data from the database
            for (String categoryName : categoryNames) {
                db.collection("Budget").document(categoryName).collection(username).document(categoryName).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        Double budgetAmount = task.getResult().getDouble("budgetProgress");

                        categories.add(new Categories(categoryName, budgetAmount));

                        System.out.println(categories);

//                        categoryAdapter.notifyDataSetChanged();

                        if (categories.size() == categoryNames.size()) {
                            categoryAdapter = new CategoryAdapterFragment(categories);
                            viewPager2.setAdapter(categoryAdapter);
                            viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                        }


                    }
                });
            }



            // Auto-scroll ViewPager2 every 10 seconds
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int currentItem = viewPager2.getCurrentItem();
                    if (currentItem < categoryAdapter.getItemCount() - 1) {
                        viewPager2.setCurrentItem(currentItem + 1);
                    } else {
                        viewPager2.setCurrentItem(0);
                    }
                    handler.postDelayed(this, 10000); // 10 seconds
                }
            }, 10000); // 10 seconds initial delay


            //--------------------------------------------------------------------------------------------------------//

            ProgressBar progressBar = root.findViewById(R.id.progressBar);

            // Get the total budget amount

            final Double[] totalMonthlyBudget = {0.0};
            final Double[] totalMonthlySpending = {0.0};

            for (String categoryName : categoryNames) {
                db.collection("Budget").document(categoryName).collection(username).document(categoryName).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Double budgetAmount = task.getResult().getDouble("budgetLimit");
                        Double budgetProgress = task.getResult().getDouble("budgetProgress");


                                totalMonthlyBudget[0] += budgetAmount;
                                totalMonthlySpending[0] += budgetProgress;

                                System.out.println(totalMonthlyBudget[0]);
                                System.out.println(totalMonthlySpending[0]);

                            progressBar.setProgress((int) (totalMonthlySpending[0] / totalMonthlyBudget[0] * 100));

                    }
                });
            }

            //--------------------------------------------------------------------------------------------------------//

            // get the highest spending category and its amount



//








            return root;
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}