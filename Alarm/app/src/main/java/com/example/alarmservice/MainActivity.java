package com.example.alarmservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView alarmRecyclerView;
    private AlarmAdapter alarmAdapter;
    private List<Alarm> alarmList = new ArrayList<>();
    private FloatingActionButton fab;
    private Button stopAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmRecyclerView = findViewById(R.id.alarmRecyclerView);
        fab = findViewById(R.id.fab);
        stopAlarmButton = findViewById(R.id.stopAlarmButton);

        alarmAdapter = new AlarmAdapter(alarmList);
        alarmRecyclerView.setAdapter(alarmAdapter);
        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAlarmFragment();
            }
        });

        stopAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
            }
        });
    }

    private void openCreateAlarmFragment() {
        Fragment fragment = new CreateAlarmFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void addAlarm(Alarm alarm) {
        alarmList.add(alarm);
        alarmAdapter.notifyDataSetChanged();
    }

    private void stopAlarm() {
        Intent intent = new Intent(this, AlarmService.class);
        stopService(intent);
        Toast.makeText(this, "Alarm stopped", Toast.LENGTH_SHORT).show();
    }

}