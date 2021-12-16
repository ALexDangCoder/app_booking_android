package com.example.fbooking.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;

import java.util.List;

public class RankRoomAdapter extends RecyclerView.Adapter<RankRoomViewHolder> {
    private View view;

    Context context;
    private List<RankRoom> rankRoomList;
    private OnRankRoomListener clickListener;

    int selected_position = -1;

    public RankRoomAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RankRoom> rankRoomList, OnRankRoomListener clickListener) {
        this.rankRoomList = rankRoomList;
        this.clickListener = clickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RankRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_rank_room_row, parent, false);
        return new RankRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankRoomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RankRoom rankRoom = rankRoomList.get(position);
        if (rankRoom == null) {
            return;
        }
        if (rankRoom.isChecked()) {
            holder.view.setBackgroundResource(R.drawable.custom_button_utilities_onpress);
            holder.tvNameRank.setTextColor(Color.WHITE);
        } else {
            holder.view.setBackgroundResource(R.drawable.custom_button_utilities);
            holder.tvNameRank.setTextColor(Color.parseColor("#3F51B5"));
        }

        holder.tvNameRank.setText(rankRoom.getName());

//        holder.view.setBackgroundResource(selected_position == position ? R.drawable.custom_button_utilities : R.drawable.custom_button_utilities_onpress);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rankRoomList.get(position).isChecked()) {
                    rankRoomList.get(position).setChecked(false);
                    clickListener.onRankClick("");
                } else {
                    rankRoomList.get(position).setChecked(true);
                    if (selected_position != position && selected_position >= 0) {
                        rankRoomList.get(selected_position).setChecked(false);
                    }
                    clickListener.onRankClick(rankRoomList.get(position).getName());
                }
                selected_position = position;
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (rankRoomList != null) {
            return rankRoomList.size();
        }
        return 0;
    }

}
