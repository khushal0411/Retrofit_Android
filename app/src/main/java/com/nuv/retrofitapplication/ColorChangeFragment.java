package com.nuv.retrofitapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ColorChangeFragment extends Fragment {
TextView home;
FrameLayout colorBackground;
ArrayList<Integer> red= new ArrayList<>();
ArrayList<Integer> green= new ArrayList<>();
ArrayList<Integer> blue= new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        ArrayList<String> finalColorArray,color;
        home=view.findViewById(R.id.tv_homefrag);
        colorBackground=view.findViewById(R.id.fl_colorChange);
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
                home.setText(R.string.INDRODUCTION_MESSAGE_COLORAPP);
            }
           int argb1=Color.argb(255,red.get(0),green.get(0),blue.get(0));
            int argb2=Color.argb(255,red.get(1),green.get(1),blue.get(1));
            int argb3=Color.argb(255,red.get(2),green.get(2),blue.get(2));
           int result1= ColorUtils.blendARGB(argb1,argb2,0.5F);
           int result2=ColorUtils.blendARGB(result1,argb3,0.5F);
           colorBackground.setBackgroundColor(result2);


        }

        return view;
    }
}