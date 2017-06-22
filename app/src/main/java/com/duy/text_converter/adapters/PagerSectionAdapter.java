package com.duy.text_converter.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.duy.text_converter.fragment.AdsFragment;
import com.duy.text_converter.fragment.ConverterFragment;
import com.duy.text_converter.fragment.DecorateFragment;
import com.duy.text_converter.fragment.StylistFragment;

/**
 * Created by DUy on 06-Feb-17.
 */

public class PagerSectionAdapter extends FragmentPagerAdapter {
    private static final int COUNT = 4;
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
                return StylistFragment.newInstance();
            case 2:
                return AdsFragment.newInstance();
            case 3:
                return DecorateFragment.newInstance();
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
            case 2:
                return "Ads";
            case 4:
                return "Decorate";
            default:
                return "";
        }
    }

}
