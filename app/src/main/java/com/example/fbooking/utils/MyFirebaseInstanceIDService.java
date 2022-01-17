package com.example.fbooking.utils;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.fbooking.MainActivity;
import com.example.fbooking.R;
import com.example.fbooking.history.HistoryActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("TOKEN", s);

        //Ham test
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("TAG", "From: " + remoteMessage.getFrom());
        Log.d("TAG", "Message data payload: " + remoteMessage.getData());

        //Ham viet them
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification == null) {
            return;
        }

        String strTitle = notification.getTitle();
        String strBody = notification.getBody();

        sendNotification(strTitle, strBody);
    }

    //Ham viet them
    private void sendNotification(String strTitle, String strBody) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,
                MyApplication.CHANNEL_ID)
                .setContentTitle(strTitle)
                .setContentText(strBody)
                .setSmallIcon(R.drawable.logo_mini)
                .setContentIntent(pendingIntent);

        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }

    //Ham test
    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }
}
