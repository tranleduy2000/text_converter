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

package com.duy.text.converter.pro.floating.codec;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.duy.text.converter.core.codec.interfaces.CodecUtil;

import java.util.ArrayList;

/**
 * Created by Duy on 9/4/2017.
 */

public class CodecMethodAdapter extends PagerAdapter {
    private ArrayList<String> names;

    CodecMethodAdapter(Context context) {
        names = CodecUtil.getAllCodecName(context);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return null;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }
}
