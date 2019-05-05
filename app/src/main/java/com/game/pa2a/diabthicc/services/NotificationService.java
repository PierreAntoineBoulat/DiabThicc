package com.game.pa2a.diabthicc.services;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.game.pa2a.diabthicc.MainActivity;
import com.game.pa2a.diabthicc.R;
import com.game.pa2a.diabthicc.models.Profile;

public class NotificationService extends IntentService {

    Profile activeProfile;

    public NotificationService() {
        super("Notification Service Diab Thicc");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Méthode appelée à chaque fois qu'une activity utilise startService()
    @Override
    protected void onHandleIntent(Intent intent) {
        //TODO: Gérer les notifs (code exemple ci-dessous)
        Intent newIntent = new Intent("com.rj.notitfications.SECACTIVITY");

        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(NotificationService.this);

        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("WhatsApp Notification");
        builder.setContentText("You have a new message");
        builder.setSmallIcon(R.drawable.male_icone_white);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);
        builder.setSubText("This is subtext...");   //API level 16
        builder.setNumber(100);
        builder.build();

        Notification myNotication = builder.getNotification();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(11, myNotication);

    }

    // Méthode appelée une seule fois au démarrage du service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("NotifService","Service démarré");
        return super.onStartCommand(intent, flags, startId);
    }
}
