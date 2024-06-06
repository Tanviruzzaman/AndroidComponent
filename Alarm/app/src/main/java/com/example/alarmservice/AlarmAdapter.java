package com.example.alarmservice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<Alarm> alarmList;

    public AlarmAdapter(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        holder.alarmTimeTextView.setText(alarm.getTime());
        holder.ringtoneTextView.setText(alarm.getRingtoneName(holder.itemView.getContext()));
        holder.alarmSwitch.setChecked(alarm.isEnabled());
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder {

        TextView alarmTimeTextView;
        TextView ringtoneTextView;
        Switch alarmSwitch;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmTimeTextView = itemView.findViewById(R.id.alarmTimeTextView);
            ringtoneTextView = itemView.findViewById(R.id.ringtoneTextView);
            alarmSwitch = itemView.findViewById(R.id.alarmSwitch);
        }
    }
}
