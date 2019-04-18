package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class WelcomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
    }

    public void openConnect(View view) {
        Intent intent = new Intent(this, ConnectActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.nothing);
    }

    public void openRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.nothing);
    }
}
