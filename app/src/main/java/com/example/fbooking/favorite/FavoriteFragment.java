package com.example.fbooking.favorite;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fbooking.booking.RoomDetailActivity;
import com.example.fbooking.room.OnRoomClickListener;
import com.example.fbooking.room.Room;
import com.example.fbooking.room.RoomAdapter;
import com.example.fbooking.userloginandsignup.LoginActivity;
import com.example.fbooking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment implements OnRoomClickListener {
    private View view;
    private LinearLayout lnLoginFavorite;
    private AppCompatButton btnOpenLogin;

    private TextView tvDeleteAll, tvTitleFavorite;
    private RecyclerView rcvRoomFavorite;
    private LinearLayoutManager linearLayoutManager;
    private RoomAdapter roomAdapter;

    private FirebaseUser user;
    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_favorite, container, false);

        initUi();

        showFrom();

        rcvRoomFavorite.setLayoutManager(linearLayoutManager);
        roomAdapter = new RoomAdapter(getActivity());
        roomAdapter.setData(getListRoom(), this::onRoomClick);
        rcvRoomFavorite.setAdapter(roomAdapter);

        return view;
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
        Intent intent = new Intent(getActivity(), RoomDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", room);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //Hien thi form dang nhap khi nguoi dung chua dang nhap
    private void showFrom() {
        if (user == null) {
            lnLoginFavorite.setVisibility(View.VISIBLE);
            btnOpenLogin.setEnabled(true);
            btnOpenLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });

            tvTitleFavorite.setVisibility(View.GONE);
            tvDeleteAll.setVisibility(View.GONE);
            rcvRoomFavorite.setVisibility(View.GONE);
        } else {
            lnLoginFavorite.setVisibility(View.GONE);
            btnOpenLogin.setEnabled(false);

            tvTitleFavorite.setVisibility(View.VISIBLE);
            tvDeleteAll.setVisibility(View.VISIBLE);
            rcvRoomFavorite.setVisibility(View.VISIBLE);
        }
    }

    private void initUi() {
        lnLoginFavorite = view.findViewById(R.id.ln_login_favorite);
        btnOpenLogin = view.findViewById(R.id.btn_open_login_favorite);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        tvTitleFavorite = view.findViewById(R.id.tv_title_favorite);
        tvDeleteAll = view.findViewById(R.id.tv_delete_all);

        rcvRoomFavorite = view.findViewById(R.id.rcv_room_favorite);
        linearLayoutManager = new LinearLayoutManager(getContext());
    }
}