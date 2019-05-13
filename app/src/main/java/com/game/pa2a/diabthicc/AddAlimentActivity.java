package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Diet;
import com.game.pa2a.diabthicc.models.Meal;

import java.util.ArrayList;
import java.util.List;

public class AddAlimentActivity extends AppCompatActivity {
    ArrayList<Meal> baseMeal = new ArrayList<>();
    ArrayList<Aliment> mAliment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aliment);

        ImageView search = findViewById(R.id.imageViewSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchAliment = findViewById(R.id.editTextResearchAliment);
                String searchString = searchAliment.getText().toString();

                ArrayList<Meal> newMeal = new ArrayList<>();

                for(Meal meal : baseMeal){
                    if(meal.getName().equals(searchString)){
                        newMeal.add(meal);
                        mAliment.addAll(meal.getAliments());
                    }
                }

                initRecyclerView(newMeal);
                initRecyclerViewAliment(mAliment);
                updateScore();

            }
        });

        initData();
        initRecyclerView(baseMeal);

        Button validAliment = findViewById(R.id.buttonValidAliment);
        validAliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAlimentActivity.this, AddMealActivity.class);
                int key = 0;
                for(Aliment aliment : mAliment){
                    intent.putExtra(key+"",aliment);
                    key++;
                }
                intent.putExtra("key",key);
                setResult(1,intent);
                finish();
            }
        });



    }

    private void initData(){
        Aliment cafe = new Aliment("Cafe", new Diet(10,3,8));
        Aliment jusO = new Aliment("Jus d'orange", new Diet(15,8,12));
        Aliment croissant = new Aliment("Croissant", new Diet(8,20,18));

        Aliment poulet = new Aliment("Poulet", new Diet(31, 0, 5));
        Aliment boeuf = new Aliment("Boeuf", new Diet(28, 0, 15));
        Aliment porc = new Aliment("Porc", new Diet(30, 0, 4));

        Aliment carotte = new Aliment("Carotte", new Diet(200,200,200));
        Aliment haricot = new Aliment("Haricot", new Diet(40,20,50));
        Aliment tomate = new Aliment("Tomate", new Diet(30,15,25));

        Aliment pate = new Aliment("Pate", new Diet(40,30,40));
        Aliment riz = new Aliment("Riz", new Diet(40,40,30));
        Aliment semoule = new Aliment("Riz", new Diet(40,40,30));

        CustomDate myDate = new CustomDate();

        Meal mCafe = new Meal("Cafe",myDate,"Aliment");
        mCafe.addAliment(cafe);
        mCafe.setImage("aliment");
        mCafe.setIcon("aliment_round");

        Meal mJusO = new Meal("Jus d'orange",myDate,"Aliment");
        mJusO.addAliment(jusO);
        mJusO.setImage("aliment");
        mJusO.setIcon("aliment_round");

        Meal mCroissant = new Meal("Croissant",myDate,"Aliment");
        mCroissant.addAliment(croissant);
        mCroissant.setImage("aliment");
        mCroissant.setIcon("aliment_round");

        Meal mPoulet = new Meal("Poulet",myDate,"Aliment");
        mPoulet.addAliment(poulet);
        mPoulet.setImage("aliment");
        mPoulet.setIcon("aliment_round");

        Meal mBoeuf = new Meal("Boeuf",myDate,"Aliment");
        mBoeuf.addAliment(boeuf);
        mBoeuf.setImage("aliment");
        mBoeuf.setIcon("aliment_round");

        Meal mPorc = new Meal("Porc",myDate,"Aliment");
        mPorc.addAliment(porc);
        mPorc.setImage("aliment");
        mPorc.setIcon("aliment_round");

        Meal mCarotte = new Meal("Carotte",myDate,"Aliment");
        mCarotte.addAliment(carotte);
        mCarotte.setImage("aliment");
        mCarotte.setIcon("aliment_round");

        baseMeal.add(mCroissant);
        baseMeal.add(mCafe);
        baseMeal.add(mJusO);
        baseMeal.add(mBoeuf);
        baseMeal.add(mPorc);
        baseMeal.add(mPoulet);
        baseMeal.add(mCarotte);

    }

    private void initRecyclerView(ArrayList<Meal> newMeals){

        LinearLayoutManager layoutManagerMeal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewMeal = findViewById(R.id.recyclerViewAddAliment);
        recyclerViewMeal.setLayoutManager(layoutManagerMeal);
        RecyclerViewAdapterMeal recyclerViewAdapterMeal = new RecyclerViewAdapterMeal(this, newMeals);
        recyclerViewMeal.setAdapter(recyclerViewAdapterMeal);

    }

    private void initRecyclerViewAliment(ArrayList<Aliment> newAliments){
        LinearLayoutManager layoutManagerAliment = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewAliment = findViewById(R.id.recyclerViewListAliment);
        recyclerViewAliment.setLayoutManager(layoutManagerAliment);
        RecyclerViewAdapterAliment recyclerViewAdapterAliment = new RecyclerViewAdapterAliment(this, newAliments);
        recyclerViewAliment.setAdapter(recyclerViewAdapterAliment);
    }

    private void updateScore(){
        int glu = 0;
        int prot = 0;
        int lip = 0;
        for (Aliment aliment : mAliment) {
            glu+= aliment.getDiet().getFatIntake();
            prot+= aliment.getDiet().getProteinIntake();
            lip+=aliment.getDiet().getCarbsIntake();
        }

        TextView textGlu = findViewById(R.id.textGlu);
        TextView textLip = findViewById(R.id.textLip);
        TextView textProt = findViewById(R.id.textProt);

        textGlu.setText(glu+"");
        textLip.setText(lip+"");
        textProt.setText(prot+"");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("aliments", mAliment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAliment = (ArrayList<Aliment>) outState.getSerializable("aliments");
        initRecyclerViewAliment(mAliment);
        updateScore();
    }
}
