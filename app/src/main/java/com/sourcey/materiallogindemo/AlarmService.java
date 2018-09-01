package com.sourcey.materiallogindemo;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmService extends IntentService {

    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        sendNotification("Wake Up! Wake Up!");
    }

    private void sendNotification(String msg) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MyReminder.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this)
                .setContentTitle("Reminder!!!!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("It's movie time!!")
                .setAutoCancel(true)
                .setVisibility(0);
    //.setStyle(new NotificationCompat.BigTextStyle().bigText("A very long message that cannot be accomodated in one line."))

        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }

}
