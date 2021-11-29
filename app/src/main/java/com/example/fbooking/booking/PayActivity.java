package com.example.fbooking.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fbooking.R;

public class PayActivity extends AppCompatActivity {
    private TextView tvDepositPay, tvPricePay;
    private AppCompatButton btnCancelPay, btnConfirmPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        initUi();

        onClickButton();
    }

    private void onClickButton() {
        btnCancelPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PayActivity.this, CheckAgainActivity.class));
            }
        });

        btnConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PayActivity.this, ConfirmActivity.class));
            }
        });
    }

    private void initUi() {
        tvDepositPay = findViewById(R.id.tv_deposit_pay);
        tvPricePay = findViewById(R.id.tv_price_pay);

        btnCancelPay = findViewById(R.id.btn_cancel_pay);
        btnConfirmPay = findViewById(R.id.btn_open_confirm_pay);
    }
}