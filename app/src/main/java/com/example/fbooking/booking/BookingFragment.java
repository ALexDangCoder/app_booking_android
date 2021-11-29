package com.example.fbooking.booking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fbooking.MainActivity;
import com.example.fbooking.R;

import com.example.fbooking.retrofit.ApiService;
import com.example.fbooking.retrofit.RetrofitInstance;
import com.example.fbooking.room.Data;
import com.example.fbooking.room.OnRoomClickListener;
import com.example.fbooking.room.Room;
import com.example.fbooking.room.RoomAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment implements OnRoomClickListener {
    private View view;
    private EditText edtSearchPerson;
    private AppCompatButton btnSortRoom, btnOpenBottomSheet,
            btnRegularRoom, btnHighClassRoom, btnLuxuryRoom,
            btnSingleRoom, btnTwinRoom, btnTwoBedRoom,
            btnWifi, btnPool, btnParking,
            btnRestaurant, btnReceptionist, btnElevator,
            btnWheelChair, btnGym, btnMeeting,
            btnTaking, btnLaundary, btnDiffrent;
    private RecyclerView rcvRoom;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout srlBooking;

    private ProgressDialog progressDialog;
    private BottomSheetBehavior bottomSheetBehavior;

    private List<Room> roomList;
    private RoomAdapter roomAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_booking, container, false);

        initUi();

        openBottomSheet();

        showDialog();
        getListRoom();

        srlBooking.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showDialog();
                getListRoom();
            }
        });

        return view;
    }

    private void showDialog() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void getListRoom() {
        Retrofit retrofit = RetrofitInstance.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getListRoom().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data data = response.body();
                assert data != null;
                roomList = data.getData();

                roomAdapter.setData(roomList, BookingFragment.this::onRoomClick);
                srlBooking.setRefreshing(false);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(getContext(), "Không kết nối internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Mo cac tien ich khac
    private void openBottomSheet() {
        btnOpenBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    private void initUi() {
        edtSearchPerson = view.findViewById(R.id.edt_search_person_per_room);

        btnSortRoom = view.findViewById(R.id.btn_sort);
        btnOpenBottomSheet = view.findViewById(R.id.btn_open_bottom_sheet);

        btnRegularRoom = view.findViewById(R.id.btn_regular_room);
        btnHighClassRoom = view.findViewById(R.id.btn_high_class_room);
        btnLuxuryRoom = view.findViewById(R.id.btn_luxury_room);
        btnSingleRoom = view.findViewById(R.id.btn_single_room);
        btnTwinRoom = view.findViewById(R.id.btn_twin_room);
        btnTwoBedRoom = view.findViewById(R.id.btn_two_bed_room);
        btnWifi = view.findViewById(R.id.btn_wifi);
        btnPool = view.findViewById(R.id.btn_pool);
        btnParking = view.findViewById(R.id.btn_parking);
        btnRestaurant = view.findViewById(R.id.btn_restaurant);
        btnReceptionist = view.findViewById(R.id.btn_receptionist);
        btnElevator = view.findViewById(R.id.btn_elevator);
        btnWheelChair = view.findViewById(R.id.btn_wheel_chair);
        btnGym = view.findViewById(R.id.btn_gym);
        btnMeeting = view.findViewById(R.id.btn_meeting);
        btnTaking = view.findViewById(R.id.btn_taking);
        btnLaundary = view.findViewById(R.id.btn_laundary);
        btnDiffrent = view.findViewById(R.id.btn_diffrent);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang tải lại dữ liệu...");
        srlBooking = view.findViewById(R.id.srl_booking);

        rcvRoom = view.findViewById(R.id.rcv_room);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rcvRoom.setLayoutManager(linearLayoutManager);
        roomAdapter = new RoomAdapter(getContext());
        rcvRoom.setAdapter(roomAdapter);

        LinearLayout linearLayout = view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
    }

    @Override
    public void onRoomClick(Room room) {
        Intent intent = new Intent(getActivity(), RoomDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", room);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}