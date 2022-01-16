package com.example.fbooking.booking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fbooking.R;

import java.util.List;

public class CheckInTimeAdapter extends ArrayAdapter<String> {
    public CheckInTimeAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tv_selected);

        String item = this.getItem(position);
        if (item != null) {
            tvSelected.setText(item);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
        TextView tvTime = convertView.findViewById(R.id.tv_time);

        String item = this.getItem(position);
        if (item.equals("Thời gian nhận!")) {
            tvTime.setTextColor(Color.GRAY);
        }

        if (item != null) {
            tvTime.setText(item);
        }
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            return false;
        } else {
            return true;
        }
    }
}
