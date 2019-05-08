package com.game.pa2a.diabthicc;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;

import java.util.ArrayList;

public class BottomNavListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Activity caller;
    private ArrayList<Meal> meals;
    private ArrayList<Person> profiles;

    public BottomNavListener(Activity caller, ArrayList<Meal> meals, ArrayList<Person> profiles){
        this.caller = caller;
        this.meals = meals;
        this.profiles = profiles;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                intent = new Intent(caller, HomeActivity.class);
                intent.putExtra("profiles", profiles);
                intent.putExtra("meals", meals);
                caller.startActivity(intent);
                break;

            case R.id.navigation_today:
                intent = new Intent(caller, TodayActivity.class);
                intent.putExtra("profiles", profiles);
                intent.putExtra("meals", meals);
                caller.startActivity(intent);
                break;

            case R.id.navigation_stats:
                intent = new Intent(caller, StatsActivity.class);
                intent.putExtra("profiles", profiles);
                intent.putExtra("meals", meals);
                caller.startActivity(intent);
                break;

            case R.id.navigation_share:
                intent = new Intent(caller, ShareActivity.class);
                intent.putExtra("profiles", profiles);
                intent.putExtra("meals", meals);
                caller.startActivity(intent);
                break;

        }
        return true;
    }
}
