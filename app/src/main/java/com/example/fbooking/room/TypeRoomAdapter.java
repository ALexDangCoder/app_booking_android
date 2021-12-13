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

public class TypeRoomAdapter extends RecyclerView.Adapter<TypeRoomViewHolder> {
    private View view;

    Context context;
    private List<TypeRoom> typeRoomList;
    private OnTypeClickListener clickListener;

    int selected_position = -1;

    public TypeRoomAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TypeRoom> typeRoomList, OnTypeClickListener clickListener) {
        this.typeRoomList = typeRoomList;
        this.clickListener = clickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TypeRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_type_room_row, parent, false);
        return new TypeRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeRoomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TypeRoom typeRoom = typeRoomList.get(position);
        if (typeRoom == null) {
            return;
        }
        if (typeRoom.isChecked()) {
            holder.view.setBackgroundResource(R.drawable.custom_button_utilities_onpress);
            holder.tvNameType.setTextColor(Color.WHITE);
        } else {
            holder.view.setBackgroundResource(R.drawable.custom_button_utilities);
            holder.tvNameType.setTextColor(Color.parseColor("#3F51B5"));
        }

        holder.tvNameType.setText(typeRoom.getName());

//        holder.view.setBackgroundResource(selected_position == position ? R.drawable.custom_button_utilities : R.drawable.custom_button_utilities_onpress);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeRoomList.get(position).isChecked()) {
                    typeRoomList.get(position).setChecked(false);
                    clickListener.onTypeClick("");
                } else {
                    typeRoomList.get(position).setChecked(true);
                    if (selected_position != position && selected_position >= 0) {
                        typeRoomList.get(selected_position).setChecked(false);
                    }
                    clickListener.onTypeClick(typeRoomList.get(position).getName());
                }
                selected_position = position;
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (typeRoomList != null) {
            return typeRoomList.size();
        }
        return 0;
    }

}
