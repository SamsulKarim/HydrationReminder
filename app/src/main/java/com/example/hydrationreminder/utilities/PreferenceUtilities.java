package com.example.hydrationreminder.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtilities {

    public static final String KEY_WATER_COUNT = "water_count";
    public static final String KEY_CHARGING_REMINDER_COUNT= "charging-reminder-count";
    public static final int DEFAULT_COUNT = 0;

    synchronized private static void setKeyWaterCount(Context context, int glassesOfWater){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor  editor = prefs.edit();
        editor.putInt(KEY_WATER_COUNT,glassesOfWater );
        editor.apply();
    }

    public static int getWaterCount(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getInt(KEY_WATER_COUNT,DEFAULT_COUNT);
    }

    public static int getChargingReminderCount(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getInt(KEY_CHARGING_REMINDER_COUNT,DEFAULT_COUNT);
    }

    synchronized public static void incrementWaterCount(Context context){

        int waterCount = PreferenceUtilities.getWaterCount(context);
        PreferenceUtilities.setKeyWaterCount(context,++waterCount);
    }


}
