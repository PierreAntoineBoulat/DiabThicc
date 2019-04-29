package com.game.pa2a.diabthicc.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends IntentService {

    public NotificationService() {
        super("Notification Service Diab Thicc");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


}
