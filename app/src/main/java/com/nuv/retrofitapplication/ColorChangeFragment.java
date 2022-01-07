package com.nuv.retrofitapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.FragmentChooseColorBinding;
import com.nuv.retrofitapplication.databinding.FragmentColorChangeBinding;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ColorChangeFragment extends Fragment {
ArrayList<Integer> red= new ArrayList<>();
ArrayList<Integer> green= new ArrayList<>();
ArrayList<Integer> blue= new ArrayList<>();
FragmentColorChangeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_color_change,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> finalColorArray,color;
        if (getArguments() != null) {
            finalColorArray = getArguments().getStringArrayList(Constants.COLOR_SELECTED_INDEX);
            color=getArguments().getStringArrayList(Constants.COLOR_ARRAYLIST);
            for(int i=0;i<3;i++){
                String  color1= color.get(Integer.parseInt(finalColorArray.get(i)));
                String[] colorFinal=color1.substring(4,color1.length()-1).split(",");

                red.add(Integer.parseInt(colorFinal[0]));
                green.add(Integer.parseInt(colorFinal[1]));
                blue.add(Integer.parseInt(colorFinal[2]));
                colorFinal=null;
            }
            int argb1=Color.argb(255,red.get(0),green.get(0),blue.get(0));
            int argb2=Color.argb(255,red.get(1),green.get(1),blue.get(1));
            int argb3=Color.argb(255,red.get(2),green.get(2),blue.get(2));
            int result1= ColorUtils.blendARGB(argb1,argb2,0.5F);
            int result2=ColorUtils.blendARGB(result1,argb3,0.5F);
            binding.flColorChange.setBackgroundColor(result2);


        }
    }
}