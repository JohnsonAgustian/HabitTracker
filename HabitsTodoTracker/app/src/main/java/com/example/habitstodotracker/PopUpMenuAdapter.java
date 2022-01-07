package com.example.habitstodotracker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class PopUpMenuAdapter extends RecyclerView.Adapter<PopUpMenuAdapter.viewHolder> {
    private ArrayList<popupitem> arrItem;

    public PopUpMenuAdapter(AddPopUpMenu.onClick onClick) {
        this.click = onClick;
        arrItem = new ArrayList<popupitem>(){
            {
                add(new popupitem("Habit","Bangun kebiasaan baik anda dengan fitur habit kami",R.drawable.ic_baseline_replay_24));
                add(new popupitem("ToDo","Manajemen ToDo List dengan mudah dan simple",R.drawable.ic_baseline_list_24));

            }
        };
    }

    AddPopUpMenu.onClick click;

    public AddPopUpMenu.onClick getClick() {
        return click;
    }

    public void setClick(AddPopUpMenu.onClick click) {
        this.click = click;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popupmenu,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        popupitem now = arrItem.get(position);
        holder.item_picture.setImageResource(now.getImg());
        holder.item_tv_title.setText(now.getTitle());
        holder.item_tv_description.setText(now.getDesc());
        holder.mainitemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(now.getImg() == R.drawable.ic_baseline_replay_24){
                    click.Click(1);
                }else if(now.getImg() == R.drawable.ic_baseline_list_24){
                    click.Click(2);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView item_tv_description,item_tv_title;
        ImageView item_picture;
        ConstraintLayout mainitemlayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            item_picture = itemView.findViewById(R.id.item_picture);
            item_tv_description = itemView.findViewById(R.id.item_tv_description);
            item_tv_title = itemView.findViewById(R.id.item_tv_title);
            mainitemlayout = itemView.findViewById(R.id.mainitemlayout);
        }
    }
}

class popupitem{
    private String title;
    private String desc;
    private int img;

    public popupitem(String title, String desc, int img) {
        this.title = title;
        this.desc = desc;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
