package com.game.pa2a.diabthicc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.CustomDate;

import java.lang.reflect.Field;

public class TodayActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CustomDate currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        bottomNavigationView = findViewById(R.id.navigationViewToday);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                intent = new Intent(TodayActivity.this, HomeActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_today:
                                intent = new Intent(TodayActivity.this, TodayActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_stats:
                                intent = new Intent(TodayActivity.this, StatsActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_share:
                                intent = new Intent(TodayActivity.this, ShareActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                        }
                        return true;
                    }
                });

        currentDate = new CustomDate(2019,05,9,15,30);


        final TextView textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText(currentDate.dayFormat());

        ImageButton nextDate = findViewById(R.id.imageButtonNextDate);
        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( currentDate.getDay() == 31){
                    currentDate.setDay(1);
                    currentDate.setMonth(currentDate.getMonth()+1);
                }
                else {
                    currentDate.setDay(currentDate.getDay() + 1);
                }
                textViewDate.setText(currentDate.dayFormat());
            }
        });


        ImageButton previousDate = findViewById(R.id.imageButtonPreviousDate) ;
        previousDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDate.getDay() == 1){
                    currentDate.setDay(31);
                    currentDate.setMonth(currentDate.getMonth()-1);
                }
                else{
                    currentDate.setDay(currentDate.getDay()-1);
                }
                textViewDate.setText(currentDate.dayFormat());
            }
        });



        Button addMeal = findViewById(R.id.buttonAddMeal);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodayActivity.this, AddMealActivity.class);
                startActivity(intent);
            }
        });
    }

}
