package com.example.networkchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is connected
            Log.d("NetworkChangeReceiver", "Network is connected");
            toggleWiFi(context, true);
        } else {
            // Network is disconnected
            Log.d("NetworkChangeReceiver", "Network is disconnected");
            toggleWiFi(context, false);
        }
    }

    private void toggleWiFi(Context context, boolean enable) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            boolean isWifiEnabled = wifiManager.isWifiEnabled();
            boolean success = wifiManager.setWifiEnabled(enable);
            String message = enable ? "Turning WiFi on" : "Turning WiFi off";
            if (success) {
                message += " - Success";
            } else {
                message += " - Failed";
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            Log.d("NetworkChangeReceiver", message);
        } else {
            Toast.makeText(context, "WiFiManager is not available", Toast.LENGTH_SHORT).show();
            Log.d("NetworkChangeReceiver", "WiFiManager is not available");
        }
    }
}
