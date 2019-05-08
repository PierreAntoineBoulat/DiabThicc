package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Diet;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.models.Profile;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShareActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ArrayList<Person> lProfiles = new ArrayList<>();
    private ArrayList<Meal> lMeals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        ArrayList<Meal> meals;

        if( (meals = (ArrayList<Meal>)getIntent().getSerializableExtra("meals")) != null)
        {
            this.lMeals = meals;
            Log.d("Share","OKMEALS");
        }

        ArrayList<Person> profiles;

        if( (profiles = (ArrayList<Person>)getIntent().getSerializableExtra("profiles")) != null)
        {
            this.lProfiles = profiles;
            Log.d("Share","OKPROFILES");
        }

        bottomNavigationView = findViewById(R.id.navigationViewShare);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavListener(this, lMeals, lProfiles)
        );

        initData();
    }

    private void initData() {

        /* -------------------------------------- */

        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManagerProfile = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewProfile = findViewById(R.id.recyclerViewProfile);
        recyclerViewProfile.setLayoutManager(layoutManagerProfile);
        RecyclerViewAdapterProfile recyclerViewAdapterProfile = new RecyclerViewAdapterProfile(this, lProfiles);
        recyclerViewProfile.setAdapter(recyclerViewAdapterProfile);

        LinearLayoutManager layoutManagerMeal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewMeal = findViewById(R.id.recyclerViewMeal);
        recyclerViewMeal.setLayoutManager(layoutManagerMeal);
        RecyclerViewAdapterMeal recyclerViewAdapterMeal = new RecyclerViewAdapterMeal(this, lMeals);
        recyclerViewMeal.setAdapter(recyclerViewAdapterMeal);
    }

    public void onClickTweet(View view){
        Intent intent = new Intent(this, TwitterList.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_down, R.anim.nothing);
    }
}
