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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;

import com.duy.text.converter.R;
import com.duy.text.converter.core.MainActivity;
import com.duy.text.converter.pro.floating.codec.FloatingCodecOpenShortCutActivity;
import com.duy.text.converter.pro.floating.stylish.FloatingStylishOpenShortCutActivity;
import com.duy.text.converter.pro.license.Installation;
import com.duy.text.converter.pro.license.Key;
import com.duy.text.converter.pro.license.PolicyFactory;
import com.duy.text.converter.pro.license.Premium;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivityPro extends MainActivity {
    private static final int REQ_SETTING = 1201;
    private LicenseChecker mChecker;
    private CheckLicenseCallBack mCallBack;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Premium.isCrack(this)) {
            finish();
            return;
        }
        checkLicense();
        setTitle(R.string.app_name);
    }

    private void checkLicense() {
        mHandler = new Handler();
        Policy policy = PolicyFactory.createPolicy(this, getPackageName());
        mChecker = new LicenseChecker(this, policy, Key.BASE_64_PUBLIC_KEY);
        mCallBack = new CheckLicenseCallBack();
        mChecker.checkAccess(mCallBack);
    }

    protected FragmentPagerAdapter getPageAdapter(String initValue) {
        return new PagerSectionAdapterPro(this, getSupportFragmentManager(), initValue);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_upgrade).setVisible(false);
        menu.findItem(R.id.action_open_codec).setVisible(true);
        menu.findItem(R.id.action_open_stylish).setVisible(true);
        menu.findItem(R.id.action_setting).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivityForResult(intent, REQ_SETTING);
        } else if (id == R.id.action_open_stylish) {
            startActivity(new Intent(this, FloatingStylishOpenShortCutActivity.class));
        } else if (id == R.id.action_open_codec) {
            startActivity(new Intent(this, FloatingCodecOpenShortCutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SETTING) {
            if (resultCode == RESULT_OK) {
                recreate();
            }
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
                bundle.putString("device_id", Installation.id(MainActivityPro.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityPro.this);
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
