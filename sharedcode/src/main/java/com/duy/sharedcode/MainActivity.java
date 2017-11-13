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

package com.duy.sharedcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.duy.common.ShareManager;
import com.duy.common.StoreUtil;
import com.duy.sharedcode.activities.DecodeAllActivity;
import com.duy.sharedcode.fragments.AdsFragment;
import com.duy.sharedcode.utils.Premium;
import com.duy.text.converter.BuildConfig;
import com.duy.text.converter.R;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.kobakei.ratethisapp.RateThisApp;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, DrawerLayout.DrawerListener {
    protected Toolbar mToolbar;
    private CoordinatorLayout mCoordinatorLayout;
    private DrawerLayout mDrawerLayout;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this);
        FirebaseCrash.setCrashCollectionEnabled(!BuildConfig.DEBUG);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_main);
        bindView();
        setupToolbar();
        showDialogRate();

        startActivity(new Intent(this, DecodeAllActivity.class));
    }

    private void showDialogRate() {
        // Monitor launch times and interval from installation
        RateThisApp.onCreate(this);
        // If the criteria is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
    }

    protected void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(toggle);
        mDrawerLayout.addDrawerListener(this);
        toggle.syncState();
    }

    private void bindView() {
        mCoordinatorLayout = findViewById(R.id.container);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        String text = getTextFromAnotherApp();

        ViewPager viewPager = findViewById(R.id.view_pager);
        FragmentPagerAdapter adapter = getPageAdapter(text);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        ((TabLayout) findViewById(R.id.tab)).setupWithViewPager(viewPager);

        //attach listener hide/show keyboard
        KeyBoardEventListener keyBoardEventListener = new KeyBoardEventListener(this);
        mCoordinatorLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyBoardEventListener);
    }

    protected FragmentPagerAdapter getPageAdapter(String initValue) {
        return new PagerSectionAdapter(this, getSupportFragmentManager(), initValue);
    }

    @Nullable
    private String getTextFromAnotherApp() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.equals("text/plain")) {
                String text = intent.getStringExtra(Intent.EXTRA_TEXT);
                FirebaseAnalytics.getInstance(this).logEvent("open_from_another_app", new Bundle());
                return text;
            }
        }
        return null;
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_upgrade).setVisible(true);
        menu.findItem(R.id.action_open_codec).setVisible(false);
        menu.findItem(R.id.action_open_stylish).setVisible(false);
        menu.findItem(R.id.action_setting).setVisible(false);

        if (StoreUtil.isAppInstalled(this, "com.duy.converter")) {
            menu.findItem(R.id.action_download_unit_converter).setVisible(false);
            if (!StoreUtil.isAppInstalled(this, "com.duy.asciiart")) {
                menu.findItem(R.id.action_download_ascii).setVisible(true);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            mFirebaseAnalytics.logEvent("click_share", new Bundle());
            ShareManager.shareApp(this, getPackageName());

        } else if (id == R.id.action_download_ascii) {
            mFirebaseAnalytics.logEvent("click_ascii", new Bundle());
            StoreUtil.gotoPlayStore(this, "com.duy.asciiart");

        } else if (id == R.id.action_download_unit_converter) {
            mFirebaseAnalytics.logEvent("click_ascii", new Bundle());
            StoreUtil.gotoPlayStore(this, "com.duy.converter");

        } else if (id == R.id.action_review) {
            StoreUtil.gotoPlayStore(this, getPackageName());

        } else if (id == R.id.action_more) {
            StoreUtil.moreApp(this);

        } else if (id == R.id.action_upgrade) {
            mFirebaseAnalytics.logEvent("click_upgrade", new Bundle());
            StoreUtil.gotoPlayStore(this, "com.duy.text_converter.pro");
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * hide appbar layout when keyboard visible
     */
    private void hideAppBar() {
        mToolbar.setVisibility(View.GONE);
    }

    /**
     * show appbar layout when keyboard gone
     */
    private void showAppBar() {
        mToolbar.setVisibility(View.VISIBLE);
    }

    protected void onShowKeyboard() {
        hideAppBar();
    }

    protected void onHideKeyboard() {
        showAppBar();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (Premium.isFree(this)) {
            if (position == AdsFragment.INDEX) {
                hideKeyboard();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        hideKeyboard();
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }


    private class KeyBoardEventListener implements ViewTreeObserver.OnGlobalLayoutListener {
        MainActivity activity;

        KeyBoardEventListener(MainActivity activityIde) {
            this.activity = activityIde;
        }

        public void onGlobalLayout() {
            int i = 0;
            int navHeight = this.activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            navHeight = navHeight > 0 ? this.activity.getResources().getDimensionPixelSize(navHeight) : 0;
            int statusBarHeight = this.activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (statusBarHeight > 0) {
                i = this.activity.getResources().getDimensionPixelSize(statusBarHeight);
            }
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            if (activity.mCoordinatorLayout.getRootView().getHeight() - ((navHeight + i) + rect.height()) <= 0) {
                activity.onHideKeyboard();
            } else {
                activity.onShowKeyboard();
            }
        }
    }
}
