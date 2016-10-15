package com.example.sagar.mobileproject;

/**
 * Created by Karthik on 10-13-2016.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.sagar.mobileproject.api.SendEmailAPI;
import com.example.sagar.mobileproject.constant.Constant;
import com.example.sagar.mobileproject.service.LocationService;

public class simchange extends BroadcastReceiver  {
    TelephonyManager tm;
    String state;
    SharedPreferences sharedPreferences;
    String strphoneType;
    String IMEINumber, subscriberID, SIMSerialNumber, networkCountryISO, SIMCountryISO, softwareVersion, voiceMailNumber, phoneType;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Display", "Sim has changed");

        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        if (tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT) {

            Log.d("Display", "absent");
             state="SIM not present";
            Intent locService = new Intent(context, LocationService.class);
            locService.putExtra(Constant.ROOTCAUSE, "Sim Card Removed");
            context.startService(locService);
        } else {
            Log.d("Display", "present");
             state="SIM is present";
            IMEINumber = tm.getDeviceId();
            subscriberID = tm.getDeviceId();
            SIMSerialNumber = tm.getSimSerialNumber();
            networkCountryISO = tm.getNetworkCountryIso();
            SIMCountryISO = tm.getSimCountryIso();
            phoneType = isPhoneGSMOrNot();
            //ifNewSIMCardIsDetected(IMEINumber,subscriberID,SIMSerialNumber,networkCountryISO,SIMCountryISO,phoneType,context);
            Intent locService = new Intent(context, LocationService.class);
            locService.putExtra(Constant.ROOTCAUSE,"Sim Card Notification");
            context.startService(locService);
        }

    }
    public void ifNewSIMCardIsDetected(String IMEINumber, String subscriberID, String SIMSerialNumber, String networkCountryISO, String SIMCountryISO, String phoneType,Context context) {
        //Obtain details of the new sim

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String secondIMEINumber = (sharedPreferences.getString("IMEINumber", ""));
        String secondsubscriberID = (sharedPreferences.getString("SubscriberId", ""));
        String secondSIMSerialNumber = (sharedPreferences.getString("SIMCardSerialNumber", ""));
        String secondnetworkCountryISO = (sharedPreferences.getString("NetworkCountryISO", ""));
        String secondSIMCountryISO = (sharedPreferences.getString("SimCountryISO", ""));
        String secondphoneType = (sharedPreferences.getString("PhoneType", ""));
        Log.d("Display", "Sim details is " + secondIMEINumber + " " + secondsubscriberID + " " + secondSIMSerialNumber + " " + secondSIMCountryISO + " " + secondnetworkCountryISO + " " + secondphoneType);
        String result = secondIMEINumber+" "+ secondsubscriberID+" "+secondSIMSerialNumber+" "+secondSIMCountryISO+" "+secondnetworkCountryISO+" "+secondphoneType;

       if (!(IMEINumber.equals(secondIMEINumber) || subscriberID.equals(secondsubscriberID) || SIMSerialNumber.equals(secondSIMSerialNumber) ||
                networkCountryISO.equals(secondnetworkCountryISO) || SIMCountryISO.equals(secondSIMCountryISO)) && phoneType.equals(secondphoneType)) {
          // new SendEmailAPI(context).execute("New Sim card details are :" + System.lineSeparator() + "IMEI Number :" + IMEINumber + System.lineSeparator() + "Subscriber Id :" + subscriberID + System.lineSeparator() + "Sim Serial Number :" + SIMSerialNumber + System.lineSeparator() + "Network Country ISO :" + networkCountryISO + System.lineSeparator() + "Sim Country Code :" + SIMCountryISO + System.lineSeparator() + "Phone Type" + phoneType);
       }else{
          // new SendEmailAPI(context).execute("Same Sim card details are :" + System.lineSeparator() + "IMEI Number :" + IMEINumber + System.lineSeparator() + "Subscriber Id :" + subscriberID + System.lineSeparator() + "Sim Serial Number :" + SIMSerialNumber + System.lineSeparator() + "Network Country ISO :" + networkCountryISO + System.lineSeparator() + "Sim Country Code :" + SIMCountryISO + System.lineSeparator() + "Phone Type" + phoneType);

    }}


    public String isPhoneGSMOrNot() {
        int phoneType = tm.getPhoneType();

        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType = "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType = "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType = "NONE";
                break;
        }
        return strphoneType;
    }
}
