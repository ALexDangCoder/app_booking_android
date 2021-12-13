package com.example.fbooking.room;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

public class RankRoomViewHolder extends RecyclerView.ViewHolder {
    public TextView tvNameRank;
    public View view;
    public RankRoomViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        tvNameRank = view.findViewById(R.id.tv_name_rank);
    }
}
