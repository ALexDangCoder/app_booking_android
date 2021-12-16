package com.example.fbooking.room;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

public class TypeRoomViewHolder extends RecyclerView.ViewHolder {
    public TextView tvNameType;
    public View view;
    public TypeRoomViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        tvNameType = view.findViewById(R.id.tv_name_type);
    }
}
