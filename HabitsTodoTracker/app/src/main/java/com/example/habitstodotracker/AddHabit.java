package com.example.habitstodotracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.habitstodotracker.databinding.ActivityAddHabitBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.IconSpinnerItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddHabit extends AppCompatActivity implements FirebaseAuth.AuthStateListener,PickerFragment.DialogTimeListener {
    private ActivityAddHabitBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddHabitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addEdCategory.setSpinnerAdapter(new IconSpinnerAdapter(binding.addEdCategory));
        binding.addEdCategory.setItems(new ArrayList<IconSpinnerItem>(){
            {
                add(new IconSpinnerItem("Study",getApplicationContext().getDrawable(R.drawable.ic_study)));
                add(new IconSpinnerItem("Entertainment",getApplicationContext().getDrawable(R.drawable.ic_entertainment)));
                add(new IconSpinnerItem("Health",getApplicationContext().getDrawable(R.drawable.ic_health)));
                add(new IconSpinnerItem("Reading",getApplicationContext().getDrawable(R.drawable.ic_reading)));
                add(new IconSpinnerItem("Social",getApplicationContext().getDrawable(R.drawable.ic_social)));
                add(new IconSpinnerItem("Sports",getApplicationContext().getDrawable(R.drawable.ic_sports)));
                add(new IconSpinnerItem("Art",getApplicationContext().getDrawable(R.drawable.ic_art)));
            }
        });
        binding.addEdCategory.selectItemByIndex(0);
        binding.addEdCategory.setLifecycleOwner(this);
    }

    public void showTimePicker(View v){
        PickerFragment dialogFragment = new PickerFragment();
        dialogFragment.show(this.getSupportFragmentManager(), "timePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_habit,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        switch (item.getItemId()){
            case R.id.mnOk:
                String nama = binding.addEdTitle.getText().toString();
                String kategori = binding.addEdCategory.getText().toString();
                Long sec = Long.parseLong(binding.addEdMinutes.getText().toString());
                String prior = binding.spPrioritas.getSelectedItem().toString();
                String waktumulai = binding.addTvStart.getText().toString();
                if(nama.isEmpty()){
                    binding.addEdTitle.setError("Nama harus diisi!");
                    binding.addEdTitle.requestFocus();
                }
                else if(kategori.isEmpty()){
                    binding.addEdCategory.setError("Kategori harus diisi!");
                    binding.addEdCategory.requestFocus();
                }
                else if(binding.addEdMinutes.getText().toString().isEmpty()){
                    binding.addEdMinutes.setError("Durasi waktu harus diisi!");
                    binding.addEdMinutes.requestFocus();
                }
                else if(waktumulai.isEmpty()){
                    binding.addTvStart.setError("Waktu mulai harus diisi!");
                    binding.addTvStart.requestFocus();
                }else{
                    Habit temp = new Habit(nama,kategori,sec,prior,waktumulai,currentuser);
                    FirebaseFirestore.getInstance()
                            .collection("Habit")
                            .add(temp)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddHabit.this, "Sukses Menambahkan Habit", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddHabit.this, "Gagal Menambahkan Habit", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                    break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);

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

    @Override
    public void onDialogTimeSet(@Nullable String var1, int var2, int var3) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, var2);
        calendar.set(Calendar.MINUTE, var3);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        binding.addTvStart.setText(dateFormat.format(calendar.getTime()));

    }
}