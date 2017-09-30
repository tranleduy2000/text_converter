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

package com.duy.text.converter;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.duy.sharedcode.BaseActivity;
import com.duy.sharedcode.utils.StoreUtil;
import com.duy.text.converter.floating.codec.FloatingCodecOpenShortCutActivity;
import com.duy.text.converter.floating.stylish.FloatingStylishOpenShortCutActivity;
import com.duy.text.converter.license.Installation;
import com.duy.text.converter.license.Key;
import com.duy.text.converter.license.PolicyFactory;
import com.duy.text.converter.license.Premium;
import com.duy.text.converter.notification.StyleNotificationManager;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.kobakei.ratethisapp.RateThisApp;


public class MainActivity extends BaseActivity {

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private KeyBoardEventListener mKeyBoardListener;
    private ViewPager mViewPager;
    private PagerSectionAdapter mAdapter;
    private LicenseChecker mChecker;
    private CheckLicenseCallBack mCallBack;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        if (BuildConfig.DEBUG) FirebaseCrash.setCrashCollectionEnabled(false);
        if (Premium.isCrack(this)) {
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        checkLicense();
        bindView();

    }

    private void checkLicense() {
        mHandler = new Handler();
        Policy policy = PolicyFactory.createPolicy(this, getPackageName());
        mChecker = new LicenseChecker(this, policy, Key.BASE_64_PUBLIC_KEY);
        mCallBack = new CheckLicenseCallBack();
        mChecker.checkAccess(mCallBack);
    }

    private void bindView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        String text = null;
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.equals("text/plain")) {
                text = intent.getStringExtra(Intent.EXTRA_TEXT);
            }
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new PagerSectionAdapter(this, getSupportFragmentManager(), text);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(mViewPager);

        //attach listener hide/show keyboard
        mKeyBoardListener = new KeyBoardEventListener(this);
        mCoordinatorLayout.getViewTreeObserver().addOnGlobalLayoutListener(mKeyBoardListener);
    }

    private void showNotification() {
        StyleNotificationManager.showNotificationDecodeIfNeed(this);
        StyleNotificationManager.showNotificationEncodeIfNeed(this);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                shareApp();
                break;
            case R.id.action_get_ascii:
                gotoPlayStore("com.duy.asciiart");
                break;
            case R.id.action_review:
                StoreUtil.gotoPlayStore(this, BuildConfig.APPLICATION_ID);
                break;
            case R.id.action_more:
                StoreUtil.moreApp(this);
                break;
            case R.id.action_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.action_open_stylish:
                startActivity(new Intent(this, FloatingStylishOpenShortCutActivity.class));
                finish();
                break;
            case R.id.action_open_codec:
                startActivity(new Intent(this, FloatingCodecOpenShortCutActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id="
                + BuildConfig.APPLICATION_ID);
        intent.setType("text/plain");
        startActivity(intent);
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
    protected void onStart() {
        super.onStart();
        // Monitor launch times and interval from installation
        RateThisApp.onStart(this);
        // If the criteria is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
        RateThisApp.setCallback(new RateThisApp.Callback() {
            @Override
            public void onYesClicked() {
                gotoPlayStore(BuildConfig.APPLICATION_ID);
            }

            @Override
            public void onNoClicked() {
            }

            @Override
            public void onCancelClicked() {
            }
        });

    }

    public void gotoPlayStore(String APP_ID) {
        Uri uri = Uri.parse("market://details?id=" + APP_ID);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |

                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + APP_ID)));
        }
    }

    private void showDialogCrack() {
        FirebaseAnalytics.getInstance(this).logEvent("crack_version", new Bundle());
        Premium.PREMIUM = false;
        Premium.setCrack(this, true);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("device_id", Installation.id(MainActivity.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.pirated);
                builder.setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        finish();
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
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

    private class CheckLicenseCallBack implements LicenseCheckerCallback {

        private static final String TAG = "CheckLicenseCallBack";

        @Override
        public void allow(int reason) {
        }

        @Override
        public void dontAllow(int reason) {
            if (isFinishing()) {
                return;
            }
            if (reason == Policy.NOT_LICENSED) {
                showDialogCrack();
            }
        }

        @Override
        public void applicationError(int errorCode) {
        }
    }
}
