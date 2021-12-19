package com.example.fbooking.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbooking.R;
import com.example.fbooking.booking.FillInformationActivity;
import com.example.fbooking.booking.RoomDetailActivity;
import com.example.fbooking.retrofit.ApiService;
import com.example.fbooking.retrofit.RetrofitInstance;
import com.example.fbooking.room.OnRoomClickListener;
import com.example.fbooking.room.Room;
import com.example.fbooking.room.RoomAdapter;
import com.example.fbooking.userloginandsignup.LoginActivity;
import com.example.fbooking.userloginandsignup.User;
import com.example.fbooking.utils.AlarmReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HistoryActivity extends AppCompatActivity implements OnHistoryClickListener {
    private TextView tvDeleteAllHistory;
    private RecyclerView rcvRoomHistory;
    private LinearLayoutManager linearLayoutManager;

    private SwipeRefreshLayout srlHistory;
    private ProgressDialog progressDialog;

    private FirebaseUser user;
    private DatabaseReference reference;

    private List<History> historyList;
    private HistoryAdapter adapter;

    private ConstraintLayout clLogin;
    private LinearLayout lnHistory;
    private Button btnOpenLogin;

    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initUi();

//        showProgressDialog();
        showUserInformation();

        deleteListHistory();
    }

    private void deleteListHistory() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        tvDeleteAllHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                Retrofit retrofit = RetrofitInstance.getInstance();
                ApiService apiService = retrofit.create(ApiService.class);
                DeleteAllHistory email = new DeleteAllHistory(user.getEmail());
                apiService.deleteAllHistory(email).enqueue(new Callback<ResultHistory>() {
                    @Override
                    public void onResponse(Call<ResultHistory> call, Response<ResultHistory> response) {
                        callApiHistory(user.getEmail());
                        Toast.makeText(HistoryActivity.this, "Xóa lịch sử thành công!", Toast.LENGTH_SHORT).show();
                        Log.d("NEWEMAIL", email + "");

                        srlHistory.setRefreshing(false);
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultHistory> call, Throwable t) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(HistoryActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //Hien thi form dang nhap khi nguoi dung chua dang nhap
    public void showUserInformation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        if (user == null) {
            clLogin.setVisibility(View.VISIBLE);
            lnHistory.setVisibility(View.GONE);

            btnOpenLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HistoryActivity.this, LoginActivity.class));
                }
            });
        } else {
            clLogin.setVisibility(View.GONE);
            btnOpenLogin.setEnabled(false);
            lnHistory.setVisibility(View.VISIBLE);
            showProgressDialog();

            reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);
                    if (userProfile != null) {
                        showProgressDialog();
                        callApiHistory(user.getEmail());

                        srlHistory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                callApiHistory(user.getEmail());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(HistoryActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    showProgressDialog();
                }
            });
        }
    }

    private void callApiHistory(String email) {
        Retrofit retrofit = RetrofitInstance.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getListHistory(email).enqueue(new Callback<ResultHistory>() {
            @Override
            public void onResponse(Call<ResultHistory> call, Response<ResultHistory> response) {
                historyList = response.body().gethistoryList();

                if (historyList == null) return;

                adapter.setData(historyList, HistoryActivity.this::onClickItem);

                Log.d("HISTORYLIST", historyList.toString());
                srlHistory.setRefreshing(false);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResultHistory> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                srlHistory.setRefreshing(false);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void showProgressDialog() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void initUi() {
        tvDeleteAllHistory = findViewById(R.id.tv_delete_all_history);
        rcvRoomHistory = findViewById(R.id.rcv_room_history);

        adapter = new HistoryAdapter(this);
        linearLayoutManager = new LinearLayoutManager(HistoryActivity.this);
        rcvRoomHistory.setLayoutManager(linearLayoutManager);
        rcvRoomHistory.setAdapter(adapter);

        progressDialog = new ProgressDialog(HistoryActivity.this);
        progressDialog.setMessage("Đang tải lại dữ liệu...");
        srlHistory = findViewById(R.id.srl_history);

        clLogin = findViewById(R.id.cl_login_history);
        lnHistory = findViewById(R.id.ln_history);
        btnOpenLogin = findViewById(R.id.btn_open_login_hisory);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
    }

    @Override
    public void onClickItem(History history) {
        Retrofit retrofit = RetrofitInstance.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        DeleteIdHistory deleteIdHistory = new DeleteIdHistory(history.getId());
        apiService.deleteHistory(deleteIdHistory).enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                callApiHistory(user.getEmail());

                Toast.makeText(HistoryActivity.this, "Xóa mục thành công!", Toast.LENGTH_SHORT).show();
                Log.d("HISTORYID", history.getId());
                Log.d("NEWHISTORYID", deleteIdHistory + "");
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                srlHistory.setRefreshing(false);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(HistoryActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}