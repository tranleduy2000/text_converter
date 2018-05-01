/*
 * Copyright (C)  2017-2018 Tran Le Duy
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.duy.text.converter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.duy.text.converter.barcode.BarCodeCodecFragment;
import com.duy.text.converter.fragments.AdsFragment;
import com.duy.text.converter.fragments.CodecFragment;
import com.duy.text.converter.core.stylish.fragments.DecorateFragment;
import com.duy.text.converter.core.stylish.fragments.StylistFragment;


/**
 * Created by DUy on 06-Feb-17.
 */
public class PagerSectionAdapter extends FragmentStatePagerAdapter {
    private static final int COUNT = 5;
    private Context context;
    private String init;

    public PagerSectionAdapter(Context context, FragmentManager fm, String initValue) {
        super(fm);
        this.context = context;
        this.init = initValue;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CodecFragment.newInstance(init);
            case 1:
                return AdsFragment.newInstance();
            case 2:
                return BarCodeCodecFragment.newInstance();
            case 3:
                return StylistFragment.newInstance();
            case 4:
                return DecorateFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.tab_title_codec);
            case 1:
                return context.getString(R.string.tab_title_ads);
            case 2:
                return context.getString(R.string.tab_title_barcode);
            case 3:
                return context.getString(R.string.tab_title_stylish);
            case 4:
                return context.getString(R.string.tab_title_decorate);
        }
        return super.getPageTitle(position);
    }

}
