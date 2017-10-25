package com.exe.jpg.nowuff;

import android.content.Context;

import net.grandcentrix.tray.AppPreferences;

/**
 * Created by Patrick on 25/10/2017.
 */

public class PreferencesController
{
    private static PreferencesController instance = null;
    private final PreferencesWrapper wrapper;

    public PreferencesController(Context c)
    {
        instance = this;
        wrapper = new PreferencesWrapper(new AppPreferences(c));
    }

    public PreferencesWrapper getWrapper(){
        return wrapper;
    }

    public static PreferencesController getInstance()
    {
        return instance;
    }
    public static PreferencesController getInstance(Context c)
    {
        if(instance != null)
            return instance;
        return new PreferencesController(c);
    }
}
