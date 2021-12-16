package com.example.fbooking.history;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView tvRoomNumber;
    public TextView tvName;
    public TextView tvIdPerson;
    public TextView tvEmail;
    public TextView tvCheckIn;
    public TextView tvCheckOut;
    public TextView tvPhone;
    public TextView tvPrice;
    public ImageView ivDelete;
    public View view;
    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        tvRoomNumber = itemView.findViewById(R.id.tv_room_number_history);
        tvName = itemView.findViewById(R.id.tv_name_history);
        tvIdPerson = itemView.findViewById(R.id.tv_id_person_history);
        tvEmail = itemView.findViewById(R.id.tv_email_history);
        tvCheckIn = itemView.findViewById(R.id.tv_check_in_history);
        tvCheckOut = itemView.findViewById(R.id.tv_check_out_history);
        tvPhone = itemView.findViewById(R.id.tv_phone_history);
        tvPrice = itemView.findViewById(R.id.tv_price_history);

        ivDelete = itemView.findViewById(R.id.iv_delete);
    }
}
