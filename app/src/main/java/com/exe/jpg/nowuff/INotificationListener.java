package com.exe.jpg.nowuff;

import android.support.v4.util.ArrayMap;

/**
 * Created by Patrick on 25/10/2017.
 */

public interface INotificationListener
{
    void onReceiveNotification(int id, String title, String body, ArrayMap<String, String> data);
}
