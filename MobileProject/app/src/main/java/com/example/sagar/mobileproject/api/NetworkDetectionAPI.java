package com.example.sagar.mobileproject.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.example.sagar.mobileproject.constant.Constant;
import com.example.sagar.mobileproject.model.NetworkPojo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class NetworkDetectionAPI {
    public static NetworkPojo getAvailableNetwork(Context context) {
        NetworkPojo networkPojo = null;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            networkPojo = new NetworkPojo();
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                networkPojo.setNetworkId(-1);
                networkPojo.setWifiNetwork("false");
                networkPojo.setNetworkName(activeNetworkInfo.getSubtypeName());
                networkPojo.setNetworkState(activeNetworkInfo.getState().name());
                return networkPojo;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null && wifiInfo.getNetworkId() != -1) {
                    networkPojo.setWifiNetwork("true");
                    networkPojo.setNetworkName(activeNetworkInfo.getTypeName());
                    networkPojo.setNetworkState(activeNetworkInfo.getState().name());
                    networkPojo.setNetworkId(wifiInfo.getNetworkId());
                    return networkPojo;
                }
            }
        }
        return null;
    }

    public static boolean isNetworkChanged(Context context) {
        Set<String> networkIds = new HashSet<String>(Arrays.asList("networkId", "networkName", "networkState", "isWifiNetwork"));
        NetworkPojo availableNetwork = NetworkDetectionAPI.getAvailableNetwork(context);
        boolean notifyUser = false;
        if (availableNetwork != null) {
            //Toast.makeText(context, "Network is available", Toast.LENGTH_LONG).show();

            Map<String, String> data = SharedPreferenceAPI.readSharePreferences(context, Constant.NETWORK, networkIds);

            if (data.keySet().size() > 0) {
                if (availableNetwork.getWifiNetwork().equals("true") && data.get("isWifiNetwork").equals("false")) {
                    notifyUser = true;
                }

                if (availableNetwork.getWifiNetwork().equals("true") && data.get("isWifiNetwork").equals("true")) {
                    if (availableNetwork.getNetworkId() != Integer.parseInt(data.get("networkId"))) {
                        notifyUser = true;
                    }
                }

                if (availableNetwork.getWifiNetwork().equals("false") && data.get("isWifiNetwork").equals("false")) {
                    if (!availableNetwork.getNetworkName().equals(data.get("networkName"))) {
                        notifyUser = true;
                    }
                }
            }

            data.put("networkId", String.valueOf(availableNetwork.getNetworkId()));
            data.put("networkName", availableNetwork.getNetworkName());
            data.put("networkState", availableNetwork.getNetworkState());
            data.put("isWifiNetwork", availableNetwork.getWifiNetwork());

            SharedPreferenceAPI.storeSharedPreferences(context, Constant.NETWORK, data);
        } else {
            Toast.makeText(context, "No network", Toast.LENGTH_LONG).show();
            notifyUser = false;
        }

        return notifyUser;

    }
}
