package com.example.habitstodotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements RegisterFragment.onClick {
    Button btnMainRegister,btnMainLogin;
    LinearLayout mainLayout;
    private FirebaseAuth mAuth;
    BottomSheetDialogFragment now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMainRegister = findViewById(R.id.btnMainRegister);
        btnMainLogin = findViewById(R.id.btnMainLogin);

        mAuth = FirebaseAuth.getInstance();
        btnMainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now = new RegisterFragment();
                now.show(getSupportFragmentManager(),now.getTag());

            }
        });
        btnMainRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now = new LoginFragment();
                now.show(getSupportFragmentManager(),now.getTag());
            }
        });
    }

    private boolean updateTheme(int mode){
        AppCompatDelegate.setDefaultNightMode(mode);

        return true;
    }

    @Override
    public void Click() {
        if(now instanceof RegisterFragment){
            now.dismiss();
            now = new LoginFragment();
            now.show(getSupportFragmentManager(),now.getTag());
        }else if(now instanceof LoginFragment){
            now.dismiss();
            now = new RegisterFragment();
            now.show(getSupportFragmentManager(),now.getTag());

        }

    }
}