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

package com.duy.text_converter.pro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.RemoteViews;

import com.duy.sharedcode.StoreUtil;
import com.duy.text_converter.pro.notification.StyleNotification;
import com.google.firebase.crash.FirebaseCrash;
import com.kobakei.ratethisapp.RateThisApp;


public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private KeyBoardEventListener keyBoardListener;
    private ViewPager viewPager;
    private PagerSectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            FirebaseCrash.setCrashCollectionEnabled(false);
        }

        setContentView(R.layout.activity_main);

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

        showNotification();
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);

        Intent intent1 = new Intent();
        intent1.setAction(StyleNotification.ACTION_STYLE_1);
        PendingIntent pendingIntentStyle1 = PendingIntent.getBroadcast(this, 12345, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_one, pendingIntentStyle1);

        Intent intent2 = new Intent();
        intent2.setAction(StyleNotification.ACTION_STYLE_2);
        PendingIntent pendingIntentStyle2 = PendingIntent.getBroadcast(this, 12345, intent2,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_two, pendingIntentStyle2);

        Intent intent3 = new Intent();
        intent3.setAction(StyleNotification.ACTION_STYLE_3);
        PendingIntent pendingIntentStyle3 = PendingIntent.getBroadcast(this, 12345, intent3,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_three, pendingIntentStyle3);

        Intent intent4 = new Intent();
        intent4.setAction(StyleNotification.ACTION_STYLE_4);
        PendingIntent pendingIntentStyle4 = PendingIntent.getBroadcast(this, 12345, intent4,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_four, pendingIntentStyle4);

        builder.setContent(remoteViews);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
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
}
