package com.oneway2.onway;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FcmMessagingService extends FirebaseMessagingService {
    @Override

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            String img_url = remoteMessage.getData().get("img_url");

            Intent i = new Intent(FcmMessagingService.this, MainActivity2.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent notification = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            String channel_id = "Default";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channel_id);
            builder.setSmallIcon(R.drawable.onwaysmallicon);
            builder.setContentTitle(title);
            builder.setContentText(message);
            builder.setContentIntent(notification);
            builder.setSound(sounduri);
//            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
           builder.setAutoCancel(true);
           builder.setContentIntent(notification);


            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel =new NotificationChannel(channel_id, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0,builder.build());

        }

    }

}