package com.example.ai_project_10;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.common.hash.Hashing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class RegisterFragment extends Fragment {

        private EditText editTextEmail;

        private EditText editTextUsername;

        private EditText editTextPassword;

        private Button buttonLogin;

        private FirebaseAuth mAuth;

        private FirebaseFirestore db;

        String userid;

        public static byte [] getSHA(String input) throws NoSuchAlgorithmException
        {
            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }

        public static String toHexString(byte [] hash)
        {
            // Convert byte array into signum representation
            BigInteger number = new BigInteger(1, hash);

            // Convert message digest into hex value
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros
            while (hexString.length() < 32)
            {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        }

        public static String sha256(String input) throws NoSuchAlgorithmException
        {
            return toHexString(getSHA(input));
        }



        @SuppressLint("MissingInflatedId")
        @Override
        public View onCreateView(
                LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_register, container, false);
            editTextUsername = view.findViewById(R.id.username);
            editTextPassword = view.findViewById(R.id.password);
            editTextEmail = view.findViewById(R.id.email);
            buttonLogin = view.findViewById(R.id.login);

            FirebaseFirestore db = FirebaseFirestore.getInstance();


            mAuth = FirebaseAuth.getInstance();

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = editTextUsername.getText().toString().trim();
                    String password = editTextPassword.getText().toString().trim();
                    String email = editTextEmail.getText().toString().trim();
                    String hashedUsername = "";

                    Map<String, String> items = new HashMap<>();
                    Map<Double, String> budgets = new HashMap<>();
                    Map<Double, String> expenses = new HashMap<>();





                    if (username.isEmpty()) {
                        editTextUsername.setError("Username is required");
                        editTextUsername.requestFocus();
                        return;
                    }

                    if (email.isEmpty()) {
                        editTextEmail.setError("Email is required");
                        editTextEmail.requestFocus();
                        return;
                    }

                    if (password.isEmpty()) {
                        editTextPassword.setError("Password is required");
                        editTextPassword.requestFocus();
                        return;
                    }

                    if (password.length() < 6) {
                        editTextPassword.setError("Password should be atleast 6 character long");
                        editTextPassword.requestFocus();
                        return;
                    }



                            User user = new User(username,email, password, hashedUsername, items, budgets, expenses); // Create a new user object with username and email
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            //set user
                            user.setEmail(email);
                            user.setPassword(password);
                            user.setUsername(username);
                            user.setItems(items);
                            user.setBudgets(budgets);
                            user.setExpenses(expenses);



                            try {
                                hashedUsername = sha256(username);
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }

                            user.setUser_id(hashedUsername);

                            System.out.println(hashedUsername);





                            // Store the user data in Firestore under a document with the username as its ID
                    String finalHashedUsername = hashedUsername;
                    db.collection("Users")
                                    .document(username)
                                    .set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getActivity(), "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("username", username);
                                        bundle.putString("userid", finalHashedUsername);
                                        //send bundle to home fragment
                                        NavHostFragment.findNavController(RegisterFragment.this)
                                                .navigate(R.id.action_nav_register_to_nav_home, bundle);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(getActivity(), "Failed to register user", Toast.LENGTH_LONG).show();
                                    });

                }
            });

            return view;
        }
    }







