package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Meal;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        final EditText editText = findViewById(R.id.editText);
        final CalendarView myCalendar = findViewById(R.id.calendarView);

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

                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTimeInMillis(myDate);
                CustomDate myCustomDate = new CustomDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));

                Meal myNewMeal = new Meal(editText.getText().toString(),myCustomDate);

                Intent intent = new Intent(AddMealActivity.this,TodayActivity.class);
                startActivity(intent);
            }
        });
    }
}
