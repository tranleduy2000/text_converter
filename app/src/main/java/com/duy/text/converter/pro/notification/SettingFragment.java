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

package com.duy.text.converter.pro.notification;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.duy.text.converter.R;
import com.duy.text.converter.pro.floating.codec.FloatingCodecOpenShortCutActivity;
import com.duy.text.converter.pro.floating.stylish.FloatingStylishOpenShortCutActivity;
import com.duy.text.converter.pro.license.Premium;

import static com.duy.common.preferences.PreferencesUtil.bindPreferenceSummaryToValue;

/**
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 */
public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences
        // to their values. When their values change, their summaries are
        // updated to reflect the new value, per the Android Design
        // guidelines.
        bindPreferenceSummaryToValue(findPreference("pref_key_encode_menu"));

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_theme)));
        addEvent();
        disableView();
    }

    private void disableView() {
        if (!Premium.isPremium(getActivity())) {
            findPreference(getString(R.string.pref_key_theme)).setEnabled(false);
            findPreference(getString(R.string.pref_key_category_floating_window)).setEnabled(false);
        }
    }

    private void addEvent() {
        findPreference("open_floating_codec").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), FloatingCodecOpenShortCutActivity.class));
                return false;
            }
        });

        findPreference("open_floating_stylish").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), FloatingStylishOpenShortCutActivity.class));
                return false;
            }
        });
    }

}
