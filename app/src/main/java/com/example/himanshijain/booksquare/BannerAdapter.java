package com.example.himanshijain.booksquare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
/**
 * Created by himanshi jain on 21-10-2015.
 */
public class BannerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {


    FragmentManager fm;

    public BannerAdapter(FragmentManager fm) {
        super(fm);
        this.fm=fm;

    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:return (Fragment) new one();
            case 1:return (Fragment) new two();
            case 2:return (Fragment) new three();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
