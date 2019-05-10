package com.game.pa2a.diabthicc.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.game.pa2a.diabthicc.models.Meal;

import java.util.Calendar;

public class BroadcastCloseNotif extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("APP RECEIVER", "je suis là");
        if (action != null && action.equals("notification_cancelled")) {
            //notifNextMeal = null;
            Calendar calendar = Calendar.getInstance();
            //timeLastNotif = calendar.getTimeInMillis();
        } else if (action != null && action.equals("delay")) {
            Meal meal = (Meal) intent.getSerializableExtra("Meal");
            meal.getConsommationDate().setMinutes(meal.getConsommationDate().getMinutes() + 30);

            int notificationId = intent.getIntExtra("ID", -1);

            if (notificationId > 0) {
                NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.cancel(notificationId);

            }

        } else if (action != null && action.equals("dismiss")) {
            int notificationId = intent.getIntExtra("ID", -1);

            if (notificationId > 0) {
                NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.cancel(notificationId);
            }

        }

    }

}
