package com.example.habitstodotracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Locale;

public class HabitAdapter extends FirestoreRecyclerAdapter<Habit,HabitAdapter.viewHolder> {
    private Context context;
    private HabitListener habitListener;

    public HabitAdapter(@NonNull FirestoreRecyclerOptions<Habit> options, Context context,HabitListener habitListener) {
        super(options);
        this.context = context;
        this.habitListener = habitListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Habit model) {
        String id = "ic_"+model.getKategori().substring(0,1).toLowerCase(Locale.ROOT) + model.getKategori().substring(1);
        holder.itemTvTitle.setText(model.getNama());
        holder.totaldurasi.setText(model.getSecond().toString() + "S");
        holder.itemTvDescription.setText(model.getStart());
        holder.itemPicture.setImageResource(context.getResources().getIdentifier(id,"drawable",context.getPackageName()));
        if(model.getKategori().equals("Tinggi")){
            holder.mainitemlayout.setBackgroundColor(Color.RED);
        }else if(model.getKategori().equals("Tinggi")){
            holder.mainitemlayout.setBackgroundColor(Color.YELLOW);
        }else if(model.getKategori().equals("Tinggi")){
            holder.mainitemlayout.setBackgroundColor(Color.GREEN);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_habit,parent,false);
        return new viewHolder(view);
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView itemPicture;
        TextView totaldurasi;
        TextView itemTvTitle;
        TextView itemTvDescription;
        ConstraintLayout mainitemlayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            itemPicture = (ImageView) itemView.findViewById(R.id.item_picture);
            totaldurasi = (TextView) itemView.findViewById(R.id.totaldurasi);
            itemTvTitle = (TextView) itemView.findViewById(R.id.item_tv_title);
            itemTvDescription = (TextView) itemView.findViewById(R.id.item_tv_description);
            mainitemlayout = itemView.findViewById(R.id.mainitemlayout);
        }

        public void deleteItem(){
            int pos = getAdapterPosition();
            habitListener.DeleteItem(getSnapshots().getSnapshot(pos));
        }
    }

    interface HabitListener{
        public void DeleteItem(DocumentSnapshot documentSnapshot);
    }
}
