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

package com.duy.text.converter.pro;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.duy.sharedcode.StoreUtil;
import com.duy.text.converter.pro.license.Installation;
import com.duy.text.converter.pro.license.Key;
import com.duy.text.converter.pro.license.PolicyFactory;
import com.duy.text.converter.pro.license.Premium;
import com.duy.text.converter.pro.notification.StyleNotificationManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.firebase.crash.FirebaseCrash;
import com.kobakei.ratethisapp.RateThisApp;

import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private KeyBoardEventListener keyBoardListener;
    private ViewPager viewPager;
    private PagerSectionAdapter adapter;
    private LicenseChecker mChecker;
    private CheckLicenseCallBack mCallBack;
    private Handler mHandler;
    @Nullable
    private InterstitialAd interstitialAd = null;
    private AtomicBoolean canShowAds = new AtomicBoolean(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        if (BuildConfig.DEBUG) FirebaseCrash.setCrashCollectionEnabled(false);
        setContentView(R.layout.activity_main);
        checkLicense();
        bindView();
        showNotification();
    }

    private void checkLicense() {
        mHandler = new Handler();
        mChecker = new LicenseChecker(this, PolicyFactory.createPolicy(this, getPackageName()),
                Key.BASE_64_PUBLIC_KEY);
        mCallBack = new CheckLicenseCallBack();
        mChecker.checkAccess(mCallBack);
    }

    private void bindView() {
        this.coordinatorLayout = (CoordinatorLayout) findViewById(R.id.container);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        String text = null;
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.equals("text/plain")) {
                text = intent.getStringExtra(Intent.EXTRA_TEXT);
            }
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new PagerSectionAdapter(getSupportFragmentManager(), text);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        //attach listener hide/show keyboard
        keyBoardListener = new KeyBoardEventListener(this);
        coordinatorLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyBoardListener);

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
        inflater.inflate(R.menu.main, menu);
        menu.findItem(R.id.action_setting).setVisible(true);
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
        toolbar.setVisibility(View.GONE);
    }

    /**
     * show appbar layout when keyboard gone
     */
    private void showAppBar() {
        toolbar.setVisibility(View.VISIBLE);
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
        Log.d(TAG, "showDialogCrack() called");
        Premium.PREMIUM = false;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                initializerAds();
                Bundle bundle = new Bundle();
                bundle.putString("device_id", Installation.id(MainActivity.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.pirated);
                builder.setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void initializerAds() {
        canShowAds.set(true);
        MobileAds.initialize(MainActivity.this);
        interstitialAd = new InterstitialAd(MainActivity.this);
        if (BuildConfig.DEBUG) {
            interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        } else {
            interstitialAd.setAdUnitId("ca-app-pub-9351804859208340/5317328332");
        }
        interstitialAd.loadAd(new AdRequest.Builder().build());
        Runnable showAds = new Runnable() {
            @Override
            public void run() {
                while (canShowAds.get()) {
                    Log.d(TAG, "run() called");

                    try {
                        Thread.sleep(20 * 1000); //20s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (interstitialAd != null && !isFinishing() && !Premium.PREMIUM) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        });
                    }
                }
            }
        };
        new Thread(showAds).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");

        canShowAds.set(false);
        interstitialAd = null;
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
            if (activity.coordinatorLayout.getRootView().getHeight() - ((navHeight + i) + rect.height()) <= 0) {
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
