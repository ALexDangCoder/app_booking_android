package com.example.fbooking.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fbooking.R;
import com.example.fbooking.retrofit.ApiService;
import com.example.fbooking.retrofit.RetrofitInstance;
import com.example.fbooking.utils.PriceFormatUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CheckAgainActivity extends AppCompatActivity {
    private TextView tvDateCheckInAgain, tvNightAgain, tvDateCheckOutAgain,
            tvRoomNumberAgain, tvRoomTypeAgain, tvRankAgain, tvPeopleAgain, tvDescriptionAgain,
            tvNameAgain, tvPhoneNumberAgain, tvIdPersonAgain, tvEmailAgain,
            tvOrderAgain, tvCheckInTimeAgain, tvPriceAgain;
    private ImageView imgRoomAgain;
    private AppCompatButton btnCancelAgain, btnOpenPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_again);

        initUi();

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        imgRoomAgain.getLayoutParams().height = height / 5;

        showRoomDetail();

        onClickButton();
    }

    private void showRoomDetail() {
        NumberFormat formatter = new DecimalFormat("#,###");
//        String formattedMoney = formatter.format(room.getPriceRoom());

        Booking booking = (Booking) getIntent().getExtras().get("booking");

        tvDateCheckInAgain.setText(booking.getNgaynhan());
        tvNightAgain.setText(String.valueOf(booking.getSodem()));
        tvDateCheckOutAgain.setText(booking.getNgayTra());
        tvRoomNumberAgain.setText(booking.getSophong());
        tvRoomTypeAgain.setText("1");
        tvRankAgain.setText("1");
        tvPeopleAgain.setText(String.valueOf(booking.getSoNguoi()));
        tvDescriptionAgain.setText("1");

        tvNameAgain.setText(booking.getHoten());
        tvPhoneNumberAgain.setText(booking.getSophong());
        tvIdPersonAgain.setText(String.valueOf(booking.getCccd()));
        tvEmailAgain.setText(booking.getEmail());

        tvOrderAgain.setText("1");
        tvCheckInTimeAgain.setText("1");

        tvPriceAgain.setText(CheckAgainActivity.this.getString(R.string.vnd, PriceFormatUtils.format(String.valueOf(booking.getGiaPhong()))));

        //Chuyen du lieu
        String dateCheckIn = tvDateCheckInAgain.getText().toString();

        int night = booking.getSodem();
        String checkNight = tvNightAgain.getText().toString();
        try {
            night = Integer.parseInt(checkNight);
        } catch (NumberFormatException e) {

        }

        String dateCheckOut = tvDateCheckOutAgain.getText().toString();
        String roomNumber = tvRoomNumberAgain.getText().toString();

        int people = booking.getSoNguoi();
        String checkPeople = tvPeopleAgain.getText().toString();
        try {
            people = Integer.parseInt(checkPeople);
            Log.d("SONGUOICHECK", String.valueOf(people));
        } catch (NumberFormatException e) {

        }

        String name = tvNameAgain.getText().toString();
        String phoneNumber = tvPhoneNumberAgain.getText().toString();

        Number idPerson = booking.getCccd();
        String checkIdPerson = tvIdPersonAgain.getText().toString();
        try {
            idPerson = Integer.parseInt(checkIdPerson);
        } catch (NumberFormatException e) {

        }

        String email = tvEmailAgain.getText().toString();
        Log.d("EMAIL CUA TOI", email);

        double price = booking.getGiaPhong();
        String checkPrice = tvIdPersonAgain.getText().toString();
        try {
            price = Double.parseDouble(checkPrice);
        } catch (NumberFormatException e) {

        }

        String roomId = booking.getRoomid();
        Log.d("ROOMID", roomId);
        String checkInTime = booking.getGioNhanPhong();
        String checkOutTime = booking.getGioTra();

//        booking = new Booking(roomId, roomNumber, name, phoneNumber, idPerson, email,
//                dateCheckIn, dateCheckOut, night, people, checkInTime, checkOutTime, price);
//
//        Retrofit retrofit = RetrofitInstance.getInstance();
//        ApiService apiService = retrofit.create(ApiService.class);
//
//        Booking finalBooking = booking;
//        btnOpenPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                apiService.createOrder(finalBooking).enqueue(new Callback<Booking>() {
//                    @Override
//                    public void onResponse(Call<Booking> call, Response<Booking> response) {
//                        Toast.makeText(CheckAgainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//
//                        Booking o = response.body();
//                        Log.d("BOOKING", o.toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<Booking> call, Throwable t) {
//                        Toast.makeText(CheckAgainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                startActivity(new Intent(CheckAgainActivity.this, ConfirmActivity.class));
//            }
//        });
    }

    private void onClickButton() {
        btnCancelAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initUi() {
        tvDateCheckInAgain = findViewById(R.id.tv_date_check_in_again);
        tvNightAgain = findViewById(R.id.tv_night_again);
        tvDateCheckOutAgain = findViewById(R.id.tv_date_check_out_again);
        tvRoomNumberAgain = findViewById(R.id.tv_room_number_again);
        tvRoomTypeAgain = findViewById(R.id.tv_room_type_again);
        tvRankAgain = findViewById(R.id.tv_rank_again);
        tvPeopleAgain = findViewById(R.id.tv_people_again);
        tvDescriptionAgain = findViewById(R.id.tv_description_again);
        tvNameAgain = findViewById(R.id.tv_name_again);
        tvPhoneNumberAgain = findViewById(R.id.tv_phone_number_again);
        tvIdPersonAgain = findViewById(R.id.tv_id_person_again);
        tvEmailAgain = findViewById(R.id.tv_email_again);
        tvOrderAgain = findViewById(R.id.tv_order_again);
        tvCheckInTimeAgain = findViewById(R.id.tv_check_in_time_again);

        tvPriceAgain = findViewById(R.id.tv_price_again);

        imgRoomAgain = findViewById(R.id.img_room_again);

        btnCancelAgain = findViewById(R.id.btn_cancel_again);
        btnOpenPay = findViewById(R.id.btn_open_pay_again);
    }
}