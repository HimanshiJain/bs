package com.example.himanshijain.booksquare;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Himanshi Jain on 1/21/2016.
 */
public class TabAdapter extends FragmentPagerAdapter {

    final int page_count=2;
    private String[] tabTitles={"Search by Book","Search by Course"};

    public TabAdapter(android.support.v4.app.FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            // Open FragmentTab1.java
            case 0:
               SearchByBookFragment fragmenttab1 = new SearchByBookFragment();
                return fragmenttab1;

            // Open FragmentTab2.java
            case 1:
                two fragmenttab2 = new two();
                return fragmenttab2;

        }
        return null;

    }

    @Override
    public int getCount() {
        return page_count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}
