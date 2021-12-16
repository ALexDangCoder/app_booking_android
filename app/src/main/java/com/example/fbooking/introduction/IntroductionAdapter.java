package com.example.fbooking.introduction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.fbooking.R;

import java.util.List;

public class IntroductionAdapter extends PagerAdapter {
    private Context context;
    private List<Introduction> introductionList;

    public IntroductionAdapter(Context context, List<Introduction> introductionList) {
        this.context = context;
        this.introductionList = introductionList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.introduction_slide, container, false);
        ImageView imageSlide = view.findViewById(R.id.img_introduction);

        Introduction introduction = introductionList.get(position);
        if (introduction != null) {
            Glide.with(context).load(introduction.getImage()).into(imageSlide);
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(introductionList!=null){
            return introductionList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
