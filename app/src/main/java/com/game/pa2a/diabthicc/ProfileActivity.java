package com.game.pa2a.diabthicc;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.services.CurrentUserService;

public class ProfileActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView user;
    private Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentUser = CurrentUserService.currentUser;

        bottomNavigationView = findViewById(R.id.navigationViewProfile);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavListener(this)
        );

        user = findViewById(R.id.textViewUser);
        user.setText("" + currentUser);

        ImageView imageUser = findViewById(R.id.imageUser);
        String path = currentUser.getImage();
        //imageUser.setImageResource();

        SeekBar seekBarProt =  findViewById(R.id.seekBarProt);
        SeekBar seekBarLip =findViewById(R.id.seekBarLip);
        SeekBar seekBarGlu = findViewById(R.id.seekBarGlu);

        seekBarGlu.setMax(500);
        seekBarGlu.setProgress(currentUser.getProfil().getMaxGlucides());
        final TextView evolGlu =findViewById(R.id.evolGlu);
        evolGlu.setText(""+currentUser.getProfil().getMaxGlucides());
        seekBarGlu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                currentUser.getProfil().setMaxGlucides(progress);
                evolGlu.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarLip.setMax(500);
        seekBarLip.setProgress(currentUser.getProfil().getMaxLipides());
        final TextView evolLip =findViewById(R.id.evolLip);
        evolLip.setText(""+currentUser.getProfil().getMaxLipides());
        seekBarLip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                currentUser.getProfil().setMaxLipides(progress);
                evolLip.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarProt.setMax(500);
        seekBarProt.setProgress(currentUser.getProfil().getMaxProt());
        final TextView evolProt =findViewById(R.id.evolProt);
        evolGlu.setText(""+currentUser.getProfil().getMaxProt());
        seekBarProt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                currentUser.getProfil().setMaxProt(progress);
                evolProt.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
}
