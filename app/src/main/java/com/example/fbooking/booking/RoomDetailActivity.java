package com.example.fbooking.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fbooking.MainActivity;
import com.example.fbooking.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class RoomDetailActivity extends AppCompatActivity {
    private ImageView imgRoomDetail1, imgRoomDetail2, imgRoomDetail3, imgRoomDetail4;
    private TextView tvRoomNumberDetail, tvViewDetail, tvRankDetail, tvRoomTypeDetail, tvPeopleDetail,
            tvDescriptionDetai, tvPositionDetail, tvPriceDetail;
    private AppCompatButton btnCancelDetail, btnBookingDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        initUi();

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        imgRoomDetail1.getLayoutParams().height = height / 5;
        imgRoomDetail2.getLayoutParams().height = height / 6;
        imgRoomDetail3.getLayoutParams().height = height / 6;
        imgRoomDetail4.getLayoutParams().height = height / 6;

        showRoomDetail();

        onClickButton();
    }

    private void showRoomDetail() {
        NumberFormat formatter = new DecimalFormat("#,###");
//        String formattedMoney = formatter.format(room.getPriceRoom());

        tvRoomNumberDetail.setText("Phòng: " + "");
        tvPositionDetail.setText("Vị trí: " + "");
        tvRankDetail.setText("Hạng: " + "");
        tvRoomTypeDetail.setText("Loại phòng: " + "");
        tvPeopleDetail.setText("Số người/phòng: " + "");
        tvDescriptionDetai.setText("Mô tả: " + "");
        tvViewDetail.setText("Tầm nhìn: " + "");

        tvPriceDetail.setText("1,500,000" + " đ");
    }

    private void onClickButton() {
        btnCancelDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoomDetailActivity.this, MainActivity.class));
            }
        });

        btnBookingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoomDetailActivity.this, FillInformationActivity.class));
            }
        });
    }

    private void initUi() {
        imgRoomDetail1 = findViewById(R.id.img_room_detail_1);
        imgRoomDetail2 = findViewById(R.id.img_room_detail_2);
        imgRoomDetail3 = findViewById(R.id.img_room_detail_3);
        imgRoomDetail4 = findViewById(R.id.img_room_detail_4);

        tvRoomNumberDetail = findViewById(R.id.tv_room_number_detail);
        tvViewDetail = findViewById(R.id.tv_view_detail);
        tvRankDetail = findViewById(R.id.tv_rank_detail);
        tvRoomTypeDetail = findViewById(R.id.tv_room_type_detail);
        tvPeopleDetail = findViewById(R.id.tv_people_detail);
        tvDescriptionDetai = findViewById(R.id.tv_description_detail);
        tvPositionDetail = findViewById(R.id.tv_position_detail);
        tvPriceDetail = findViewById(R.id.tv_price_detail);

        btnCancelDetail = findViewById(R.id.btn_cancel_booking_detail);
        btnBookingDetail = findViewById(R.id.btn_open_booking_detail);
    }
}