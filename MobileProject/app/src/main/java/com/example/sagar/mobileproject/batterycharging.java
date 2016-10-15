package com.example.sagar.mobileproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Karthik on 10-08-2016.
 */
public class batterycharging extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        Log.d("Display", "Inside Reciever2 " + level);
        String action=intent.getAction();
        //new RetrieveFeedTask().execute(" From baterycharging "+level+action);
    }
}
