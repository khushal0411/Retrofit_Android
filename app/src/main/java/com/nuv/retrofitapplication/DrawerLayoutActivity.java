package com.nuv.retrofitapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class DrawerLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        HomeFragment homeFragment= new HomeFragment();
        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
        transactions.replace(R.id.frame, homeFragment);
        transactions.commit();

        Toolbar toolbar= findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.Home_Screen);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView= findViewById(R.id.navigation);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        drawerLayout.addDrawerListener(drawerToggle);
        navView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment frag = null;
            if (itemId == R.id.mr_app) {
                Intent intent =new Intent(DrawerLayoutActivity.this,SplashScreenActivity.class);
                startActivity(intent);
            }
            else if(itemId==R.id.cr_app)
            {
                getSupportActionBar().setTitle(R.string.COLOR_SELECTION_APP);
                frag =new DefaultColorFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, frag);
                transaction.commit();
                drawerLayout.closeDrawers();
                return true;
            }
            else if(itemId==R.id.vapi_app)
            {
                getSupportActionBar().setTitle(R.string.VideoAPIFRagment);
                frag =new VideoListFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, frag);
                transaction.commit();
                drawerLayout.closeDrawers();
                return true;
            }
            else
            { getSupportActionBar().setTitle(R.string.HOME_SCREEN);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, homeFragment);
                transaction.commit();
                drawerLayout.closeDrawers();
                return true;
            }
            return true;
        });
    }
}