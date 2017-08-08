/*
 * Copyright (c) 2017 by Tran Le Duy
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

package com.duy.text_converter.converter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.duy.sharedcode.fragment.CodecFragment;
import com.duy.sharedcode.fragment.DecorateFragment;
import com.duy.sharedcode.fragment.HashFragment;
import com.duy.sharedcode.fragment.SpecialEffectFragment;
import com.duy.sharedcode.fragment.StylistFragment;


/**
 * Created by DUy on 06-Feb-17.
 */
public class PagerSectionAdapter extends FragmentPagerAdapter {
    private static final int COUNT = 6;
    private Context context;
    private String init;

    public PagerSectionAdapter(Context context, FragmentManager fm, String init) {
        super(fm);
        this.context = context;
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
                return CodecFragment.newInstance(init);
            case 1:
                return AdsFragment.newInstance();
            case 2:
                return StylistFragment.newInstance();
            case 3:
                return DecorateFragment.newInstance();
            case 4:
                return SpecialEffectFragment.newInstance();
            case 5:
                return HashFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.codec);
            case 1:
                return "Ads";
            case 2:
                return "Style";
            case 3:
                return "Decorate";
            case 4:
                return "Special";
            case 5:
                return context.getString(R.string.hash_function);
            default:
                return "";
        }
    }

}
