package com.example.fbooking.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fbooking.R;
import com.example.fbooking.booking.RoomDetailActivity;
import com.example.fbooking.room.OnRoomClickListener;
import com.example.fbooking.room.Room;
import com.example.fbooking.room.RoomAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnRoomClickListener {
    private View view;
    private ViewPager vpSlide;
    private CircleIndicator ciSlide;
    private PhotoAdapter photoAdapter;
    private List<Photo> photoList;
    private Timer timer;
    private List<Room> roomList;

    private RecyclerView rcvRoom;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout srlHome;

    private ProgressDialog progressDialog;

    private RoomAdapter roomAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home, container, false);

        initUi();
        rcvRoom.setLayoutManager(linearLayoutManager);
        roomAdapter = new RoomAdapter(getActivity());
        roomAdapter.setData(getListRoom(), HomeFragment.this::onRoomClick);
        rcvRoom.setAdapter(roomAdapter);
        autoSlideShow();
        roomList = new ArrayList<>();
        // callApi();
        return view;
    }

    private List<Room> getListRoom() {
        List<Room> list = new ArrayList<>();
//        for (int i = 1; i <= 20; i++) {
//            list.add(new Room(301, "Phòng đơn", "Cao cấp", 2,
//                    1500000.0, "Còn phòng", true, true, true, true, true,
//                    true, true, true, true, true, true,
//                    "sdadsadsadsadsadsa", new ImageRoom("https://vcdn-vnexpress.vnecdn.net/2021/07/07/Aventador-Ultimae-3-4-Front-92-5094-9826-1625659942.jpg", "", "", "")));
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

    //Slide anh
    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.room1));
        list.add(new Photo(R.drawable.room2));
        list.add(new Photo(R.drawable.room3));
        list.add(new Photo(R.drawable.room4));
        list.add(new Photo(R.drawable.room5));
        list.add(new Photo(R.drawable.room6));
        list.add(new Photo(R.drawable.room7));
        list.add(new Photo(R.drawable.room8));
        list.add(new Photo(R.drawable.room9));
        list.add(new Photo(R.drawable.room10));

        return list;
    }

    private void autoSlideShow() {
        if (photoList == null || photoList.isEmpty() || vpSlide == null) {
            return;
        }

        if (timer == null) {
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = vpSlide.getCurrentItem();
                        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
                        vpSlide.getLayoutParams().height = height / 4;
                        int totalItem = photoList.size() - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            vpSlide.setCurrentItem(currentItem);
                        } else {
                            vpSlide.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);
    }

    private void initUi() {
        vpSlide = view.findViewById(R.id.vp_slide);
        ciSlide = view.findViewById(R.id.ci_slide);

        photoList = getListPhoto();

        photoAdapter = new PhotoAdapter(getContext(), photoList);
        vpSlide.setAdapter(photoAdapter);
        ciSlide.setViewPager(vpSlide);
        photoAdapter.registerDataSetObserver(ciSlide.getDataSetObserver());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang tải lại dữ liệu...");
        srlHome = view.findViewById(R.id.srl_home);
        rcvRoom = view.findViewById(R.id.rcv_room_home);
        linearLayoutManager = new LinearLayoutManager(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}