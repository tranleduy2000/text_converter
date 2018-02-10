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

package com.duy.text.converter.core.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.duy.common.purchase.InAppPurchaseHelper;
import com.duy.common.utils.DLog;
import com.duy.common.utils.ShareUtil;
import com.duy.common.utils.StoreUtil;
import com.duy.text.converter.R;
import com.duy.text.converter.core.PagerSectionAdapter;
import com.duy.text.converter.core.fragments.AdsFragment;
import com.duy.text.converter.core.help.HelpDialog;
import com.duy.text.converter.pro.PagerSectionAdapterPro;
import com.duy.text.converter.pro.SettingActivity;
import com.duy.text.converter.pro.floating.codec.FloatingCodecOpenShortCutActivity;
import com.duy.text.converter.pro.floating.stylish.FloatingStylishOpenShortCutActivity;
import com.duy.text.converter.pro.license.Key;
import com.duy.text.converter.pro.license.PolicyFactory;
import com.duy.text.converter.pro.license.Premium;
import com.google.android.gms.ads.MobileAds;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kobakei.ratethisapp.RateThisApp;
import com.ogaclejapan.smarttablayout.SmartTabLayout;


public class MainActivity extends InAppPurchaseActivityImpl implements ViewPager.OnPageChangeListener {
    private static final int REQ_CODE_SETTING = 1201;
    private static final String TAG = "MainActivity";
    protected Toolbar mToolbar;
    private LicenseChecker mChecker;
    private CheckLicenseCallBack mCallBack;
    private CoordinatorLayout mCoordinatorLayout;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Premium.isPremium(this)) {
            MobileAds.initialize(this);
        }
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_main);
        bindView();
        setupToolbar();
        showDialogRate();
        checkLicense();
    }

    private void checkLicense() {
        mHandler = new Handler();
        Policy policy = PolicyFactory.createPolicy(this, getPackageName());
        mChecker = new LicenseChecker(this, policy, Key.BASE_64_PUBLIC_KEY);
        mCallBack = new CheckLicenseCallBack();
        mChecker.checkAccess(mCallBack);
    }

    private void showDialogRate() {
        // Monitor launch times and interval from installation
        RateThisApp.onCreate(this);
        // If the criteria is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
    }

    private void handleCracked() {
        FirebaseAnalytics.getInstance(this).logEvent("crack_version", new Bundle());
        Premium.setCracked(this, true);
        if (getPackageName().equals(Premium.PRO_PACKAGE)) {
            Toast.makeText(this, "Licence check failed", Toast.LENGTH_LONG).show();
        }
    }

    protected void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void bindView() {
        mCoordinatorLayout = findViewById(R.id.container);
        String text = getTextFromAnotherApp();

        ViewPager viewPager = findViewById(R.id.view_pager);
        FragmentPagerAdapter adapter = getPageAdapter(text);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        ((SmartTabLayout) findViewById(R.id.tab_layout)).setViewPager(viewPager);

        //attach listener hide/show keyboard
        KeyBoardEventListener keyBoardEventListener = new KeyBoardEventListener(this);
        mCoordinatorLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyBoardEventListener);
    }

    protected FragmentPagerAdapter getPageAdapter(String initValue) {
        if (Premium.isPremium(this)) {
            return new PagerSectionAdapterPro(this, getSupportFragmentManager(), initValue);
        } else {
            return new PagerSectionAdapter(this, getSupportFragmentManager(), initValue);
        }
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
        if (Premium.isPremium(this)) {
            menu.findItem(R.id.action_upgrade).setVisible(false);
            menu.findItem(R.id.action_open_codec).setVisible(true);
            menu.findItem(R.id.action_open_stylish).setVisible(true);
        } else {
            menu.findItem(R.id.action_upgrade).setVisible(true);
            menu.findItem(R.id.action_open_codec).setVisible(false);
            menu.findItem(R.id.action_open_stylish).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mFirebaseAnalytics.logEvent(item.getTitle().toString(), new Bundle());
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                ShareUtil.shareThisApp(this);
                break;
            case R.id.action_review:
                StoreUtil.gotoPlayStore(this, getPackageName());
                break;
            case R.id.action_upgrade:
                Premium.upgrade(this);
                break;
            case R.id.action_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivityForResult(intent, REQ_CODE_SETTING);
                break;
            case R.id.action_open_stylish:
                startActivity(new Intent(this, FloatingStylishOpenShortCutActivity.class));
                break;
            case R.id.action_open_codec:
                startActivity(new Intent(this, FloatingCodecOpenShortCutActivity.class));
                break;
            case R.id.action_how_to_use:
                HelpDialog helpDialog = new HelpDialog();
                helpDialog.show(getSupportFragmentManager(), HelpDialog.TAG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DLog.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SETTING:
                if (resultCode == RESULT_OK) {
                    recreate();
                }
                break;
            case InAppPurchaseHelper.RC_REQUEST_UPGRADE:
                if (resultCode == RESULT_OK){
                    recreate();
                }
                break;
        }
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

    private static class KeyBoardEventListener implements ViewTreeObserver.OnGlobalLayoutListener {
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

    private class CheckLicenseCallBack implements LicenseCheckerCallback {

        @Override
        public void allow(int reason) {
        }

        @Override
        public void dontAllow(int reason) {
            if (isFinishing()) {
                return;
            }
            if (reason == Policy.NOT_LICENSED) {
                handleCracked();
            }
        }

        @Override
        public void applicationError(int errorCode) {
        }
    }
}
