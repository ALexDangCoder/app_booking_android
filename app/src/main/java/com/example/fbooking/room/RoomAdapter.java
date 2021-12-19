package com.example.fbooking.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbooking.R;
import com.example.fbooking.booking.FillInformationActivity;
import com.example.fbooking.booking.RoomDetailActivity;
import com.example.fbooking.retrofit.ApiService;
import com.example.fbooking.retrofit.RetrofitInstance;
import com.example.fbooking.userloginandsignup.User;
import com.example.fbooking.utils.PriceFormatUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RoomAdapter extends RecyclerView.Adapter<RoomViewHolder> implements Filterable {
    private View view;

    Context context;
    private List<Room> roomList;
    private List<Room> oldRoomList;
    private OnRoomClickListener onRoomClickListener;
    private OnFavoriteClickListener onFavoriteClickListener;

    //Cach lam ko toi uu

    public RoomAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Room> roomList, OnRoomClickListener onRoomClickListener, OnFavoriteClickListener onFavoriteClickListener) {
        this.roomList = roomList;
        this.onRoomClickListener = onRoomClickListener;
        this.onFavoriteClickListener = onFavoriteClickListener;
        this.oldRoomList = roomList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_row, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Room room = roomList.get(position);
        if (room == null) {
            return;
        }

        //Cach lam k toi uu
        if (holder.user != null) {
            String userEmail = holder.user.getEmail();
            if (room.getFavorite().contains(userEmail)) {
                holder.ivUnFavorite.setBackgroundResource(R.drawable.ic_favorite_fill);
            } else {
                holder.ivUnFavorite.setBackgroundResource(R.drawable.ic_favorite_border);
            }
        } else {
            holder.tvRoomNumber.setText(String.valueOf(room.getRoomNumber()));
            holder.tvTypeRoom.setText(String.valueOf(room.getTypeRoom()));
            holder.tvRankRoom.setText(String.valueOf(room.getRankRoom()));
            holder.tvStatusRoom.setText(String.valueOf(room.getStatusRoom()));

            holder.tvPriceRoom.setText(context.getString(R.string.vnd, PriceFormatUtils.format(String.valueOf(room.getPriceRoom()))));

            if (room.getStatusRoom().equalsIgnoreCase("Hết phòng")) {
                holder.tvStatusRoom.setTextColor(Color.RED);
            } else if (room.getStatusRoom().equalsIgnoreCase("Chờ xác nhận")) {
                holder.tvStatusRoom.setTextColor(Color.BLUE);
            } else {
                holder.tvStatusRoom.setTextColor(Color.parseColor("#30C536"));
            }

            holder.ivUnFavorite.setVisibility(View.GONE);

            String path = room.getRoomPhoto().get(0).getFilename();
            Glide.with(context).load(context.getString(R.string.path, path)).into(holder.imgRoom);

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRoomClickListener.onRoomClick(room);
                }
            });
            return;
        }

        if (room.getStatusRoom().equalsIgnoreCase("Hết phòng")) {
            holder.tvStatusRoom.setTextColor(Color.RED);
        } else if (room.getStatusRoom().equalsIgnoreCase("Chờ xác nhận")) {
            holder.tvStatusRoom.setTextColor(Color.BLUE);
        } else {
            holder.tvStatusRoom.setTextColor(Color.parseColor("#30C536"));
        }

        holder.tvRoomNumber.setText(String.valueOf(room.getRoomNumber()));
        holder.tvTypeRoom.setText(String.valueOf(room.getTypeRoom()));
        holder.tvRankRoom.setText(String.valueOf(room.getRankRoom()));
        holder.tvStatusRoom.setText(String.valueOf(room.getStatusRoom()));

        holder.tvPriceRoom.setText(context.getString(R.string.vnd, PriceFormatUtils.format(String.valueOf(room.getPriceRoom()))));

        String path = room.getRoomPhoto().get(0).getFilename();
        Glide.with(context).load(context.getString(R.string.path, path)).into(holder.imgRoom);
        Log.d("ANH", path);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRoomClickListener.onRoomClick(room);
            }
        });

        holder.ivUnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomList.get(position).isChecked()) {
                    roomList.get(position).setChecked(false);
                    onFavoriteClickListener.onClickFavorite(roomList.get(position));
                } else {
                    roomList.get(position).setChecked(true);
                    onFavoriteClickListener.onClickFavorite(roomList.get(position));
                }
                notifyDataSetChanged();
//                onFavoriteClickListener.onClickFavorite(room);
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

    public Filter getTypeFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strFilter = constraint.toString();
                if (strFilter.isEmpty()) {
                    roomList = oldRoomList;
                } else {
                    List<Room> list = new ArrayList<>();
                    for (Room room : oldRoomList) {
                        String a = room.getTypeRoom();
                        if (a.equalsIgnoreCase(strFilter)) {
                            list.add(room);
                        }
                    }

                    roomList = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = roomList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                roomList = (List<Room>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public Filter getRankFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strFilter = constraint.toString();
                if (strFilter.isEmpty()) {
                    roomList = oldRoomList;
                } else {
                    List<Room> list = new ArrayList<>();
                    for (Room room : oldRoomList) {
                        String a = room.getRankRoom();
                        if (a.equalsIgnoreCase(strFilter)) {
                            list.add(room);
                        }
                    }

                    roomList = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = roomList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                roomList = (List<Room>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public Filter getPeopleFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strFilter = constraint.toString();
                if (strFilter.isEmpty()) {
                    roomList = oldRoomList;
                } else {
                    List<Room> list = new ArrayList<>();
                    for (Room room : oldRoomList) {
                        if (String.valueOf(room.getPeopleRoom()).equalsIgnoreCase(strFilter)) {
                            list.add(room);
                        }
                    }

                    roomList = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = roomList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                roomList = (List<Room>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

