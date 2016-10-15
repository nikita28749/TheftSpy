package com.example.sagar.mobileproject.activity;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.*;
import android.widget.Toast;

import com.example.sagar.mobileproject.ActivityDialog;
import com.example.sagar.mobileproject.PowerButtonService;
import com.example.sagar.mobileproject.R;
import com.example.sagar.mobileproject.batterycharging;
import com.example.sagar.mobileproject.receiver.MyBroadCastReceiver;
import com.example.sagar.mobileproject.savepassword;
import com.example.sagar.mobileproject.simchange;

public class ServiceActivity extends AppCompatActivity {
    TextView   batteryTxt;
    TelephonyManager tm;
    String strphoneType;
    SharedPreferences sharedPreferences;
    String IMEINumber, subscriberID, SIMSerialNumber, networkCountryISO, SIMCountryISO, softwareVersion, voiceMailNumber, phoneType;
    batterycharging mBatteryLevelReceiver;
    Button stopBroadCastButton = null;
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        startService(new Intent(this, PowerButtonService.class));
        stopBroadCastButton = (Button) findViewById(R.id.stopservicebtn);

    }

    public void getdata(View view) {
        obtainDetailsOfThePhoneFromTelephony();
    }
    public void sndGmail(View view) {
        Intent i = new Intent(this,savepassword.class);
        startActivity(i);
    }

    public void updatesim(View view){
        String result = isSimPresent(getApplicationContext());

        if (result.equals("SIM is present"))
        {
            IMEINumber = tm.getDeviceId();
            subscriberID = tm.getDeviceId();
            SIMSerialNumber = tm.getSimSerialNumber();
            networkCountryISO = tm.getNetworkCountryIso();
            SIMCountryISO = tm.getSimCountryIso();
            phoneType = isPhoneGSMOrNot();
            storePasscodeToSharedPreferenceFile(IMEINumber, subscriberID, SIMSerialNumber, networkCountryISO, SIMCountryISO, phoneType);
        }
    }

    public void obtainDetailsOfThePhoneFromTelephony() {
        String result = isSimPresent(getApplicationContext());

        if (result.equals("SIM is present"))
        {
            IMEINumber = tm.getDeviceId();
            subscriberID = tm.getDeviceId();
            SIMSerialNumber = tm.getSimSerialNumber();
            networkCountryISO = tm.getNetworkCountryIso();
            SIMCountryISO = tm.getSimCountryIso();
            phoneType = isPhoneGSMOrNot();
            storePasscodeToSharedPreferenceFile(IMEINumber, subscriberID, SIMSerialNumber, networkCountryISO, SIMCountryISO, phoneType);
            ifNewSIMCardIsDetected(IMEINumber, subscriberID, SIMSerialNumber, networkCountryISO, SIMCountryISO, phoneType);
        } else {
            Toast.makeText(getApplicationContext(), "SIM absent", Toast.LENGTH_LONG).show();
        }
    }

    public void ifNewSIMCardIsDetected(String IMEINumber, String subscriberID, String SIMSerialNumber, String networkCountryISO, String SIMCountryISO, String phoneType) {
        //Obtain details of the new sim
        String secondIMEINumber = (sharedPreferences.getString("IMEINumber", ""));
        String secondsubscriberID = (sharedPreferences.getString("SubscriberId", ""));
        String secondSIMSerialNumber = (sharedPreferences.getString("SIMCardSerialNumber", ""));
        String secondnetworkCountryISO = (sharedPreferences.getString("NetworkCountryISO", ""));
        String secondSIMCountryISO = (sharedPreferences.getString("SimCountryISO", ""));
        String secondphoneType = (sharedPreferences.getString("PhoneType", ""));
        Log.d("Display", "Sim details is " + secondIMEINumber + " " + secondsubscriberID + " " + secondSIMSerialNumber + " " + secondSIMCountryISO + " " + secondnetworkCountryISO + " " + secondphoneType);
        String result = secondIMEINumber+" "+ secondsubscriberID+" "+secondSIMSerialNumber+" "+secondSIMCountryISO+" "+secondnetworkCountryISO+" "+secondphoneType;

       /* if (!(IMEINumber.equals(secondIMEINumber) || subscriberID.equals(secondsubscriberID) || SIMSerialNumber.equals(secondSIMSerialNumber) ||
                networkCountryISO.equals(secondnetworkCountryISO) || SIMCountryISO.equals(secondSIMCountryISO)) && phoneType.equals(secondphoneType)) {
            Intent intentService = new Intent(MainActivity.this, MyService.class);
            startService(intentService);*/

        }



    public void storePasscodeToSharedPreferenceFile(String IMEINumber, String subscriberID, String SIMSerialNumber, String networkCountryISO, String SIMCountryISO, String phoneType) {
        sharedPreferences = getSharedPreferences("SIMCARD_ACTIVITY", MODE_PRIVATE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("IMEINumber", IMEINumber);
        editor.putString("SubscriberId", subscriberID);
        editor.putString("SIMCardSerialNumber", SIMSerialNumber);
        editor.putString("NetworkCountryISO", networkCountryISO);
        editor.putString("SimCountryISO", SIMCountryISO);
        editor.putString("PhoneType", phoneType);
        editor.commit();
        Toast.makeText(getApplicationContext(),"Sim Card details are saved",Toast.LENGTH_LONG).show();
        String result = IMEINumber+" "+ subscriberID+" "+SIMSerialNumber+" "+SIMCountryISO+" "+networkCountryISO+" "+phoneType;
        Log.d("Display","Sim details are "+result);
    }

    public String isSimPresent(Context context) {
            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
            if (tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT) {

                Log.d("Display", "absent");
                return "SIM not present";
            } else {
                Log.d("Display", "present");
                return "SIM is present";

            }

        }
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

    public void toggleBroadCast(View v) {
        ComponentName component = new ComponentName(this, MyBroadCastReceiver.class);
        ComponentName component2 = new ComponentName(this, simchange.class);
        int status = getPackageManager().getComponentEnabledSetting(component);
        if (status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || status == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
            Log.d("Receiver is ", "enabled");
            stopService(new Intent(this, PowerButtonService.class));
            this.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            this.getPackageManager().setComponentEnabledSetting(component2, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            stopBroadCastButton.setText("START\nSERVICE");
        } else if (status == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            Log.d("receiver is ", " disabled");
            startService(new Intent(this, PowerButtonService.class));
            this.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
            this.getPackageManager().setComponentEnabledSetting(component2, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT , PackageManager.DONT_KILL_APP);
            stopBroadCastButton.setText("STOP\nSERVICE");
        }
    }


  /*  @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mBatteryLevelReceiver);
    }*/

}
