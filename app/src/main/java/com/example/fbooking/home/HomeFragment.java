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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fbooking.R;
import com.example.fbooking.booking.BookingFragment;
import com.example.fbooking.booking.RoomDetailActivity;
import com.example.fbooking.retrofit.ApiService;
import com.example.fbooking.retrofit.RetrofitInstance;
import com.example.fbooking.room.OnFavoriteClickListener;
import com.example.fbooking.room.OnRoomClickListener;
import com.example.fbooking.room.Result;
import com.example.fbooking.room.Room;
import com.example.fbooking.room.RoomAdapter;
import com.example.fbooking.room.RoomHorizontalAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

//    //Danh sach dich vu
//    private RecyclerView rcvService;
//    private LinearLayoutManager serviceLayoutManager;
//    private ServiceAdapter serviceAdapter;
//    private List<Service> serviceList;

    //Danh sach phong
    private RecyclerView rcvRoom;
    private LinearLayoutManager linearLayoutManager;
    private RoomHorizontalAdapter roomHorizontalAdapter;
    private List<Room> roomList;

    private ProgressDialog progressDialog;
    private SwipeRefreshLayout srlHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home, container, false);

        initUi();

        autoSlideShow();

        showProgressDialog();
        getListRoom();

        srlHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgressDialog();
                getListRoom();
            }
        });
        return view;
    }

    private void showProgressDialog() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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

//    //Slide anh
//    private List<Service> getListService() {
//        List<Service> list = new ArrayList<>();
//        list.add(new Service(R.drawable.bar_service));
//        list.add(new Service(R.drawable.pool_service));
//        list.add(new Service(R.drawable.gym_service));
//        list.add(new Service(R.drawable.bar_service));
//        list.add(new Service(R.drawable.tennis_service));
//        list.add(new Service(R.drawable.laundry_service));
//        list.add(new Service(R.drawable.meeting_service));
//
//        return list;
//    }

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

//        //Danh sách dịch vụ
//        rcvService = view.findViewById(R.id.rcv_service);
//        serviceLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        rcvService.setLayoutManager(serviceLayoutManager);
//        serviceAdapter = new ServiceAdapter(getContext(), getListService());
//        rcvService.setAdapter(serviceAdapter);

        //Danh sach phong
        rcvRoom = view.findViewById(R.id.rcv_room_home);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvRoom.setLayoutManager(linearLayoutManager);
        roomHorizontalAdapter = new RoomHorizontalAdapter(getContext());
        rcvRoom.setAdapter(roomHorizontalAdapter);

        srlHome = view.findViewById(R.id.srl_home);
    }

    @Override
    public void onRoomClick(Room room) {
        Intent intent = new Intent(getActivity(), RoomDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", room);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getListRoom() {
        Retrofit retrofit = RetrofitInstance.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getListRoom().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                roomList = response.body().getData();

                if (roomList == null) return;

                roomHorizontalAdapter.setData(roomList, HomeFragment.this::onRoomClick);
                Log.d("ROOMLISTSIZE", roomList.size() + "");

                srlHome.setRefreshing(false);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}