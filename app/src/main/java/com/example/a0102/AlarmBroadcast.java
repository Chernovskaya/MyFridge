package com.example.a0102;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/*
    Будильник для создания уведомления
*/
public class AlarmBroadcast extends BroadcastReceiver {
    
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        String title=intent.getStringExtra("title");
        String msg=intent.getStringExtra("msg");


        PendingIntent contentIntent = PendingIntent.getActivity(context,0, notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.icona1)
                        .setContentText(msg)
                        .setContentIntent(contentIntent)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
           notificationManager.createNotificationChannel(notificationChannel);
        }
        
        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}


