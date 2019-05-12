package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.MealsDaily;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.services.CurrentUserService;


import java.util.ArrayList;
import java.util.Arrays;

public class TodayActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CustomDate currentDate;
    private ArrayList<Meal> mMeals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        bottomNavigationView = findViewById(R.id.navigationViewToday);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavListener(this)
        );


        final Person currentUser = CurrentUserService.currentUser;
        final MealsDaily myDiet = currentUser.getCurrentDiet();
        currentDate = new CustomDate();


        final TextView textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText(currentDate.dayFormat());

        Button nextDate = findViewById(R.id.ButtonNextDate);
        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Meal> dayMeals = new ArrayList<>();
                currentDate.setNextDay();
                textViewDate.setText(currentDate.dayFormat());
                for (Meal meal: myDiet.getMeals()) {
                    if (meal.getConsommationDate().getDay() == currentDate.getDay() && meal.getConsommationDate().getMonth() == currentDate.getMonth()){
                        dayMeals.add(meal);
                    }
                }

                initRecyclerView(dayMeals);
            }
        });


        Button previousDate = findViewById(R.id.ButtonPreviousDate) ;
        previousDate.setText("<<");
        previousDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Meal> dayMeals = new ArrayList<>();
                currentDate.setPreviousDay();
                textViewDate.setText(currentDate.dayFormat());

                for (Meal meal: myDiet.getMeals()) {
                    if (meal.getConsommationDate().getDay() == currentDate.getDay() && meal.getConsommationDate().getMonth() == currentDate.getMonth()){
                        dayMeals.add(meal);
                    }
                }

                initRecyclerView(dayMeals);
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

        ArrayList<Meal> dayMeals = new ArrayList<>();
        for (Meal meal: myDiet.getMeals()) {
            if (meal.getConsommationDate().getDay() == currentDate.getDay() && meal.getConsommationDate().getMonth() == currentDate.getMonth()){
                dayMeals.add(meal);
            }
        }
        initRecyclerView(dayMeals);

    }

    private void initRecyclerView(ArrayList<Meal> dayMeals) {

        LinearLayoutManager layoutManagerMeal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewMeal = findViewById(R.id.recyclerViewTodayMeal);
        recyclerViewMeal.setLayoutManager(layoutManagerMeal);
        RecyclerViewAdapterMeal recyclerViewAdapterMeal = new RecyclerViewAdapterMeal(this, dayMeals);
        recyclerViewMeal.setAdapter(recyclerViewAdapterMeal);
    }

    public void onClickCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

}
