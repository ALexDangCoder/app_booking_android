package com.example.fbooking.room;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbooking.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomHorizontalAdapter extends RecyclerView.Adapter<RoomHorizontalViewHolder> {
    private View view;

    Context context;
    private List<Room> roomList;
    private OnRoomClickListener onRoomClickListener;

    public RoomHorizontalAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Room> roomList, OnRoomClickListener onRoomClickListener) {
        this.roomList = roomList;
        this.onRoomClickListener = onRoomClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_horizontal_row, parent, false);
        return new RoomHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHorizontalViewHolder holder, int position) {
        Room room = roomList.get(position);
        if (room == null) {
            return;
        }

        holder.tvRoomNumber.setText(room.getRoomNumber());
        holder.tvTypeRoom.setText(room.getTypeRoom());
        holder.tvRankRoom.setText(room.getRankRoom());
        holder.tvCount.setText(room.getCountAccept() + " lượt đặt");

        String path = room.getRoomPhoto().get(0).getFilename();
        Picasso.get().load(context.getString(R.string.path, path)).into(holder.imgRoom);
        Log.d("ANH", path);

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
