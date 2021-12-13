package com.example.fbooking.favorite;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgRoom;
    public TextView tvRoomNumber, tvTypeRoom, tvRankRoom, tvPriceRoom;
    public View view;

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        imgRoom = itemView.findViewById(R.id.img_room);
        tvRoomNumber = itemView.findViewById(R.id.tv_room_number_favortie);
        tvTypeRoom = itemView.findViewById(R.id.tv_type_room_favorite);
        tvRankRoom = itemView.findViewById(R.id.tv_rank_room_favorite);
        tvPriceRoom = itemView.findViewById(R.id.tv_price_room_favorite);
    }
}
