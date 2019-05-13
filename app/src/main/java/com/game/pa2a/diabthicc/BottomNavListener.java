package com.game.pa2a.diabthicc;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.services.CurrentUserService;

import java.util.ArrayList;

public class BottomNavListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Activity caller;

    public BottomNavListener(Activity caller){
        this.caller = caller;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(CurrentUserService.currentUser == null){
            Person mcBibi = ConnectActivity.buildUser();
            Intent iService = new Intent(caller, CurrentUserService.class);
            iService.putExtra("User", mcBibi);
            caller.startService(iService);
        }
        Intent intent;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                intent = new Intent(caller, HomeActivity.class);
                caller.startActivity(intent);
                break;

            case R.id.navigation_today:
                intent = new Intent(caller, TodayActivity.class);
                caller.startActivity(intent);
                break;

            case R.id.navigation_stats:
                intent = new Intent(caller, StatsActivity.class);
                caller.startActivity(intent);
                break;

            case R.id.navigation_share:
                intent = new Intent(caller, ShareActivity.class);
                caller.startActivity(intent);
                break;

            case R.id.navigation_profil:
                intent = new Intent(caller, ProfileActivity.class);
                caller.startActivity(intent);
                break;

        }
        return true;
    }
}
