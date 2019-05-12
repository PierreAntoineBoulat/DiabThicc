package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.MealsDaily;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.services.CurrentUserService;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddMealActivity extends AppCompatActivity {

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
                CustomDate myCustomDate = new CustomDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),simpleTimePicker.getCurrentHour(),simpleTimePicker.getCurrentMinute());

                Meal myNewMeal = new Meal(editText.getText().toString(),myCustomDate);

                Person currentUser = CurrentUserService.currentUser;
                MealsDaily myDiet = currentUser.getCurrentDiet();
                myDiet.addMeal(myNewMeal);

                myCalendar(myNewMeal, calendar, myHour, myMinutes);

                //Intent endIntent = new Intent(AddMealActivity.this,TodayActivity.class);
                //startActivity(endIntent);
            }
        });
    }

    private void myCalendar(Meal myNewMeal, Calendar calendar, int hour, int minute){
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        int changement = ((hour-calendar.get(Calendar.HOUR_OF_DAY))*60*60*1000)+((minute-calendar.get(Calendar.MINUTE))*60*1000);
        intent.putExtra("beginTime", calendar.getTimeInMillis()+ changement );
        intent.putExtra("allDay", false);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", calendar.getTimeInMillis()+changement+60*60*1000);
        intent.putExtra("title", myNewMeal.getName());
        startActivity(intent);
    }
}
