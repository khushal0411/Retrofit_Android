package com.nuv.retrofitapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.nuv.retrofitapplication.databinding.ActivityHomeScreenBinding;

import java.util.Objects;

import butterknife.ButterKnife;

public class HomeScreenActivity extends AppCompatActivity  {
    ActivityHomeScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_home_screen);
        //setContentView(R.layout.activity_home_screen);
        HomeFragment homeFragment= new HomeFragment();
        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
        transactions.replace(R.id.fl_home_screen, homeFragment);
        transactions.commit();
        ButterKnife.bind(this);
        binding.toolbar.tbMain.setTitle(R.string.Home_Screen);
        setSupportActionBar(binding.toolbar.tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout,binding.toolbar.tbMain, R.string.drawer_open,  R.string.drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        binding.drawerLayout.addDrawerListener(drawerToggle);
        binding.navigation.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment frag = null;
            if (itemId == R.id.mr_app) {
                Intent intent =new Intent(HomeScreenActivity.this, MoviesSplashScreenActivity.class);
                startActivity(intent);
            }
            else if(itemId==R.id.cr_app)
            {
                getSupportActionBar().setTitle(R.string.COLOR_SELECTION_APP);
                frag =new ChooseColorFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_home_screen, frag).addToBackStack(null);
                transaction.commit();
                binding.drawerLayout.closeDrawers();
                return true;
            }
            else if(itemId==R.id.vapi_app)
            {
                getSupportActionBar().setTitle(R.string.VideoAPIFRagment);
                frag =new VideoListFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_home_screen, frag).addToBackStack(null);
                transaction.commit();
                binding.drawerLayout.closeDrawers();
                return true;
            }
            else if(itemId==R.id.alarm_manager)
            {
                getSupportActionBar().setTitle("Alarm Manager");
                frag =new AlarmFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_home_screen, frag).addToBackStack(null);
                transaction.commit();
                binding.drawerLayout.closeDrawers();
                return true;
            }
            else
            { getSupportActionBar().setTitle(R.string.HOME_SCREEN);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_home_screen, homeFragment).addToBackStack(null);
                transaction.commit();
                binding.drawerLayout.closeDrawers();
                return true;
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}