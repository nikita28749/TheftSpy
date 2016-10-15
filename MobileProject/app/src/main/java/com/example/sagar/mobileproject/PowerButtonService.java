package com.example.sagar.mobileproject;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class PowerButtonService extends Service {

    public PowerButtonService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Display","Service has started");
        LinearLayout mLinear = new LinearLayout(getApplicationContext()) {

            //home or recent button
            public void onCloseSystemDialogs(String reason) {
                if ("globalactions".equals(reason)) {
                    Log.i("Key", "Long press on power button");
                    SharedPreferences sharedPreferences = getSharedPreferences("PASSCODE_ACTIVITY", MODE_PRIVATE);
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor  editor = sharedPreferences.edit();
                    String flag= sharedPreferences.getString("Flag","false");
                    Log.d("Display","Stored flag is "+flag);
                    if(flag.equalsIgnoreCase("false")){
                        Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                        sendBroadcast(closeDialog);
                        Intent i = new Intent();
                        i.setClassName("com.example.sagar.mobileproject", "com.example.sagar.mobileproject.ActivityDialog");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(i);
                    }else{
                        editor.putString("Flag","false");
                        editor.apply();

                    }
                } else if ("homekey".equals(reason)) {
                    //home key pressed
                } else if ("recentapss".equals(reason)) {
                    // recent apps button clicked
                }
            }


            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                Log.d("Display","Inside disptachkey");
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                        || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
                        || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN
                        || event.getKeyCode() == KeyEvent.KEYCODE_CAMERA
                        || event.getKeyCode() == KeyEvent.KEYCODE_POWER) {
                    Log.d("Display", " Key pressed is " + event.getKeyCode());
                }
                return super.dispatchKeyEvent(event);
            }
        };

        mLinear.setFocusable(true);

        View mView = LayoutInflater.from(this).inflate(R.layout.service_layout, mLinear);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        //params
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                100,
                100,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
    wm.addView(mView, params);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

        Log.d("Display","Service is destroyed");
    }

}

