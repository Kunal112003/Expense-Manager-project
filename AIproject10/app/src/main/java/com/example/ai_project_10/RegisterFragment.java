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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterFragment extends Fragment {

        private EditText editTextEmail;

        private EditText editTextUsername;

        private EditText editTextPassword;

        private Button buttonLogin;

        private FirebaseAuth mAuth;

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



                            User user = new User(username,email, password); // Create a new user object with username and email
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            //set user
                            user.setEmail(email);
                            user.setPassword(password);
                            user.setUsername(username);



                            // Store the user data in Firestore under a document with the username as its ID
                            db.collection("Users")
                                    .document(username)
                                    .set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getActivity(), "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("username", username);
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


