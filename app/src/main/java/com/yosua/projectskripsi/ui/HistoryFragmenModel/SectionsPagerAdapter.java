package com.yosua.projectskripsi.ui.HistoryFragmenModel;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yosua.projectskripsi.Fragmen.HistoryMakanan;
import com.yosua.projectskripsi.Fragmen.HistoryOlahraga;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
            Fragment fragment = new Fragment();
            switch (position){
                case 0 :
                    fragment = new HistoryMakanan();
                    return fragment;

                case 1 :
                    fragment = new HistoryOlahraga();
                    return fragment;
            }
            return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       switch (position){
           case 0 :
               return "Makanan";

           case 1:
               return "Olahraga";

       } return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}