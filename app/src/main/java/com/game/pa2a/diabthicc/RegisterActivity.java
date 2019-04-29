package com.game.pa2a.diabthicc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void openRegister2(View view) {
        Intent intent = new Intent(this, Register2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.nothing);
    }
}
