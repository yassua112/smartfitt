package com.yosua.projectskripsi.ui.Notification;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yosua.projectskripsi.Fragmen.notifOlahraga;
import com.yosua.projectskripsi.Fragmen.notivMakanan;
import com.yosua.projectskripsi.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
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
                fragment = new notivMakanan();
                return fragment;

            case 1 :
                fragment = new notifOlahraga();
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