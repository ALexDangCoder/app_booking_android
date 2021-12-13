package com.example.fbooking.accept;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbooking.R;
import com.example.fbooking.booking.Booking;
import com.example.fbooking.history.DeleteIdHistory;
import com.example.fbooking.history.History;
import com.example.fbooking.history.HistoryActivity;
import com.example.fbooking.history.HistoryAdapter;
import com.example.fbooking.history.ResultHistory;
import com.example.fbooking.retrofit.ApiService;
import com.example.fbooking.retrofit.RetrofitInstance;
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

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WaitToAcceptAcitivity extends AppCompatActivity implements OnClickAcceptListener{
    private RecyclerView rcvRoomAccept;
    private LinearLayoutManager linearLayoutManager;

    private SwipeRefreshLayout srlAccept;
    private ProgressDialog progressDialog;

    private FirebaseUser user;
    private DatabaseReference reference;

    private List<Accept> acceptList;
    private WaitAcceptAdapter adapter;

    private ConstraintLayout clLogin;
    private LinearLayout lnAccept;
    private Button btnOpenLogin;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_to_accept_acitivity);

        initUi();

        showProgressDialog();
        showUserInformation();
    }

    private void showProgressDialog() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    //Hien thi form dang nhap khi nguoi dung chua dang nhap
    public void showUserInformation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        if (user == null) {
            clLogin.setVisibility(View.VISIBLE);
            lnAccept.setVisibility(View.GONE);

            btnOpenLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(WaitToAcceptAcitivity.this, LoginActivity.class));
                }
            });
        } else {
            clLogin.setVisibility(View.GONE);
            btnOpenLogin.setEnabled(false);
            lnAccept.setVisibility(View.VISIBLE);
            showProgressDialog();

            reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);
                    if (userProfile != null) {
                        showProgressDialog();
                        callApiAccept(user.getEmail());

                        srlAccept.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                callApiAccept(user.getEmail());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    srlAccept.setRefreshing(false);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(WaitToAcceptAcitivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void callApiAccept(String email) {
        Retrofit retrofit = RetrofitInstance.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getListAccept(email).enqueue(new Callback<ResultAccept>() {
            @Override
            public void onResponse(Call<ResultAccept> call, Response<ResultAccept> response) {
                acceptList = response.body().getAcceptList();

                if (acceptList == null) return;

                adapter.setData(acceptList, WaitToAcceptAcitivity.this::onDeleteAccept);
                Log.d("ACCEPTLIST", acceptList.toString());
                srlAccept.setRefreshing(false);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResultAccept> call, Throwable t) {
                Toast.makeText(WaitToAcceptAcitivity.this, "Có lỗi xảy ra", Toast.LENGTH_LONG).show();
                srlAccept.setRefreshing(false);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void initUi() {
        rcvRoomAccept = findViewById(R.id.rcv_room_accept);

        linearLayoutManager = new LinearLayoutManager(WaitToAcceptAcitivity.this);
        rcvRoomAccept.setLayoutManager(linearLayoutManager);

        adapter = new WaitAcceptAdapter(this);
        linearLayoutManager = new LinearLayoutManager(WaitToAcceptAcitivity.this);
        rcvRoomAccept.setLayoutManager(linearLayoutManager);
        rcvRoomAccept.setAdapter(adapter);

        progressDialog = new ProgressDialog(WaitToAcceptAcitivity.this);
        progressDialog.setMessage("Đang tải lại dữ liệu...");
        srlAccept = findViewById(R.id.srl_accept);

        clLogin = findViewById(R.id.cl_login_accept);
        lnAccept = findViewById(R.id.ln_accept);
        btnOpenLogin = findViewById(R.id.btn_open_login_accept);
    }

    @Override
    public void onDeleteAccept(Accept accept) {
        Retrofit retrofit = RetrofitInstance.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        DeleteIdAccept deleteIdAccept = new DeleteIdAccept(accept.getId());
        apiService.deleteAccept(deleteIdAccept).enqueue(new Callback<Accept>() {
            @Override
            public void onResponse(Call<Accept> call, Response<Accept> response) {
                //Huy dat thoi gian
                Intent intent = new Intent(WaitToAcceptAcitivity.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(WaitToAcceptAcitivity.this, 0, intent, 0);
                if (alarmManager == null) {
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                }
                alarmManager.cancel(pendingIntent);
                Toast.makeText(WaitToAcceptAcitivity.this, "Hủy đặt giờ", Toast.LENGTH_SHORT).show();

                srlAccept.setRefreshing(false);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(WaitToAcceptAcitivity.this, "Xóa dữ liệu thành công!", Toast.LENGTH_SHORT).show();
                Log.d("ACCEPTID", accept.getId());
                Log.d("NEWACCEPTID", deleteIdAccept + "");
            }

            @Override
            public void onFailure(Call<Accept> call, Throwable t) {
                srlAccept.setRefreshing(false);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(WaitToAcceptAcitivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}