package com.nuv.retrofitapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class DrawerLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        DefaultColorFragment defaultColorFragment =new DefaultColorFragment();
        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
        transactions.replace(R.id.frame, defaultColorFragment);
        transactions.commit();

        Toolbar toolbar= findViewById(R.id.tb_main);
        toolbar.setTitle("Home Screen ");
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
                getSupportActionBar().setTitle("Color Selection App");
                frag =new DefaultColorFragment();
            }
            if(frag !=null)
            {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, frag);
                transaction.commit();
                drawerLayout.closeDrawers();
                return true;
            }
            return true;
        });
    }
}