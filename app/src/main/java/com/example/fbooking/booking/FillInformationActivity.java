package com.example.fbooking.booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fbooking.R;
import com.example.fbooking.userloginandsignup.LoginActivity;
import com.example.fbooking.userloginandsignup.SignUpActivity;
import com.example.fbooking.userloginandsignup.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class FillInformationActivity extends AppCompatActivity {
    private EditText edtNameFill, edtPhoneFill, edtIdPersonFill, edtEmailFill,
            edtCheckInDateFill, edtCheckOutDateFill, edtPeopleFill,
            edtNightFill, edtCheckInTimeFill, edtCheckOutTimeFill;
    private RadioGroup rgCheck;
    private RadioButton rbSelfFill, rbOtherFill, rbCheck;
    private TextView tvPriceFill, tvErrorFill, tvRoomNumber, tvRoomRank, tvRoomType, tvRoomPrice;
    private AppCompatButton btnCancelFill, btnOpenCheckAgain, btnOpenLoginFill;

    private String amPm;

    private ConstraintLayout clLogin, clInfo;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String formatDate = "dd/MM/yyyy";
    private String formatTime = "HH:mm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);

        initUi();

//        showFrom();

        showUserInformation();

        onChoseDate();

        onChoseTime();

        onClickButton();
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

        NumberFormat formatter = new DecimalFormat("#,###");
        tvPriceFill.setText("1,500,000" + " đ");
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
        Calendar calendarCheckInDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener checkInDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarCheckInDate.set(Calendar.YEAR, year);
                calendarCheckInDate.set(Calendar.MONTH, month);
                calendarCheckInDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalender();
            }

            private void updateCalender() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
                edtCheckInDateFill.setText(simpleDateFormat.format(calendarCheckInDate.getTime()));
            }
        };
        edtCheckInDateFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DatePickerDialog(FillInformationActivity.this, checkInDate, calendarCheckInDate.get(Calendar.YEAR),
//                        calendarCheckInDate.get(Calendar.MONTH), calendarCheckInDate.get(Calendar.DAY_OF_MONTH)).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(FillInformationActivity.this, checkInDate,
                        calendarCheckInDate.get(Calendar.YEAR), calendarCheckInDate.get(Calendar.MONTH), calendarCheckInDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        Calendar calendarCheckOutDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener checkOutDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarCheckOutDate.set(Calendar.YEAR, year);
                calendarCheckOutDate.set(Calendar.MONTH, month);
                calendarCheckOutDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalender();
            }

            private void updateCalender() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());
                edtCheckOutDateFill.setText(simpleDateFormat.format(calendarCheckOutDate.getTime()));
            }
        };

        edtCheckOutDateFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DatePickerDialog(FillInformationActivity.this, checkOutDate, calendarCheckOutDate.get(Calendar.YEAR),
//                        calendarCheckOutDate.get(Calendar.MONTH), calendarCheckOutDate.get(Calendar.DAY_OF_MONTH)).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(FillInformationActivity.this, checkOutDate,
                        calendarCheckOutDate.get(Calendar.YEAR), calendarCheckOutDate.get(Calendar.MONTH), calendarCheckOutDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        edtCheckInDateFill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateDates();
            }
        });

        edtCheckOutDateFill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateDates();
            }
        });
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
                return;
            } else {
                tvErrorFill.setText("");
                edtNightFill.setText(TimeUnit.DAYS.convert(duration, TimeUnit.MILLISECONDS) + " đêm");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void calculateTimes() {
        String checkInTime = edtCheckInTimeFill.getText().toString();
        String checkOutTime = edtCheckOutTimeFill.getText().toString();

        String checkInDate = edtCheckInDateFill.getText().toString();
        String checkOutDate = edtCheckOutDateFill.getText().toString();
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
            Date startDate = simpleDateFormat.parse(checkInDate);
            Date endDate = simpleDateFormat.parse(checkOutDate);
            long duration = (endDate.getTime() - startDate.getTime());

            SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat(formatTime);
            Date startTime = simpleDateFormatTime.parse(checkInTime);
            Date endTime = simpleDateFormatTime.parse(checkOutTime);

            long difference = (endTime.getTime() - startTime.getTime());
            if (difference < 0) {
                Date dateMax = simpleDateFormatTime.parse("24:00");
                Date dateMin = simpleDateFormatTime.parse("00:00");
                difference = (dateMax.getTime() - startTime.getTime()) + (endTime.getTime() - dateMin.getTime());
            }
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);

            if (startDate.compareTo(endDate) == 0) {
                if (startTime.compareTo(endTime) > 0) {
                    tvErrorFill.setText(FillInformationActivity.this.getString(R.string.sai_ngay));
                    edtNightFill.setText(FillInformationActivity.this.getString(R.string.loi));
                    return;
                } else {
                    tvErrorFill.setText("");
                    edtNightFill.setText(FillInformationActivity.this.getString(R.string.dem, String.valueOf(TimeUnit.DAYS.convert(duration, TimeUnit.MILLISECONDS))));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void onChoseTime() {
        Calendar selectedTime = Calendar.getInstance();
        int hour = selectedTime.get(Calendar.HOUR_OF_DAY);
        int minute = selectedTime.get(Calendar.MINUTE);
        TimePickerDialog checkInTime = new TimePickerDialog(FillInformationActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                selectedTime.set(Calendar.MINUTE, selectedMinute);
                if (selectedHour >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                edtCheckInTimeFill.setText(String.format("%02d:%02d", selectedHour, selectedMinute) + " " + amPm);
            }
        }, hour, minute, false);

        edtCheckInTimeFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInTime.show();
            }
        });

        TimePickerDialog checkOutTime = new TimePickerDialog(FillInformationActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                selectedTime.set(Calendar.MINUTE, selectedMinute);
                if (selectedHour >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                edtCheckOutTimeFill.setText(String.format("%02d:%02d", selectedHour, selectedMinute) + " " + amPm);
            }
        }, hour, minute, false);

        edtCheckOutTimeFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOutTime.show();
            }
        });

        edtCheckInTimeFill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateTimes();
            }
        });

        edtCheckOutTimeFill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateDates();
            }
        });
    }

    private void openCheckInformationAgain() {
        String strName = edtNameFill.getText().toString().trim();
        String strPhoneNumber = edtPhoneFill.getText().toString().trim();
        String strIdPerson = edtIdPersonFill.getText().toString().trim();
        String strEmail = edtEmailFill.getText().toString().trim();

        String strCheckInDate = edtCheckInDateFill.getText().toString().trim();
        String strCheckOutDate = edtCheckOutDateFill.getText().toString().trim();
        String strPeopleFill = edtPeopleFill.getText().toString().trim();
        String strNight = edtNightFill.getText().toString().trim();

        int selectedId = rgCheck.getCheckedRadioButtonId();
        rbCheck = findViewById(selectedId);

        String strCheckInTime = edtCheckInTimeFill.getText().toString().trim();
        String strCheckOutTime = edtCheckOutTimeFill.getText().toString().trim();

        if (strName.isEmpty() && strPhoneNumber.isEmpty() && strIdPerson.isEmpty() && strEmail.isEmpty()
                && strCheckInDate.isEmpty() && strCheckOutDate.isEmpty() && strPeopleFill.isEmpty() && strNight.isEmpty()
                && strCheckInTime.isEmpty() && strCheckOutTime.isEmpty()) {
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
                return;
            } else if (startDate.compareTo(endDate) == 0) {
                if (startTime.compareTo(endTime) > 0) {
                    tvErrorFill.setText(FillInformationActivity.this.getString(R.string.sai_thoi_gian_nhan_tra_phong));
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

        if (strNight.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_so_dem));
            return;
        }

        if (strCheckInTime.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_thoi_gian_nhan));
            return;
        }

        if (strCheckInTime.isEmpty()) {
            tvErrorFill.setText(FillInformationActivity.this.getString(R.string.chua_nhap_thoi_gian_tra));
            return;
        }

        tvErrorFill.setText("");

        startActivity(new Intent(FillInformationActivity.this, CheckAgainActivity.class));
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
        edtCheckInTimeFill = findViewById(R.id.edt_check_in_time_fill);
        edtCheckOutTimeFill = findViewById(R.id.edt_check_out_time_fill);

        rgCheck = findViewById(R.id.rg_check);

        tvPriceFill = findViewById(R.id.tv_price_fill);
        tvErrorFill = findViewById(R.id.tv_error_fill);

        tvRoomNumber = findViewById(R.id.tv_room_number_fill);
        tvRoomRank = findViewById(R.id.tv_room_rank_fill);
        tvRoomType = findViewById(R.id.tv_room_type_fill);
        tvRoomPrice = findViewById(R.id.tv_price_fill);

        btnCancelFill = findViewById(R.id.btn_cancel_fill);
        btnOpenCheckAgain = findViewById(R.id.btn_open_check_again);

        clLogin = findViewById(R.id.cl_login_fill);
        clInfo = findViewById(R.id.cl_info_fill);
        btnOpenLoginFill = findViewById(R.id.btn_open_login_fill);
    }
}