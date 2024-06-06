package com.example.alarmservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.HashMap;
import java.util.Map;

/**
 * create an Toha.
 */
public class CreateAlarmFragment extends Fragment {

    private TimePicker timePicker;
    private TextView ringtoneTextView;
    private Button selectRingtoneButton;
    private Button saveAlarmButton;
    private int selectedRingtoneResId = R.raw.alarm; // Default ringtone

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_alarm_fragment, container, false);

        timePicker = view.findViewById(R.id.timePicker);
        ringtoneTextView = view.findViewById(R.id.ringtoneTextView);
        selectRingtoneButton = view.findViewById(R.id.selectRingtoneButton);
        saveAlarmButton = view.findViewById(R.id.saveAlarmButton);

        selectRingtoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRingtoneDialog();
            }
        });

        saveAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

        return view;
    }

    private void showRingtoneDialog() {
        final String[] ringtones = getResources().getStringArray(R.array.ringtone_array);
        final int[] ringtoneResIds = {
                R.raw.alarm,
                R.raw.alarm1,
                R.raw.alarm_clock_old
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Ringtone")
                .setItems(ringtones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedRingtoneResId = ringtoneResIds[which];
                        ringtoneTextView.setText("Selected Ringtone: " + ringtones[which]);
                    }
                });
        builder.create().show();
    }

    private void setAlarm() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        Alarm alarm = new Alarm(hour, minute, selectedRingtoneResId, true);
        ((MainActivity) getActivity()).addAlarm(alarm);
        getFragmentManager().popBackStack();
    }
}