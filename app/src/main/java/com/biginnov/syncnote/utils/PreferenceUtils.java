package com.biginnov.syncnote.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ken on 2015/12/11.
 */
public class PreferenceUtils {

    /**
     * set String Preference Value
     *
     * @param context
     * @param prefName Preference name
     * @param Value    Preference value
     */
    public static void setStringPrefrences(Context context, String prefName, String Value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(prefName, Value);
        editor.apply();
    }

    /**
     * get String Preference Value
     *
     * @param context
     * @param prefName
     * @return
     */
    public static String getStringPrefrences(Context context, String prefName) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains(prefName))
            return prefs.getString(prefName, null);
        else
            return "";
    }
}
