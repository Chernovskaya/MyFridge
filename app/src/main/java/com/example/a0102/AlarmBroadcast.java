package com.example.a0102;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmBroadcast extends BroadcastReceiver {
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "channel";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0, notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setContentTitle("Напоминание")
                        .setSmallIcon(R.drawable.icona1)
                        .setContentText("Пора выбросить продукт")
                        .setContentIntent(contentIntent)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}
