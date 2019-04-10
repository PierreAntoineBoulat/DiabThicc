package com.game.pa2a.diabthicc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Field;

public class StatsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        bottomNavigationView = findViewById(R.id.navigationViewStats);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                intent = new Intent(StatsActivity.this, HomeActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_today:
                                intent = new Intent(StatsActivity.this, TodayActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_stats:
                                intent = new Intent(StatsActivity.this, StatsActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_share:
                                intent = new Intent(StatsActivity.this, ShareActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                        }
                        return true;
                    }
                });
    }

}
