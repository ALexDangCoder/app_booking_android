package com.example.fbooking.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fbooking.R;
import com.example.fbooking.booking.RoomDetailActivity;
import com.example.fbooking.room.OnRoomClickListener;
import com.example.fbooking.room.Room;
import com.example.fbooking.room.RoomAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements OnRoomClickListener {
    private TextView tvTitleHistory, tvDeleteAllHistory;
    private RecyclerView rcvRoomHistory;
    private LinearLayoutManager linearLayoutManager;
    private RoomAdapter roomAdapter;

    private SwipeRefreshLayout srlHistory;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

//        srlHistory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                progressDialog.show();
//
////                srlBooking.setRefreshing(false);
////                if (progressDialog.isShowing()) {
////                    progressDialog.dismiss();
////                }
//            }
//        });

        initUi();

        rcvRoomHistory.setLayoutManager(linearLayoutManager);
        roomAdapter = new RoomAdapter(HistoryActivity.this);
        roomAdapter.setData(getListRoom(), this::onRoomClick);
        rcvRoomHistory.setAdapter(roomAdapter);
    }

    private List<Room> getListRoom() {
        List<Room> list = new ArrayList<>();
//        for (int i = 1; i <= 20; i++) {
//            list.add(new Room(301, "Phòng đơn", "Cao cấp", 2,
//                    1500000.0, "Còn phòng", true, true, true, true, true,
//                    true, true, true, true, true, true,
//                    "sdadsadsadsadsadsa"));
//        }
        return list;
    }

    @Override
    public void onRoomClick(Room room) {
        Intent intent = new Intent(HistoryActivity.this, RoomDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", room);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void initUi() {
        tvTitleHistory = findViewById(R.id.tv_title_history);
        tvDeleteAllHistory = findViewById(R.id.tv_delete_all_history);
        rcvRoomHistory = findViewById(R.id.rcv_room_history);

        linearLayoutManager = new LinearLayoutManager(HistoryActivity.this);

        progressDialog = new ProgressDialog(HistoryActivity.this);
        progressDialog.setMessage("Đang tải lại dữ liệu...");
        srlHistory = findViewById(R.id.srl_history);
    }
}