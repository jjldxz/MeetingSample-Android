package com.jjl.dxz.platform.meeting.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

    private static SharedPreferences sp;

    public static void init(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public static Integer getInt(String key, int defaultVale) {
        return sp.getInt(key, defaultVale);
    }

    public static void putStr(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public static String getStr(String key, String defaultVale) {
        return sp.getString(key, defaultVale);
    }

    public static void putBool(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBool(String key, boolean defaultVale) {
        return sp.getBoolean(key, defaultVale);
    }

    public static void putFloat(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key, float defaultVale) {
        return sp.getFloat(key, defaultVale);
    }
}
