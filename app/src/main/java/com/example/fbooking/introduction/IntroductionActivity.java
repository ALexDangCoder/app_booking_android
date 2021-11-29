package com.example.fbooking.introduction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.fbooking.userloginandsignup.LoginActivity;
import com.example.fbooking.MainActivity;
import com.example.fbooking.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class IntroductionActivity extends AppCompatActivity {
    private ViewPager vpIntroduction;
    private CircleIndicator ciIntroduction;
    private IntroductionAdapter introductionAdapter;
    private AppCompatButton btnStart, btnOpenLogin;
    private List<Introduction> introductionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        initUi();
    }

    private void initUi() {
        vpIntroduction = findViewById(R.id.vp_introduction);
        ciIntroduction = findViewById(R.id.ci_introduction);
        btnOpenLogin = findViewById(R.id.btn_open_login_introduction);
        btnStart = findViewById(R.id.btn_start);

        introductionList = getListIntroduction();
        introductionAdapter = new IntroductionAdapter(this, introductionList);
        vpIntroduction.setAdapter(introductionAdapter);
        ciIntroduction.setViewPager(vpIntroduction);
        introductionAdapter.registerDataSetObserver(ciIntroduction.getDataSetObserver());

        onClickButton();

        runActivityOnceTine();
    }

    private void runActivityOnceTine() {
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String firstTime = preferences.getString("FirstTimeInstall", "");

        if (firstTime.equals("Yes")) {
            Intent intent = new Intent(IntroductionActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }
    }

    private void onClickButton() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroductionActivity.this, MainActivity.class));
            }
        });

        btnOpenLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroductionActivity.this, LoginActivity.class));
            }
        });
    }

    private List<Introduction> getListIntroduction() {
        List<Introduction> list = new ArrayList<>();
        list.add(new Introduction(R.drawable.lobby));
        list.add(new Introduction(R.drawable.pool));
        list.add(new Introduction(R.drawable.restaurant));
        list.add(new Introduction(R.drawable.spa));
        list.add(new Introduction(R.drawable.gym));
        return list;
    }
}