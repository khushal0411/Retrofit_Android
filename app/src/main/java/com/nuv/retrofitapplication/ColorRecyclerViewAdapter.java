package com.nuv.retrofitapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorRecyclerViewAdapter extends RecyclerView.Adapter<ColorRecyclerViewAdapter.MyViewHolder> {
    private final ArrayList<String> color;

    int countIndex=0;
    ArrayList<String> selectedIndex= new ArrayList<>();
    OnColorSelect onColorSelect;

    public ColorRecyclerViewAdapter(ArrayList<String> color,OnColorSelect onColorSelect) {
        this.color = color;
        this.onColorSelect=onColorSelect;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_colors) CardView colorsCard;
        @BindView(R.id.cv_bgcolor) CardView bgColorCard;
        OnColorSelect colorSelect;
        public MyViewHolder(@NonNull View itemView,OnColorSelect colorSelect) {
            super(itemView);
            this.colorSelect=colorSelect;
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public ColorRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.color_items,parent,false);
        MyViewHolder myViewHolder =new MyViewHolder(itemView,onColorSelect);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ColorRecyclerViewAdapter.MyViewHolder holder, int position) {
        String selectedColor = color.get(position);
        String[] colorFinal=selectedColor.substring(4,selectedColor.length()-1).split(",");
        int r= Integer.parseInt(colorFinal[0]);
        int g= Integer.parseInt(colorFinal[1]);
        int b= Integer.parseInt(colorFinal[2]);
        holder.colorsCard.setBackgroundColor(Color.rgb(r,g,b));
        holder.bgColorCard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(holder.bgColorCard.isSelected()== true){
                    holder.bgColorCard.setBackgroundColor(Color.DKGRAY);
                    holder.bgColorCard.setSelected(false);
                    selectedIndex.remove(String.valueOf(position));
                    countIndex= countIndex-1;
                    onColorSelect.colorSelected(selectedIndex);
                }
                else {
                    if(countIndex<=2){
                holder.bgColorCard.setBackgroundColor(Color.CYAN);
                    holder.bgColorCard.setSelected(true);
                    selectedIndex.add(String.valueOf(position));
                    countIndex= countIndex+1;
                    onColorSelect.colorSelected(selectedIndex);
                }
                }
               //Toast.makeText(v.getContext(),selectedIndex.toString(),Toast.LENGTH_SHORT).show();
                if(countIndex==3){
                   onColorSelect.colorSelected(selectedIndex);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return color.size();
    }

interface OnColorSelect{
        void colorSelected(ArrayList<String> selectedIndex);

}
}
