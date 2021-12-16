package com.example.fbooking.accept;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

public class WaitAcceptViewHolder extends RecyclerView.ViewHolder {
    public TextView tvRoomNumber;
    public TextView tvName;
    public TextView tvIdPerson;
    public TextView tvEmail;
    public TextView tvCheckIn;
    public TextView tvCheckOut;
    public TextView tvPhone;
    public TextView tvPrice;
    public AppCompatButton btnAbort;
    public View view;
    public WaitAcceptViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;
        tvRoomNumber = itemView.findViewById(R.id.tv_room_number_accept);
        tvName = itemView.findViewById(R.id.tv_name_accept);
        tvIdPerson = itemView.findViewById(R.id.tv_id_person_accept);
        tvEmail = itemView.findViewById(R.id.tv_email_accept);
        tvCheckIn = itemView.findViewById(R.id.tv_check_in_accept);
        tvCheckOut = itemView.findViewById(R.id.tv_check_out_accept);
        tvPhone = itemView.findViewById(R.id.tv_phone_accept);
        tvPrice = itemView.findViewById(R.id.tv_price_accept);

        btnAbort = itemView.findViewById(R.id.btn_abort);
    }
}
