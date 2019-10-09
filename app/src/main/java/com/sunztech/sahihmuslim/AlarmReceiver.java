package com.sunztech.sahihmuslim;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.sunztech.sahihmuslim.BackgroundTasks.AppExecutor;
import com.sunztech.sahihmuslim.Database.DatabaseAccess;
import com.sunztech.sahihmuslim.Models.HadithItem;
import com.sunztech.sahihmuslim.Utilities.AppConstants;

import androidx.core.app.NotificationCompat;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static com.sunztech.sahihmuslim.Utilities.MyUtils.setAlarmManager;
import static com.sunztech.sahihmuslim.Utilities.MyUtils.setAlarmManager;

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private String CHANNEL_1_ID = "CHANNEL_1_ID";
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("lksjdf","receiver called");

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");
            notificationManager.createNotificationChannel(channel1);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("lksjdf","thread called");
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                databaseAccess.open();
                HadithItem hadithItem = databaseAccess.getRandomHadith();
                databaseAccess.close();

                AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("lksjdf","main thread called");

                        Intent notificationIntent = new Intent(context, HadithDetailsActivity.class);
                        AppConstants.CURRENT_HADITH_LIST.clear();
                        AppConstants.CURRENT_HADITH_LIST.add(hadithItem);
                        notificationIntent.addFlags(FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                                0, notificationIntent, 0);
                        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                                .setContentText(hadithItem.getUrdu())
                                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .build();
                        notificationManager.notify((int) (System.currentTimeMillis() - 10000000), notification);

                    }
                });

            }
        }).start();

        setAlarmManager(context,System.currentTimeMillis() + 86400000L);








    }
}
