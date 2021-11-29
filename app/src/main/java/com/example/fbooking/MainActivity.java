package com.example.fbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

        return super.onOptionsItemSelected(item);
    }

    //Anh xa
    private void initUi() {
        bottomNv = findViewById(R.id.bottom_nv);
        vpFrag = findViewById(R.id.vp_frag);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}