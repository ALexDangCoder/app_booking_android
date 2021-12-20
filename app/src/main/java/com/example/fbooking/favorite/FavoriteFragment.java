package com.example.fbooking.favorite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbooking.accept.DeleteIdAccept;
import com.example.fbooking.booking.BookingFragment;
import com.example.fbooking.booking.RoomDetailActivity;
import com.example.fbooking.history.HistoryActivity;
import com.example.fbooking.retrofit.ApiService;
import com.example.fbooking.retrofit.RetrofitInstance;
import com.example.fbooking.room.FavoriteResult;
import com.example.fbooking.room.OnFavoriteClickListener;
import com.example.fbooking.room.OnRoomClickListener;
import com.example.fbooking.room.Result;
import com.example.fbooking.room.Room;
import com.example.fbooking.room.RoomAdapter;
import com.example.fbooking.userloginandsignup.LoginActivity;
import com.example.fbooking.R;
import com.example.fbooking.userloginandsignup.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
public class FavoriteFragment extends Fragment implements OnRoomClickListener, OnFavoriteClickListener {
    private View view;
    private LinearLayout lnLoginFavorite;
    private AppCompatButton btnOpenLogin;

    private TextView tvTitleFavorite;
    private RecyclerView rcvRoomFavorite;
    private LinearLayoutManager linearLayoutManager;

    private FirebaseUser user;
    private DatabaseReference reference;

    private SwipeRefreshLayout srlFavorite;
    private ProgressDialog progressDialog;
    private List<Room> roomList;
    private RoomAdapter roomAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_favorite, container, false);

        initUi();

        showFrom();

        srlFavorite.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgressDialog();
                getListRoom(user.getEmail());
            }
        });

        return view;
    }

    private void showProgressDialog() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    //Hien thi form dang nhap khi nguoi dung chua dang nhap
    private void showFrom() {
        if (user == null) {
            lnLoginFavorite.setVisibility(View.VISIBLE);
            srlFavorite.setEnabled(false);
            btnOpenLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });

            tvTitleFavorite.setVisibility(View.GONE);
            rcvRoomFavorite.setVisibility(View.GONE);
        } else {
            lnLoginFavorite.setVisibility(View.GONE);

            tvTitleFavorite.setVisibility(View.VISIBLE);
            rcvRoomFavorite.setVisibility(View.VISIBLE);
            srlFavorite.setEnabled(true);

            reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);
                    if (userProfile != null) {
                        showProgressDialog();
                        getListRoom(userProfile.getEmail());
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

    private void getListRoom(String email) {
        Retrofit retrofit = RetrofitInstance.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getListFavoriteByUser(email).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                roomList = response.body().getData();

                if (roomList == null) return;

                roomAdapter.setData(roomList, FavoriteFragment.this::onRoomClick, FavoriteFragment.this::onClickFavorite);

                srlFavorite.setRefreshing(false);
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

    private void initUi() {
        lnLoginFavorite = view.findViewById(R.id.ln_login_favorite);
        btnOpenLogin = view.findViewById(R.id.btn_open_login_favorite);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        tvTitleFavorite = view.findViewById(R.id.tv_title_favorite);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang tải lại dữ liệu...");
        srlFavorite = view.findViewById(R.id.srl_favorite);

        rcvRoomFavorite = view.findViewById(R.id.rcv_room_favorite);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rcvRoomFavorite.setLayoutManager(linearLayoutManager);
        roomAdapter = new RoomAdapter(getContext());
        rcvRoomFavorite.setAdapter(roomAdapter);
    }

    @Override
    public void onRoomClick(Room room) {
        Intent intent = new Intent(getActivity().getApplicationContext(), RoomDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", room);
        intent.putExtras(bundle);
        startActivity(intent);
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
                            Toast.makeText(getActivity().getApplicationContext(), "Đã hủy yêu thích!", Toast.LENGTH_SHORT).show();
                            getListRoom(userEmail);
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