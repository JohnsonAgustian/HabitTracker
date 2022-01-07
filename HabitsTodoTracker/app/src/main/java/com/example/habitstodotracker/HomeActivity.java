package com.example.habitstodotracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.habitstodotracker.databinding.ActivityHomeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements AddPopUpMenu.onClick, FirebaseAuth.AuthStateListener {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment now = new AddPopUpMenu();
                now.show(getSupportFragmentManager(),now.getTag());
            }
        });

        binding.navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment f;
                switch (item.getItemId()){
                    case R.id.mnHome:
                        f = new HomeFragment();

                        break;
                    case R.id.mnProfile:
                        f = new ProfileFragment();
                        break;
                    case R.id.mnHabbit:
                        f = new HabitFragment();
                        break;
                    default:
                        f = ToDoFragment.newInstance();
                        break;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerHome, f)
                        .commit();

                return false;
            }
        });
        if(savedInstanceState == null){
            binding.navigation.setSelectedItemId(R.id.mnHome);
        }
    }

    @Override
    public void Click(int i) {
        Intent intent;
        if(i == 1){
            intent = new Intent(this,AddHabit.class);
        }else{
            intent = new Intent(this,AddHabit.class);
        }
        startActivity(intent);
    }



    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser() == null){

            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
            return;
        }
    }
}