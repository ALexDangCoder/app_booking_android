package com.example.fbooking.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fbooking.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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

        tvDateCheckInAgain.setText(CheckAgainActivity.this.getString(R.string.check_nhan_phong, ""));
        tvNightAgain.setText(CheckAgainActivity.this.getString(R.string.check_so_dem, ""));
        tvDateCheckOutAgain.setText(CheckAgainActivity.this.getString(R.string.check_tra_phong, ""));
        tvRoomNumberAgain.setText(CheckAgainActivity.this.getString(R.string.check_phong_so, ""));
        tvRoomTypeAgain.setText(CheckAgainActivity.this.getString(R.string.check_loai_phong, ""));
        tvRankAgain.setText(CheckAgainActivity.this.getString(R.string.check_hang_phong, ""));
        tvPeopleAgain.setText(CheckAgainActivity.this.getString(R.string.check_so_nguoi, ""));
        tvDescriptionAgain.setText(CheckAgainActivity.this.getString(R.string.check_mo_ta, ""));

        tvNameAgain.setText(CheckAgainActivity.this.getString(R.string.check_ten_khach, ""));
        tvPhoneNumberAgain.setText(CheckAgainActivity.this.getString(R.string.check_so_dien_thoai, ""));
        tvIdPersonAgain.setText(CheckAgainActivity.this.getString(R.string.check_cmnd, ""));
        tvEmailAgain.setText(CheckAgainActivity.this.getString(R.string.check_email, ""));

        tvOrderAgain.setText(CheckAgainActivity.this.getString(R.string.check_dat_phong_cho, ""));
        tvCheckInTimeAgain.setText(CheckAgainActivity.this.getString(R.string.check_gio_den, ""));

        tvPriceAgain.setText("1,500,000" + " đ");

    }

    private void onClickButton() {
        btnCancelAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOpenPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckAgainActivity.this, PayActivity.class));
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