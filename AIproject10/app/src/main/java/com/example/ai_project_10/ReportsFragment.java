package com.example.ai_project_10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class ReportsFragment extends Fragment{

    private boolean entryExists;

    public ReportsFragment() {
        // Required empty public constructor
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private UsernamePersistent usernamePersistent;

    private Integer count = 0;

    // Define a method to find the X-value for a given category
    private float findXValueForCategory(List<BarEntry> barEntries, String category) {
        for (int i = 0; i < barEntries.size(); i++) {
            if (barEntries.get(i).getData().equals(category)) {
                return i;
            }
        }
        return barEntries.size();
    }


    public String getBarLabel(BarEntry barEntry) {
        return barEntry.getData().toString(); // Assuming that the category name is stored as data for the BarEntry
    }

    // Define a method to retrieve expense details for a given expenseId
    private void retrieveExpenseDetails(String expenseId, List<PieEntry> pieEntries, ArrayList<String> expenseIds, PieChart pieChart, BarChart barChart, List<BarEntry> barEntries) {
        db.collection("expenses")
                .document(expenseId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            String category = (String) task.getResult().getData().get("categoryId");
                            double amount = (double) task.getResult().getData().get("amount");
                            //convert amount to float
                            float amountFloat = (float) amount;


                            // Create a new list to hold updated PieEntries
                            List<PieEntry> updatedPieEntries = new ArrayList<>(pieEntries);

                            //if two expenses have the same category, add their amounts together
                            boolean foundCategory = false;
                            for (PieEntry pieEntry : updatedPieEntries) {
                                if (pieEntry.getLabel().equals(category)) {
                                    pieEntry.setY(pieEntry.getValue() + amountFloat);
                                    foundCategory = true;
                                    break;
                                }
                            }

                            if (!foundCategory) {
                                updatedPieEntries.add(new PieEntry(amountFloat, category));
                            }

                            // Update the pieEntries list
                            pieEntries.clear();
                            pieEntries.addAll(updatedPieEntries);

                            // Update the barEntries list
                            boolean entryExists = false; // Reset the flag
                            for (BarEntry barEntry : barEntries) {
                                if (barEntry.getData().equals(category)) {
                                    // Update the existing BarEntry
                                    barEntry.setY(barEntry.getY() + amountFloat);
                                    entryExists = true;
                                    break;
                                }
                            }

                            if (!entryExists) {
                                // Add a new BarEntry with an appropriate X-value
                                barEntries.add(new BarEntry(findXValueForCategory(barEntries, category), amountFloat, category));
                            }

                            System.out.println("pieEntries: " + pieEntries);


                            count++;

                            System.out.println("count: " + count);


                            if (count == expenseIds.size()) {
                                setupPieChart(pieEntries, pieChart);
                                setupBarChart(barEntries, barChart);
                            }
                        }
                    }
                });
    }



    // Define a method to setup the pie chart
    private void setupPieChart(List<PieEntry> pieEntries, PieChart pieChart) {
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Expenses");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    //define a method to set up a bar chart
    private void setupBarChart(List<BarEntry> barEntries, BarChart barChart) {
        BarDataSet barDataSet = new BarDataSet(barEntries, "Expenses");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barChart.setFitBars(true);
        barChart.invalidate();

    }


    private void updateCharts(String selectedTimeRange, ArrayList<String> expenseIds, List<PieEntry> pieEntries, List<BarEntry> barEntries, PieChart pieChart, BarChart barChart,String expenseId,String username) {
        // Clear existing data from pieEntries and barEntries lists
        pieEntries.clear();
        barEntries.clear();

        // Retrieve data based on the selectedTimeRange
        if (selectedTimeRange.equals("Monthly")) {
            pieEntries.clear();
            barEntries.clear();

            // Query expenses for the current month
            // Modify your Firestore queries here
            //get current month
            System.out.println("expenseId: " + expenseId);

            count = 0;

            Calendar calendar = Calendar.getInstance();
            int currentMonth = calendar.get(Calendar.MONTH)+1;
            //check if the expenseId is in the current month
            db.collection("expenses")
                    .document(expenseId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                String date = (String) task.getResult().getData().get("date");

                                String[] dateParts = date.split("/");
                                int month = Integer.parseInt(dateParts[0]);
//                                System.out.println("month: " + month);
//                                System.out.println("currentMonth: " + currentMonth);
                                if (month == currentMonth) {
                                    Double amount = (Double) task.getResult().getData().get("amount");
                                    Float amountFloat = amount.floatValue();
                                    String category = (String) task.getResult().getData().get("categoryId");
                                    System.out.println("amount: " + amount);
                                    System.out.println("category: " + category);
                                    System.out.println("expenseId: " + expenseId);

                                    //if two expenses have the same category, add their amounts together
                                    for (PieEntry pieEntry : pieEntries) {
                                        if (pieEntry.getLabel().equals(category)) {
                                            amountFloat += pieEntry.getValue();
                                            pieEntries.remove(pieEntry);
                                            break;
                                        }
                                    }
                                    // Add your logic here to use the retrieved expense details, e.g., adding to pieEntries
                                    pieEntries.add(new PieEntry(amountFloat, category));


                                    //bar chart
                                    entryExists = false; // Reset the flag

                                    // Check if the BarEntry for the category already exists
                                    for (BarEntry barEntry : barEntries) {
                                        if (barEntry.getData().equals(category)) {
                                            // Update the existing BarEntry
                                            barEntry.setY(amountFloat);
                                            entryExists = true;
                                            break;
                                        }
                                    }

                                    if (!entryExists) {
                                        // Add a new BarEntry with an appropriate X-value
                                        barEntries.add(new BarEntry(findXValueForCategory(barEntries, category), amountFloat, category));
                                    }

                                    System.out.println("pieEntries: " + pieEntries);


                                }

                                count++;
                                System.out.println("count: " + count);

                                if (count == expenseIds.size()) {
                                    setupPieChart(pieEntries, pieChart);
                                    setupBarChart(barEntries, barChart);}
                            }
                        }
                    });




        } else if (selectedTimeRange.equals("Yearly")) {
            pieEntries.clear();
            barEntries.clear();
            // Query expenses for the current year
            // Modify your Firestore queries here

            //get current year
            Calendar calendar = Calendar.getInstance();

            int currentYear = calendar.get(Calendar.YEAR);

            count = 0;

            //check if the expenseId is in the current year
            db.collection("expenses")
                    .document(expenseId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                String date = (String) task.getResult().getData().get("date");

                                String[] dateParts = date.split("/");
                                int year = Integer.parseInt(dateParts[2]);

                                if (year == currentYear) {
                                    Double amount = (Double) task.getResult().getData().get("amount");
                                    Float amountFloat = amount.floatValue();
                                    String category = (String) task.getResult().getData().get("categoryId");
                                    System.out.println("amount: " + amount);
                                    System.out.println("category: " + category);
                                    System.out.println("expenseId: " + expenseId);

                                    //if two expenses have the same category, add their amounts together
                                    for (PieEntry pieEntry : pieEntries) {
                                        if (pieEntry.getLabel().equals(category)) {
                                            amountFloat += pieEntry.getValue();
                                            pieEntries.remove(pieEntry);
                                            break;
                                        }
                                    }
                                    // Add your logic here to use the retrieved expense details, e.g., adding to pieEntries
                                    pieEntries.add(new PieEntry(amountFloat, category));

                                    //bar chart
                                    entryExists = false; // Reset the flag
                                    //if two expenses have the same category, add their amounts together

                                    // Check if the BarEntry for the category already exists
                                    for (BarEntry barEntry : barEntries) {
                                        if (barEntry.getData().equals(category)) {
                                            // Update the existing BarEntry
                                            barEntry.setY(amountFloat);
                                            entryExists = true;
                                            break;
                                        }
                                    }
                                    if (!entryExists) {
                                        // Add a new BarEntry for the category
                                        barEntries.add(new BarEntry(barEntries.size(), amountFloat, category));
                                    }

//
                                System.out.println("pieEntries: " + pieEntries);
                                }


                                if (count == expenseIds.size()) {
                                    setupPieChart(pieEntries, pieChart);
                                    setupBarChart(barEntries, barChart);
                                }
                            }
                        }
                    });


        } else if (selectedTimeRange.equals("Total")) {
            pieEntries.clear();
            barEntries.clear();
            count = 0;
            // Query all expenses
            // Modify your Firestore queries here
            retrieveExpenseDetails(expenseId, pieEntries, expenseIds, pieChart, barChart, barEntries);
        }

        // Update pie chart and bar chart
        // Call your methods to set up charts with the new data
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        PieChart pieChart = view.findViewById(R.id.pieChart);
        BarChart barChart = view.findViewById(R.id.barChart);

        // Set up the left y-axis
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // Minimum value on the y-axis
        leftAxis.setGranularity(10f); // Interval between axis values

        // Set up the right y-axis (if needed)
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false); // Disable the right y-axis

        //top x axis false
        XAxis topAxis = barChart.getXAxis();
        topAxis.setEnabled(false);


        // remove grid lines
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);

        Spinner timeRangeSpinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.time_ranges, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeRangeSpinner.setAdapter(adapter);

        usernamePersistent = new UsernamePersistent(requireContext());
        String username = usernamePersistent.fetchUsername();

        List<PieEntry> pieEntries = new ArrayList<>();
        List<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> expenseIds = new ArrayList<>();

        // Main Firestore query to retrieve expenseIds and their details
        db.collection("Users")
                .document(username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            ArrayList<HashMap<String, String>> expenses = (ArrayList<HashMap<String, String>>) task.getResult().getData().get("expenses");

                            if (expenses != null) {
                                for (HashMap<String, String> expense : expenses) {
                                    String expenseId = expense.keySet().iterator().next();
                                    expenseIds.add(expenseId);
                                }

                                // Set up initial charts with "Total" time range

                                for (String expenseId : expenseIds) {
                                    updateCharts("Total", expenseIds, pieEntries, barEntries, pieChart, barChart, expenseId, username);
                                }


//                                updateCharts("Total", expenseIds, pieEntries, barEntries, pieChart, barChart, expenseIds.get(0), username);

//                                 Set listener for time range spinner
                                timeRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String selectedTimeRange = parent.getItemAtPosition(position).toString();

                                        // Update charts based on selected time range for each expenseId
                                        for (String expenseId : expenseIds) {
                                            updateCharts(selectedTimeRange, expenseIds, pieEntries, barEntries, pieChart, barChart, expenseId, username);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
//                                         Handle when nothing is selected
                                    }
                                });
                            }
                        }
                    }
                });

        return view;
    }

}












