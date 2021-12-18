package com.example.fbooking.userloginandsignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fbooking.MainActivity;
import com.example.fbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmailLogin, edtPasswordLogin;
    private CheckBox cbRemember;
    private TextView tvForgotPassword, tvShowErrorLogin, tvOpenSignUp;
    private ImageView btnHideShowPassLogin;
    private AppCompatButton btnSignIn;
    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();

        onClickButton();

        //Hien email va mat khau tren edittext
        onSharePreferences();
    }

    private void onClickButton() {
        //Dang nhap
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignIn();
            }
        });

        //Mo man hinh dang ky
        tvOpenSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOpenSignUp();
            }
        });

        //Hien, an mat khau
        btnHideShowPassLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePass();
            }
        });

        //Quen mat khau
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
    }

    //Ham quen mat khau
    private void forgotPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        String emailAddress = edtEmailLogin.getText().toString().trim();
        String password = edtPasswordLogin.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailAddress.isEmpty() || password.isEmpty()) {
            tvShowErrorLogin.setText("Thông tin bị trống");
            return;
        }

        if (TextUtils.isEmpty(emailAddress)) {
            tvShowErrorLogin.setText("Bạn chưa nhập email");
            return;
        } else if (!emailAddress.matches(emailPattern)) {
            tvShowErrorLogin.setText("Sai định dạng Email");
            return;
        }

        if (password.isEmpty()) {
            tvShowErrorLogin.setText("Bạn chưa nhập mật khẩu");
            return;
        }

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Gửi email thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Gửi email thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Ham dang nhap
    private void onClickSignIn() {
        String emailAddress = edtEmailLogin.getText().toString().trim();
        String password = edtPasswordLogin.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailAddress.isEmpty() && password.isEmpty()) {
            tvShowErrorLogin.setText("Thông tin bị trống!");
            return;
        }

        if (TextUtils.isEmpty(emailAddress)) {
            tvShowErrorLogin.setText("Bạn chưa nhập email!");
            return;
        } else if (!emailAddress.matches(String.valueOf(Patterns.EMAIL_ADDRESS))) {
            tvShowErrorLogin.setText("Sai định dạng Email!");
            return;
        }

        if (password.isEmpty()) {
            tvShowErrorLogin.setText("Bạn chưa nhập mật khẩu!");
            return;
        }

        tvShowErrorLogin.setVisibility(View.GONE);
        remember();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.signInWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Sai tên đăng nhập hoặc sai mật khẩu", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

    }

    //Hien mat khau
    private void showHidePass() {
        if (edtPasswordLogin.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            //Show Password
            edtPasswordLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            edtPasswordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    //Mo man hinh dang ky
    private void onClickOpenSignUp() {
        Intent openSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(openSignUp);
    }

    //Hien thi email va mat khau tren edittext
    private void onSharePreferences() {
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        boolean check = sharedPreferences.getBoolean("remember", false);
        edtEmailLogin.setText(email);
        edtPasswordLogin.setText(password);
        cbRemember.setChecked(check);
    }

    //Nho mat khau
    public void remember() {
        if (cbRemember.isChecked()) {
            editor.putString("email", edtEmailLogin.getText().toString());
            editor.putString("password", edtPasswordLogin.getText().toString());
            editor.putBoolean("remember", true);
            editor.commit();
        } else {
            editor.putString("email", "");
            editor.putString("password", "");
            editor.putBoolean("remember", false);
            editor.commit();
        }
    }

    //Anh xa view
    private void initUi() {
        edtEmailLogin = findViewById(R.id.edt_email_login);
        edtPasswordLogin = findViewById(R.id.edt_password_login);

        cbRemember = findViewById(R.id.cb_remember);

        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        tvForgotPassword.setPaintFlags(tvForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvShowErrorLogin = findViewById(R.id.tv_show_error_login);

        btnHideShowPassLogin = findViewById(R.id.btn_hide_show_password_login);

        btnSignIn = findViewById(R.id.btn_sign_in);
        tvOpenSignUp = findViewById(R.id.tv_open_sign_up);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang kiểm tra thông tin...");
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}