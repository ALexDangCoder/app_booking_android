package com.example.fbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fbooking.accept.WaitToAcceptAcitivity;
import com.example.fbooking.history.HistoryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNv;
    private ViewPager vpFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        if (!isConnected(this)) {
            showInternetDialog();
        }

        setUpViewPager();

        onClickBottomNavigation();
    }

    //Click bottom navigation
    private void onClickBottomNavigation() {
        bottomNv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        vpFrag.setCurrentItem(0);
                        break;

                    case R.id.action_booking:
                        vpFrag.setCurrentItem(1);
                        break;

                    case R.id.action_favorite:
                        vpFrag.setCurrentItem(2);
                        break;

                    case R.id.action_profile:
                        vpFrag.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }

    //Cai dat viewpager
    private void setUpViewPager() {
        ViewPagerApdapter viewPagerAdapter = new ViewPagerApdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpFrag.setAdapter(viewPagerAdapter);

        vpFrag.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNv.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;

                    case 1:
                        bottomNv.getMenu().findItem(R.id.action_booking).setChecked(true);
                        break;

                    case 2:
                        bottomNv.getMenu().findItem(R.id.action_favorite).setChecked(true);
                        break;

                    case 3:
                        bottomNv.getMenu().findItem(R.id.action_profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tool_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.history) {
            Intent i = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(i);
        }

        if (id == R.id.waitToAccept) {
            Intent i = new Intent(MainActivity.this, WaitToAcceptAcitivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    //Anh xa
    private void initUi() {
        bottomNv = findViewById(R.id.bottom_nv);
        vpFrag = findViewById(R.id.vp_frag);
    }

    private void showInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.wifi_dialog, findViewById(R.id.ln_no_internet));
        AppCompatButton btnConfirm = view.findViewById(R.id.btn_confirm_wifi);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private boolean isConnected(MainActivity mainActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConnect != null && wifiConnect.isConnected()) || (mobileConnect != null && mobileConnect.isConnected());
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}