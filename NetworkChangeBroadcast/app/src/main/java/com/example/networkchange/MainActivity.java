package com.example.networkchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NetworkChangeReceiver networkChangeReceiver;
    private Button btnToggleWiFi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToggleWiFi = findViewById(R.id.btnToggleWiFi);

        btnToggleWiFi.setOnClickListener(v -> {
            toggleWiFi();
        });

        networkChangeReceiver = new NetworkChangeReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }

    private void toggleWiFi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            boolean isWifiEnabled = wifiManager.isWifiEnabled();
            boolean success = wifiManager.setWifiEnabled(!isWifiEnabled);
            String message = isWifiEnabled ? "Turning WiFi off" : "Turning WiFi on";
            if (success) {
                message += " - Success";
            } else {
                message += " - Failed";
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", message);
        } else {
            Toast.makeText(this, "WiFiManager is not available", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "WiFiManager is not available");
        }
    }
}