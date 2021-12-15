package com.example.fbooking.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import com.example.fbooking.R;

public class HowToUseActivity extends AppCompatActivity {
    private AppCompatButton btnCancelUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);

        initUi();

        onClickButton();
    }

    private void onClickButton() {
        btnCancelUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initUi() {
        btnCancelUse = findViewById(R.id.btn_cancel_use);
    }
}