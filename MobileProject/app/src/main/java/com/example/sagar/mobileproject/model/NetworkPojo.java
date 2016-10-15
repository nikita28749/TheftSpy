package com.example.sagar.mobileproject.model;

public class NetworkPojo {
    private int networkId;
    private String isWifiNetwork;
    private String networkName;
    private String networkState;

    public String getNetworkState() {
        return networkState;
    }

    public void setNetworkState(String networkState) {
        this.networkState = networkState;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getWifiNetwork() {
        return isWifiNetwork;
    }

    public void setWifiNetwork(String wifiNetwork) {
        isWifiNetwork = wifiNetwork;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }
}
