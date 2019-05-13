package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
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
    String curDate;
    int myYear;
    int myMonth;
    int myDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);


        final EditText editText = findViewById(R.id.editText);
        final CalendarView myCalendar = findViewById(R.id.calendarView);
        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                int d = dayOfMonth;
                curDate =String.valueOf(d);
                myYear = year;
                myMonth = month;
                myDay = dayOfMonth;
            }
        });

        final TimePicker simpleTimePicker = findViewById(R.id.simpleTimePicker);
        simpleTimePicker.setIs24HourView(true);

        Button validMeal = findViewById(R.id.buttonValidMeal);

        validMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //long myDate = myCalendar.getDate();
                int myHour = simpleTimePicker.getCurrentHour();
                int myMinutes = simpleTimePicker.getCurrentMinute();

                //GregorianCalendar calendar = new GregorianCalendar();
                //calendar.setTimeInMillis(myDate);
                //CustomDate myCustomDate = new CustomDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH),simpleTimePicker.getCurrentHour(),simpleTimePicker.getCurrentMinute());
                CustomDate myCustomDate = new CustomDate(myYear,myMonth+1,myDay,simpleTimePicker.getCurrentHour(),simpleTimePicker.getCurrentMinute());
                Meal myNewMeal = new Meal(editText.getText().toString(),myCustomDate, getType(myCustomDate));
                for (Aliment aliment : mAliments){
                    myNewMeal.addAliment(aliment);
                }
                myNewMeal.setIcon("default_meal_round");
                myNewMeal.setImage("default_meal");

                CurrentUserService.currentUser.getCurrentDiet().addMeal(myNewMeal);

                Intent firstIntent = myCalendar(myNewMeal, myCustomDate, myHour, myMinutes);
                startActivityForResult(firstIntent,2);
            }
        });

        initDataAliment();

        // Si on reçoit un intent de la caméra
        Meal cameraMeal;
        if((cameraMeal = (Meal)getIntent().getSerializableExtra("meal")) != null) {
            editText.setText(cameraMeal.getName());

            mAliments = cameraMeal.getAliments();
            Log.d("meal", "size :" +mAliments.size());

            initRecyclerView(mAliments);
        }

        ImageButton imageButtonAddAliment = findViewById(R.id.imageButtonAliment);

        imageButtonAddAliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAliment = new Intent(AddMealActivity.this, AddAlimentActivity.class);
                startActivityForResult(intentAliment,1);
            }
        });
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
        if(requestCode==1){
            mAliments = new ArrayList<>();
            int key = data.getIntExtra("key",0);
            int newKey = 0;
            while(newKey != key){
                Aliment aliment = (Aliment) data.getSerializableExtra(newKey+"key");
                mAliments.add(aliment);
                newKey++;
            }
            Aliment carotte = new Aliment("Carotte",new Diet(200,200,200));
            //mAliments.add(carotte);
            initRecyclerView(mAliments);
        }
    }

    private Intent myCalendar(Meal myNewMeal, CustomDate myCustomDate, int hour, int minute){
        String description = " " ;
        for(Aliment aliment : myNewMeal.getAliments()){
            description= description+"    "+aliment.getName();
        }
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", myCustomDate.getTime() );
        intent.putExtra("allDay", false);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description );
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", myCustomDate.getTime() +60*60*1000);
        intent.putExtra("title", myNewMeal.getName());
        return intent;
    }

    private void initDataAliment(){
        Aliment cafe = new Aliment("Cafe", new Diet(10,3,8));
        Aliment jusO = new Aliment("Jus d'Orange", new Diet(15,8,12));
        Aliment croissant = new Aliment("Croissant", new Diet(8,20,18));

        //mAliments.add(cafe);
        //mAliments.add(jusO);
        //mAliments.add(croissant);
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
