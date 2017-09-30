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

package com.duy.text.converter.notification;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.duy.text_converter.pro.R;

import static com.duy.text.converter.notification.PrefUtil.bindPreferenceSummaryToValue;

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
        bindPreferenceSummaryToValue(findPreference("pref_encode_style_1"));
        bindPreferenceSummaryToValue(findPreference("pref_encode_style_2"));
        bindPreferenceSummaryToValue(findPreference("pref_encode_style_3"));
        bindPreferenceSummaryToValue(findPreference("pref_encode_style_4"));
        bindPreferenceSummaryToValue(findPreference("pref_encode_style_5"));

        bindPreferenceSummaryToValue(findPreference("pref_decode_style_1"));
        bindPreferenceSummaryToValue(findPreference("pref_decode_style_2"));
        bindPreferenceSummaryToValue(findPreference("pref_decode_style_3"));
        bindPreferenceSummaryToValue(findPreference("pref_decode_style_4"));
        bindPreferenceSummaryToValue(findPreference("pref_decode_style_5"));

        bindPreferenceSummaryToValue(findPreference("pref_key_encode_menu"));
        bindPreferenceSummaryToValue(findPreference("pref_key_decode_menu"));
    }

}
