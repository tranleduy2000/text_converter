package com.duy.text_converter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by DUy on 06-Feb-17.
 */

public class PagerSectionAdapter extends FragmentPagerAdapter {
    private static final int COUNT = 2;
    private String init;

    public PagerSectionAdapter(FragmentManager fm, String init) {
        super(fm);
        this.init = init;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ConverterFragment.newInstance(init);
            case 1:
                return StyleListFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Converter";
            case 1:
                return "Style";
            default:
                return "";
        }
    }

}
