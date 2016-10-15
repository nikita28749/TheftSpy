package com.example.sagar.mobileproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.sagar.mobileproject.PowerButtonService;
import com.example.sagar.mobileproject.api.NetworkDetectionAPI;
import com.example.sagar.mobileproject.api.SendEmailAPI;
import com.example.sagar.mobileproject.constant.Constant;
import com.example.sagar.mobileproject.model.LocationAlertPojo;
import com.example.sagar.mobileproject.service.LocationService;

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            //Toast.makeText(context, "Network changes", Toast.LENGTH_LONG).show();
            boolean notifyUser =  NetworkDetectionAPI.isNetworkChanged(context);
            if (notifyUser) {
                startLocationService(context, Constant.NETWORK_SWITCH_DETECTTION_ROOTCAUSE);
            }
        } else if (intent.getAction().equals("com.android.intent.location")) {
            LocationAlertPojo alertObj =  (LocationAlertPojo) intent.getSerializableExtra(Constant.LOCATIONALERT);
            if (alertObj != null) {
                //Toast.makeText(context, "Location is received\n" + alertObj.getFullAddress() + ": " + alertObj.getRootcause(), Toast.LENGTH_LONG).show();
                new SendEmailAPI(context).execute(alertObj);
            }  else {
                Toast.makeText(context, "Failed to retrieve location", Toast.LENGTH_LONG).show();
            }
        } else if (intent.getAction().equals("android.intent.action.BATTERY_LOW") ||
                intent.getAction().equals("android.intent.action.BATTERY_OKAY")) {
            startLocationService(context, Constant.BATTERY_LOW_DETECTION_ROOTCAUSE);
        } else if (intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")) {
            startLocationService(context,Constant.DEVICE_SHUTDOWN_ROOTCAUSE);
        } else if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, PowerButtonService.class));
        }
    }

    public void startLocationService(Context context, String rootCause) {
        Intent locService = new Intent(context, LocationService.class);
        locService.putExtra(Constant.ROOTCAUSE,rootCause);
        context.startService(locService);
    }
}
