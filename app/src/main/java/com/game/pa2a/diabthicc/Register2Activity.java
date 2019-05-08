package com.game.pa2a.diabthicc;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Register2Activity extends FragmentActivity {

    boolean status = false;
    Button bFrag;
    RegisterFragActivity rfa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);
        bFrag = (Button)findViewById(R.id.btnFrag);
        rfa = new RegisterFragActivity();
    }

    public void openHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_down, R.anim.nothing);
    }

    public  void buttonFrag(View view){

        if(!status){
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(rfa)
                    .commit();
            bFrag.setText("Normal");
            status = true;
        }else {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .hide(rfa)
                    .commit();
            bFrag.setText("Avancé");
            status = false;
        }
    }
}
