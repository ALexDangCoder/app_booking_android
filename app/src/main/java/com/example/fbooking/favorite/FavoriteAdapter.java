package com.example.fbooking.favorite;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbooking.R;
import com.example.fbooking.room.OnFavoriteClickListener;
import com.example.fbooking.room.OnRoomClickListener;
import com.example.fbooking.room.Room;
import com.example.fbooking.room.RoomViewHolder;
import com.example.fbooking.utils.PriceFormatUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {
    private View view;

    Context context;
    private List<Room> roomList;
    private OnRoomClickListener onRoomClickListener;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Room> roomList, OnRoomClickListener onRoomClickListener) {
        this.roomList = roomList;
        this.onRoomClickListener = onRoomClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Room room = roomList.get(position);
        if (room == null) {
            return;
        }

        holder.tvRoomNumber.setText(String.valueOf(room.getRoomNumber()));
        holder.tvTypeRoom.setText(String.valueOf(room.getTypeRoom()));
        holder.tvRankRoom.setText(String.valueOf(room.getRankRoom()));

        holder.tvPriceRoom.setText(context.getString(R.string.vnd, PriceFormatUtils.format(String.valueOf(room.getPriceRoom()))));

        String path = room.getRoomPhoto().get(0).getFilename();
        Glide.with(context).load(context.getString(R.string.path, path)).into(holder.imgRoom);
        Log.d("ANHFAVORITE", path);

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
