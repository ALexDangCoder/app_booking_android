package com.example.fbooking.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

import java.util.ArrayList;
import java.util.List;

public class UtilitiesAdapter extends RecyclerView.Adapter<UtilitiesViewHolder> {
    private View view;

    Context context;
    private List<Utilities> utilitiesList;
    private OnUtilitiesCheckListener listener;

    public UtilitiesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Utilities> utilitiesList, OnUtilitiesCheckListener listener) {
        this.utilitiesList = utilitiesList;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UtilitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_row, parent, false);
        return new UtilitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilitiesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Utilities utilities = utilitiesList.get(position);
        if (utilities == null) {
            return;
        }

        if (utilities.isChecked()) {
            holder.view.setBackgroundResource(R.drawable.bg_color_purple);
            holder.ivChecked.setVisibility(View.VISIBLE);
        } else {
            holder.view.setBackgroundResource(R.drawable.bg_color_grey);
            holder.ivChecked.setVisibility(View.GONE);
        }

        holder.tvTitle.setText(utilities.getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utilitiesList.get(position).isChecked()) {
                    utilitiesList.get(position).setChecked(false);
                    listener.onUtilitiesCheck(utilitiesList.get(position));
                } else {
                    utilitiesList.get(position).setChecked(true);
                    listener.onUtilitiesCheck(utilitiesList.get(position));
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (utilitiesList != null) {
            return utilitiesList.size();
        }
        return 0;
    }

}
