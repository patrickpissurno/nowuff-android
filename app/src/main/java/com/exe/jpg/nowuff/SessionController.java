package com.exe.jpg.nowuff;

/**
 * Created by Patrick on 25/10/2017.
 */

public class SessionController
{
    private static SessionController instance;
    public static SessionController getInstance(){
        return instance;
    }

    private static final String TOKEN_KEY = "authToken";
    private static final String ID_KEY = "authId";

    private String authToken = null;
    private long id = -1;
    private String fcmToken = null;
    private boolean fcmTokenPendingUpdate = false;

    public SessionController(){
        instance = this;

        authToken = PreferencesController.getInstance().getWrapper().getString(TOKEN_KEY, authToken);
        id = PreferencesController.getInstance().getWrapper().getLong(ID_KEY, id);
        fcmToken = getFcmToken();
        fcmTokenPendingUpdate = isPendingFcmTokenUpdate();
    }

    public void setAuth(String authToken, long id){
        if(authToken == null || id <= 0)
            return;

        this.authToken = authToken;
        this.id = id;

        final PreferencesWrapper prefs = PreferencesController.getInstance().getWrapper();
        prefs.put(TOKEN_KEY, this.authToken);
        prefs.put(ID_KEY, this.id);
    }

    public String getAuthToken(){
        return authToken;
    }

    public long getId(){
        return id;
    }

    public boolean isAuthenticated(){
        return authToken != null;
    }

    public boolean isPendingFcmTokenUpdate(){
        return PreferencesController.getInstance().getWrapper().getBoolean(FCMInstanceIDService.KEY_PENDING_NEW_TOKEN, fcmTokenPendingUpdate);
    }

    public void removePendingFcmTokenUpdate(){
        PreferencesController.getInstance().getWrapper().put(FCMInstanceIDService.KEY_PENDING_NEW_TOKEN, false);
    }

    public String getFcmToken(){
        return PreferencesController.getInstance().getWrapper().getString(FCMInstanceIDService.KEY_FCM_TOKEN, fcmToken);
    }

    public void reset(){
        this.authToken = null;
        this.id = -1;
        final PreferencesWrapper prefs = PreferencesController.getInstance().getWrapper();
        prefs.put(TOKEN_KEY, this.authToken);
        prefs.put(ID_KEY, this.id);
        prefs.put(FCMInstanceIDService.KEY_PENDING_NEW_TOKEN, true);
    }
}
