package com.example.fbooking.room;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbooking.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomViewHolder>{
    private View view;

    Context context;
    private List<Room> roomList;
    private OnRoomClickListener onRoomClickListener;

    public RoomAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Room> roomList, OnRoomClickListener onRoomClickListener) {
        this.roomList = roomList;
        this.onRoomClickListener = onRoomClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_row, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        if (room == null) {
            return;
        }

        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedMoney = formatter.format(room.getPriceRoom());

        holder.tvRoomNumber.setText(room.getRoomNumber() + "");
        holder.tvTypeRoom.setText(room.getTypeRoom() + "");
        holder.tvRankRoom.setText(room.getRankRoom() + "");
        holder.tvStatusRoom.setText(room.getStatusRoom() + "");
        holder.tvPriceRoom.setText(formattedMoney + " đ");

//        Glide.with(context).load(room.getImageRoom().getMainImage()).into(holder.imgRoom);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRoomClickListener.onRoomClick(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (roomList != null) {
            return roomList.size();
        }
        return 0;
    }
}
