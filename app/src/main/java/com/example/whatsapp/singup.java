package com.example.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp.databinding.ActivitySingupBinding;
import com.example.whatsapp.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class singup extends AppCompatActivity {

    ActivitySingupBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(singup.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Please, wait....!");

        binding.singUPBt.setOnClickListener(v -> {
            // Get user input
            String userName = binding.userName.getText().toString().trim();
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            // Validate input fields
            boolean isValid = true;

            if (userName.isEmpty()) {
                binding.userName.setError("Please enter your username");
                isValid = false;
            }

            if (email.isEmpty()) {
                binding.email.setError("Please enter your email");
                isValid = false;
            }

            if (password.isEmpty()) {
                binding.password.setError("Please enter your password");
                isValid = false;
            }

            // Stop execution if input is invalid
            if (!isValid) {
                return;
            }

            progressDialog.show();
            // If inputs are valid, proceed with Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Create a new user object
                                Users users = new Users(userName, email, password);

                                // Get the UID of the newly created user
                                String id = task.getResult().getUser().getUid();

                                // Save user data in Firebase Realtime Database under "Users" node
                                database.getReference().child("Users").child(id).setValue(users);

                                progressDialog.dismiss();
                                // Navigate to the sign-in activity
                                Intent intent = new Intent(singup.this, singin.class);
                                startActivity(intent);
                            } else {
                                // Show the error message if signup fails
                                binding.email.setError(task.getException().getMessage());
                            }
                        }
                    });
        });
    }
}
