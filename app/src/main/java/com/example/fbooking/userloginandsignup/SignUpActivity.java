package com.example.fbooking.userloginandsignup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fbooking.R;
import com.example.fbooking.service.ApiService;
import com.example.fbooking.service.TestLoginService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtNameSignUp, edtDateOfBirthSignUp, edtPhoneNumberSignUp, edtIdPersonSignUp,
            edtEmailSignUp, edtPasswordSignUp, edtConfirmPasswordSignUp;
    private ImageView btnHideShowPassSignUp, btnHideShowConfirmPassSignUp;
    private TextView tvShowErrorSignUp, tvOpenLogin;
    private AppCompatButton btnSignUp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUi();

        //Chon ngay sinh
        choseDateOfBirth();

        onClickButton();
    }


    private void onClickButton() {
        //Dang ky
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });

        //Mo man hinh dang nhap
        tvOpenLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOpenLogin();
            }
        });

        //Hien thi mat khau
        btnHideShowPassSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePass();
            }
        });

        //Hien thi xac nhan mat khau
        btnHideShowConfirmPassSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideConfirmPass();
            }
        });
    }

    private void onClickSignUp() {
        String strName = edtNameSignUp.getText().toString().trim();
        String strBirth = edtDateOfBirthSignUp.getText().toString().trim();
        String strPhoneNumber = edtPhoneNumberSignUp.getText().toString().trim();
        String strIdPerson = edtIdPersonSignUp.getText().toString().trim();
        String strEmail = edtEmailSignUp.getText().toString().trim();
        String strPassword = edtPasswordSignUp.getText().toString().trim();
        String strConfirmPassword = edtConfirmPasswordSignUp.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (strName.isEmpty() && strBirth.isEmpty() && strPhoneNumber.isEmpty() && strIdPerson.isEmpty()
                && strEmail.isEmpty() && strPassword.isEmpty() && strConfirmPassword.isEmpty()) {
            tvShowErrorSignUp.setText("Thông tin bị trống!");
            return;
        }

        if (strName.isEmpty()) {
            tvShowErrorSignUp.setText("Bạn chưa nhập họ và tên!");
            return;
        }

        if (strBirth.isEmpty()) {
            tvShowErrorSignUp.setText("Bạn chưa nhập ngày sinh!");
            return;
        }

        if (strPhoneNumber.isEmpty()) {
            tvShowErrorSignUp.setText("Bạn chưa nhập số điện thoại!");
            return;
        } else if (!strPhoneNumber.matches(String.valueOf(Patterns.PHONE))) {
            tvShowErrorSignUp.setText("Sai định dạng số điện thoại!");
            return;
        }

        if (strIdPerson.isEmpty()) {
            tvShowErrorSignUp.setText("Bạn chưa nhập CMND/CCCD!");
            return;
        }

        if (strEmail.isEmpty()) {
            tvShowErrorSignUp.setText("Bạn chưa nhập email!");
            return;
        } else if (!strEmail.matches(String.valueOf(Patterns.EMAIL_ADDRESS))) {
            tvShowErrorSignUp.setText("Sai định dạng Email!");
            return;
        }

        if (strPassword.isEmpty()) {
            tvShowErrorSignUp.setText("Bạn chưa nhập mật khẩu!");
            return;
        } else if (strPassword.length() < 8) {
            tvShowErrorSignUp.setText("Mật khẩu phải nhiều hơn 8 ký tự!");
            return;
        }

        if (strConfirmPassword.isEmpty()) {
            tvShowErrorSignUp.setText("Bạn chưa nhập xác nhận mật khẩu!");
            return;
        } else if (strConfirmPassword.length() != strPassword.length()) {
            tvShowErrorSignUp.setText("Sai mật khẩu!");
            return;
        }

        tvShowErrorSignUp.setVisibility(View.GONE);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(strName, strBirth, strPhoneNumber, strIdPerson, strEmail);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        postAccountToService(strEmail, strPassword, strName, strBirth, strPhoneNumber, strIdPerson);
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Email đã tồn tại, vui lòng sử dụng email khác!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //post account
    private void postAccountToService(String email, String pass, String name, String birthday, String phoneNumber, String cccd) {
        ApiService.apiService.covertApi(email, pass, name, birthday, phoneNumber, cccd).enqueue(new Callback<TestLoginService>() {
            @Override
            public void onResponse(Call<TestLoginService> call, Response<TestLoginService> response) {
                TestLoginService testLoginService = response.body();

                if (testLoginService.getSuccess() == true) {
                    Log.e("okkk", "");
                } else {
                    Log.e("false", "");
                }
            }

            @Override
            public void onFailure(Call<TestLoginService> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "False", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //Chon ngay sinh
    private void choseDateOfBirth() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalender();
            }

            private void updateCalender() {
                String formatDate = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.CHINA);
                edtDateOfBirthSignUp.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        edtDateOfBirthSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this, date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    //Hien mat khau
    private void showHidePass() {
        if (edtPasswordSignUp.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            //Show Password
            edtPasswordSignUp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            edtPasswordSignUp.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    //Hien xac nhan mat khau
    private void showHideConfirmPass() {
        if (edtConfirmPasswordSignUp.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            //Show Password
            edtConfirmPasswordSignUp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            edtConfirmPasswordSignUp.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    //Mo man hinh dang ky
    private void onClickOpenLogin() {
        Intent openLogin = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(openLogin);
    }

    //Ham anh xa
    private void initUi() {
        edtNameSignUp = findViewById(R.id.edt_name_sign_up);
        edtDateOfBirthSignUp = findViewById(R.id.edt_date_sign_up);
        edtPhoneNumberSignUp = findViewById(R.id.edt_phone_sign_up);
        edtIdPersonSignUp = findViewById(R.id.edt_id_person_sign_up);
        edtEmailSignUp = findViewById(R.id.edt_email_sign_up);
        edtPasswordSignUp = findViewById(R.id.edt_password_sign_up);
        edtConfirmPasswordSignUp = findViewById(R.id.edt_confirm_password_sign_up);

        tvShowErrorSignUp = findViewById(R.id.tv_show_error_sign_up);

        btnHideShowPassSignUp = findViewById(R.id.btn_hide_show_password_sign_up);
        btnHideShowConfirmPassSignUp = findViewById(R.id.btn_hide_show_confirm_password_sign_up);

        btnSignUp = findViewById(R.id.btn_sign_up);
        tvOpenLogin = findViewById(R.id.tv_open_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang kiểm tra thông tin...");
    }
}