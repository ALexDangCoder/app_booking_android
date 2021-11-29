package com.example.fbooking.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fbooking.R;
import com.example.fbooking.introduction.IntroductionActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;

    Animation topAnim, bottomAnim;
    ImageView logoSplashScreen;
    TextView tvSplashScreen, tvSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initUi();

        //Xet animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logoSplashScreen.setAnimation(topAnim);
        tvSplashScreen.setAnimation(bottomAnim);
        tvSlogan.setAnimation(bottomAnim);

        //Delay activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, IntroductionActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }

    private void initUi() {
        logoSplashScreen = findViewById(R.id.logo_splash_screen);
        tvSplashScreen = findViewById(R.id.tv_splash_screen);
        tvSlogan = findViewById(R.id.tv_slogan_splash_screen);
    }
}