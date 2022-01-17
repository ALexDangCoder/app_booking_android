package com.example.fbooking.booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbooking.R;
import com.example.fbooking.room.Room;
import com.example.fbooking.userloginandsignup.LoginActivity;
import com.example.fbooking.userloginandsignup.User;
import com.example.fbooking.utils.MyFirebaseInstanceIDService;
import com.example.fbooking.utils.PriceFormatUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FillInformationActivity extends AppCompatActivity {
    private EditText edtNameFill, edtPhoneFill, edtIdPersonFill, edtEmailFill,
            edtCheckInDateFill, edtCheckOutDateFill, edtPeopleFill,
            edtNightFill, edtCheckInTimeFill, edtCheckOutTimeFill;
    private TextView tvErrorFill, tvRoomNumber, tvRoomRank, tvRoomType, tvtotalPrice, tvPriceFill, tvExtraFill;
    private AppCompatButton btnCancelFill, btnOpenCheckAgain, btnOpenLoginFill;

    private String amPm;

    private ConstraintLayout clLogin, clInfo;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String formatDate = "yyyy-MM-dd";
    private String formatTime = "HH:mm";
    private Room room;

    double total = 0;
    int night = 0;
    int expectedTotal = 0;

    private Spinner spnCheckInFill;
    private CheckInTimeAdapter checkInTimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);

        initUi();

        room = (Room) getIntent().getExtras().get("data_next");

        showUserInformation();

        showRoomInfomation();

        onChoseDate();

        onClickButton();
    }

    private void onChoseCheckInTime() {
        spnCheckInFill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                caculatorPrice(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void caculatorPrice(int position) {
        int priceRoom = (int) room.getPriceRoom();
        if (position == 1) {
            expectedTotal = (int) total;
            tvExtraFill.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(0)));
            tvtotalPrice.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(expectedTotal)));

            calculateDates();

            Toast.makeText(FillInformationActivity.this, "Thu thêm: 0 Vnđ - Tổng tiền: " + total + " Vnđ",
                    Toast.LENGTH_SHORT).show();
        } else if (position == 2) {
            expectedTotal = (int) ((int) (priceRoom * 0.5) + total);
            tvExtraFill.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(priceRoom * 0.5)));
            tvtotalPrice.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(expectedTotal)));

            calculateDates();

            Toast.makeText(FillInformationActivity.this, "Thu thêm: " + (priceRoom * 0.5) + " Vnđ - Tổng tiền: " + total + " Vnđ",
                    Toast.LENGTH_SHORT).show();
        } else if (position == 3) {
            expectedTotal = (int) ((int) (priceRoom * 0.3) + total);
            tvExtraFill.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(priceRoom * 0.3)));
            tvtotalPrice.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(expectedTotal)));

            calculateDates();

            Toast.makeText(FillInformationActivity.this, "Thu thêm: " + (priceRoom * 0.3) + " Vnđ - Tổng tiền: " + total + " Vnđ",
                    Toast.LENGTH_SHORT).show();
        } else {
            return;
        }

    }

    //Hien thi thong tin phong
    private void showRoomInfomation() {
        tvRoomNumber.setText(room.getRoomNumber());
        tvRoomType.setText(room.getTypeRoom());
        tvRoomRank.setText(room.getRankRoom());
        tvExtraFill.setText("0 đ");
        tvPriceFill.setText("0 đ");
        tvtotalPrice.setText("0 đ");
    }

    //Hien thi form dang nhap khi nguoi dung chua dang nhap
    public void showUserInformation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        if (user == null) {
            clLogin.setVisibility(View.VISIBLE);
            btnOpenLoginFill.setEnabled(true);
            btnOpenLoginFill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FillInformationActivity.this, LoginActivity.class));
                }
            });

            clInfo.setBackgroundColor(Color.WHITE);
            clInfo.setVisibility(View.GONE);
        } else {
            clLogin.setVisibility(View.GONE);
            btnOpenLoginFill.setEnabled(false);

            clInfo.setVisibility(View.VISIBLE);
            reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if (userProfile != null) {
                        String fullname = userProfile.name;
                        String phoneNumber = userProfile.phoneNumber;
                        String idPerson = userProfile.idPerson;
                        String email = userProfile.email;

                        edtNameFill.setText(fullname);
                        edtPhoneFill.setText(phoneNumber);
                        edtIdPersonFill.setText(idPerson);
                        edtEmailFill.setText(email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(FillInformationActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onClickButton() {
        btnCancelFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOpenCheckAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCheckInformationAgain();
            }
        });
    }

    private void onChoseDate() {
        //Check in
        Calendar calendarCheckInDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener checkInDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarCheckInDate.set(Calendar.YEAR, year);
                calendarCheckInDate.set(Calendar.MONTH, month);
                calendarCheckInDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCheckInCalender(calendarCheckInDate);
                calculateDates();
                onChoseCheckInTime();
                caculatorPrice(spnCheckInFill.getSelectedItemPosition());
            }
        };

        edtCheckInDateFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FillInformationActivity.this, checkInDate,
                        calendarCheckInDate.get(Calendar.YEAR), calendarCheckInDate.get(Calendar.MONTH), calendarCheckInDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        //Check out
        Calendar calendarCheckOutDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener checkOutDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarCheckOutDate.set(Calendar.YEAR, year);
                calendarCheckOutDate.set(Calendar.MONTH, month);
                calendarCheckOutDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCheckOutCalender(calendarCheckOutDate);
                calculateDates();
                onChoseCheckInTime();
                caculatorPrice(spnCheckInFill.getSelectedItemPosition());
            }
        };

        edtCheckOutDateFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FillInformationActivity.this, checkOutDate,
                        calendarCheckOutDate.get(Calendar.YEAR), calendarCheckOutDate.get(Calendar.MONTH), calendarCheckOutDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }

    private void updateCheckInCalender(Calendar calendarCheckInDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        edtCheckInDateFill.setText(simpleDateFormat.format(calendarCheckInDate.getTime()));
    }

    private void updateCheckOutCalender(Calendar calendarCheckOutDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
        edtCheckOutDateFill.setText(simpleDateFormat.format(calendarCheckOutDate.getTime()));
    }

    private void calculateDates() {
        String checkInDate = edtCheckInDateFill.getText().toString();
        String checkOutDate = edtCheckOutDateFill.getText().toString();

        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
            Date startDate = simpleDateFormat.parse(checkInDate);
            Date endDate = simpleDateFormat.parse(checkOutDate);

            assert startDate != null;
            assert endDate != null;
            long duration = (endDate.getTime() - startDate.getTime());

            if (startDate.compareTo(endDate) > 0) {
                tvErrorFill.setText(FillInformationActivity.this.getString(R.string.sai_ngay));
                edtNightFill.setText(FillInformationActivity.this.getString(R.string.loi));
                tvPriceFill.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(0)));

                tvExtraFill.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(0)));
                tvtotalPrice.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(0)));

                return;
            } else if (startDate.compareTo(endDate) == 0) {
                tvErrorFill.setText(FillInformationActivity.this.getString(R.string.sai_so_dem));
                edtNightFill.setText(TimeUnit.DAYS.convert(duration, TimeUnit.MILLISECONDS) + " đêm");
                tvPriceFill.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(0)));

                tvExtraFill.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(0)));
                tvtotalPrice.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(0)));

                return;
            } else {
                tvErrorFill.setText("");
                edtNightFill.setText(TimeUnit.DAYS.convert(duration, TimeUnit.MILLISECONDS) + " đêm");
                total = room.getPriceRoom() * TimeUnit.DAYS.convert(duration, TimeUnit.MILLISECONDS);
                tvPriceFill.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(total)));
//                tvtotalPrice.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(expectedTotal)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void openCheckInformationAgain() {
        String strName = edtNameFill.getText().toString().trim();
        String strPhoneNumber = edtPhoneFill.getText().toString().trim();

        String strIdPerson = edtIdPersonFill.getText().toString().trim();
        Log.d("IDPERSONFILL", strIdPerson + "");

        String strEmail = edtEmailFill.getText().toString().trim();

        String strCheckInDate = edtCheckInDateFill.getText().toString().trim();
        String strCheckOutDate = edtCheckOutDateFill.getText().toString().trim();

        int people = 0;
        String strPeopleFill = edtPeopleFill.getText().toString().trim();
        try {
            people = Integer.parseInt(strPeopleFill);
            Log.d("PEOPLE", String.valueOf(people));
        } catch (NumberFormatException e) {

        }

        String strNight = edtNightFill.getText().toString().trim();

        String strCheckInTime = spnCheckInFill.getSelectedItem().toString();
        String strCheckOutTime = edtCheckOutTimeFill.getText().toString().trim();

        if (strName.isEmpty() && strPhoneNumber.isEmpty() && strIdPerson.isEmpty() && strEmail.isEmpty()
                && strCheckInDate.isEmpty() && strCheckOutDate.isEmpty() && strPeopleFill.isEmpty() && strNight.isEmpty()
                && strCheckOutTime.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.thong_tin_bi_trong));
            return;
        }

        if (strName.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_ho_ten));
            return;
        }

        if (strPhoneNumber.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_so_dien_thoai));
            return;
        } else if (!strPhoneNumber.matches(String.valueOf(Patterns.PHONE))) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.sai_so_dien_thoai));
            return;
        }

        if (strIdPerson.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_cmnd));
            return;
        }

        if (strEmail.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_email));
            return;
        } else if (!strEmail.matches(String.valueOf(Patterns.EMAIL_ADDRESS))) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.sai_dinh_dang_email));
            return;
        }

        if (strCheckInDate.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_ngay_nhan));
            return;
        }

        if (strCheckOutDate.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_ngay_tra));
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat(formatTime);
        try {
            Date startDate = simpleDateFormat.parse(strCheckInDate);
            Date endDate = simpleDateFormat.parse(strCheckOutDate);

            Date startTime = simpleDateFormatTime.parse(strCheckInTime);
            Date endTime = simpleDateFormatTime.parse(strCheckOutTime);

            if (startDate.compareTo(endDate) > 0) {
                tvErrorFill.setText(FillInformationActivity.this.getString(R.string.sai_ngay_nhan_tra_phong));
//                tvtotalPrice.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(0)));
                return;
            } else if (startDate.compareTo(endDate) == 0) {
                if (startTime.compareTo(endTime) > 0) {
                    tvErrorFill.setText(FillInformationActivity.this.getString(R.string.sai_thoi_gian_nhan_tra_phong));
//                    tvtotalPrice.setText(FillInformationActivity.this.getString(R.string.vnd, PriceFormatUtils.format(0)));
                    return;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (strPeopleFill.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_nguoi_lon));
            return;
        }

        if (people <= 0) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.so_nguoi_lon_hon));
            return;
        } else if (people > 4) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.so_nguoi_be_hon));
            return;
        }

        if (room.getTypeRoom().equalsIgnoreCase("Phòng đơn") && people > 1) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.phong_toi_thieu_1));
            return;
        } else if (room.getTypeRoom().equalsIgnoreCase("Phòng đôi") && people > 2) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.phong_toi_thieu_2));
            return;
        } else if (room.getTypeRoom().equalsIgnoreCase("Phòng 2 giường") && people > 4) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.phong_toi_thieu_4));
            return;
        }

        if (strNight.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_so_dem));
            return;
        }

        if (spnCheckInFill.getSelectedItemPosition() == 0) {
            tvErrorFill.setText("Bạn chưa chọn giờ nhận phòng!");
            return;
        }

        tvRoomNumber.setText(room.getRoomNumber());
        tvRoomType.setText(room.getTypeRoom());
        tvRoomRank.setText(room.getRankRoom());

        String roomId = room.getRoomId();
        Log.d("IDROOM", roomId);
        String roomNumber = tvRoomNumber.getText().toString();
        String roomType = tvRoomType.getText().toString();
        String roomRank = tvRoomRank.getText().toString();

        tvErrorFill.setText("");

//        Booking booking = new Booking("61ab35ac7140436ae6942b10",
//                "301", "Nguyễn Thành Nhật", "123456789", "1",
//                "1", 987654321, "nhatbeo@gmail.com", "1/1/2022",
//                "1/1/2022", 2, 1,
//                "12:30", "12:30", 1000000);

        String token = MyFirebaseInstanceIDService.getToken(FillInformationActivity.this);
        Log.d("TOKENCHECK", token);

        Booking booking = new Booking(roomId, roomNumber, strName, strPhoneNumber, roomType, roomRank,
                strIdPerson, strEmail, strCheckInDate, strCheckOutDate, night, people, strCheckInTime,
                strCheckOutTime, room.getPriceRoom(), expectedTotal, token);

        Intent intent = new Intent(FillInformationActivity.this, CheckAgainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("booking", booking);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void initUi() {
        edtNameFill = findViewById(R.id.edt_name_fill);
        edtPhoneFill = findViewById(R.id.edt_phone_fill);
        edtIdPersonFill = findViewById(R.id.edt_id_person_fill);
        edtEmailFill = findViewById(R.id.edt_email_fill);
        edtCheckInDateFill = findViewById(R.id.edt_check_in_date_fill);
        edtCheckOutDateFill = findViewById(R.id.edt_check_out_date_fill);
        edtPeopleFill = findViewById(R.id.edt_people_fill);
        edtNightFill = findViewById(R.id.edt_night_fill);
//        edtCheckInTimeFill = findViewById(R.id.edt_check_in_time_fill);
        spnCheckInFill = findViewById(R.id.spn_check_in_time_fill);

        List<String> checkInTime = new ArrayList<>();
        checkInTime.add("Thời gian nhận!");
        checkInTime.add("14:00 PM");
        checkInTime.add("5:00 AM - 9:00 AM");
        checkInTime.add("9:00 AM - 14:00 PM");
        checkInTimeAdapter = new CheckInTimeAdapter(FillInformationActivity.this,
                R.layout.item_selected, checkInTime);
        spnCheckInFill.setAdapter(checkInTimeAdapter);

        edtCheckOutTimeFill = findViewById(R.id.edt_check_out_time_fill);

        tvErrorFill = findViewById(R.id.tv_error_fill);

        tvRoomNumber = findViewById(R.id.tv_room_number_fill);
        tvRoomRank = findViewById(R.id.tv_room_rank_fill);
        tvRoomType = findViewById(R.id.tv_room_type_fill);
        tvPriceFill = findViewById(R.id.tv_price_fill);
        tvtotalPrice = findViewById(R.id.tv_total_fill);
        tvExtraFill = findViewById(R.id.tv_extra_fill);

        btnCancelFill = findViewById(R.id.btn_cancel_fill);
        btnOpenCheckAgain = findViewById(R.id.btn_open_check_again);

        clLogin = findViewById(R.id.cl_login_fill);
        clInfo = findViewById(R.id.cl_info_fill);
        btnOpenLoginFill = findViewById(R.id.btn_open_login_fill);
    }
}