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

package com.duy.sharedcode.barcode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.duy.textconverter.sharedcode.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

/**
 * Created by Duy on 14-Aug-17.
 */
public class BarcodeEncodeActivity extends AppCompatActivity {

    private NativeExpressAdView adView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.barcode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String text = getIntent().getStringExtra("data");
        if (text == null) text = "";
        toolbar.setSubtitle(text);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new BarcodeEncodedPagerAdapter(getSupportFragmentManager(), text));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(8); //barcode
        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(viewPager);

        loadAdIfNeed();
    }

    private void loadAdIfNeed() {
        if (isProVersion()) {
            findViewById(R.id.ad_view).setVisibility(View.GONE);
        } else {
            adView = (NativeExpressAdView) findViewById(R.id.ad_view);
            adView.loadAd(new AdRequest.Builder().build());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) adView.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) adView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adView != null) adView.pause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isProVersion() {
        return getPackageName().equals("com.duy.text_converter.pro");
    }
}
