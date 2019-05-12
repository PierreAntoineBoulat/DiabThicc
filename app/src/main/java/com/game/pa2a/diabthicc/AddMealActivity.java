package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Diet;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.services.CurrentUserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddMealActivity extends AppCompatActivity {

    ArrayList<Aliment> mAliments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);


        final EditText editText = findViewById(R.id.editText);
        final CalendarView myCalendar = findViewById(R.id.calendarView);

        final TimePicker simpleTimePicker = findViewById(R.id.simpleTimePicker);
        simpleTimePicker.setIs24HourView(true);

        Button validMeal = findViewById(R.id.buttonValidMeal);

        Meal cameraMeal;
        if((cameraMeal = (Meal)getIntent().getSerializableExtra("cameraMeal")) != null) {
            editText.setText(cameraMeal.getName());

            // TODO : Need to add aliments to the selection
            for (Aliment aliment : cameraMeal.getAliments()) {

            }
        }
        validMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long myDate = myCalendar.getDate();
                int myHour = simpleTimePicker.getCurrentHour();
                int myMinutes = simpleTimePicker.getCurrentMinute();

                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTimeInMillis(myDate);
                CustomDate myCustomDate = new CustomDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH),simpleTimePicker.getCurrentHour(),simpleTimePicker.getCurrentMinute());

                Meal myNewMeal = new Meal(editText.getText().toString(),myCustomDate, getType(myCustomDate));
                for (Aliment aliment : mAliments){
                    myNewMeal.addAliment(aliment);
                }
                myNewMeal.setIcon("default_meal_round");
                myNewMeal.setImage("default_meal");

                CurrentUserService.currentUser.getCurrentDiet().addMeal(myNewMeal);

                Intent firstIntent = myCalendar(myNewMeal, calendar, myHour, myMinutes);
                startActivityForResult(firstIntent,2);
            }
        });

        initDataAliment();

        initRecyclerView(mAliments);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            Intent endIntent = new Intent(AddMealActivity.this,TodayActivity.class);
            startActivity(endIntent);
        }
    }

    private Intent myCalendar(Meal myNewMeal, Calendar calendar, int hour, int minute){
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        int changement = ((hour-calendar.get(Calendar.HOUR_OF_DAY))*60*60*1000)+((minute-calendar.get(Calendar.MINUTE))*60*1000);
        intent.putExtra("beginTime", calendar.getTimeInMillis()+ changement );
        intent.putExtra("allDay", false);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", calendar.getTimeInMillis()+changement+60*60*1000);
        intent.putExtra("title", myNewMeal.getName());
        return intent;
    }

    private void initDataAliment(){
        Aliment cafe = new Aliment("Cafe", new Diet(10,3,8));
        Aliment jusO = new Aliment("Jus d'Orange", new Diet(15,8,12));
        Aliment croissant = new Aliment("Croissant", new Diet(8,20,18));

        Aliment poulet = new Aliment("Poulet", new Diet(31, 0, 5));
        Aliment boeuf = new Aliment("Boeuf", new Diet(28, 0, 15));
        Aliment porc = new Aliment("Porc", new Diet(30, 0, 4));

        mAliments.add(cafe);
        mAliments.add(jusO);
        mAliments.add(croissant);
    }

    private void initRecyclerView(ArrayList<Aliment> mAliments) {

        LinearLayoutManager layoutManagerMeal = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewAliment = findViewById(R.id.recyclerViewAliment);
        recyclerViewAliment.setLayoutManager(layoutManagerMeal);
        RecyclerViewAdapterAliment recyclerViewAdapterAliment = new RecyclerViewAdapterAliment(this, mAliments);
        recyclerViewAliment.setAdapter(recyclerViewAdapterAliment);
    }

    private String getType(CustomDate date){
        int hour = date.getHours();
        if(hour <12){
            return "Petit Dejeuner";
        }
        if(hour<15){
            return "Dejeuner";
        }
        if(hour<18){
            return "Gouter";
        }
        else{
            return "Diner";
        }
    }
}
