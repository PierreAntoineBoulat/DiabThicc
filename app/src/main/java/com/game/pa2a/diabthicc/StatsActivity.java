package com.game.pa2a.diabthicc;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.game.pa2a.diabthicc.MPclasses.DateXAxisFormat;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Diet;
import com.game.pa2a.diabthicc.models.DietResult;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.services.CurrentUserService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Person currentUser;

    Button weight7D;
    Button weight14D;

    Button changeWeight;

    TextView actualWeight;

    LineChart weightChart;
    LineChart respectChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        currentUser = CurrentUserService.currentUser;

        //weight7D = findViewById(R.id.weight7d);
        //weight14D = findViewById(R.id.weight14d);

        changeWeight = findViewById(R.id.updateWeight);

        actualWeight = findViewById(R.id.aujdPoids);

        actualWeight.setText("Aujourd'hui, vous faites " + currentUser.getWeight() + " kg.");

        bottomNavigationView = findViewById(R.id.navigationViewStats);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavListener(this)
        );

        weightChart = findViewById(R.id.WeightChart);
        respectChart = findViewById(R.id.RespectChart);

        ArrayList<Entry> entries = new ArrayList<>();

        HashMap<CustomDate, Double> allWeights = currentUser.getArchivedWeights();
        if(currentUser.getWeight() > 0.0){ // => if not null
            allWeights.put(currentUser.getLastModified(), currentUser.getWeight());
        }

        for (Map.Entry<CustomDate, Double> entry : allWeights.entrySet()) {
            CustomDate key = entry.getKey();
            Double value = entry.getValue();
            entries.add(new Entry(key.getTime(), value.floatValue()));
        }

        /* Was done using an array and is now deprecated
        for(int i = 0; i < allWeights.size(); i++) {
            entries.add(new Entry(allWeights.get(i).first.getTime(), allWeights.get(i).second.floatValue()));
        }*/

        /*
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new DateXAxisFormat());
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1000 * 24 * 60 * 60);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);

        Collections.sort(entries, new EntryXComparator());

        LineDataSet dataSet = new LineDataSet(entries, "Poids (en kg)"); // add entries to dataset
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.RED); // styling, ...

        LineData lineData = new LineData(dataSet);
        lineData.setDrawValues(false);
        chart.setData(lineData);
        chart.invalidate(); // refresh
        */

        initChart(entries, weightChart, "Poids (en kg)");

        ArrayList<Entry> respectEntries = new ArrayList<>();

        HashMap<CustomDate, Diet> allDiets = currentUser.getArchivedDiets();

        Diet objectif = currentUser.getProfil().getObjectif();

        for (Map.Entry<CustomDate, Diet> entry : allDiets.entrySet()) {
            CustomDate key = entry.getKey();
            Diet value = entry.getValue();
            DietResult result = new DietResult(objectif, value);
            respectEntries.add(new Entry(key.getTime(), (float)result.getPercentCaloric()));
        }

        initChart(respectEntries, respectChart, "Respect (en %)");


        // TODO :  NOT USABLE RN, moveViewToX fait bugger le linechart...
        /*
        weight7D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDate cd = new CustomDate();
                long time = cd.getTime();
                time-= (1000 * 24 * 60 * 60) * 7; // Today - 7 days
                //chart.moveViewToX(time);
                chart.invalidate();
                Log.d("test", "onClick: ");
            }
        });
        weight14D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDate cd = new CustomDate();
                long time = cd.getTime();
                time-= (1000 * 24 * 60 * 60) * 14; // Today - 14 days
                //chart.moveViewToX(time);
                chart.invalidate();
            }
        });
        */

        // TODO : Le poids n'est pas actualisé après modification par wd. Si qqun a une idée?
        changeWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeightDialog wd = new WeightDialog(StatsActivity.this);
                wd.show();
                wd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        refreshWeight();
                    }
                });
            }
        });

    }

    private void initChart(ArrayList<Entry> entries, LineChart chart, String label) {
        if(entries.size() < 1) {
            chart.setVisibility(View.INVISIBLE);
            return;
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new DateXAxisFormat());
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1000 * 24 * 60 * 60);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);

        Collections.sort(entries, new EntryXComparator());

        LineDataSet dataSet = new LineDataSet(entries, label); // add entries to dataset
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.RED); // styling, ...

        LineData lineData = new LineData(dataSet);
        lineData.setDrawValues(false);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }

    private void refreshWeight() {
        actualWeight.setText("Aujourd'hui, vous faites " + currentUser.getWeight() + " kg.");

        List<Entry> entries = new ArrayList<>();

        HashMap<CustomDate, Double> allWeights = currentUser.getArchivedWeights();
        if(currentUser.getWeight() > 0.0){ // => if not null
            allWeights.put(currentUser.getLastModified(), currentUser.getWeight());
        }

        for (Map.Entry<CustomDate, Double> entry : allWeights.entrySet()) {
            CustomDate key = entry.getKey();
            Double value = entry.getValue();
            entries.add(new Entry(key.getTime(), value.floatValue()));
        }

        Collections.sort(entries, new EntryXComparator());

        LineDataSet dataSet = new LineDataSet(entries, "Poids (en kg)"); // add entries to dataset
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.RED); // styling, ...

        LineData lineData = new LineData(dataSet);
        lineData.setDrawValues(false);
        weightChart.setData(lineData);
        weightChart.invalidate(); // refresh
    }

}
