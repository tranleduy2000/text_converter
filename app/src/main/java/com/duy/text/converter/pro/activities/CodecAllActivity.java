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

package com.duy.text.converter.pro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.duy.text.converter.R;
import com.duy.text.converter.activities.base.BaseActivity;
import com.duy.text.converter.pro.menu.fragments.DecodeAllFragment;
import com.duy.text.converter.pro.menu.fragments.EncodeAllFragment;

/**
 * Created by Duy on 11/13/2017.
 */

public class CodecAllActivity extends BaseActivity {

    public static final String EXTRA_INPUT = "EXTRA_INPUT";
    public static final String EXTRA_ACTION_ENCODE = "EXTRA_ACTION_ENCODE";
    public static final String EXTRA_ACTION_DECODE = "EXTRA_ACTION_DECODE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_all);
        setupToolbar();
        String action = getIntent().getAction();

        if (EXTRA_ACTION_ENCODE.equals(action)) {
            String input = getIntent().getStringExtra(EXTRA_INPUT);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, EncodeAllFragment.newInstance(input, false)).commit();

            setTitle(R.string.encode);
            toolbar.setSubtitle(input);
        } else if (EXTRA_ACTION_DECODE.equals(action)) {
            String input = getIntent().getStringExtra(EXTRA_INPUT);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, DecodeAllFragment.newInstance(input, false)).commit();

            setTitle(R.string.decode);
            toolbar.setSubtitle(input);
        } else {
//            finish();
        }
    }


}
