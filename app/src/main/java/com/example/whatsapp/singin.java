package com.example.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.whatsapp.databinding.ActivitySinginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class singin extends AppCompatActivity {

    ActivitySinginBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySinginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        auth= FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(singin.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please, wait....!");

        binding.LogInBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String email = binding.email.getText().toString().trim();
                String password = binding.password.getText().toString().trim();

                // Validate input fields
                boolean isValid = true;

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
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            Intent intent = new Intent(singin.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(singin.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        binding.clickForSingUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(singin.this, singup.class);
                startActivity(intent);
            }
        });

    }
}