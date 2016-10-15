package com.example.sagar.mobileproject;

/**
 * Created by Karthik on 10-13-2016.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UninstallReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // fetching package names from extras
        Log.d("Display","Inside on recieve on uninstall app");
        String[] packageNames = intent.getStringArrayExtra("android.intent.extra.PACKAGES");

        if(packageNames!=null){
            for(String packageName: packageNames){
                Log.d("Display","Package is"+packageName);
                if(packageName!=null && packageName.equals("com.example.sagar.mobileproject")){
                    Log.d("Display","We can stop uninstall");
                    // start your activity here and ask the user for com.example.sagar.mobile password
                }
            }
        }
    }

}
