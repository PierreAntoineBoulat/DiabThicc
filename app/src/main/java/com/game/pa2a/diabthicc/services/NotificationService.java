package com.game.pa2a.diabthicc.services;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.game.pa2a.diabthicc.R;
import com.game.pa2a.diabthicc.TodayActivity;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.models.Profile;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static android.app.Notification.EXTRA_NOTIFICATION_TAG;

public class NotificationService extends Service {
    private static NotificationManagerCompat mNotificationManager;
    private static NotificationCompat.Builder notifNextMeal = null;
    private WeakReference<Context> context;
    private static long timeLastNotif = 0;
    private Person currentUser;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = new WeakReference<>(getBaseContext());
        currentUser = (Person) intent.getExtras().get("ACTIVE_PROFILE");
        mNotificationManager = NotificationManagerCompat.from(context.get());
        createNotificationChannel();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new NotificationTask(), 0, 5000);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceTask = new Intent(getApplicationContext(), this.getClass());
        restartServiceTask.setPackage(getPackageName());
        PendingIntent restartPendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager myAlarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        if (myAlarmService != null)
            myAlarmService.set(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + 5000,
                    restartPendingIntent);

        super.onTaskRemoved(rootIntent);
    }

    private void createNotification(Meal meal) {
        Intent delayIntent = new Intent(this, BroadcastCloseNotif.class);
        delayIntent.setAction("delay");
        delayIntent.putExtra("ID", 1);
        delayIntent.putExtra("Meal", meal);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 1, delayIntent, 0);

        Intent dismissIntent = new Intent(this, BroadcastCloseNotif.class);
        dismissIntent.setAction("dismiss");
        dismissIntent.putExtra("ID", 1);
        PendingIntent dismissPendingIntent =
                PendingIntent.getBroadcast(this, 1, dismissIntent, 0);


        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, TodayActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        int resId = this.getResources().getIdentifier(
                meal.getImage(),
                "drawable",
                this.getPackageName()
        );
        Bitmap big = BitmapFactory.decodeResource(getResources(), resId);

        notifNextMeal = new NotificationCompat.Builder(context.get(), "channel_notif_id_meal")
                .setSmallIcon(R.drawable.ic_local_dining_black_24dp)
                .setContentTitle("Vous avez un nouveau repas à prendre")
                .setContentText(meal.getName() + ": " + meal.getConsommationDate().hourFormat())
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setAutoCancel(true)
                .setLargeIcon(big)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(big)
                        .bigLargeIcon(null))
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDeleteIntent(getDeleteIntent())
                .addAction(R.drawable.ic_add_alarm_black_24dp, "ADD 30 MIN", snoozePendingIntent)
                .addAction(R.drawable.ic_home_black_24dp, "DISMISS", dismissPendingIntent)
                .setContentIntent(resultPendingIntent);

        Log.d("APP LAUNCHER", "je suis là");
        mNotificationManager.notify(1, notifNextMeal.build());
    }

    protected PendingIntent getDeleteIntent() {
        Intent intent = new Intent(this, BroadcastCloseNotif.class);
        intent.setAction("notification_cancelled");
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_notif_meal";
            String description = "channel_notif_description_tmeal";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("channel_notif_id_meal", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.get().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private class NotificationTask extends TimerTask {
        public void run() {
            if (notifNextMeal == null && currentUser.getCurrentDiet().getMeals() != null) {
                Log.d("NotificationService", currentUser.toString());
                for (Meal mealDated : currentUser.getCurrentDiet().getMeals()) {
                    CustomDate now = new CustomDate();
                    CustomDate mealDate = mealDated.getConsommationDate();
                    mealDate.setMinutes(mealDate.getMinutes() - 5);
                    if (mealDate.isAnteriorAs(now)) {
                        mealDate.setMinutes(mealDate.getMinutes() + 5);
                        createNotification(mealDated);
                        return;
                    }
                }
            }
        }
    }
}
