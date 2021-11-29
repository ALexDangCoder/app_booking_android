package com.example.fbooking.room;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

public class RoomViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgRoom;
    public TextView tvRoomNumber, tvTypeRoom, tvRankRoom, tvStatusRoom, tvPriceRoom;
    public View view;

    public RoomViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        imgRoom = itemView.findViewById(R.id.img_room);
        tvRoomNumber = itemView.findViewById(R.id.tv_room_number);
        tvTypeRoom = itemView.findViewById(R.id.tv_type_room);
        tvRankRoom = itemView.findViewById(R.id.tv_rank_room);
        tvStatusRoom = itemView.findViewById(R.id.tv_status_room);
        tvPriceRoom = itemView.findViewById(R.id.tv_price_room);
    }
}
