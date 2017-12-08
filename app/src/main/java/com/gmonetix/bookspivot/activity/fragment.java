package com.gmonetix.bookspivot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.gmonetix.bookspivot.R;
import com.gmonetix.bookspivot.SecondFragment;
import com.gmonetix.bookspivot.ThirdFragment;
import com.gmonetix.bookspivot.firstfragment;

public class fragment extends AppCompatActivity {
    int a;
    int b;
    // ...
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ViewPager vpPager = findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);


    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return firstfragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show SecondFragment
                    return SecondFragment.newInstance(1, "Page # 2");
                case 2: // Fragment # 0 - This will show ThirdFragment
                    return ThirdFragment.newInstance(2, "Page # 3");

                default:
                    return null;
            }
        }


        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {

                return "Books"                 ;
            } if (position == 1) {
                return "Credit";
            }
            else {
                return "Info";
            }


        }}


}