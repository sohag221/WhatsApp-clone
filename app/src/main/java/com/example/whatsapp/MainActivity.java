package com.example.whatsapp;

import static com.example.whatsapp.R.*;
import static com.example.whatsapp.R.id.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatsapp.Adapters.FragmentsAdapter;
import com.example.whatsapp.R;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.whatsapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FragmentsAdapter((getSupportFragmentManager())));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
       if (id==R.id.setting){
           Toast.makeText(this, "Setting is clicked", Toast.LENGTH_SHORT).show();
       }if (id== R.id.log_out){
           Toast.makeText(this, "Log out is clicked", Toast.LENGTH_SHORT).show();
       }if (id==R.id.search){
           Toast.makeText(this, "Search is clicked", Toast.LENGTH_SHORT).show();
       }if (id== R.id.camera){
           Toast.makeText(this, "Camera is clicked", Toast.LENGTH_SHORT).show();
       }
       return true;
    }
}