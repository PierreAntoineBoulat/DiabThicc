package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ShareActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ArrayList<String> mProfiles = new ArrayList<>();
    private ArrayList<String> mMeals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        bottomNavigationView = findViewById(R.id.navigationViewShare);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                intent = new Intent(ShareActivity.this, HomeActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_today:
                                intent = new Intent(ShareActivity.this, TodayActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_stats:
                                intent = new Intent(ShareActivity.this, StatsActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_share:
                                intent = new Intent(ShareActivity.this, ShareActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                        }
                        return true;
                    }
                });

        initData();
    }

    private void initData() {
        mProfiles.add("John McBiceps");
        mProfiles.add("Jean Phillipe");
        mProfiles.add("Florian Vraicasse");
        mProfiles.add("Aha Sku");
        mProfiles.add("Memory Zeh");
        mProfiles.add("Koor Advanced");
        mProfiles.add("Penny Uherelle");
        mProfiles.add("Editor Wise");

        mMeals.add("Poulet curry");
        mMeals.add("Risotto cumin");
        mMeals.add("Hachi Parmentier");
        mMeals.add("Poulet paprika");
        mMeals.add("Salade de tomates");
        mMeals.add("Boeuf Bourguignon");
        mMeals.add("Porc au caramel");
        mMeals.add("Pates carbonara");

        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManagerProfile = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewProfile = findViewById(R.id.recyclerViewProfile);
        recyclerViewProfile.setLayoutManager(layoutManagerProfile);
        RecyclerViewAdapter recyclerViewAdapterProfile = new RecyclerViewAdapter(this, mProfiles);
        recyclerViewProfile.setAdapter(recyclerViewAdapterProfile);

        LinearLayoutManager layoutManagerMeal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewMeal = findViewById(R.id.recyclerViewMeal);
        recyclerViewMeal.setLayoutManager(layoutManagerMeal);
        RecyclerViewAdapterMeal recyclerViewAdapterMeal = new RecyclerViewAdapterMeal(this, mMeals);
        recyclerViewMeal.setAdapter(recyclerViewAdapterMeal);
    }
}
