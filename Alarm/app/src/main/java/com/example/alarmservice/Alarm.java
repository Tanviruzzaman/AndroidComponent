package com.example.alarmservice;

import android.content.Context;

public class Alarm {

    private String time;
    private int ringtoneResId;
    private boolean isEnabled;

    public Alarm(int hour, int minute, int ringtoneResId, boolean isEnabled) {
        this.time = String.format("%02d:%02d", hour, minute);
        this.ringtoneResId = ringtoneResId;
        this.isEnabled = isEnabled;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRingtoneResId() {
        return ringtoneResId;
    }

    public void setRingtoneResId(int ringtoneResId) {
        this.ringtoneResId = ringtoneResId;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getRingtoneName(Context context) {
        if (ringtoneResId == R.raw.alarm) {
            return context.getString(R.string.ringtone_1);
        } else if (ringtoneResId == R.raw.alarm1) {
            return context.getString(R.string.ringtone_2);
        } else if (ringtoneResId == R.raw.alarm_clock_old) {
            return context.getString(R.string.ringtone_3);
        } else {
            return context.getString(R.string.unknown_ringtone);
        }
    }

}

