package com.example.fbooking.booking;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbooking.R;

import com.example.fbooking.accept.DeleteIdAccept;
import com.example.fbooking.history.HistoryActivity;
import com.example.fbooking.retrofit.ApiService;
import com.example.fbooking.retrofit.RetrofitInstance;
import com.example.fbooking.room.FavoriteResult;
import com.example.fbooking.room.OnFavoriteClickListener;
import com.example.fbooking.room.OnRankRoomListener;
import com.example.fbooking.room.OnTypeClickListener;
import com.example.fbooking.room.OnUtilitiesCheckListener;
import com.example.fbooking.room.RankRoom;
import com.example.fbooking.room.RankRoomAdapter;
import com.example.fbooking.room.Result;
import com.example.fbooking.room.OnRoomClickListener;
import com.example.fbooking.room.Room;
import com.example.fbooking.room.RoomAdapter;
import com.example.fbooking.room.RoomViewHolder;
import com.example.fbooking.room.TypeRoom;
import com.example.fbooking.room.TypeRoomAdapter;
import com.example.fbooking.room.Utilities;
import com.example.fbooking.room.UtilitiesAdapter;
import com.example.fbooking.userloginandsignup.User;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment implements OnRoomClickListener, OnTypeClickListener, OnRankRoomListener, OnUtilitiesCheckListener, OnFavoriteClickListener {
    private View view;
    private EditText edtSearchPerson;
    private AppCompatButton btnSortRoom, btnOpenBottomSheet;

    private TextView tvFilter;

    private RecyclerView rvFilter;
    private UtilitiesAdapter adapter;

    private RecyclerView rcvRoom;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout srlBooking;

    private RecyclerView rvButtonType;
    private TypeRoomAdapter typeRoomAdapter;

    private RecyclerView rvButtonRank;
    private RankRoomAdapter rankRoomAdapter;

    private ProgressDialog progressDialog;
    private BottomSheetBehavior bottomSheetBehavior;

    private List<Room> roomList;
    private RoomAdapter roomAdapter;
    private List<Utilities> listFilter;

    private FirebaseUser user;
    private DatabaseReference reference;

    private int index = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_booking, container, false);
        listFilter = new ArrayList<>();
        initUi();

        searchAndSort();

        openBottomSheet();

//        showProgressDialog();
        getListRoom();

        filterList();

        srlBooking.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgressDialog();
                index = 0;
                getListRoom();
            }
        });

        return view;
    }

    private void searchAndSort() {
        edtSearchPerson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                roomAdapter.getFloorFilter().filter(s);
            }
        });

        btnSortRoom.setOnClickListener(new View.OnClickListener() {
            int press = 1;

            @Override
            public void onClick(View v) {
                if (press == 1) {
                    Collections.sort(roomList, Room.roomComparatorMinToMaxPrice);
                    roomAdapter.notifyDataSetChanged();
                    press = 0;
                } else {
                    Collections.sort(roomList, Room.roomComparatorMaxToMinPrice);
                    roomAdapter.notifyDataSetChanged();
                    press = 1;
                }
            }
        });
    }

    private List<RankRoom> buttonRank() {
        List<RankRoom> list = new ArrayList<>();
        list.add(new RankRoom("Phổ thông", false));
        list.add(new RankRoom("Cao cấp", false));
        list.add(new RankRoom("Sang trọng", false));
        return list;
    }


    private List<TypeRoom> buttonType() {
        List<TypeRoom> list = new ArrayList<>();
        list.add(new TypeRoom("Phòng đơn", false));
        list.add(new TypeRoom("Phòng đôi", false));
        list.add(new TypeRoom("Phòng 2 giường", false));
        return list;
    }

    private List<Utilities> buttonUtilites() {
        List<Utilities> list = new ArrayList<>();
        list.add(new Utilities("Wifi", "wifi", false));
        list.add(new Utilities("Bể bơi", "pool", false));
        list.add(new Utilities("Đỗ xe", "parking", false));
        list.add(new Utilities("Nhà hàng", "restaurant", false));
        list.add(new Utilities("Lễ tân", "receptionist", false));
        list.add(new Utilities("Thang máy", "elevator", false));
        list.add(new Utilities("Lối xe lăn", "wheelChairWay", false));
        list.add(new Utilities("Tập gym", "gym", false));
        list.add(new Utilities("Phòng họp", "roomMeeting", false));
        list.add(new Utilities("Đưa đón", "shuttle", false));
        list.add(new Utilities("Giặt là", "laundry", false));
//        list.add(new Utilities("Khác" ,"other", false));
        return list;
    }

    private void showProgressDialog() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void getListRoom() {
        Retrofit retrofit = RetrofitInstance.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getListRoom().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                roomList = response.body().getData();

                if (roomList == null) return;

                //Sap xep
                for (int i = 0; i < roomList.size(); i++) {
                    if (roomList.get(i).getStatusRoom().equals("Còn phòng")) {
                        Collections.swap(roomList, index, i);
                        index++;
                    }
                }

                roomAdapter.setData(roomList, BookingFragment.this::onRoomClick, BookingFragment.this::onClickFavorite);

                srlBooking.setRefreshing(false);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getContext(), "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
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

        tvFilter = view.findViewById(R.id.tv_filter);

        rvFilter = view.findViewById(R.id.rv_button_filter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvFilter.setLayoutManager(gridLayoutManager);
        adapter = new UtilitiesAdapter(getContext());
        adapter.setData(buttonUtilites(), this::onUtilitiesCheck);
        rvFilter.setAdapter(adapter);

        //Loai phong
        rvButtonType = view.findViewById(R.id.rv_button_type);
        GridLayoutManager typeLayoutManager = new GridLayoutManager(getContext(), 3);
        rvButtonType.setLayoutManager(typeLayoutManager);
        typeRoomAdapter = new TypeRoomAdapter(getContext());
        typeRoomAdapter.setData(buttonType(), this::onTypeClick);
        rvButtonType.setAdapter(typeRoomAdapter);

        //Hang phong
        rvButtonRank = view.findViewById(R.id.rv_button_rank);
        GridLayoutManager rankLayoutManager = new GridLayoutManager(getContext(), 3);
        rvButtonRank.setLayoutManager(rankLayoutManager);
        rankRoomAdapter = new RankRoomAdapter(getContext());
        rankRoomAdapter.setData(buttonRank(), this::onRankClick);
        rvButtonRank.setAdapter(rankRoomAdapter);
    }

    @Override
    public void onRoomClick(Room room) {
        Intent intent = new Intent(getActivity().getApplicationContext(), RoomDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", room);
        intent.putExtras(bundle);
        if (room.getStatusRoom().equalsIgnoreCase("Hết phòng")) {
            Toast.makeText(getActivity().getApplication(), "Phòng đã hết!", Toast.LENGTH_LONG).show();
            return;
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onTypeClick(String typeRoom) {
        roomAdapter.getTypeFilter().filter(typeRoom);
    }

    @Override
    public void onRankClick(String rankRoom) {
        roomAdapter.getRankFilter().filter(rankRoom);
    }

    @Override
    public void onUtilitiesCheck(Utilities utilities) {
        if (utilities.isChecked()) {
            listFilter.add(utilities);
        } else {
            for (int i = 0; i < listFilter.size(); i++) {
                if (listFilter.get(i).getName().equalsIgnoreCase(utilities.getName())) {
                    listFilter.remove(listFilter.get(i));
                }
            }
        }
        Log.e("SIZE: ", "Value +" + listFilter.size());
    }

    private void filterList() {
        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = RetrofitInstance.getInstance();
                ApiService apiService = retrofit.create(ApiService.class);
                Map<String, Boolean> option = new HashMap<>();

                for (int i = 0; i < listFilter.size(); i++) {
                    option.put(listFilter.get(i).getName(), true);
                }

                apiService.getFilter(option).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.d("OPTION", option + "");
                        roomList = response.body().getData();

                        if (roomList == null) return;

//                        //Sap xep
//                        for (int i = 0; i < roomList.size(); i++) {
//                            if (roomList.get(i).getStatusRoom().equals("Còn phòng")) {
//                                Collections.swap(roomList, index, i);
//                                index++;
//                            }
//                        }

                        roomAdapter.setData(roomList, BookingFragment.this::onRoomClick, BookingFragment.this::onClickFavorite);
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onClickFavorite(Room room) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    Retrofit retrofit = RetrofitInstance.getInstance();
                    ApiService apiService = retrofit.create(ApiService.class);
                    String idRoom = room.getRoomId();
                    String userEmail = userProfile.getEmail();
                    DeleteIdAccept deleteIdAccept = new DeleteIdAccept();
                    deleteIdAccept.setIdRoom(idRoom);
                    deleteIdAccept.setUserEmail(userEmail);
                    apiService.setFavoriteRoom(deleteIdAccept).enqueue(new Callback<FavoriteResult>() {
                        @Override
                        public void onResponse(Call<FavoriteResult> call, Response<FavoriteResult> response) {
                            Toast.makeText(getActivity().getApplicationContext(), "Đã thêm vào mục yêu thích!", Toast.LENGTH_SHORT).show();
                            getListRoom();
                        }

                        @Override
                        public void onFailure(Call<FavoriteResult> call, Throwable t) {
                            Log.d("ERRORMESS", t.getMessage());
                            Toast.makeText(getActivity().getApplicationContext(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                showProgressDialog();
            }
        });
    }
}
