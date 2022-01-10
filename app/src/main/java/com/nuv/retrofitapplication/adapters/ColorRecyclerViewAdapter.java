package com.nuv.retrofitapplication.adapters;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nuv.retrofitapplication.R;
import com.nuv.retrofitapplication.databinding.ColorItemsBinding;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorRecyclerViewAdapter extends RecyclerView.Adapter<ColorRecyclerViewAdapter.MyViewHolder> {
    private final ArrayList<String> color;

    int countIndex=0;
    ArrayList<String> selectedIndex= new ArrayList<>();
    OnColorSelect onColorSelect;
    ColorItemsBinding binding;

    public ColorRecyclerViewAdapter(ArrayList<String> color,OnColorSelect onColorSelect) {
        this.color = color;
        this.onColorSelect=onColorSelect;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.cv_bgcolor) CardView bgColorCard;
        ColorItemsBinding binding;
        public MyViewHolder(@NonNull View itemView,ColorItemsBinding binding) {
            super(itemView);
            this.binding=binding;
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public ColorRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.color_items,parent,false);
        return new MyViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorRecyclerViewAdapter.MyViewHolder holder, int position) {
        String selectedColor = color.get(position);
        String[] colorFinal=selectedColor.substring(4,selectedColor.length()-1).split(",");
        int r= Integer.parseInt(colorFinal[0]);
        int g= Integer.parseInt(colorFinal[1]);
        int b= Integer.parseInt(colorFinal[2]);
        binding.cvColors.setBackgroundColor(Color.rgb(r,g,b));
        binding.cvBgcolor.setOnClickListener(v -> {
            if(binding.cvBgcolor.isSelected()){
                binding.cvBgcolor.setBackgroundColor(Color.DKGRAY);
                binding.cvBgcolor.setSelected(false);
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
        });
    }

    @Override
    public int getItemCount() {
        return color.size();
    }

    public interface OnColorSelect
    {
        void colorSelected(ArrayList<String> selectedIndex);

    }
}
