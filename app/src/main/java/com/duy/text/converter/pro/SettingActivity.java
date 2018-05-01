/*
 * Copyright (C)  2017-2018 Tran Le Duy
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import com.duy.text.converter.activities.base.BaseActivity;
import com.duy.text.converter.pro.menu.DecodeAllProcessTextActivity;
import com.duy.text.converter.pro.menu.EncodeAllProcessTextActivity;
import com.duy.text.converter.pro.menu.StylishProcessTextActivity;
import com.duy.text.converter.pro.notification.SettingFragment;

import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DISABLED;

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
        if (key.equalsIgnoreCase(getString(R.string.pref_key_theme))) {
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
        for (String activityClass : classes) {
            try {
                ComponentName compName = new ComponentName(getPackageName(), activityClass);
                pm.setComponentEnabledSetting(
                        compName,
                        show ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
            } catch (Exception e) {
                e.printStackTrace();
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
