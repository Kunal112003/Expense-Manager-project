package com.example.ai_project_10;

import static android.content.ContentValues.TAG;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;


public class LoginFragment extends Fragment {

    private EditText editTextUsername;

    private EditText editTextPassword;

//    private EditText editTextEmail;


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;




//    private void addUserToFirestore(String username, String email, String password, String UserId,
//                                    Map<String,String> item) {
//        // Create a new User object with username and email (you can modify this based on your actual data structure)
//        User user = new User(username,email, password, UserId,);
//
//        // Add the user data to Firestore under a document with the user ID as its name
//        FirebaseFirestore.getInstance().collection("Users")
//                .document(username)
//                .set(user)
//                .addOnSuccessListener(aVoid -> {
//                    // User data added successfully, navigate to home fragment
//                    Bundle bundle = new Bundle();
//                    bundle.putString("username", email);
//                    NavHostFragment.findNavController(LoginFragment.this)
//                            .navigate(R.id.action_nav_login_to_nav_home, bundle);
//                })
//                .addOnFailureListener(e -> {
//                    // Failed to add user data to Firestore
//                    Log.e(TAG, "Error adding user data to Firestore: ", e);
//                    Toast.makeText(requireContext(), "Error occurred. Please try again.",
//                            Toast.LENGTH_SHORT).show();
//                });
//    }


    private void checkIfUserExists(String username,String password) {
        // Create a reference to the "users" collection in Firestore
        CollectionReference usersRef = FirebaseFirestore.getInstance().collection("Users");

        // Query the collection to find the user document with the given userId
        usersRef.document(username).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //if user exists in firestore check is password is correct
                                if(document.get("password").equals(password)) {


                                    //if password is correct navigate to home fragment
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", username);
                                    NavHostFragment.findNavController(LoginFragment.this)
                                            .navigate(R.id.action_nav_login_to_nav_home, bundle);
                                }else{
                                    //if password is incorrect show error message
                                    Toast.makeText(requireContext(), "Incorrect password. Please try again.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // User does not exist in Firestore,
                                //show error message and tell to register user
                                Toast.makeText(requireContext(), "User does not exist. Please register.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Failed to query Firestore
                            Log.e(TAG, "Error checking if user exists in Firestore: ", task.getException());
                            Toast.makeText(requireContext(), "Error occurred. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }









//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Bundle bundle = new Bundle();
//            bundle.putString("username", currentUser.getEmail());
//            //send bundle to home fragment
//            NavHostFragment.findNavController(LoginFragment.this)
//                    .navigate(R.id.action_nav_login_to_nav_home, bundle);
//        }
//    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        editTextUsername = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);


        Button buttonLogin = view.findViewById(R.id.login);

        Button buttonRegister = view.findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if register button is clicked then navigate to register fragment
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_nav_login_to_nav_register);
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String password = editTextPassword.getText().toString();
                String username = editTextUsername.getText().toString();

                if(TextUtils.isEmpty(username)){
                    editTextUsername.setError("Username is required");
                    return;
                }


                if(TextUtils.isEmpty(password)){
                    editTextPassword.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    editTextPassword.setError("Password must be >= 6 Characters");
                    return;
                }




                checkIfUserExists(username,password);








            }
        });
        return view;
    }


}

