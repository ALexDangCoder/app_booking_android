package com.example.fbooking.accept;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;
import com.example.fbooking.booking.Booking;
import com.example.fbooking.history.OnHistoryClickListener;
import com.example.fbooking.utils.PriceFormatUtils;

import java.util.List;

public class WaitAcceptAdapter extends RecyclerView.Adapter<WaitAcceptViewHolder> {
    private View view;
    Context context;
    private List<Accept> acceptList;
    private OnClickAcceptListener onClickAcceptListener;

    public WaitAcceptAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Accept> acceptList, OnClickAcceptListener onClickAcceptListener) {
        this.acceptList = acceptList;
        this.onClickAcceptListener = onClickAcceptListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaitAcceptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wait_accept_row, parent, false);
        return new WaitAcceptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaitAcceptViewHolder holder, int position) {
        Accept accept = acceptList.get(position);
        if (accept == null) {
            return;
        }

        holder.tvRoomNumber.setText(context.getString(R.string.his_phong_so, accept.getSophong()));
        holder.tvName.setText(context.getString(R.string.his_ten, accept.getHoten()));
        holder.tvIdPerson.setText(context.getString(R.string.his_cmnd, String.valueOf(accept.getCccd())));
        holder.tvEmail.setText(context.getString(R.string.his_email, accept.getEmail()));
        holder.tvCheckIn.setText(context.getString(R.string.his_thoi_gian_nhan, accept.getNgaynhan(), accept.getGioNhanPhong()));
        holder.tvCheckOut.setText(context.getString(R.string.his_thoi_gian_tra, accept.getNgayTra(), "12:00 PM"));
        holder.tvPhone.setText(context.getString(R.string.his_phong_so, accept.getSophong()));
        holder.tvPrice.setText(PriceFormatUtils.format(String.valueOf(accept.getGiaPhong())));

        holder.btnAbort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAcceptListener.onDeleteAccept(accept);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (acceptList != null) {
            return acceptList.size();
        }
        return 0;
    }
}
