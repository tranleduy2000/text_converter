/*
 * Copyright (c) 2018 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
