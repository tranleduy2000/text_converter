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

package com.duy.text.converter.core.barcode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.duy.common.ads.AdsManager;
import com.duy.text.converter.core.activities.BaseActivity;
import com.duy.text.converter.R;

/**
 * Created by Duy on 14-Aug-17.
 */
public class BarcodeEncodedActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.barcode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String text = getIntent().getStringExtra("data");
        if (text == null) text = "";
        toolbar.setSubtitle(text);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new BarcodeEncodedPagerAdapter(getSupportFragmentManager(), text));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(8); //barcode
        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(viewPager);

        AdsManager.loadBannerAds(this, findViewById(R.id.ads_wrapper), findViewById(R.id.ad_view));
    }



}
