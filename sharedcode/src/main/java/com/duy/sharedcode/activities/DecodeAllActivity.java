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

package com.duy.sharedcode.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.duy.common.services.ads.AdsManager;
import com.duy.sharedcode.BaseActivity;
import com.duy.sharedcode.fragments.DecodeAllFragment;
import com.duy.text.converter.R;

/**
 * Created by Duy on 11/13/2017.
 */

public class DecodeAllActivity extends BaseActivity {

    private static final String EXTRA_INPUT = "extra_text";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_all);

        String input = getIntent().getStringExtra(EXTRA_INPUT);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, DecodeAllFragment.newInstance(input)).commit();

        AdsManager.loadBannerAds(this, findViewById(R.id.ads_wrapper), findViewById(R.id.ad_view));
    }
}
