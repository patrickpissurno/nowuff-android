package com.exe.jpg.nowuff;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;

/**
 * Created by Patrick on 25/10/2017.
 */

public class NotificationController
{
    private static NotificationController instance;
    public static NotificationController getInstance(){
        return instance;
    }

    public static final int NOTIFICATION_TEST = 1;

    private ArrayList<INotificationListener> listeners;

    public NotificationController(){
        if(instance == null) {
            instance = this;
            listeners = new ArrayList<>();
        }
    }

    public void addListener(INotificationListener listener){
        if(listener != null && !listeners.contains(listener))
            listeners.add(listener);
    }

    public void removeListener(INotificationListener listener){
        if(listener != null && listeners.contains(listener))
            listeners.remove(listener);
    }

    public void onReceiveNotification(String title, String body, ArrayMap<String, String> data, Context context){
        if(!listeners.isEmpty()) {
            final int notificationId = Integer.parseInt(data == null ? "-1" : (data.get("notification_id") == null ? "-1" : data.get("notification_id")));

            for (INotificationListener listener : listeners)
                listener.onReceiveNotification(notificationId, title, body, data);
        }
        else
        {
            final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(SplashActivity.class);
            stackBuilder.addNextIntent(new Intent(context, SplashActivity.class));

            final PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(context.getResources().getString(R.string.notification_title))
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}
