package com.example.habitstodotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cuberto.liquid_swipe.LiquidPager;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        LiquidPager start = findViewById(R.id.pagerStart);
        start.setAdapter(new LiquidAdapter(getSupportFragmentManager()));
    }

}