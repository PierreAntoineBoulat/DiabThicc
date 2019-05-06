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
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TodayActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CustomDate currentDate;
    private ArrayList<Meal> lMeals;
    private ArrayList<Person> lProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        ArrayList<Meal> meals;

        if( (meals = (ArrayList<Meal>)getIntent().getSerializableExtra("meals")) != null)
        {
            this.lMeals = meals;
            Log.d("Today","OKMEALS");
        }

        ArrayList<Person> profiles;

        if( (profiles = (ArrayList<Person>)getIntent().getSerializableExtra("profiles")) != null)
        {
            this.lProfiles = profiles;
            Log.d("Today","OKPROFILES");
        }

        bottomNavigationView = findViewById(R.id.navigationViewToday);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavListener(this, lMeals, lProfiles)
        );

        currentDate = new CustomDate(2019,05,9,15,30);


        final TextView textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText(currentDate.dayFormat());

//        ImageButton nextDate = findViewById(R.id.imageButtonNextDate);
//        nextDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ( currentDate.getDay() == 31){
//                    currentDate.setDay(1);
//                    currentDate.setMonth(currentDate.getMonth()+1);
//                }
//                else {
//                    currentDate.setDay(currentDate.getDay() + 1);
//                }
//                textViewDate.setText(currentDate.dayFormat());
//            }
//        });
//
//
//        ImageButton previousDate = findViewById(R.id.imageButtonPreviousDate) ;
//        previousDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentDate.getDay() == 1){
//                    currentDate.setDay(31);
//                    currentDate.setMonth(currentDate.getMonth()-1);
//                }
//                else{
//                    currentDate.setDay(currentDate.getDay()-1);
//                }
//                textViewDate.setText(currentDate.dayFormat());
//            }
//        });
//
//
//
//        Button addMeal = findViewById(R.id.buttonAddMeal);
//        addMeal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TodayActivity.this, AddMealActivity.class);
//                startActivity(intent);
//            }
//        });
    }

}
