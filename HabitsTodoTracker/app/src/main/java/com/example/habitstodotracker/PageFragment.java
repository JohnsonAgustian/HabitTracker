package com.example.habitstodotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PageFragment  extends Fragment {
    int posisi = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posisi = getArguments().getInt("POS");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int idLayout =(posisi == 1) ?R.layout.first_page : (posisi == 2) ? R.layout.second_page : R.layout.third_page;
        return inflater.inflate(idLayout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(posisi < 3){
            TextView skip = view.findViewById(R.id.btnSkip);
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(),MainActivity.class);
                    startActivity(i);
                }
            });
        }else{
            Button finish = view.findViewById(R.id.btn_finish_third);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(),MainActivity.class);
                    startActivity(i);
                }
            });
        }

    }

}
