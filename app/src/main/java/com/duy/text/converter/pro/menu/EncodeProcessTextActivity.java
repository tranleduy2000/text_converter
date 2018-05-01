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

package com.duy.text.converter.pro.menu;
//Need the following import to get access to the app resources, since this
//class is in a sub-package.


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.duy.common.utils.DLog;
import com.duy.text.converter.R;
import com.duy.text.converter.core.codec.interfaces.CodecUtil;

@TargetApi(Build.VERSION_CODES.M)
public class EncodeProcessTextActivity extends Activity {
    private static final String TAG = "EncodeProcessTextActivi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DLog.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        boolean readonly = getIntent().getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false);

        if (!readonly && text != null && !text.toString().isEmpty()) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            String method = pref.getString(getString(R.string.pref_key_encode_menu), "");
            if (method.isEmpty()) {
                finish();
                return;
            }
            String result = CodecUtil.encode(method, this, text.toString());
            Intent intent = getIntent();
            intent.putExtra(Intent.EXTRA_PROCESS_TEXT, result);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}