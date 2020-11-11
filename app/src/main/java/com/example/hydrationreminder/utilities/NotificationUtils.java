package com.example.hydrationreminder.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.hydrationreminder.MainActivity;
import com.example.hydrationreminder.R;

public class NotificationUtils {

    private static final int WATER_REMINDER_PENDING_INTENT_ID = 1138;
    private static final String WATER_REMINDER_NOTIFICATION_CHANNEL_ID = "notification-channel-id";
    private static PendingIntent contentIntent(Context context){

        Intent startActivityIntent = new Intent(context, MainActivity.class);


        return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private static Bitmap largeIcon(Context context){
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, R.drawable.ic_launcher_foreground);
    }

    public static void remindUsersBecauseCharging(Context context){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel mChannel = new NotificationChannel(WATER_REMINDER_NOTIFICATION_CHANNEL_ID,context.getString(R.string.notification_channel_name),NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,WATER_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context,R.color.design_default_color_primary))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(largeIcon(context))
                .setContentTitle("This is a reminder to drink water")
                .setContentText("Drink water hoe, or else....")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Big ass text"))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN  && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(WATER_REMINDER_PENDING_INTENT_ID,notificationBuilder.build());

    }


}
