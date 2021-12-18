package com.example.fbooking.history;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbooking.R;
import com.example.fbooking.retrofit.ApiService;
import com.example.fbooking.retrofit.RetrofitInstance;
import com.example.fbooking.room.Room;
import com.example.fbooking.utils.PriceFormatUtils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private View view;
    Context context;
    private List<History> historyList;
    private OnHistoryClickListener listener;

    public HistoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<History> historyList, OnHistoryClickListener listener) {
        this.historyList = historyList;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History history = historyList.get(position);
        if (history == null) {
            return;
        }

        holder.tvRoomNumber.setText(context.getString(R.string.his_phong_so, history.getSoPhong()));
        holder.tvName.setText(context.getString(R.string.his_ten, history.getHoten()));
        holder.tvIdPerson.setText(context.getString(R.string.his_cmnd, history.getCmnd()));
        holder.tvEmail.setText(context.getString(R.string.his_email, history.getEmail()));
        holder.tvCheckIn.setText(context.getString(R.string.his_thoi_gian_nhan, history.getNgayNhan(), history.getGioNhanPhong()));
        holder.tvCheckOut.setText(context.getString(R.string.his_thoi_gian_tra, history.getNgayTra(), history.getGioTraPhong()));
        holder.tvPhone.setText(context.getString(R.string.his_so_dien_thoai, String.valueOf(history.getSdt())));
        holder.tvPrice.setText(PriceFormatUtils.format(String.valueOf(history.getGiaPhong())) + " đ");

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(history);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (historyList != null) {
            return historyList.size();
        }
        return 0;
    }

//    public void updateData(List<History> listUpdated) {
//        historyList.clear();
//        historyList.addAll(listUpdated);
//        notifyDataSetChanged();
//    }
}
