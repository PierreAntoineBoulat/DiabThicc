package com.game.pa2a.diabthicc.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.models.Profile;

import java.util.ArrayList;

public class CurrentUserService extends IntentService {

    public static Person currentUser;

    public CurrentUserService() {
        super("Service for the current user of Diab&Thicc");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if(currentUser == null)
            currentUser = (Person)intent.getSerializableExtra("User");
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
