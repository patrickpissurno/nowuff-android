package com.exe.jpg.nowuff;

import android.app.Service;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Patrick on 25/10/2017.
 */
public class FCMService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        Log.d("FCM", "From: " + remoteMessage.getFrom());

        NotificationController controller = NotificationController.getInstance();
        if(controller == null)
            controller = new NotificationController();

        final RemoteMessage.Notification notification = remoteMessage.getNotification();
        final ArrayMap<String, String> data = (ArrayMap<String, String>) remoteMessage.getData();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0)
        {
            Log.d("FCM", "Message data payload: " + remoteMessage.getData());
            if(data.get("_title") != null && data.get("_body") != null)
            {
                final String title = data.get("_title");
                final String body = data.get("_body");
                data.remove("_title");
                data.remove("_body");
                controller.onReceiveNotification(title, body, data.size() > 0 ? data : null, this);
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null)
        {
            Log.d("FCM", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            controller.onReceiveNotification(notification.getTitle(), notification.getBody(), null, this);
        }
    }
}
