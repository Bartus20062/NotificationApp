package com.example.notification;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class notificationHelper {
    private static final String CHANNEL_ID_DEFAULT = "jakis domyslny kanał";
    public static final String CHANNEL_ID_LOW ="jakis domyslny kanal niski";
    public static final String CHANNEL_ID_HIGH = "sexdrvftbgynhumjiok";
    private static final String CHANNEL_NAME = "domyslna nazwa kanalu";

    public static void createNotificationChannels(Context context){
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channellow = new NotificationChannel(CHANNEL_ID_LOW, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            NotificationChannel channelDefault = new NotificationChannel(CHANNEL_ID_DEFAULT, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channelHigh = new NotificationChannel(CHANNEL_ID_HIGH, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channellow);
                notificationManager.createNotificationChannel(channelDefault);
                notificationManager.createNotificationChannel(channelHigh);
            }
        }
    }

    public static void sendNotification(String CHANNEL_ID, int NOTIFICATION_ID, AppCompatActivity activity, Context context, String title, String message, int styleType, Integer LargeIconResID){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.TIRAMISU){
            if(activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                activity.requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},100);
                return;
            }
        };
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(activity, CHANNEL_ID)
                        .setSmallIcon(R.drawable.aniel)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

        switch(styleType){
            case 1:
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                break;
            case 2:
                if(LargeIconResID!=null){
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), LargeIconResID);
                    builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setBigContentTitle(title));
                    break;
                }
            case 3:
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.addLine(message);
                inboxStyle.addLine("Kolejna linia tekstu");
                builder.setStyle(inboxStyle);
                break;
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
