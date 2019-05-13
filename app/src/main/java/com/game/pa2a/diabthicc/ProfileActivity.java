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

    }
}
