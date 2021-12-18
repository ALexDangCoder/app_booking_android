package com.example.fbooking.room;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoomViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgRoom, ivUnFavorite;
    public TextView tvRoomNumber, tvTypeRoom, tvRankRoom, tvStatusRoom, tvPriceRoom;
    public View view;

    public FirebaseUser user;
    public DatabaseReference reference;

    public RoomViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        imgRoom = itemView.findViewById(R.id.img_room);
        tvRoomNumber = itemView.findViewById(R.id.tv_room_number);
        tvTypeRoom = itemView.findViewById(R.id.tv_type_room);
        tvRankRoom = itemView.findViewById(R.id.tv_rank_room);
        tvStatusRoom = itemView.findViewById(R.id.tv_status_room);
        tvPriceRoom = itemView.findViewById(R.id.tv_price_room);

        ivUnFavorite = itemView.findViewById(R.id.iv_un_favorite);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
    }
}
