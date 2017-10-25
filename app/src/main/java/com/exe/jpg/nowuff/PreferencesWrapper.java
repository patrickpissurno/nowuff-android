package com.exe.jpg.nowuff;

import android.util.Log;

import com.google.gson.Gson;

import net.grandcentrix.tray.AppPreferences;

import java.util.List;

/**
 * Created by Patrick on 25/10/2017.
 */

public class PreferencesWrapper
{
    private static final boolean DEBUG_ENABLED = true;

    private final AppPreferences prefs;
    private final Gson gson;

    public PreferencesWrapper(AppPreferences prefs){
        this.prefs = prefs;
        gson = new Gson();
    }

    public boolean getBoolean(String key, boolean defaultValue){
        return prefs.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key){
        return getBoolean(key, false);
    }

    public Boolean[] getBooleanArray(String key){
        final String str = prefs.getString(key, null);
        if(str == null)
            return null;
        return gson.fromJson(str, Boolean[].class);
    }

    public String getString(String key, String defaultValue){
        return prefs.getString(key, defaultValue);
    }

    public String getString(String key){
        return getString(key, null);
    }

    public String[] getStringArray(String key){
        final String str = prefs.getString(key, null);
        if(str == null)
            return null;
        return gson.fromJson(str, String[].class);
    }

    public int getInt(String key, int defaultValue){
        return prefs.getInt(key, defaultValue);
    }

    public int getInt(String key){
        return getInt(key, -1);
    }

    public Integer[] getIntArray(String key){
        final String str = prefs.getString(key, null);
        if(str == null)
            return null;
        return gson.fromJson(str, Integer[].class);
    }

    public long getLong(String key, long defaultValue){
        return prefs.getLong(key, defaultValue);
    }

    public long getLong(String key){
        return getLong(key, -1);
    }

    public Long[] getLongArray(String key){
        final String str = prefs.getString(key, null);
        if(str == null)
            return null;
        return gson.fromJson(str, Long[].class);
    }

    public Object getObject(String key, Class c){
        final String str = prefs.getString(key, null);
        if(str == null)
            return null;
        return gson.fromJson(str, c);
    }

    public Object getObjectArray(String key, Class c) throws ClassNotFoundException{
        final String str = prefs.getString(key, null);
        if(str == null)
            return null;
        return gson.fromJson(str, GetArrayClass(c));
    }

    public void put(String key, boolean value){
        prefs.put(key, value);
    }

    public void put(String key, String value){
        prefs.put(key, value);
    }

    public void put(String key, int value){
        prefs.put(key, value);
    }

    public void put(String key, long value){
        prefs.put(key, value);
    }

    public void put(String key, boolean[] values){
        prefs.put(key, debugJson(key, gson.toJson(values)));
    }

    public void put(String key, Boolean[] values){
        prefs.put(key, debugJson(key, gson.toJson(values)));
    }

    public void put(String key, String[] values){
        prefs.put(key, debugJson(key, gson.toJson(values)));
    }

    public void put(String key, int[] values){
        prefs.put(key, debugJson(key, gson.toJson(values)));
    }

    public void put(String key, Integer[] values){
        prefs.put(key, debugJson(key, gson.toJson(values)));
    }

    public void put(String key, long[] values){
        prefs.put(key, debugJson(key, gson.toJson(values)));
    }

    public void put(String key, Long[] values){
        prefs.put(key, debugJson(key, gson.toJson(values)));
    }

    public void put(String key, Object value){
        prefs.put(key, debugJson(key, gson.toJson(value)));
    }

    public void put(String key, Object[] values){
        prefs.put(key, debugJson(key, gson.toJson(values)));
    }

    public void putBooleanList(String key, List<Boolean> values){
        put(key, values.toArray(new Boolean[values.size()]));
    }

    public void putStringList(String key, List<String> values){
        put(key, values.toArray(new String[values.size()]));
    }

    public void putIntList(String key, List<Integer> values){
        put(key, values.toArray(new Integer[values.size()]));
    }

    public void putLongList(String key, List<Long> values){
        put(key, values.toArray(new Long[values.size()]));
    }

    private static String debugJson(String key, String json){
        if(DEBUG_ENABLED)
            Log.d("JSON: " + key, json);
        return json;
    }

    public static Class<?> GetArrayClass(Class<?> componentType) throws ClassNotFoundException{
        final ClassLoader classLoader = componentType.getClassLoader();
        final String name;
        if(componentType.isArray()){
            // just add a leading "["
            name = "["+componentType.getName();
        }else if(componentType == boolean.class){
            name = "[Z";
        }else if(componentType == byte.class){
            name = "[B";
        }else if(componentType == char.class){
            name = "[C";
        }else if(componentType == double.class){
            name = "[D";
        }else if(componentType == float.class){
            name = "[F";
        }else if(componentType == int.class){
            name = "[I";
        }else if(componentType == long.class){
            name = "[J";
        }else if(componentType == short.class){
            name = "[S";
        }else{
            // must be an object non-array class
            name = "[L"+componentType.getName()+";";
        }
        return classLoader != null ? classLoader.loadClass(name) : Class.forName(name);
    }
}
