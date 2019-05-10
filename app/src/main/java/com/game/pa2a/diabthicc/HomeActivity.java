package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Diet;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.MealsDaily;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.models.Profile;
import com.game.pa2a.diabthicc.services.NotificationService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ArrayList<Meal> lMeals = new ArrayList<>();
    private ArrayList<Person> lProfiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buildUser();

        ArrayList<Meal> meals;

        if( (meals = (ArrayList<Meal>)getIntent().getSerializableExtra("meals")) != null)
        {
            this.lMeals = meals;
        }

        ArrayList<Person> profiles;

        if( (profiles = (ArrayList<Person>)getIntent().getSerializableExtra("profiles")) != null)
        {
            this.lProfiles = profiles;
            Log.d("Home","OKPROFILES");
        }

        bottomNavigationView = findViewById(R.id.navigationViewHome);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavListener(this, lMeals, lProfiles)
        );

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);

        int protConso = 0, protMax = 0;

        for(Meal m : lMeals){
            for(Aliment a : m.getAliments()){
                protConso += a.getDiet().getProteinIntake();
            }
        }

        lProfiles.get(0).getProfil().setMaxProt(500);
        protMax = lProfiles.get(0).getProfil().getMaxProt();

        MealsDaily mealsDaily = new MealsDaily();
        this.lMeals.get(0).setConsommationDate(new CustomDate());
        mealsDaily.addMeal(this.lMeals.get(0));
        mealsDaily.addMeal(this.lMeals.get(1));
        lProfiles.get(0).setCurrentDiet(mealsDaily);

        float consommes = (float) protConso / protMax * 100;
        float restants = 100 - consommes;

        Log.d("HomeRestants", protMax+" "+lProfiles.get(0).getName());
        Log.d("HomeConso", protConso+"");

        List<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(restants,"Restants"));
        values.add(new PieEntry(consommes,"Consommés"));

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
        i.putExtra("ACTIVE_PROFILE", lProfiles.get(0)); // TODO: Passer le profile (need profile implements Serializable)

        startService(i);
    }

    private void buildUser() {

        if(lProfiles.size() < 1 || lMeals.size() < 1) {
            /* ----------------PROFILS----------------- */

            Log.d("HomeActivity","ca construit");

            Person mcBibi = new Person("McBiceps", "John", "Sportif Confirmé", "mc_biceps", "mc_biceps_round");
            Profile mcBibiProfile = new Profile("BibiSportif");
            mcBibiProfile.setMaxProt(500);
            mcBibiProfile.setMaxGlucides(200);
            mcBibiProfile.setMaxLipides(300);
            mcBibi.setProfil(mcBibiProfile);

            lProfiles.addAll(Arrays.asList(
                    mcBibi,
                    new Person("Phillipe", "Jean", "Sportif Debutant", "mc_biceps_2", "mc_biceps_2_round"),
                    new Person("Vraicas", "Florian", "Sportif Amateur", "mc_biceps_3", "mc_biceps_3_round"),
                    new Person("Sku", "Aha", "Diabétique", "mc_biceps_4", "mc_biceps_4_round"),
                    new Person("Zeh", "Memory", "Coach Sportif", "mc_biceps_5", "mc_biceps_5_round"),
                    new Person("Advanced", "Koor", "Coach Sportif", "mc_biceps_6", "mc_biceps_6_round"),
                    new Person("Uherelle", "Penny", "Coach Sportif", "mc_biceps_7", "mc_biceps_7_round"),
                    new Person("Wise", "Editor", "Coach Sportif", "mc_biceps_8", "mc_biceps_8_round")
            ));

            /* ----------------ALIMENTS---------------- */

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

            Meal rpc = new Meal("Riz Poulet Curry", new CustomDate(), "Déjeuner");
            rpc.addAliment(riz);
            rpc.addAliment(poulet);
            rpc.addAliment(creme);
            rpc.setImage("poulet_curry");
            rpc.setIcon("poulet_curry_round");

            Meal risotto = new Meal("Risotto Champignons", new CustomDate(), "Déjeuner");
            risotto.addAliment(riz);
            risotto.addAliment(creme);
            risotto.addAliment(champignons);
            risotto.setImage("risotto_champignon");
            risotto.setIcon("risotto_champignon_round");

            Meal hachis = new Meal("Hachis Parmentier", new CustomDate(), "Diner");
            hachis.addAliment(boeuf);
            hachis.addAliment(patates);
            hachis.addAliment(fromage);
            hachis.setImage("hachis_parmentier");
            hachis.setIcon("hachis_parmentier_round");

            Meal pp = new Meal("Poulet Paprika", new CustomDate(), "Déjeuner, Diner");
            pp.addAliment(poulet);
            pp.addAliment(tomate);
            pp.setImage("poulet_paprika");
            pp.setIcon("poulet_paprika_round");

            Meal salade = new Meal("Salade Tomates Mozza", new CustomDate(), "Déjeuner");
            salade.addAliment(tomate);
            salade.addAliment(fromage);
            salade.setImage("tomate_mozza");
            salade.setIcon("tomate_mozza_round");

            Meal bb = new Meal("Boeuf Bourguignon", new CustomDate(), "Diner");
            bb.addAliment(boeuf);
            bb.addAliment(tomate);
            bb.addAliment(carotte);
            bb.setImage("boeuf_bourguignon");
            bb.setIcon("boeuf_bourguignon_round");

            Meal porcPatate = new Meal("Rôti de Porc à la Patate Douce", new CustomDate(), "Déjeuner, Diner");
            porcPatate.addAliment(porc);
            porcPatate.addAliment(patates);
            porcPatate.addAliment(creme);
            porcPatate.setImage("roti_de_porc");
            porcPatate.setIcon("roti_de_porc_round");

            Meal patesCarbonara = new Meal("Pates Carbonara", new CustomDate(), "Déjeuner");
            patesCarbonara.addAliment(pates);
            patesCarbonara.addAliment(porc);
            patesCarbonara.addAliment(creme);
            patesCarbonara.setImage("pate_carbonara");
            patesCarbonara.setIcon("pate_carbonara_round");

            /*-----------------MEALS------------------*/

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
        }
    }

}
