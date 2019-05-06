package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Diet;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;

import java.util.ArrayList;
import java.util.Arrays;

public class ShareActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ArrayList<String> mProfiles = new ArrayList<>();
    private ArrayList<Person> lProfiles = new ArrayList<>();
    private ArrayList<String> mMeals = new ArrayList<>();
    private ArrayList<Meal> lMeals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        bottomNavigationView = findViewById(R.id.navigationViewShare);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                intent = new Intent(ShareActivity.this, HomeActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_today:
                                intent = new Intent(ShareActivity.this, TodayActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_stats:
                                intent = new Intent(ShareActivity.this, StatsActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                            case R.id.navigation_share:
                                intent = new Intent(ShareActivity.this, ShareActivity.class);
                                //intent.putExtra("sum", player);
                                startActivity(intent);
                                break;

                        }
                        return true;
                    }
                });

        initData();
    }

    private void initData() {
        mProfiles.add("John McBiceps");
        mProfiles.add("Jean Phillipe");
        mProfiles.add("Florian Vraicasse");
        mProfiles.add("Aha Sku");
        mProfiles.add("Memory Zeh");
        mProfiles.add("Koor Advanced");
        mProfiles.add("Penny Uherelle");
        mProfiles.add("Editor Wise");

        mMeals.add("Poulet Curry");
        mMeals.add("Risotto Champignons");
        mMeals.add("Hachis Parmentier");
        mMeals.add("Poulet Paprika");
        mMeals.add("Salade Tomates Mozza");
        mMeals.add("Boeuf Bourguignon");
        mMeals.add("Rôti de Porc à la Patate Douce");
        mMeals.add("Pates Carbonara");

        /* ----------------MEALS----------------- */

        lProfiles.addAll(Arrays.asList(
                new Person("McBiceps", "John", "Sportif Confirmé", "mc_biceps", "mc_biceps_round"),
                new Person("Phillipe", "Jean", "Sportif Debutant",  "mc_biceps_2", "mc_biceps_2_round"),
                new Person("Vraicas", "Florian", "Sportif Amateur",  "mc_biceps_3", "mc_biceps_3_round"),
                new Person("Sku", "Aha", "Diabétique",  "mc_biceps_4", "mc_biceps_4_round"),
                new Person("Zeh", "Memory", "Coach Sportif",  "mc_biceps_5", "mc_biceps_5_round"),
                new Person("Advanced", "Koor", "Coach Sportif",  "mc_biceps_6", "mc_biceps_6_round"),
                new Person("Uherelle", "Penny", "Coach Sportif",  "mc_biceps_7", "mc_biceps_7_round"),
                new Person("Wise", "Editor", "Coach Sportif",  "mc_biceps_8", "mc_biceps_8_round")
        ));

        /* ----------------ALIMENTS---------------- */

        Aliment poulet = new Aliment("Poulet", new Diet(31, 0, 5));
        Aliment boeuf = new Aliment("Boeuf", new Diet(28, 0, 15));
        Aliment porc = new Aliment("Porc", new Diet(30, 0, 4));

        Aliment fromage = new Aliment("Fromage",new Diet(24,30,1));

        Aliment riz = new Aliment("Riz", new Diet(2, 0, 26));
        Aliment pates = new Aliment("Pates", new Diet(12, 4, 67));
        Aliment patates = new Aliment("Pommes de terre", new Diet(2, 0, 18));

        Aliment creme = new Aliment("Creme fraiche", new Diet(0, 12, 0));
        Aliment tomate = new Aliment("Tomates", new Diet(1, 2, 0));
        Aliment carotte = new Aliment("Carottes", new Diet(1, 7, 0));
        Aliment champignons = new Aliment("Champignons", new Diet(3, 0, 1));


        /* ----------------REPAS---------------- */

        Meal rpc = new Meal("Riz Poulet Curry", new CustomDate());
        rpc.addAliment(riz);
        rpc.addAliment(poulet);
        rpc.addAliment(creme);
        rpc.setImage("poulet_curry");
        rpc.setIcon("poulet_curry_round");

        Meal risotto = new Meal("Risotto Champignons", new CustomDate());
        risotto.addAliment(riz);
        risotto.addAliment(creme);
        risotto.addAliment(champignons);
        risotto.setImage("risotto_champignon");
        risotto.setIcon("risotto_champignon_round");

        Meal hachis = new Meal("Hachis Parmentier", new CustomDate());
        hachis.addAliment(boeuf);
        hachis.addAliment(patates);
        hachis.addAliment(fromage);
        hachis.setImage("hachis_parmentier");
        hachis.setIcon("hachis_parmentier_round");

        Meal pp = new Meal("Poulet Paprika", new CustomDate());
        pp.addAliment(poulet);
        pp.addAliment(tomate);
        pp.setImage("poulet_paprika");
        pp.setIcon("poulet_paprika_round");

        Meal salade = new Meal("Salade Tomates Mozza", new CustomDate());
        salade.addAliment(tomate);
        salade.addAliment(fromage);
        salade.setImage("tomate_mozza");
        salade.setIcon("tomate_mozza_round");

        Meal bb = new Meal("Boeuf Bourguignon", new CustomDate());
        bb.addAliment(boeuf);
        bb.addAliment(tomate);
        bb.addAliment(carotte);
        bb.setImage("boeuf_bourguignon");
        bb.setIcon("boeuf_bourguignon_round");

        Meal porcPatate = new Meal("Rôti de Porc à la Patate Douce", new CustomDate());
        porcPatate.addAliment(porc);
        porcPatate.addAliment(patates);
        porcPatate.addAliment(creme);
        porcPatate.setImage("roti_de_porc");
        porcPatate.setIcon("roti_de_porc_round");

        Meal patesCarbonara = new Meal("Pates Carbonara", new CustomDate());
        patesCarbonara.addAliment(pates);
        patesCarbonara.addAliment(porc);
        patesCarbonara.addAliment(creme);
        patesCarbonara.setImage("pate_carbonara");
        patesCarbonara.setIcon("pate_carbonara_round");

        /* ----------------PROFILS----------------- */

        lMeals.addAll(Arrays.asList(
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
}
