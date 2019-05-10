package com.game.pa2a.diabthicc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.services.CurrentUserService;
import com.game.pa2a.diabthicc.services.NotificationService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        currentUser = CurrentUserService.currentUser;

        initData();

        bottomNavigationView = findViewById(R.id.navigationViewHome);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavListener(this)
        );

        Intent i = new Intent(HomeActivity.this, NotificationService.class);
        i.putExtra("ACTIVE_PROFILE", currentUser);
        startService(i);
    }

    private void initData(){
        List<Meal> lMeals = currentUser.getCurrentDiet().getMeals();

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);

        int protConso = 0, protMax = 0;
        int fatConso = 0, fatMax = 0;
        int carbsConso = 0, carbsMax = 0;

        for(Meal m : lMeals){
            for(Aliment a : m.getAliments()){
                protConso += a.getDiet().getProteinIntake();
                fatConso += a.getDiet().getFatIntake();
                carbsConso += a.getDiet().getCarbsIntake();
            }
        }

        protMax = currentUser.getProfil().getMaxProt();
        fatMax = currentUser.getProfil().getMaxLipides();
        carbsMax = currentUser.getProfil().getMaxGlucides();

        float pcProt = (float) protConso / protMax * 100;
        float pcFat = (float) fatConso / fatMax * 100;
        float pcCarbs = (float) carbsConso / carbsMax * 100;

        Log.d("STATS", pcProt + " - " + pcFat + " - " + pcCarbs);

        ProgressBar pbProt = findViewById(R.id.progressBarProt);
        ProgressBar pbFat = findViewById(R.id.progressBarFat);
        ProgressBar pbCarbs = findViewById(R.id.progressBarCarbs);

        pbProt.setProgress((int)pcProt);
        pbFat.setProgress((int)pcFat);
        pbCarbs.setProgress((int)pcCarbs);

        float consommes = ( (pcProt > 100 ? 100 : pcProt) + (pcFat > 100 ? 100 : pcFat) + (pcCarbs > 100 ? 100 : pcCarbs) ) / 3;
        float restants = 100 - consommes;

        List<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(restants,"Restants"));
        values.add(new PieEntry(consommes,"Consommés"));

        PieDataSet pieDataSet = new PieDataSet(values,"");
        PieData pieData = new PieData(pieDataSet);

        pieDataSet.setColors(
                Color.rgb(200,200,200),
                Color.rgb(103,148,54)
        );
        pieData.setDrawValues(false);

        pieChart.setData(pieData);

        Description d = new Description();
        d.setText("");
        pieChart.setDescription(d);

        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.getLegend().setCustom(new ArrayList<LegendEntry>());

        TextView pcConso = findViewById(R.id.pcConso);
        TextView pcLeft = findViewById(R.id.pcLeft);

        pcConso.setText(String.format("%s %%", (int)Math.round(consommes)));
        pcLeft.setText(String.format("%s %%", (int)Math.round(restants)));

    }

}
