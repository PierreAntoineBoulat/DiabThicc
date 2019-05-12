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
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.MealsDaily;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.models.Diet;
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
        currentDate = new CustomDate(2019,5,9,15,30);


        final TextView textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText(currentDate.dayFormat());

        ImageButton nextDate = findViewById(R.id.imageButtonNextDate);
        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Meal> dayMeals = new ArrayList<>();
                if ( currentDate.getDay() == 31){
                    currentDate.setDay(1);
                    currentDate.setMonth(currentDate.getMonth()+1);
                }
                else {
                    currentDate.setDay(currentDate.getDay() + 1);
                }
                textViewDate.setText(currentDate.dayFormat());
                for (Meal meal: myDiet.getMeals()) {
                    if (meal.getConsommationDate().getDay() == currentDate.getDay() && meal.getConsommationDate().getMonth() == currentDate.getMonth()){
                        dayMeals.add(meal);
                    }
                }

                initRecyclerView(dayMeals);
            }
        });


        ImageButton previousDate = findViewById(R.id.imageButtonPreviousDate) ;
        previousDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Meal> dayMeals = new ArrayList<>();
                if (currentDate.getDay() == 1){
                    currentDate.setDay(31);
                    currentDate.setMonth(currentDate.getMonth()-1);
                }
                else{
                    currentDate.setDay(currentDate.getDay()-1);
                }
                textViewDate.setText(currentDate.dayFormat());

                for (Meal meal: myDiet.getMeals()) {
                    if (meal.getConsommationDate().getDay() == currentDate.getDay() && meal.getConsommationDate().getMonth() == currentDate.getMonth()){
                        dayMeals.add(meal);
                    }
                }

                initRecyclerView(dayMeals);
            }
        });

        initData();

        Button addMeal = findViewById(R.id.buttonAddMeal);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodayActivity.this, AddMealActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<Meal> dayMeals = myDiet.getMeals();
        for (Meal meal: mMeals) {
            if (meal.getConsommationDate().getDay() == currentDate.getDay() && meal.getConsommationDate().getMonth() == currentDate.getMonth()){
                dayMeals.add(meal);
            }
        }
        initRecyclerView(dayMeals);

    }

    private void initData() {

        /* ----------------ALIMENTS---------------- */

        Aliment cafe = new Aliment("Cafe", new Diet(10,3,8));
        Aliment jusO = new Aliment("Jus d'Orange", new Diet(15,8,12));
        Aliment croissant = new Aliment("Croissant", new Diet(8,20,18));

        Aliment poulet = new Aliment("Poulet", new Diet(31, 0, 5));
        Aliment boeuf = new Aliment("Boeuf", new Diet(28, 0, 15));
        Aliment porc = new Aliment("Porc", new Diet(30, 0, 4));

        Aliment fromage = new Aliment("Fromage", new Diet(24, 30, 1));

        Aliment riz = new Aliment("Riz", new Diet(2, 0, 26));
        Aliment pates = new Aliment("Pates", new Diet(12, 4, 67));
        Aliment patates = new Aliment("Pommes de terre", new Diet(2, 0, 18));

        Aliment creme = new Aliment("Creme fraiche", new Diet(0, 12, 0));
        Aliment tomate = new Aliment("Tomates", new Diet(1, 2, 0));
        Aliment carotte = new Aliment("Carottes", new Diet(1, 7, 0));
        Aliment champignons = new Aliment("Champignons", new Diet(3, 0, 1));


        /* ----------------REPAS---------------- */
        CustomDate myDate = new CustomDate(2019,5,9,14,37);

        Meal pdc = new Meal("Petit dejeuner croissant",myDate);
        pdc.addAliment(cafe);
        pdc.addAliment(jusO);
        pdc.addAliment(croissant);
        pdc.setImage("petit_dejeuner_croissant");
        pdc.setIcon("petit_dejeuner_croissant_round");

        Meal rpc = new Meal("Riz Poulet Curry",myDate);
        rpc.addAliment(riz);
        rpc.addAliment(poulet);
        rpc.addAliment(creme);
        rpc.setImage("poulet_curry");
        rpc.setIcon("poulet_curry_round");


        Meal risotto = new Meal("Risotto Champignons", myDate);
        risotto.addAliment(riz);
        risotto.addAliment(creme);
        risotto.addAliment(champignons);
        risotto.setImage("risotto_champignon");
        risotto.setIcon("risotto_champignon_round");

        CustomDate myDate2 = new CustomDate(2019,5,10,14,37);


        Meal hachis = new Meal("Hachis Parmentier", myDate2);
        hachis.addAliment(boeuf);
        hachis.addAliment(patates);
        hachis.addAliment(fromage);
        hachis.setImage("hachis_parmentier");
        hachis.setIcon("hachis_parmentier_round");

        Meal pp = new Meal("Poulet Paprika", myDate2);
        pp.addAliment(poulet);
        pp.addAliment(tomate);
        pp.setImage("poulet_paprika");
        pp.setIcon("poulet_paprika_round");

        Meal salade = new Meal("Salade Tomates Mozza", myDate2);
        salade.addAliment(tomate);
        salade.addAliment(fromage);
        salade.setImage("tomate_mozza");
        salade.setIcon("tomate_mozza_round");

        Meal bb = new Meal("Boeuf Bourguignon", myDate2);
        bb.addAliment(boeuf);
        bb.addAliment(tomate);
        bb.addAliment(carotte);
        bb.setImage("boeuf_bourguignon");
        bb.setIcon("boeuf_bourguignon_round");

        CustomDate myDate3 = new CustomDate(2019,5,11,14,37);


        Meal porcPatate = new Meal("Rôti de Porc à la Patate Douce", myDate3);
        porcPatate.addAliment(porc);
        porcPatate.addAliment(patates);
        porcPatate.addAliment(creme);
        porcPatate.setImage("roti_de_porc");
        porcPatate.setIcon("roti_de_porc_round");

        Meal patesCarbonara = new Meal("Pates Carbonara", myDate3);
        patesCarbonara.addAliment(pates);
        patesCarbonara.addAliment(porc);
        patesCarbonara.addAliment(creme);
        patesCarbonara.setImage("pate_carbonara");
        patesCarbonara.setIcon("pate_carbonara_round");

        /* ----------------PROFILS----------------- */

        mMeals.addAll(Arrays.asList(
                pdc,
                rpc,
                risotto,
                hachis,
                pp,
                salade,
                bb,
                porcPatate,
                patesCarbonara
        ));

        /* -------------------------------------- */
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
