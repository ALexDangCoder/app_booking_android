package com.example.fbooking.home;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

public class ServiceViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgService;
    public ServiceViewHolder(@NonNull View itemView) {
        super(itemView);

        imgService = itemView.findViewById(R.id.img_service);
    }
}
