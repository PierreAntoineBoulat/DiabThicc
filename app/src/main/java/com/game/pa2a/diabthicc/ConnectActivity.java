package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Diet;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.models.Profile;
import com.game.pa2a.diabthicc.services.CurrentUserService;

import java.util.Arrays;

public class ConnectActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        Intent intent;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    public void openHome(View view) {
        Person mcBibi = buildUser();
        Intent iService = new Intent(this, CurrentUserService.class);
        iService.putExtra("User", mcBibi);
        startService(iService);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_down, R.anim.nothing);
    }

    public Person buildUser(){

        Person mcBibi = new Person("McBiceps", "John", "Sportif Confirmé", "mc_biceps", "mc_biceps_round");
        Profile mcBibiProfile = new Profile("BibiSportif");
        mcBibiProfile.setMaxProt(500);
        mcBibiProfile.setMaxGlucides(200);
        mcBibiProfile.setMaxLipides(300);
        mcBibi.setProfil(mcBibiProfile);

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

        CustomDate today = new CustomDate();
        CustomDate yesterday = new CustomDate();
        yesterday.setPreviousDay();
        CustomDate tomorrow = new CustomDate();
        tomorrow.setNextDay();

        Meal rpc = new Meal("Riz Poulet Curry", today, "Déjeuner");
        rpc.addAliment(riz);
        rpc.addAliment(poulet);
        rpc.addAliment(creme);
        rpc.setImage("poulet_curry");
        rpc.setIcon("poulet_curry_round");

        Meal risotto = new Meal("Risotto Champignons", today, "Déjeuner");
        risotto.addAliment(riz);
        risotto.addAliment(creme);
        risotto.addAliment(champignons);
        risotto.setImage("risotto_champignon");
        risotto.setIcon("risotto_champignon_round");

        Meal hachis = new Meal("Hachis Parmentier", today, "Diner");
        hachis.addAliment(boeuf);
        hachis.addAliment(patates);
        hachis.addAliment(fromage);
        hachis.setImage("hachis_parmentier");
        hachis.setIcon("hachis_parmentier_round");

        Meal pp = new Meal("Poulet Paprika", tomorrow, "Déjeuner, Diner");
        pp.addAliment(poulet);
        pp.addAliment(tomate);
        pp.setImage("poulet_paprika");
        pp.setIcon("poulet_paprika_round");

        Meal salade = new Meal("Salade Tomates Mozza", tomorrow, "Déjeuner");
        salade.addAliment(tomate);
        salade.addAliment(fromage);
        salade.setImage("tomate_mozza");
        salade.setIcon("tomate_mozza_round");

        Meal bb = new Meal("Boeuf Bourguignon", yesterday, "Diner");
        bb.addAliment(boeuf);
        bb.addAliment(tomate);
        bb.addAliment(carotte);
        bb.setImage("boeuf_bourguignon");
        bb.setIcon("boeuf_bourguignon_round");

        Meal porcPatate = new Meal("Rôti de Porc à la Patate Douce", yesterday, "Déjeuner, Diner");
        porcPatate.addAliment(porc);
        porcPatate.addAliment(patates);
        porcPatate.addAliment(creme);
        porcPatate.setImage("roti_de_porc");
        porcPatate.setIcon("roti_de_porc_round");

        Meal patesCarbonara = new Meal("Pates Carbonara", yesterday, "Déjeuner");
        patesCarbonara.addAliment(pates);
        patesCarbonara.addAliment(porc);
        patesCarbonara.addAliment(creme);
        patesCarbonara.setImage("pate_carbonara");
        patesCarbonara.setIcon("pate_carbonara_round");

        mcBibi.getCurrentDiet().getMeals().addAll(Arrays.asList(
                rpc,
                risotto,
                hachis,
                pp,
                salade,
                bb,
                porcPatate,
                patesCarbonara
                ));
        return mcBibi;
    }
}
