package com.example.habitstodotracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class LiquidAdapter extends FragmentPagerAdapter  {
    private ArrayList<PageFragment> listFrag = new ArrayList<>();

    public LiquidAdapter(@NonNull FragmentManager fm) {
        super(fm);
        for (int i = 1; i <= 3; i++) {
            PageFragment temp = new PageFragment();
            Bundle bun = new Bundle();
            bun.putInt("POS",i);
            temp.setArguments(bun);
            listFrag.add(temp);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listFrag.get(position);
    }

    @Override
    public int getCount() {
        return listFrag.size();
    }
}
