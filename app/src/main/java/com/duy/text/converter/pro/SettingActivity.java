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

import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.duy.common.utils.DLog;
import com.duy.text.converter.R;
import com.duy.text.converter.core.activities.BaseActivity;
import com.duy.text.converter.pro.menu.DecodeAllProcessTextActivity;
import com.duy.text.converter.pro.menu.EncodeAllProcessTextActivity;
import com.duy.text.converter.pro.menu.StylishProcessTextActivity;
import com.duy.text.converter.pro.notification.SettingFragment;
import com.duy.text.converter.pro.notification.StyleNotificationManager;

/**
 * Created by Duy on 26-Jul-17.
 */

public class SettingActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "SettingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle(R.string.action_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, new SettingFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        super.onSharedPreferenceChanged(sharedPreferences, key);
        if (key.equalsIgnoreCase(getString(R.string.pref_key_enable_encode_notification))) {
            StyleNotificationManager.showNotificationEncodeIfNeed(this);
        } else if (key.equalsIgnoreCase(getString(R.string.pref_key_enable_decode_notification))) {
            StyleNotificationManager.showNotificationDecodeIfNeed(this);
        } else if (key.equalsIgnoreCase(getString(R.string.pref_key_theme))) {
        } else if (key.equals(getString(R.string.pref_key_enable_progress_text))) {
            changeModeProcessTextMenu(sharedPreferences, key);
        }
    }

    private void changeModeProcessTextMenu(SharedPreferences pref, String key) {
        if (DLog.DEBUG)
            DLog.d(TAG, "changeModeProcessTextMenu() called with: pref = [" + pref + "], key = [" + key + "]");
        boolean show = pref.getBoolean(key, true);
        final String[] classes = new String[]{
                DecodeAllProcessTextActivity.class.getName(),
                EncodeAllProcessTextActivity.class.getName(),
                StylishProcessTextActivity.class.getName()};
        final PackageManager pm = getApplicationContext().getPackageManager();
        if (show) {
            for (String activityClass : classes) {
                ComponentName compName = new ComponentName(getPackageName(), activityClass);
                pm.setComponentEnabledSetting(
                        compName,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);
            }
        } else {
            for (String activityClass : classes) {
                ComponentName compName = new ComponentName(getPackageName(), activityClass);
                pm.setComponentEnabledSetting(
                        compName,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

}
