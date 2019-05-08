package com.game.pa2a.diabthicc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

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

        LineChart chart = (LineChart) findViewById(R.id.WeightChart);

        List<Entry> entries = new ArrayList<Entry>();

        // turn your data into Entry objects
        entries.add(new Entry(1, 15));
        entries.add(new Entry(2, 17));
        entries.add(new Entry(3, 19));

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.RED); // styling, ...

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }

}
