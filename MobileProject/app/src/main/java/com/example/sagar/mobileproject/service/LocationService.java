package com.example.sagar.mobileproject.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.sagar.mobileproject.api.SendEmailAPI;

import com.example.sagar.mobileproject.constant.Constant;
import com.example.sagar.mobileproject.model.LocationAlertPojo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationService extends Service {
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //Toast.makeText(getApplicationContext(), "Location service initialization", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LocationAlertPojo locAlertObj = null;
                Intent locServResponse = new Intent();
                String rootcause=intent.getExtras().getString("rootcause");

                Location currentLocation = getCurrentLocation();
                if (currentLocation != null) {
                    //String locationAddr = UtilsAPI.decodeGPSLocation(getApplicationContext(), currentLocation.getLongitude(), currentLocation.getLatitude());
                    String addr ="";
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(),1);
                        if (addressList != null) {
                            Address address = addressList.get(0);
                            StringBuilder fullAddress = new StringBuilder("");
                            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                                fullAddress.append(address.getAddressLine(i)).append("\n");
                            }

                            addr = fullAddress.toString();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                 // Create location alert object
                    locAlertObj = new LocationAlertPojo();
                    locAlertObj.setLongitude(String.valueOf(currentLocation.getLongitude()));
                    locAlertObj.setLatitude(String.valueOf(currentLocation.getLatitude()));
                    locAlertObj.setFullAddress(addr);
                    locAlertObj.setRootcause(intent.getExtras().getString(Constant.ROOTCAUSE));
                    //locServResponse.putExtra("location", addr);
                    //locServResponse.putExtra("longitude", currentLocation.getLongitude());
                    //locServResponse.putExtra("latitude", currentLocation.getLatitude());
                    locServResponse.putExtra(Constant.LOCATIONALERT, locAlertObj);

                   /* locServResponse.putExtra("location", addr);
                    locServResponse.putExtra("longitude", currentLocation.getLongitude());
                    locServResponse.putExtra("latitude", currentLocation.getLatitude());
                    locServResponse.putExtra("rootcause",rootcause);*/

                    locServResponse.setAction(Constant.LOCATION_SERVICE);
                    //Log.i(" Location Lat ", String.valueOf(currentLocation.getLatitude()));
                    //Log.i(" Location Lon ", String.valueOf(currentLocation.getLongitude()));
                    //Log.i(" Address ", addr);
                } else {
                    locServResponse.setAction(Constant.LOCATION_SERVICE);
                    Log.d("Location return", "empty");
                }
                sendBroadcast(locServResponse);
                stopSelf();
            }
        }).start();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Location getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        } else {
            LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location bestLocation = null;
            Location myCurrentLocation = null;
            List<String> providers = mLocationManager.getAllProviders();
            for (String provider : providers) {
                myCurrentLocation = mLocationManager.getLastKnownLocation(provider);
                if (myCurrentLocation != null) {
                    if (bestLocation == null || (bestLocation != null && myCurrentLocation.getAccuracy() < bestLocation.getAccuracy())) {
                        bestLocation = myCurrentLocation;
                    }
                }
            }

            return bestLocation;
        }

    }
/*
    @Override
    protected Void doInBackground(Void... params) {
        Toast.makeText(context, "Location service initialization", Toast.LENGTH_LONG).show();
        Log.i("does it", "work");
        Looper.prepare();
        Location currentLocation = getCurrentLocation();
        if (currentLocation != null) {
            String locationAddr = UtilsAPI.decodeGPSLocation(context, currentLocation.getLongitude(), currentLocation.getLatitude());
            //locServResponse.putExtra("location", locationAddr);
            //locServResponse.putExtra("longitude", currentLocation.getLongitude());
            //locServResponse.putExtra("latitude", currentLocation.getLatitude());
            //locServResponse.setAction(Constant.LOCATION_SERVICE);
            Log.i(" Location Lat ", String.valueOf(currentLocation.getLatitude()));
            Log.i(" Location Lon ", String.valueOf(currentLocation.getLongitude()));
            Log.i(" Address ", locationAddr);
            SendEmailAPI.sendEmail(locationAddr);
        } else {
            //locServResponse.setAction(Constant.LOCATION_SERVICE);
            Log.i("Location return", "empty");
        }

        return null;
    }
    */
}
