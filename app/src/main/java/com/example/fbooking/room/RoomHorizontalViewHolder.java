package com.example.fbooking.room;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

public class RoomHorizontalViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgRoom;
    public TextView tvRoomNumber, tvTypeRoom, tvRankRoom, tvCount;
    public View view;

    public RoomHorizontalViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        imgRoom = itemView.findViewById(R.id.img_room_home);
        tvRoomNumber = itemView.findViewById(R.id.tv_room_number_home);
        tvTypeRoom = itemView.findViewById(R.id.tv_room_type_home);
        tvRankRoom = itemView.findViewById(R.id.tv_room_rank_home);
        tvCount = itemView.findViewById(R.id.tv_count);
    }
}
