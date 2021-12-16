package com.example.fbooking.room;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

public class UtilitiesViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle;
    public ImageView ivChecked;
    public View view;
    public UtilitiesViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        tvTitle = view.findViewById(R.id.tv_title_uti);
        ivChecked = view.findViewById(R.id.iv_check_uti);
    }
}
