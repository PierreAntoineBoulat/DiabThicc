package com.game.pa2a.diabthicc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.game.pa2a.diabthicc.services.NotificationService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.navigationViewHome);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                intent = new Intent(HomeActivity.this, HomeActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_today:
                                intent = new Intent(HomeActivity.this, TodayActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_stats:
                                intent = new Intent(HomeActivity.this, StatsActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_share:
                                intent = new Intent(HomeActivity.this, ShareActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                        }
                        return true;
                    }
                }); // TODO Factoriser le code dans un fragment et éviter d'avoir tout en copié-collé

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);

        List<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(70f,"Restants"));
        values.add(new PieEntry(30f,"Consommés"));

        PieDataSet pieDataSet = new PieDataSet(values,"");
        PieData pieData = new PieData(pieDataSet);

        pieDataSet.setColors(
                Color.rgb(200,200,200),
                Color.rgb(103,148,54)
        );

        pieChart.setData(pieData);

        Description d = new Description();
        d.setText("");
        pieChart.setDescription(d);

        pieChart.setDrawEntryLabels(false);

        Log.d("HomeActivity","Démarrage du service...");


        Intent i = new Intent(HomeActivity.this, NotificationService.class);
        // i.putExtra("ACTIVE_PROFILE", active_profile); TODO: Passer le profile (need profile implements Serializable)

        startService(i);
    }

}
