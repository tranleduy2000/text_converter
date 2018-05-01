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
