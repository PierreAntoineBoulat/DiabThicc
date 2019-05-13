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

import com.game.pa2a.diabthicc.ConnectActivity;
import com.game.pa2a.diabthicc.HomeActivity;
import com.game.pa2a.diabthicc.ProfileActivity;
import com.game.pa2a.diabthicc.R;
import com.game.pa2a.diabthicc.Register2Activity;
import com.game.pa2a.diabthicc.StatsActivity;
import com.game.pa2a.diabthicc.TodayActivity;
import com.game.pa2a.diabthicc.models.Aliment;
import com.game.pa2a.diabthicc.models.CustomDate;
import com.game.pa2a.diabthicc.models.Meal;
import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.models.Profile;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static android.app.Notification.EXTRA_NOTIFICATION_TAG;

public class NotificationService extends Service {
    private static NotificationManagerCompat mNotificationManager;
    private static NotificationCompat.Builder notifNextMeal = null;
    private static NotificationCompat.Builder notifNextWeight = null;
    private static NotificationCompat.Builder notifOverflow = null;
    private static boolean firstConnexionOfDay = true;
    private static boolean firstNotifOfDay = true;
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
        if (CurrentUserService.currentUser == null) {
            CurrentUserService.currentUser = ConnectActivity.buildUser();
        }
        currentUser = CurrentUserService.currentUser;
        mNotificationManager = NotificationManagerCompat.from(context.get());
        createNotificationChannel();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new NotificationTask(), 0, 500);
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

    private void createNotificationMeal(Meal meal) {
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


        Intent resultIntent = new Intent(this, TodayActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);


        int resId = this.getResources().getIdentifier(
                meal.getImage(),
                "drawable",
                this.getPackageName()
        );
        Bitmap big = BitmapFactory.decodeResource(getResources(), resId);

        notifNextMeal = new NotificationCompat.Builder(context.get(), "channel_notif_meal_id")
                .setSmallIcon(R.drawable.ic_local_dining_black_24dp)
                .setContentTitle("Vous avez un nouveau repas à prendre aujourd'hui !")
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
                .addAction(R.drawable.ic_add_alarm_black_24dp, "REPORTER (30MIN)", snoozePendingIntent)
                .addAction(R.drawable.ic_home_black_24dp, "FERMER", dismissPendingIntent)
                .setContentIntent(resultPendingIntent);

        Log.d("APP LAUNCHER", "je suis là");
        mNotificationManager.notify(1, notifNextMeal.build());
    }

    private void createNotificationWeight() {

        Intent dismissIntent = new Intent(this, BroadcastCloseNotif.class);
        dismissIntent.setAction("dismiss");
        dismissIntent.putExtra("ID", 2);
        PendingIntent dismissPendingIntent =
                PendingIntent.getBroadcast(this, 2, dismissIntent, 0);

        Intent awayIntent = new Intent(this, StatsActivity.class);
        awayIntent.setAction("away");
        awayIntent.putExtra("ID", 2);
        PendingIntent awayPendingIntent =
                PendingIntent.getBroadcast(this, 2, awayIntent, 0);

        Intent resultIntent = new Intent(this, StatsActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(2, PendingIntent.FLAG_UPDATE_CURRENT);

        notifNextWeight = new NotificationCompat.Builder(context.get(), "channel_notif_weight_id")
                .setSmallIcon(R.drawable.ic_local_dining_black_24dp)
                .setContentTitle("C'est l'heure de la pesée !")
                .setContentText("N'oubliez pas de relever votre poid..!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Il est important de controler son poid tous les jours pour s'assurer du bon déroulement de vos objectifs !"))
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDeleteIntent(getDeleteIntent())
                .addAction(R.drawable.ic_add_alarm_black_24dp, "C'EST PARTI!", awayPendingIntent)
                .addAction(R.drawable.ic_home_black_24dp, "FERMER", dismissPendingIntent)
                .setContentIntent(resultPendingIntent);

        mNotificationManager.notify(2, notifNextWeight.build());
    }

    public void createNotificationObjectif(String title, String description) {
        Intent consulterIntent = new Intent(this, HomeActivity.class);
        consulterIntent.setAction("dismiss");
        consulterIntent.putExtra("ID", 3);
        PendingIntent consulterPendingIntent =
                PendingIntent.getBroadcast(this, 3, consulterIntent, 0);

        Intent modifierIntent = new Intent(this, ProfileActivity.class);
        modifierIntent.setAction("dismiss");
        modifierIntent.putExtra("ID", 3);
        PendingIntent modifierPendingIntent =
                PendingIntent.getBroadcast(this, 3, modifierIntent, 0);

        Intent dismissIntent = new Intent(this, BroadcastCloseNotif.class);
        dismissIntent.setAction("dismiss");
        dismissIntent.putExtra("ID", 3);
        PendingIntent dismissPendingIntent =
                PendingIntent.getBroadcast(this, 3, dismissIntent, 0);

        Intent resultIntent = new Intent(this, HomeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(3, PendingIntent.FLAG_UPDATE_CURRENT);

        notifNextWeight = new NotificationCompat.Builder(context.get(), "channel_notif_exed_id")
                .setSmallIcon(R.drawable.ic_local_dining_black_24dp)
                .setContentTitle(title)
                .setContentText(description.split("\n")[0] + ".")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(description))
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDeleteIntent(getDeleteIntent())
                .addAction(R.drawable.ic_add_alarm_black_24dp, "CONSULTER", consulterPendingIntent)
                .addAction(R.drawable.ic_add_alarm_black_24dp, "MODIFIER", modifierPendingIntent)
                .addAction(R.drawable.ic_home_black_24dp, "FERMER", dismissPendingIntent)
                .setContentIntent(resultPendingIntent);

        mNotificationManager.notify(3, notifNextWeight.build());
    }

    protected PendingIntent getDeleteIntent() {
        Intent intent = new Intent(this, BroadcastCloseNotif.class);
        intent.setAction("notification_cancelled");
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "channel_notif_meal";
            String description = "channel_notif_description_meal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_notif_meal_id", name, importance);
            channel.setDescription(description);

            CharSequence name2 = "channel_notif_weight";
            String description2 = "channel_notif_description_weight";
            int importance2 = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel2 = new NotificationChannel("channel_notif_weight_id", name2, importance2);
            channel2.setDescription(description2);

            CharSequence name3 = "channel_notif_exed";
            String description3 = "channel_notif_description_exed";
            int importance3 = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel3 = new NotificationChannel("channel_notif_exed_id", name3, importance3);
            channel3.setDescription(description3);

            NotificationManager notificationManager = context.get().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                List<NotificationChannel> channels = Arrays.asList(channel, channel2, channel3);
                notificationManager.createNotificationChannels(channels);
            }
        }
    }

    private class NotificationTask extends TimerTask {
        public void run() {
            if (firstConnexionOfDay && notifNextWeight == null) {
                createNotificationWeight();
                firstConnexionOfDay = false;
            }

            if (notifNextMeal == null && currentUser.getCurrentDiet().getMeals() != null) {
                Log.d("APP NotificationService", currentUser.toString());
                for (Meal mealDated : currentUser.getCurrentDiet().getMeals()) {
                    CustomDate now = new CustomDate();
                    CustomDate mealDate = mealDated.getConsommationDate();
                    mealDate.setMinutes(mealDate.getMinutes() - 5);
                    if (mealDate.isAnteriorAs(now)) {
                        mealDate.setMinutes(mealDate.getMinutes() + 5);
                        createNotificationMeal(mealDated);
                    }
                }
            }

            if (notifOverflow == null && currentUser.getProfil().getObjectif() != null && firstNotifOfDay) {
                firstNotifOfDay = false;
                int protConso = 0;
                int fatConso = 0;
                int carbsConso = 0;
                for (Meal m : currentUser.getCurrentDiet().getMeals()) {
                    for (Aliment a : m.getAliments()) {
                        protConso += a.getDiet().getProteinIntake();
                        fatConso += a.getDiet().getFatIntake();
                        carbsConso += a.getDiet().getCarbsIntake();
                    }
                }
                if (currentUser.getProfil().getMaxGlucides() < fatConso) {
                    String title = "Attention, vous avez depassé votre apport recommandé en glucides !";
                    int ex = currentUser.getProfil().getObjectif().getFatIntake() - currentUser.getProfil().getMaxGlucides();
                    String description = "Vous dépassez actuellement de " + ex + "g votre taux de glucide.\n";
                    description += "Arrangez votre alimention, ou modifiez vos objectifs";
                    createNotificationObjectif(title, description);
                }
                else if (currentUser.getProfil().getMaxLipides() < carbsConso) {
                    String title = "Attention, vous avez depassé votre apport recommandé en lipides !";
                    int ex = currentUser.getProfil().getObjectif().getFatIntake() - currentUser.getProfil().getMaxGlucides();
                    String description = "Vous dépassez actuellement de " + ex + "g votre taux de lipide.\n";
                    description += "Arrangez votre alimention, ou modifiez vos objectifs";
                    createNotificationObjectif(title, description);
                }
                else if (currentUser.getProfil().getMaxProt() < protConso) {
                    String title = "Attention, vous avez depassé votre apport recommandé en proteines !";
                    int ex = currentUser.getProfil().getObjectif().getFatIntake() - currentUser.getProfil().getMaxGlucides();
                    String description = "Vous dépassez actuellement de " + ex + "g votre taux de proteine.\n";
                    description += "Arrangez votre alimention, ou modifiez vos objectifs";
                    createNotificationObjectif(title, description);
                }
                /*if (currentUser.getProfil().getMaxProt() < currentUser.getProfil().getObjectif().getProteinIntake()) {
                    String title = "Attention, vous avez depassé votre apport calorifique recommandé!";
                    int ex = currentUser.getProfil().getObjectif().getFatIntake() - currentUser.getProfil().getMaxGlucides();
                    String description = "Vous dépassez actuellement de " + ex + "votre taux calorifique.\n";
                    description += "Arrangez votre alimention, ou modifiez vos objectifs";
                    createNotificationObjectif(title, description);
                }*/
            }

        }
    }
}
