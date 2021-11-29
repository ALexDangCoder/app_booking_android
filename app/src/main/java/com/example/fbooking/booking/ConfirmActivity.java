package com.example.fbooking.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fbooking.MainActivity;
import com.example.fbooking.R;

public class ConfirmActivity extends AppCompatActivity {
    private AppCompatButton btnConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        initUi();

        onClickButton();
    }

    private void onClickButton() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmActivity.this, MainActivity.class));
            }
        });
    }

    private void initUi() {
        btnConfirm = findViewById(R.id.btn_confirm);
    }
}