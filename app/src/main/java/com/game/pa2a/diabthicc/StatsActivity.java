package com.game.pa2a.diabthicc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import com.game.pa2a.diabthicc.MPclasses.DateXAxisFormat;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.services.CurrentUserService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        bottomNavigationView = findViewById(R.id.navigationViewStats);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavListener(this)
        );

        LineChart chart = findViewById(R.id.WeightChart);

        List<Entry> entries = new ArrayList<Entry>();

        currentUser = CurrentUserService.currentUser;

        HashMap<CustomDate, Double> allWeights = currentUser.getArchivedWeights();

        // Only for demo purposes
        CustomDate today = new CustomDate();
        CustomDate yesterday = new CustomDate(today.getYear(), today.getMonth()-1, today.getDay() - 1, today.getHours(), today.getMinutes());
        allWeights.put(yesterday, 40.0);
        allWeights.put(today, 45.0);

        for (Map.Entry<CustomDate, Double> entry : allWeights.entrySet()) {
            CustomDate key = entry.getKey();
            Double value = entry.getValue();
            entries.add(new Entry(key.getTime(), value.floatValue()));
        }

        /* Was done using an array and is now deprecated
        for(int i = 0; i < allWeights.size(); i++) {
            entries.add(new Entry(allWeights.get(i).first.getTime(), allWeights.get(i).second.floatValue()));
        }*/

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new DateXAxisFormat());
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1000 * 24 * 60 * 60);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);

        LineDataSet dataSet = new LineDataSet(entries, "Poids (en kg)"); // add entries to dataset
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.RED); // styling, ...

        LineData lineData = new LineData(dataSet);
        lineData.setDrawValues(false);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }

}
