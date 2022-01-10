package com.nuv.retrofitapplication;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
FragmentHomeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        HomeScreenActivity homeScreenActivity= (HomeScreenActivity)getActivity();
        homeScreenActivity.binding.toolbar.tbMain.setTitle(Constants.HOME_SCREEN);
        return binding.getRoot();
    }
}