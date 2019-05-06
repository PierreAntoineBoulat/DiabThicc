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

import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ArrayList<Meal> lMeals;
    private ArrayList<Person> lProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        bottomNavigationView = findViewById(R.id.navigationViewStats);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        ArrayList<Meal> meals;

        if( (meals = (ArrayList<Meal>)getIntent().getSerializableExtra("meals")) != null)
        {
            this.lMeals = meals;
            Log.d("Stats","OKMEALS");
        }

        ArrayList<Person> profiles;

        if( (profiles = (ArrayList<Person>)getIntent().getSerializableExtra("profiles")) != null)
        {
            this.lProfiles = profiles;
            Log.d("Stats","OKPROFILES");
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavListener(this, lMeals, lProfiles)
        );
    }

}
