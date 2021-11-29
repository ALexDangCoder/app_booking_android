package com.example.fbooking;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fbooking.booking.BookingFragment;
import com.example.fbooking.favorite.FavoriteFragment;
import com.example.fbooking.home.HomeFragment;
import com.example.fbooking.profile.ProfileFragment;

public class ViewPagerApdapter extends FragmentStatePagerAdapter {
    public ViewPagerApdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();

            case 1:
                return new BookingFragment();

            case 2:
                return new FavoriteFragment();

            case 3:
                return new ProfileFragment();

            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
