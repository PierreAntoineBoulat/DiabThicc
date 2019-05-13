package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.MealsDaily;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.services.CurrentUserService;


import java.util.ArrayList;

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
                updateDayObjectif(dayMeals, currentUser);
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
                updateDayObjectif(dayMeals, currentUser);
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
        updateDayObjectif(dayMeals, currentUser);

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

    public void updateDayObjectif(ArrayList<Meal> dealMeals, Person currentUser){
        int dailyDietGlu = currentUser.getProfil().getMaxGlucides();
        int dailyDietProt = currentUser.getProfil().getMaxProt();
        int dailyDietLip = currentUser.getProfil().getMaxLipides();

        int actualGlu = 0;
        int actualProt = 0;
        int actualLip = 0;

        for(Meal meal : dealMeals) {
            for(Aliment aliment : meal.getAliments()){
                actualGlu+= aliment.getDiet().getFatIntake();
                actualProt+= aliment.getDiet().getProteinIntake();
                actualLip+=aliment.getDiet().getCarbsIntake();
            }
        }
        float moyGlu = (float) actualGlu / dailyDietGlu * 100;
        float moyLip = (float) actualLip / dailyDietLip * 100;
        float moyProt = (float) actualProt / dailyDietProt * 100;

        float result = (moyGlu+moyLip+moyProt)/3;
        ProgressBar progressBar = findViewById(R.id.progressBarMealDay);

        progressBar.setProgress((int)result);
        TextView textViewLip = findViewById(R.id.textViewLip);
        TextView textViewGlu =findViewById(R.id.textViewGlu);
        TextView textViewProt = findViewById(R.id.textViewProt);

        textViewLip.setText((int)moyLip+"%");
        textViewGlu.setText((int)moyGlu+"%");
        textViewProt.setText((int)moyProt+"%");

        TextView textViewMood = findViewById(R.id.textViewMood);
        ImageView mood = findViewById(R.id.imageViewMood);
        if(result<=66){
            mood.setImageResource(R.drawable.sad_mood);
            textViewMood.setText("Insuffisant...");
            textViewMood.setTextColor(Color.RED);
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        if(66<result && result<=90){
            mood.setImageResource(R.drawable.good_mood);
            textViewMood.setText("Correct");
            textViewMood.setTextColor(Color.MAGENTA);
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.MAGENTA));
        }
        if(90<result){
            mood.setImageResource(R.drawable.excellent_mood);
            textViewMood.setText("Bravo !");
            textViewMood.setTextColor(Color.GREEN);
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));;
        }

    }

}
