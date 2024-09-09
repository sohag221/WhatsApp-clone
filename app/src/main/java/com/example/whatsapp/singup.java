package com.example.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp.databinding.ActivitySingupBinding;
import com.example.whatsapp.model.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class singup extends AppCompatActivity {

    ActivitySingupBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 101;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // User is already logged in, navigate to MainActivity
            Intent intent = new Intent(singup.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the signup activity
            return; // Stop further execution
        }
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(singup.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Please, wait....!");

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Ensure this is set in your strings.xml
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.googleBt.setOnClickListener(v -> signInWithGoogle());

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
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Create a new user object
                                Users users = new Users(userName, email, password);

                                // Get the UID of the newly created user
                                String id = task.getResult().getUser().getUid();

                                // Save user data in Firebase Realtime Database under "Users" node
                                database.getReference().child("Users").child(id).setValue(users);

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

        binding.alreadyHaveAccount.setOnClickListener(v -> {
            Intent intent = new Intent(singup.this, singin.class);
            startActivity(intent);
        });
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // Google Sign In was successful, authenticate with Firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Handle error
                Log.w("Google Sign-In", "Google sign-in failed", e);
                binding.email.setError("Google sign-in failed: " + e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Users users = new Users(account.getDisplayName(), account.getEmail(), account.getPhotoUrl().toString());
                        String id = task.getResult().getUser().getUid();
                        database.getReference().child("Users").child(id).setValue(users);

                        // Navigate to the next screen
                        Intent intent = new Intent(singup.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Close the signup activity to avoid returning to it
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Firebase Auth", "signInWithCredential:failure", task.getException());
                        Toast.makeText(singup.this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }



}