package com.sunbi.organisatiom.activity.kitabclub.classes;

import android.content.Context;
import android.content.SharedPreferences;

import com.sunbi.organisatiom.activity.kitabclub.MainActivity;

/**
 * Created by gokarna on 7/24/15.
 */
public class SharedPreferenceValueProvider {
    private Context context;

    public SharedPreferenceValueProvider(Context context) {
        this.context = context;
    }

    public String returnPreferenceValue() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.preference_value, context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        return username;
    }
}
