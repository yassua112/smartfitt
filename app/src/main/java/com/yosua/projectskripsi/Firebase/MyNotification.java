package com.yosua.projectskripsi.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.yosua.projectskripsi.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyNotification extends FirebaseMessagingService {

    private static final String TAG = "MyNotification";
    private FirebaseInstanceId firebaseInstanceId;
    
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

    }

    public void showNotification(String title, String message){

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANEL_ID = "com.example.projectskripsi";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL_ID,"Notification",notificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Smart FIT FOOD ");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANEL_ID);
              builder.setAutoCancel(true)
                      .setDefaults(Notification.DEFAULT_ALL)
                      .setWhen(System.currentTimeMillis())
                      .setSmallIcon(R.mipmap.ic_launcher_round)
                      .setContentTitle(title)
                      .setContentText(message)
                      .setContentInfo("Info")
                ;

  notificationManager.notify(new Random().nextInt(),builder.build());


    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);


    }


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
