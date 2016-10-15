package com.example.sagar.mobileproject.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SharedPreferenceAPI {
    public static void storeSharedPreferences(Context context, String fileName, Map<String, String> data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        for (Entry<String, String> pairs: data.entrySet()) {
            editor.putString(pairs.getKey(), pairs.getValue());
        }
        editor.commit();
    }

    public static Map<String, String> readSharePreferences(Context context, String fileName, Set<String> keys) {
        Map<String, String> data = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        for (String key: keys) {
            if (sharedPreferences.contains(key)) {
                data.put(key, sharedPreferences.getString(key, null));
                Log.i(key, sharedPreferences.getString(key, ""));
            }
        }

        return data;
    }
}
