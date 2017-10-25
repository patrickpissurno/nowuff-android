package com.exe.jpg.nowuff;

import android.app.Service;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Patrick on 25/10/2017.
 */
public class FCMInstanceIDService extends FirebaseInstanceIdService
{
    public static final String KEY_PENDING_NEW_TOKEN = "updateFCM";
    public static final String KEY_FCM_TOKEN = "fcmToken";

    @Override
    public void onTokenRefresh()
    {
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM", "Refreshed token: " + refreshedToken);

        if(refreshedToken != null)
            sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token)
    {
        final PreferencesWrapper prefs = PreferencesController.getInstance(getBaseContext()).getWrapper();
        prefs.put(KEY_PENDING_NEW_TOKEN, true);
        prefs.put(KEY_FCM_TOKEN, token);
    }
}
