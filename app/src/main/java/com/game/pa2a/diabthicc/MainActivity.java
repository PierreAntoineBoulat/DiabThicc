package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent mIntent = new Intent(MainActivity.this, WelcomActivity.class);
        //mIntent.putExtra("sum", "0");
        startActivity(mIntent);
    }
}
